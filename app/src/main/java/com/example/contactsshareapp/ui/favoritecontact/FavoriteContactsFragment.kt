package com.example.contactsshareapp.ui.favoritecontact

import com.example.contactsshareapp.repo.DataRepo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.ui.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.databinding.FragmentFavoritContactsBinding
import com.example.contactsshareapp.callbacks.FavoriteChangeListener
import com.example.contactsshareapp.model.UserInformation
import com.example.contactsshareapp.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteContactsFragment : Fragment(), FavoriteChangeListener {
    @Inject
    lateinit var dataRepo: DataRepo

    private lateinit var binding: FragmentFavoritContactsBinding
    private lateinit var favoritesContactAdapter: RecyclerviewAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerview: RecyclerView

    private val viewModel: FavoriteContactsFragmentViewModel by lazy {
        createViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritContactsBinding.inflate(inflater)
        val view = binding.root
        setupUI()
        viewModel.getFavoriteUserList().observe(viewLifecycleOwner,Observer{ favoriteUserList ->
            favoritesContactAdapter.updateData(favoriteUserList)
        })
        return view
    }

    private fun createViewModel(): FavoriteContactsFragmentViewModel {
        return activity?.let {
            ViewModelProvider(it).get(FavoriteContactsFragmentViewModel::class.java)
        } ?: throw IllegalStateException("Invalid Activity")
    }

    private fun setupUI() {
        setupRecyclerView()
        observeSharedViewModel()
    }

    private fun setupRecyclerView() {
        recyclerview = binding.allContactRv
        recyclerview.layoutManager = LinearLayoutManager(activity)
        favoritesContactAdapter = RecyclerviewAdapter(requireContext(), emptyList(), this)
        recyclerview.adapter = favoritesContactAdapter
    }

    private fun observeSharedViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.favoriteChangedLiveData.observe(viewLifecycleOwner) {
            favoritesContactAdapter.notifyDataSetChanged()
        }
    }

    override fun onFavoriteChanged(user: UserInformation) {
        viewModel.onFavoriteChanged(user)
        sharedViewModel.notifyFavoriteChanged()
        sharedViewModel.notifyContactAdded()
    }
}