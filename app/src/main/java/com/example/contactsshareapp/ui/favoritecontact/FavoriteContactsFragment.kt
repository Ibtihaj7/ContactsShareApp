package com.example.contactsshareapp.ui.favoritecontact

import DataRepo
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsshareapp.ui.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.databinding.FragmentFavoritContactsBinding
import com.example.contactsshareapp.callbacks.ContactsInterface
import com.example.contactsshareapp.callbacks.FavoriteChangeListener
import com.example.contactsshareapp.model.UserInformation
import com.example.contactsshareapp.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteContactsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var binding:FragmentFavoritContactsBinding
    private lateinit var favoritesContactAdapter: RecyclerviewAdapter
    private lateinit var userList :ArrayList<UserInformation>
    private var allContactInterface:ContactsInterface? = null

    companion object {
        var FILTERED_DATA: ArrayList<UserInformation> = ArrayList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity){
            context.favoriteContactsInterface = object:ContactsInterface{
                override fun onFavoriteChanged() {
                    favoritesContactAdapter.notifyDataSetChanged()
                }
            }
        }
        if(context is ContactsInterface){
            allContactInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritContactsBinding.inflate(inflater)
        val view = binding.root

        binding.allContactRv.layoutManager = LinearLayoutManager(activity)

        userList = DataRepo.getAllUserList()
        FILTERED_DATA = filterData()
        favoritesContactAdapter = RecyclerviewAdapter(requireContext(), FILTERED_DATA, this)

        binding.allContactRv.adapter = favoritesContactAdapter
        return view
    }

    private fun filterData(): ArrayList<UserInformation> {
        return ArrayList(
            userList.filter {
                it.getFavoriteState()
            }
        )
    }

    override fun onFavoriteChanged(user: UserInformation) {
        if (user.getFavoriteState()) {
            FILTERED_DATA.add(user)
        } else {
            FILTERED_DATA.remove(user)
        }
        favoritesContactAdapter?.notifyDataSetChanged()
        allContactInterface?.onFavoriteChanged()
    }
}