package com.example.contactsshareapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.interfaces.FavoriteChangeListener
import com.example.contactsshareapp.model.UserInformation

class FavoriteContactsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var recyclerview: RecyclerView
    companion object {
        lateinit var FAVORITES_CONTACT_ADAPTER: RecyclerviewAdapter
        var FILTERED_DATA: ArrayList<UserInformation> = ArrayList()
        var IS_ADAPTER_INITIALIZED: Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorit_contacts, container, false)

        recyclerview = view.findViewById(R.id.all_contact_rv)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        FILTERED_DATA = filterData()
        FAVORITES_CONTACT_ADAPTER = RecyclerviewAdapter(requireContext(), FILTERED_DATA, this)
        IS_ADAPTER_INITIALIZED = true

        recyclerview.adapter = FAVORITES_CONTACT_ADAPTER
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
            if (!FILTERED_DATA.contains(user)) {
                FILTERED_DATA.add(user)
            }
        } else {
            FILTERED_DATA.remove(user)
        }
        FAVORITES_CONTACT_ADAPTER?.notifyDataSetChanged()

        AllContactsFragment.ALL_CONTACTS_ADAPTER?.notifyDataSetChanged()
    }
}