package com.example.musical.ui.recorder

import android.Manifest
import android.media.AudioRecord
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musical.databinding.FragmentRecorderBinding
import com.example.musical.ui.guitar.RecorderViewModel
import org.tensorflow.lite.support.audio.TensorAudio
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import org.tensorflow.lite.task.audio.classifier.Classifications
import java.util.Timer
import java.util.TimerTask

open class RecorderFragment : Fragment() {

    private var _binding: FragmentRecorderBinding? = null
    protected lateinit var start: Button
    protected lateinit var stop: Button
    protected lateinit var outputView:TextView
    protected lateinit var specsView:TextView

    private lateinit var audioRecord: AudioRecord
    private lateinit var timerTask: TimerTask
    private lateinit var audioClassifier: AudioClassifier
    private lateinit var tensorAudio: TensorAudio

    private val binding get() = _binding!!
    private val model = "yamnet_classification.tflite"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pianoViewModel =
            ViewModelProvider(this).get(RecorderViewModel::class.java)

        _binding = FragmentRecorderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        outputView = binding.Output
        specsView = binding.Specs
        start = binding.start
        stop = binding.stop

        stop.isEnabled = false
        if(checkSelfPermission(requireContext(),Manifest.permission.RECORD_AUDIO)
            != PermissionChecker.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), 0)

        }

//        try {
//            audioClassifier = AudioClassifier.createFromFile(requireContext(),model)
//        } catch (e: IOException){
//            e.printStackTrace()
//        }

        audioClassifier = AudioClassifier.createFromFile(requireContext(),model)
        tensorAudio = audioClassifier.createInputTensorAudio()

        val start = binding.start
        val stop = binding.stop

        start.setOnClickListener {
            startRecording(root)
        }
        stop.setOnClickListener {
            stopRecording(root)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startRecording(view: View){
        start.isEnabled = false
        stop.isEnabled = true

        val format = audioClassifier.requiredTensorAudioFormat
        val specs = "No of channels" + format.channels + "\n" + "Sample Rate" + format.sampleRate
        specsView.text = specs

        audioRecord = audioClassifier.createAudioRecord()
        audioRecord.startRecording()

        timerTask= object : TimerTask() {
            override fun run() {
                val output = audioClassifier.classify(tensorAudio)
                val finalOutput:MutableList<Category> = arrayListOf()

                for (classifications: Classifications in output){
                    for (category: Category in classifications.categories){
                        if (category.score > 0.3f){
                            finalOutput.add(category)
                        }
                    }
                }

                val outputStr = StringBuilder()
                for (category in finalOutput){
                    outputStr.append(category.label).append(": ").append(category.score).append("\n")
                }

                Thread(object : Runnable {
                    override fun run() {
                        outputView.text = outputStr.toString()
                    }

                }

                )
            }

        }

        Timer().schedule(timerTask,1,500)
    }
    fun stopRecording(view: View){
        start.isEnabled = true
        stop.isEnabled = false

        timerTask.cancel()
        audioRecord.stop()
    }

}