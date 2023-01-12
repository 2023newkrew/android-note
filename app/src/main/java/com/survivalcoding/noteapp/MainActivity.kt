package com.survivalcoding.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.window.layout.WindowMetricsCalculator
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.presentation.notes.NotesFragment

enum class WindowSizeClass { COMPACT, MEDIUM, EXPANDED }

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        computeWindowSizeClasses()
    }

    private fun computeWindowSizeClasses() {
        val metrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)

        val widthDp = metrics.bounds.width() /
                resources.displayMetrics.density
        val widthWindowSizeClass = when {
            widthDp < 600f -> WindowSizeClass.COMPACT
            widthDp < 840f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }

        val heightDp = metrics.bounds.height() /
                resources.displayMetrics.density
        val heightWindowSizeClass = when {
            heightDp < 480f -> WindowSizeClass.COMPACT
            heightDp < 900f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }

        // Use widthWindowSizeClass and heightWindowSizeClass.
        when (widthWindowSizeClass) {
            WindowSizeClass.COMPACT -> {
                supportFragmentManager.commit {
                    replace<NotesFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                }
            }
            else -> {
            }
        }
    }
}