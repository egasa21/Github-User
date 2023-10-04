package com.lazzy.githubuserdicoding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazzy.githubuserdicoding.adapter.FollowingAdapter
import com.lazzy.githubuserdicoding.databinding.FragmentFollowersBinding
import com.lazzy.githubuserdicoding.model.GithubUser
import com.lazzy.githubuserdicoding.ui.DetailUserActivity
import com.lazzy.githubuserdicoding.utils.Helper
import com.lazzy.githubuserdicoding.viewmodel.FollowingViewModel


class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.showLoading(it, binding.progressBar3)
        }
        followingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setDataToFragment(listFollowing)
        }

        followingViewModel.getFollowing(arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString())
    }

    /**
     * Function to set the data from API into fragment's view.
     */
    private fun setDataToFragment(listFollowing: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollowing) {
                listUser.clear()
                listUser.addAll(listFollowing)
            }
            rvFollower.layoutManager = LinearLayoutManager(context)
            val adapter = FollowingAdapter(listFollowing)
            rvFollower.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}