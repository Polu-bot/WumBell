package com.example.wumbell.bleconnect

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConnectBleViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectBleViewModel::class.java)) {
            return ConnectBleViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}