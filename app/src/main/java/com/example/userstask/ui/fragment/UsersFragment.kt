package com.example.userstask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userstask.R
import com.example.userstask.data.entity.UserData
import com.example.userstask.ui.MainViewModel
import com.example.userstask.ui.recyclerview.MainAdapter

class UsersFragment : Fragment() {

    private lateinit var adapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        initRecyclerView(view)
        initViewModel()

        return view
    }

    private fun initRecyclerView(view: View) {
        adapter = MainAdapter(object : MainAdapter.ItemClickListener {
            override fun onClick(user: UserData) {
                mainViewModel.userData = user
                Navigation.findNavController(view).navigate(R.id.navigate_to_userDetails)
            }
        })
        val recyclerView = view.findViewById<RecyclerView>(R.id.users_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.apiUsersData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }
}