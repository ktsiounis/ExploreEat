package com.example.exploreat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exploreat.repository.MainRepository
import java.lang.IllegalArgumentException


class MainActivityViewModelFactory constructor(private val mainRepository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            MainActivityViewModel(this.mainRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}