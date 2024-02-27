package com.recon.filmaster

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchButton: ImageButton = findViewById(R.id.searchButton)

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()

        // Замените PlaceholderFragment на фрагмент, который вы хотите загрузить
        fragment = TopMoviesFragment()

        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()

        searchButton.setOnClickListener{

            val newFragmentTransaction = fragmentManager.beginTransaction()

            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, SearchFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }


    }
}