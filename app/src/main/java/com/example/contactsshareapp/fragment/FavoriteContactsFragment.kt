package com.example.contactsshareapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.MainActivity
import com.example.contactsshareapp.R
import com.example.contactsshareapp.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.interfaces.ContactsInterface
import com.example.contactsshareapp.interfaces.FavoriteChangeListener
import com.example.contactsshareapp.model.UserInformation

class FavoriteContactsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var recyclerview: RecyclerView
    private lateinit var favoritesContactAdapter: RecyclerviewAdapter
    private var allContactInterface:ContactsInterface? = null
    companion object {
        var FILTERED_DATA: ArrayList<UserInformation> = ArrayList()
        var IS_ADAPTER_INITIALIZED: Boolean = false
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
        val view = inflater.inflate(R.layout.fragment_favorit_contacts, container, false)

        recyclerview = view.findViewById(R.id.all_contact_rv)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        FILTERED_DATA = filterData()
        favoritesContactAdapter = RecyclerviewAdapter(requireContext(), FILTERED_DATA, this)
        IS_ADAPTER_INITIALIZED = true

        recyclerview.adapter = favoritesContactAdapter
        return view
    }

    private fun filterData(): ArrayList<UserInformation> {
        return ArrayList(
            AllContactsFragment.USER_INFO_LIST.filter {
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