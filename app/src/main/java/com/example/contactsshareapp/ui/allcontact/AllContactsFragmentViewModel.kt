package com.example.contactsshareapp.ui.allcontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactsshareapp.model.UserInformation
import com.example.contactsshareapp.repo.DataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllContactsFragmentViewModel @Inject constructor(
    private val dataRepo: DataRepo
): ViewModel() {
    private val mutableUserList: MutableLiveData<List<UserInformation>> = MutableLiveData()
    init {
        mutableUserList.value = dataRepo.getAllUsers()
    }
    fun getUserList(): LiveData<List<UserInformation>> = mutableUserList

    fun filterList(query: String?): List<UserInformation> {

        return dataRepo.getAllUsers().filter { user ->
            query.isNullOrBlank() || user.getFirstName().contains(query, ignoreCase = true ) || user.getLastName().contains(query, ignoreCase = true )
        }
    }

    fun onFavoriteChanged(user: UserInformation) {
        if (user.getFavoriteState()) {
            dataRepo.addToFavorites(user)
        } else {
            dataRepo.removeFromFavorites(user)
        }
    }
}