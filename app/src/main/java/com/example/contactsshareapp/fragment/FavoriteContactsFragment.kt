package com.example.contactsshareapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.model.UserInformation

class FavoriteContactsFragment : Fragment() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: RecyclerviewAdapter
    private lateinit var filteredData: ArrayList<UserInformation>
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        val view = inflater.inflate(R.layout.fragment_favorit_contacts, container, false)

        recyclerview = view.findViewById(R.id.all_contact_rv)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        filteredData = filterData()

        val obj = AllContactsFragment.createMyItemClickListener()
        adapter = RecyclerviewAdapter(filteredData,obj)

        recyclerview.adapter = adapter

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })

        return view
    }
    private fun filterData():ArrayList<UserInformation>{
        return ArrayList(
            AllContactsFragment.USER_INFO_LIST.filter {
                it.getFavoriteState()
            }
        )
    }
    private fun filter(query: String?) {
        val filteredList = ArrayList<UserInformation>()

        query?.let {
            if (it.isEmpty()) {
                filteredList.addAll(AllContactsFragment.USER_INFO_LIST)
            } else {
                for (user in AllContactsFragment.USER_INFO_LIST) {
                    if (user.getFirstName().lowercase().contains(it.lowercase())) {
                        filteredList.add(user)
                    }
                }
            }
        }
        adapter.filterList(filteredList)
    }
}