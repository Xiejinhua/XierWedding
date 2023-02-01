package com.mazhang.xierwedding.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var name: MutableLiveData<String> = MutableLiveData()

    fun getName(): MutableLiveData<String> {
        return this.name
    }
}