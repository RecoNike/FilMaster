package com.recon.filmaster

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragment: Fragment
    lateinit var searchButton: ImageButton
    lateinit var topMoviesButton: ImageButton
    lateinit var favoriteButton: ImageButton
    lateinit var matchButton: ImageButton
    lateinit var settingsButton: ImageButton
    lateinit var aboutButton: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        searchButton = findViewById(R.id.searchButton)
        topMoviesButton = findViewById(R.id.topMoviesButton)
        favoriteButton = findViewById(R.id.favoriteButton)
        matchButton = findViewById(R.id.matchButton)
        settingsButton = findViewById(R.id.settingsButton)
        aboutButton = findViewById(R.id.aboutButton)

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()

        // Замените PlaceholderFragment на фрагмент, который вы хотите загрузить
        fragment = TopMoviesFragment()

        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()

        searchButton.setOnClickListener{
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_icon_on_click_anim)
            searchButton.startAnimation(animation)
            val newFragmentTransaction = fragmentManager.beginTransaction()
            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, SearchFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }
        topMoviesButton.setOnClickListener{
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_icon_on_click_anim)
            topMoviesButton.startAnimation(animation)
            val newFragmentTransaction = fragmentManager.beginTransaction()
            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, TopMoviesFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }
        favoriteButton.setOnClickListener{
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_icon_on_click_anim)
            favoriteButton.startAnimation(animation)
            val newFragmentTransaction = fragmentManager.beginTransaction()
            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, FavoriteFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }
        matchButton.setOnClickListener{
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_icon_on_click_anim)
            matchButton.startAnimation(animation)
            val newFragmentTransaction = fragmentManager.beginTransaction()
            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, MatchFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }
        settingsButton.setOnClickListener{
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_icon_on_click_anim)
            settingsButton.startAnimation(animation)
            val newFragmentTransaction = fragmentManager.beginTransaction()
            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, SettingsFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }
        aboutButton.setOnClickListener{
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_icon_on_click_anim)
            aboutButton.startAnimation(animation)
            val newFragmentTransaction = fragmentManager.beginTransaction()
            fragment = SearchFragment()
            newFragmentTransaction.replace(R.id.fragmentContainer, AboutFragment())
            newFragmentTransaction.addToBackStack(null)
            newFragmentTransaction.commit()
        }

    }
}