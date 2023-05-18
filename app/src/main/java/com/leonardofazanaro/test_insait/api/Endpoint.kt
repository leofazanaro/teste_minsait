package com.leonardofazanaro.test_insait.api

import com.leonardofazanaro.test_insait.domain.GHRepo
import com.leonardofazanaro.test_insait.domain.GHUserDetail
import com.leonardofazanaro.test_insait.domain.GHUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("users")
    fun getUsers() : Call<List<GHUsers>>


    @GET("users/{username}/repos")
    fun getRepos(@Path(value = "username",encoded = true)username : String) : Call<List<GHRepo>>

    @GET("users/{username}")
    fun getUserDetail(@Path(value = "username",encoded = true)username : String) : Call<GHUserDetail>
}