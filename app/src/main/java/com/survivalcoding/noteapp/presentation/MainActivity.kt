package com.survivalcoding.noteapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_MAIN
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ActivityMainBinding
import com.survivalcoding.noteapp.presentation.fragment.EditFragment
import com.survivalcoding.noteapp.presentation.fragment.MainFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, MainFragment())
        fragmentTransaction.commit()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    val transaction = supportFragmentManager.beginTransaction()
                    if (state.fragmentCode == FRAGMENT_CODE_MAIN) {
                        binding.fab.setImageResource(R.drawable.ic_add)
                    } else {
                        transaction.replace(R.id.fragment_container, EditFragment())
                        transaction.addToBackStack(null)
                        binding.fab.setImageResource(R.drawable.ic_save)
                    }
                    transaction.commit()
                }
            }
        }

        binding.fab.setOnClickListener {
            viewModel.changeFragmentCode()
        }
    }
}