package com.example.contactsshareapp.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.model.UserInformation
import androidx.appcompat.widget.SearchView
import com.example.contactsshareapp.AddNewContact
import com.example.contactsshareapp.interfaces.FavoriteChangeListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AllContactsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var recyclerview:RecyclerView
    private lateinit var searchView: SearchView

    companion object{
        var USER_INFO_LIST = ArrayList<UserInformation>()
        lateinit var ALL_CONTACTS_ADAPTER: RecyclerviewAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_contacts, container, false)
        recyclerview = view.findViewById(R.id.all_contact_rv)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        USER_INFO_LIST.addAll(loadContactsFromSharedPreferences())

        ALL_CONTACTS_ADAPTER = RecyclerviewAdapter(requireContext(),USER_INFO_LIST,this)
        recyclerview.adapter = ALL_CONTACTS_ADAPTER

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })
        return view
    }

    private fun loadContactsFromSharedPreferences(): List<UserInformation> {
        val sharedPreferences = requireActivity().getSharedPreferences(AddNewContact.MY_CONTACTS_KEY, MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(AddNewContact.MY_CONTACTS_KEY, null)

        return if (json != null) {
            val type = object : TypeToken<List<UserInformation>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun filter(query: String?) {
        val filteredList = ArrayList<UserInformation>()
        val noResultsTextView = view?.findViewById<TextView>(R.id.noResultsTextView)
        var flag = false

        query?.let {
            if (it.isEmpty()) {
                filteredList.addAll(USER_INFO_LIST)
                noResultsTextView?.visibility = View.GONE
            } else {
                for (user in USER_INFO_LIST) {
                    if (user.getFirstName().lowercase().contains(it.lowercase())) {
                        filteredList.add(user)
                        flag = true
                        noResultsTextView?.visibility = View.GONE
                    }
                }
                if (!flag) noResultsTextView?.visibility = View.VISIBLE
            }
        }
        ALL_CONTACTS_ADAPTER.filterList(filteredList)
    }

    override fun onFavoriteChanged(user: UserInformation) {
        if (user.getFavoriteState()) {
            if (!FavoriteContactsFragment.FILTERED_DATA.contains(user)) {
                FavoriteContactsFragment.FILTERED_DATA.add(user)
            }
        } else {
            FavoriteContactsFragment.FILTERED_DATA.remove(user)
        }

        if (FavoriteContactsFragment.IS_ADAPTER_INITIALIZED ) {
            FavoriteContactsFragment.FAVORITES_CONTACT_ADAPTER?.notifyDataSetChanged()
        }

        ALL_CONTACTS_ADAPTER?.notifyDataSetChanged()
    }
}
