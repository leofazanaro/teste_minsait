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

        cardOffline = findViewById<CardView>(R.id.cardOffline)
        txt = findViewById<TextView>(R.id.txt)

        cardOffline!!.setOnClickListener {

            load()
        }


        load()

    }


    fun load(){

       cardOffline!!.visibility = View.GONE

        if(isOnline(this@MainActivity)){

            Handler().postDelayed({


                startActivity(Intent(baseContext,GHUserListActivity::class.java))
                finish()

            }, 1500)



        }else {
            cardOffline!!.visibility = View.VISIBLE
            txt!!.text = "Seu dispositivo não está conectado com a internet.\nVerifique sua conexão e tente novamente."

        }
    }

    fun isOnline(context: Context): Boolean {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

}