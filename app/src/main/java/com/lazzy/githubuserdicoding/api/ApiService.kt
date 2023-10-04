package com.lazzy.githubuserdicoding.api


import com.lazzy.githubuserdicoding.BuildConfig.GITHUB_TOKEN
import com.lazzy.githubuserdicoding.model.DetailResponse
import com.lazzy.githubuserdicoding.model.GithubUser
import com.lazzy.githubuserdicoding.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@GET("search/users")
	@Headers("Authorization: token  $GITHUB_TOKEN")
	fun searchUser(
		@Query("q") login: String
	): Call<UserResponse>

	@GET("users/{login}")
	@Headers("Authorization: token $GITHUB_TOKEN")
	fun getUserDetail(
		@Path("login") login: String
	): Call<DetailResponse>

	@GET("users/{login}/followers")
	@Headers("Authorization: token  $GITHUB_TOKEN")
	fun getUserFollowers(
		@Path("login") login: String
	): Call<List<GithubUser>>

	@GET("users/{login}/following")
	@Headers("Authorization: token  $GITHUB_TOKEN")
	fun getUserFollowings(
		@Path("login") login: String
	): Call<List<GithubUser>>
}