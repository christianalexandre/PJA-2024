package com.example.todo

import android.app.FragmentTransaction
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.bottomNavigationView?.setOnItemSelectedListener { item ->

            when(item.itemId) {

                R.id.home -> {
                    replaceFragment(HomeFragment())
                }

                R.id.archived -> {
                    replaceFragment(ArchivedFragment())
                }

                R.id.add -> {
                    replaceFragment(AddFragment())
                }

            }

            true

        }

    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()

    }
}