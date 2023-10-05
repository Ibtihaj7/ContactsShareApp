package com.example.contactsshareapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.interfaces.OnItemClickListener
import com.example.contactsshareapp.model.UserInformation
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.currentCoroutineContext

class AllContactsFragment : Fragment() {
    private lateinit var recyclerview:RecyclerView
    private lateinit var searchView: SearchView

    companion object{
        var USER_INFO_LIST = ArrayList<UserInformation>()
        lateinit var ALL_CONTACTS_ADAPTER: RecyclerviewAdapter

        fun createMyItemClickListener(): OnItemClickListener {
            return object : OnItemClickListener {
                override fun onItemClick(userInformation: UserInformation) {
                   Log.d("onItemClick","${userInformation.getFirstName()} ${userInformation.getLastName()}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_contacts, container, false)
        recyclerview = view.findViewById(R.id.all_contact_rv)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        USER_INFO_LIST.add(UserInformation("Ahmad","Ali","+970597876404"))
        USER_INFO_LIST.add(UserInformation("Rami","Ahmad","+970593432123"))

        val obj = createMyItemClickListener()
        ALL_CONTACTS_ADAPTER = RecyclerviewAdapter(USER_INFO_LIST,obj)
        recyclerview.adapter = ALL_CONTACTS_ADAPTER

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
    private fun filter(query: String?) {
        val filteredList = ArrayList<UserInformation>()

        query?.let {
            if (it.isEmpty()) {
                filteredList.addAll(USER_INFO_LIST)
            } else {
                for (user in USER_INFO_LIST) {
                    if (user.getFirstName().lowercase().contains(it.lowercase())) {
                        filteredList.add(user)
                    }
                }
            }
        }
        ALL_CONTACTS_ADAPTER.filterList(filteredList)
    }
}