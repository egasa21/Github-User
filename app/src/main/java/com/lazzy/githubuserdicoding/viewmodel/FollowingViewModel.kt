package com.lazzy.githubuserdicoding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazzy.githubuserdicoding.api.ApiConfig
import com.lazzy.githubuserdicoding.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
	private val _listFollowing = MutableLiveData<List<GithubUser>>()
	val listFollowing: LiveData<List<GithubUser>> = _listFollowing

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	companion object {
		private const val TAG = "FollowerViewModel"
	}

	internal fun getFollowing(username: String) {
		_isLoading.value = true
		val client = ApiConfig.getApiService().getUserFollowings(username)
		client.enqueue(object : Callback<List<GithubUser>> {
			override fun onResponse(
				call: Call<List<GithubUser>>,
				response: Response<List<GithubUser>>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_listFollowing.value = response.body()
					}
					else {
						Log.e(TAG, "onFailure: ${response.message()}")
					}
				}
			}

			override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
			}
		})
	}
}
