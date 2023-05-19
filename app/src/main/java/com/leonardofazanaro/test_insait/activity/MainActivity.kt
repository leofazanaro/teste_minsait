package com.leonardofazanaro.test_insait.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.leonardofazanaro.test_insait.R


class MainActivity : AppCompatActivity() {


    var cardOffline : CardView? = null
    var txt : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //COMPONENTES VISUAIS
        cardOffline = findViewById<CardView>(R.id.cardOffline)
        txt = findViewById<TextView>(R.id.txt)


        cardOffline!!.setOnClickListener {

            load()
        }



        load()

    }

    fun load(){


       cardOffline!!.visibility = View.GONE



        //VERIFICA SE O DISPOSITIVO TEM CONECXAO COM A INTERNET
        if(isOnline(this@MainActivity)){

            //CASO SIM, FICA NA TELA DE APRESENTAÇÃO POR 1,5s E ABRE A TELA DE LISTA DE USUARIOS
            Handler().postDelayed({


                startActivity(Intent(baseContext,GHUserListActivity::class.java))
                finish()

            }, 1500)



        }else {

            //CASO CONTRARIO, EXIBE O BOTAO DE ERRO QUE PODE SER CLICADO PARA FAZER UMA NOVA TENTATIVA
            cardOffline!!.visibility = View.VISIBLE
            txt!!.text = "Seu dispositivo não está conectado com a internet.\nVerifique sua conexão e tente novamente."

        }
    }

    //METODO RESPONSAVEL POR VERIFICAR SE O DISPOSITIVO POSSUE CONEXAO COM A INTERNET
    fun isOnline(context: Context): Boolean {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

}