package org.silvermoon.devicediagnostics.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Select a component from the navigation drawer."
    }
    val text: LiveData<String> = _text
}