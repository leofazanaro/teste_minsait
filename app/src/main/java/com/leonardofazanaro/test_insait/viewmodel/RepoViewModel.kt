package com.leonardofazanaro.test_insait.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.leonardofazanaro.test_insait.api.Endpoint
import com.leonardofazanaro.test_insait.domain.GHRepo
import com.leonardofazanaro.test_insait.util.NetworkUtil

import retrofit2.Call
import retrofit2.Response
import java.util.*


open class RepoViewModel(val userLogin: String) {


    private var fullList: MutableList<GHRepo>? =null

    private val _listGHRepo= MutableLiveData<MutableList<GHRepo>>()

    val listGHRepo: LiveData<MutableList<GHRepo>>
        get() = _listGHRepo

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

        endpoint.getRepos(userLogin).enqueue(object : retrofit2.Callback<List<GHRepo>> {
            override fun onResponse(call: Call<List<GHRepo>>, response: Response<List<GHRepo>>) {


                val data = response.body()

                //VERICA SE EXISTEM DADOS NA RESPOTA
                if(data != null){

                    //GUARDA TODOS OS DADOS EM UMA LISTA COMPLETA QUE SERÁ USADA COMO BASE NA BUSCAS MAIS TARDE
                    fullList = (data as MutableList<GHRepo>?)
                    //MANDA A LISTA COMPLETA PARA A TELA PARA SER EXIBIDA PARA O USUARIO
                    _listGHRepo.postValue(fullList as MutableList<GHRepo>)


                }else{

                    //ERRO
                    _error.postValue(true)
                    _errorText.postValue("Erro ao carregar a lista.\n\nTente novamente mais tarde")

                }




            }

            override fun onFailure(call: Call<List<GHRepo>>, t: Throwable) {

                Log.e("lof_log","load data fail")
                Log.e("lof_log","${t.message}")
                //ERRO
                _error.postValue(true)
                _errorText.postValue("Erro ao carregar a lista.\n\nTente novamente mais tarde")

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

                it.name!!.lowercase(Locale.ROOT).contains(src.lowercase(Locale.ROOT)) // || it.id!!.lowercase(Locale.ROOT).contains(src.lowercase(Locale.ROOT))
            }

            _listGHRepo.postValue(fiterList as MutableList<GHRepo>)




        }
    }

}