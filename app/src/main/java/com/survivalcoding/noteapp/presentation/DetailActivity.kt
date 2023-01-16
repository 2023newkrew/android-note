package com.survivalcoding.noteapp.presentation

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.noteapp.Config.Companion.EXTRA_KEY_FRAGMENT
import com.survivalcoding.noteapp.Config.Companion.EXTRA_KEY_NOTE
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_ADD
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_EDIT
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ActivityDetailBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.fragment.AddFragment
import com.survivalcoding.noteapp.presentation.fragment.EditFragment

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.detail_fragment_container,
                    when (intent.getIntExtra(EXTRA_KEY_FRAGMENT, 0)) {
                        FRAGMENT_CODE_ADD -> AddFragment()
                        FRAGMENT_CODE_EDIT -> {
                            val note: Note =
                                // case version code T 33
                                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    intent.getParcelableExtra(EXTRA_KEY_NOTE, Note::class.java)
                                } else {
                                    intent.getParcelableExtra(EXTRA_KEY_NOTE)
                                } ?: Note()

                            EditFragment(note)
                        }
                        else -> AddFragment()
                    }
                ).commit()
        }
    }
}