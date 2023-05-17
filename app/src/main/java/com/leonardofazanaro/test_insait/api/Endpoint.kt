package com.leonardofazanaro.test_insait.api

import com.leonardofazanaro.test_insait.domain.GHUsers
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @GET("users")
    fun getUsers() : Call<List<GHUsers>>
}