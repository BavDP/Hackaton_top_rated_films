package com.example.hackaton_15_02_24_top_rated_films

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hackaton_15_02_24_top_rated_films.databinding.ActivityMainBinding
import com.example.hackaton_15_02_24_top_rated_films.fragments.MovieListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainerView, MovieListFragment.newInstance())
            .commit()
    }
}