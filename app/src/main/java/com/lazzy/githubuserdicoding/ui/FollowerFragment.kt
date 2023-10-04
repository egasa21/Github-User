package com.lazzy.githubuserdicoding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazzy.githubuserdicoding.adapter.FollowerAdapter
import com.lazzy.githubuserdicoding.databinding.FragmentFollowersBinding
import com.lazzy.githubuserdicoding.model.GithubUser
import com.lazzy.githubuserdicoding.utils.Helper
import com.lazzy.githubuserdicoding.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowerViewModel
    private val helper = Helper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.showLoading(it, binding.progressBar3)
        }
        followerViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
        }

        followerViewModel.getFollower(arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString())
    }

    private fun setDataToFragment(listFollower: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollower) {
                listUser.clear()
                listUser.addAll(listFollower)
            }
            rvFollower.layoutManager = LinearLayoutManager(context)
            val adapter = FollowerAdapter(listFollower)
            rvFollower.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}