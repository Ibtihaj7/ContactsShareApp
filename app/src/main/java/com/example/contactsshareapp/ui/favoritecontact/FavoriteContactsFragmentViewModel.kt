package com.example.contactsshareapp.ui.favoritecontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactsshareapp.model.UserInformation
import com.example.contactsshareapp.repo.DataRepo
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FavoriteContactsFragmentViewModel @Inject constructor(
    private val dataRepo: DataRepo
):ViewModel() {
    private val mutableFavoriteUserList: MutableLiveData<List<UserInformation>> = MutableLiveData()
    init {
        mutableFavoriteUserList.value = dataRepo.getFavoriteUsers()
    }
    fun getFavoriteUserList():LiveData<List<UserInformation>> = mutableFavoriteUserList
    fun onFavoriteChanged(user: UserInformation) {
        if (user.getFavoriteState()) {
            dataRepo.addToFavorites(user)
        } else {
            dataRepo.removeFromFavorites(user)
        }
    }
}