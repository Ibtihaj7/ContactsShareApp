package com.example.contactsshareapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _favoriteChangedLiveData = MutableLiveData<Unit>()
    private val _contactAddedLiveData = MutableLiveData<Unit>()
    val favoriteChangedLiveData: LiveData<Unit>
        get() = _favoriteChangedLiveData

    val contactAddedLiveData: LiveData<Unit>
        get() = _contactAddedLiveData
    fun notifyFavoriteChanged() {
        _favoriteChangedLiveData.value = Unit
    }

    fun notifyContactAdded() {
        _contactAddedLiveData.value = Unit
    }
}