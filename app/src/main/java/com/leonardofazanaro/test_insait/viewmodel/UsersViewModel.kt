package com.leonardofazanaro.test_insait.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.leonardofazanaro.test_insait.api.Endpoint
import com.leonardofazanaro.test_insait.domain.GHUsers
import com.leonardofazanaro.test_insait.util.NetworkUtil
import org.json.JSONArray

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


open class UsersViewModel {


    private var fullList: MutableList<GHUsers>? =null

    private val _listGHUsers= MutableLiveData<MutableList<GHUsers>>()

    val listGHUsers: LiveData<MutableList<GHUsers>>
        get() = _listGHUsers

    private val _error = MutableLiveData<Boolean>(false)
    val error = Transformations.map(_error) { it }


    private var _errorText = MutableLiveData<String>()

    val errorText: MutableLiveData<String>
        get() = _errorText


    fun loadData() {

        Log.w("lof_log","load data")

        val WS_URL_BASE = "https://api.github.com/"

        val retrofitClient = NetworkUtil.getRetrofitInstance(WS_URL_BASE)

        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getUsers().enqueue(object : retrofit2.Callback<List<GHUsers>> {
            override fun onResponse(call: Call<List<GHUsers>>, response: Response<List<GHUsers>>) {


                val data = response.body()

               //VERICA SE EXISTEM DADOS NA RESPOTA
                if(data != null){

                    //GUARDA TODOS OS DADOS EM UMA LISTA COMPLETA QUE SERÁ USADA COMO BASE NA BUSCAS MAIS TARDE
                    fullList = (data as MutableList<GHUsers>?)

                    //MANDA A LISTA COMPLETA PARA A TELA PARA SER EXIBIDA PARA O USUARIO
                    _listGHUsers.postValue(fullList as MutableList<GHUsers>)


                }else{

                    //ERRO
                    _error.postValue(true)
                    _errorText.postValue("Erro ao carregar a lista.")

                }




            }

            override fun onFailure(call: Call<List<GHUsers>>, t: Throwable) {

                Log.e("lof_log","load data fail")
                Log.e("lof_log","${t.message}")
                //ERRO
                _error.postValue(true)
                _errorText.postValue("${t.message}")

            }

        })


    }

    //METODO RESPOSAVEL POR FILTRAR A LISTA BASEADO LOGIN DO USUARIO
    fun search(src: String) {

        //CASO O INPUT SEJA VAZIO A LISTA É RECARREGADA NOVAMENTE
        if (src.isEmpty()) {


            loadData()

        } else {


            //CASO NAO, A LISTA COMPLETA É FILTRADA BASEADA NO INPUT DO USUARIO

            var fiterList = fullList!!.filter {

                it.login!!.lowercase(Locale.ROOT).contains(src.lowercase(Locale.ROOT))
            }

            _listGHUsers.postValue(fiterList as MutableList<GHUsers>)


        }
    }



}