package com.leonardofazanaro.test_insait.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.leonardofazanaro.test_insait.api.Endpoint
import com.leonardofazanaro.test_insait.domain.GHUserDetail
import com.leonardofazanaro.test_insait.util.NetworkUtil
import org.json.JSONArray

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


open class UserDetailViewModel(val userLogin: String) {


    private val _ghUserDetail= MutableLiveData<GHUserDetail>()

    val ghUserDetail: LiveData<GHUserDetail>
        get() = _ghUserDetail

    private val _error = MutableLiveData<Boolean>(true)
    val error = Transformations.map(_error) { !it }


    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading = Transformations.map(_isLoading) { !it }


    private var _errorText = MutableLiveData<String>()

    val errorText: MutableLiveData<String>
        get() = _errorText


    fun loadData() {

        Log.w("lof_log","load data")


        val WS_URL_BASE = "https://api.github.com/"

        val retrofitClient = NetworkUtil.getRetrofitInstance(WS_URL_BASE)
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getUserDetail(userLogin).enqueue(object : retrofit2.Callback<GHUserDetail> {
            override fun onResponse(call: Call<GHUserDetail>, response: Response<GHUserDetail>) {


                val data = response.body()

                Log.w("lof_log",data.toString())



                if(data != null){

                    _ghUserDetail.postValue(data)


                }else{

                    //ERRO
                    _error.postValue(true)
                    _errorText.postValue("Erro ao carregar.\n\nTente novamente mais tarde")

                }




            }

            override fun onFailure(call: Call<GHUserDetail>, t: Throwable) {

                Log.e("lof_log","load data fail")
                Log.e("lof_log","${t.message}")
                //ERRO
                _error.postValue(true)
                _errorText.postValue("Erro ao carregar a lista.\n\nTente novamente mais tarde")

            }

        })


    }

}