package com.example.misandroid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.misandroid.database.LbdDatabase
import com.example.misandroid.database.RemoteDatabase
import com.example.misandroid.database.UserEntity
import com.example.misandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var PREFS_KEY = "prefs"

    companion object {
        lateinit var database: LbdDatabase
        lateinit var currentUser: UserEntity
        lateinit var sharedPreferences: SharedPreferences
        var USER_KEY = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread {
            database = Room.databaseBuilder(
                            applicationContext,
                            LbdDatabase::class.java,
                            "lbd.db"
                        ).build()
            RemoteDatabase(database.userDao, database.measurementDao, database.strengthDao, database.userSignalDao)

        }.start()

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar: Toolbar = findViewById(R.id.nav_toolbar)
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_signal_map, R.id.navigation_signals
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
            navView.isVisible = appBarConfiguration.topLevelDestinations.contains(destination.id)
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if(!sharedPreferences.contains(key)){
                navController.navigate(R.id.navigation_identification)
                toolbar.isVisible = false;
            }
            else {
                navController.navigate(R.id.navigation_home)
                toolbar.isVisible = true;
            }
        }

        if(!sharedPreferences.contains(USER_KEY)){
            navController.navigate(R.id.navigation_identification)
            toolbar.isVisible = false;
        }
        else {
            navController.navigate(R.id.navigation_home)
            toolbar.isVisible = true;
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var mi = menuInflater
        mi.inflate(R.menu.appbar_nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            val edit = sharedPreferences.edit()
            edit.remove(USER_KEY)
            edit.apply()
        }
        return super.onOptionsItemSelected(item)
    }
}