package com.lazzy.githubuserdicoding.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lazzy.githubuserdicoding.R
import com.lazzy.githubuserdicoding.adapter.SectionsPagerAdapter
import com.lazzy.githubuserdicoding.databinding.ActivityDetailUserBinding
import com.lazzy.githubuserdicoding.model.DetailResponse
import com.lazzy.githubuserdicoding.model.GithubUser
import com.lazzy.githubuserdicoding.utils.Helper
import com.lazzy.githubuserdicoding.viewmodel.UserDetailViewModel

class DetailUserActivity : AppCompatActivity() {
    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    private val helper = Helper()

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailViewModel.listDetail.observe(this) { detailList ->
            setDataToView(detailList)
        }

        userDetailViewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar2)
        }

        setTabLayoutView()
    }

    private fun setTabLayoutView() {
        val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
        userDetailViewModel.getGithubUser(userIntent.login)

        val login = Bundle()
        login.putString(EXTRA_FRAGMENT, userIntent.login)

        val sectionPagerAdapter = SectionsPagerAdapter(this, login)
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun setDataToView(detailList: DetailResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(detailsIvAvatar)
            detailsTvName.text = detailList.name ?: "No Name."
            detailsTvUsername.text = detailList.login
            detailsTvBio.text = if (detailList.bio == null) "This person haven\'t set their bio yet." else detailList.bio.toString()
            detailsTvFollower.text = resources.getString(R.string.follower, detailList.followers)
            detailsTvFollowing.text = resources.getString(R.string.following, detailList.following)
            detailsTvGist.text = resources.getString(R.string.gist, detailList.publicGists)
            detailsTvRepository.text = resources.getString(R.string.repository, detailList.publicRepos)
            detailsTvCompany.text = detailList.company ?: "No company."
            detailsTvLocation.text = detailList.location ?: "No location."
            detailsTvBlog.text = if (detailList.blog == "") "No website/blog." else detailList.blog

        }
    }
}