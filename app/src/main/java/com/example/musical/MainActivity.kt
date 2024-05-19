package com.example.musical

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musical.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

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

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val header = navView.getHeaderView(0)
        val email = header.findViewById<TextView>(R.id.emailView)

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //finish()
        } else {
            email.text = user.email
        }

        if (intent.hasExtra("fragment")){
            val fragmentToOpen = intent.getStringExtra("fragment")
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            when (fragmentToOpen) {
                "guitar" -> navController.navigate(R.id.nav_Guitar)
                "keyboard" -> navController.navigate(R.id.nav_Keyboard)
                "piano" -> navController.navigate(R.id.nav_Piano)
                "violin" -> navController.navigate(R.id.nav_Violin)
                "tabla" -> navController.navigate(R.id.nav_Tabla)
                "harmonium" -> navController.navigate(R.id.nav_Harmonium)
                "vocals" -> navController.navigate(R.id.nav_Vocals)
            }
            fragmentTransaction.commit()
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