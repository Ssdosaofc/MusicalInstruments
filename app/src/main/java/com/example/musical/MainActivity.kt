package com.example.musical

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musical.databinding.ActivityMainBinding
import com.example.musical.ui.guitar.GuitarFragment
import com.example.musical.ui.harmonium.HarmoniumFragment
import com.example.musical.ui.keyboard.KeyboardFragment
import com.example.musical.ui.piano.PianoFragment
import com.example.musical.ui.tabla.TablaFragment
import com.example.musical.ui.violin.ViolinFragment
import com.example.musical.ui.vocals.VocalsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_Guitar, R.id.nav_Keyboard, R.id.nav_Piano, R.id.nav_Violin, R.id.nav_Tabla, R.id.nav_Harmonium, R.id.nav_Vocals
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (intent.hasExtra("fragment")){
            val fragmentToOpen = intent.getStringExtra("fragment")
            supportFragmentManager.popBackStack()
            if(fragmentToOpen == "guitar"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                navController.navigate(R.id.nav_Guitar)
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, GuitarFragment())
                fragmentTransaction.commit()
            }
            if(fragmentToOpen == "keyboard"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                navController.navigate(R.id.nav_Keyboard)
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,KeyboardFragment())
                fragmentTransaction.commit()
            }
            if(fragmentToOpen == "piano"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                navController.navigate(R.id.nav_Piano)
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, PianoFragment())
                fragmentTransaction.commit()
            }
            if(fragmentToOpen == "violin"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                navController.navigate(R.id.nav_Violin)
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,ViolinFragment())
                fragmentTransaction.commit()
            }
            if(fragmentToOpen == "tabla"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                navController.navigate(R.id.nav_Tabla)
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,TablaFragment())
                fragmentTransaction.commit()
            }
            if(fragmentToOpen == "harmonium"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                navController.navigate(R.id.nav_Harmonium)
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, HarmoniumFragment())
                fragmentTransaction.commit()
            }
            if(fragmentToOpen == "vocals"){
                val fragmentTransaction = supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,VocalsFragment())
                fragmentTransaction.commit()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}