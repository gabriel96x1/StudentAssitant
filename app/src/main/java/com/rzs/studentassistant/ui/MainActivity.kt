package com.rzs.studentassistant.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rzs.studentassistant.R
import com.rzs.studentassistant.ui.fragment.HomeworkFragment
import com.rzs.studentassistant.ui.fragment.PomodoroFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar!!.hide()

        bottomNavigation = findViewById(R.id.bottomNavigationView)

        val homeworkFragment = HomeworkFragment()
        val pomodoroFragment = PomodoroFragment()


        bottomNavigation.selectedItemId = R.id.pomodoro
        makeCurrentFragment(pomodoroFragment)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pomodoro -> makeCurrentFragment(pomodoroFragment)
                R.id.homework -> makeCurrentFragment(homeworkFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_menu_fragment_wrapper, fragment)
            .commit()
    }
}