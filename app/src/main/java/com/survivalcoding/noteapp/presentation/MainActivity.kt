package com.survivalcoding.noteapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.noteapp.Config.Companion.dualPane
import com.survivalcoding.noteapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dualPane = binding.listFragmentContainer != null
    }
}