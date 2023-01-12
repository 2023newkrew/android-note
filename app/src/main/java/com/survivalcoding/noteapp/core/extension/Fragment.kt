package com.survivalcoding.noteapp.core.extension

import androidx.fragment.app.Fragment
import com.survivalcoding.noteapp.App

val Fragment.app: App
    get() = requireActivity().application as App