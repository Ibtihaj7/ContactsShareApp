package com.example.contactsshareapp.ui.allcontact

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.ui.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.model.UserInformation
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contactsshareapp.databinding.FragmentAllContactsBinding
import com.example.contactsshareapp.callbacks.FavoriteChangeListener
import com.example.contactsshareapp.repo.DataRepo
import com.example.contactsshareapp.ui.SharedViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AllContactsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var binding: FragmentAllContactsBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var allContactsAdapter: RecyclerviewAdapter
    private lateinit var sharedViewModel: SharedViewModel

    private val viewModel: AllContactsFragmentViewModel by lazy {
        createViewModel()
    }
    companion object{
        var contactAddedListener: ContactAddedListener? = null
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllContactsBinding.inflate(inflater)
        setupUI()
        contactAddedListener = object : ContactAddedListener {
            override fun onContactAdded() {
                allContactsAdapter.notifyDataSetChanged()
            }
        }

        viewModel.getUserList().observe(viewLifecycleOwner, Observer{ favoriteUserList ->
            allContactsAdapter.updateData(favoriteUserList)
        })

        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupSearchView()
        observeSharedViewModel()
    }

    private fun createViewModel(): AllContactsFragmentViewModel {
        return activity?.let {
            ViewModelProvider(it).get(AllContactsFragmentViewModel::class.java)
        } ?: throw IllegalStateException("Invalid Activity")
    }

    private fun setupRecyclerView() {
        recyclerview = binding.allContactRv
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        allContactsAdapter = RecyclerviewAdapter(requireContext(), emptyList(), this)
        recyclerview.adapter = allContactsAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })
    }

    private fun filter(query: String?) {
        val filteredList = viewModel.filterList(query)
        val noResultsTextView = binding.noResultsTextView
        noResultsTextView.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE

        allContactsAdapter.updateData(filteredList)
    }

    private fun observeSharedViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.contactAddedLiveData.observe(viewLifecycleOwner) {
            allContactsAdapter.notifyDataSetChanged()
        }
    }

    override fun onFavoriteChanged(user: UserInformation) {
        viewModel.onFavoriteChanged(user)
        sharedViewModel.notifyFavoriteChanged()
        sharedViewModel.notifyContactAdded()
    }

    interface ContactAddedListener {
        fun onContactAdded()
    }
}