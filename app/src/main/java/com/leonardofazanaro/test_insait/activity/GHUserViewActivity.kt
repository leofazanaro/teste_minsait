package com.leonardofazanaro.test_insait.activity

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.leonardofazanaro.test_insait.R
import com.leonardofazanaro.test_insait.domain.GHRepo
import com.leonardofazanaro.test_insait.domain.GHUsers
import com.leonardofazanaro.test_insait.util.CustomAlert
import com.leonardofazanaro.test_insait.viewmodel.RepoViewModel
import com.leonardofazanaro.test_insait.viewmodel.UserDetailViewModel
import com.lofstudio.listadelivros.adapter.GHRepoAdapter
import com.lofstudio.minhascolecoes.interfaces.RecyclerViewButtonOnClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class GHUserViewActivity : AppCompatActivity(),RecyclerViewButtonOnClickListener {

    var edtSearch: EditText? =null
    var lyRefresh: SwipeRefreshLayout? =null
    var lyNest: NestedScrollView? =null
    var lyData: LinearLayout? =null
    var lyEmpty: LinearLayout? =null
    var txtEmpty: TextView? =null
    var txtCountRepos: TextView? =null
    var txtCountGits: TextView? =null

    var recyclerview: RecyclerView? = null


    var viewModel: RepoViewModel? = null



    var txtName : TextView? = null
    var txtUser : TextView? = null
    var imgUser : ImageView? = null
    var btnBack : CardView? = null
    var btnGitHub: CardView? = null


    var jsonData = ""
    var ghUser: GHUsers? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ghuser_view)

        txtName = findViewById(R.id.txtName)
        txtUser = findViewById(R.id.txtUser)
        txtCountRepos = findViewById(R.id.txtCountRepos)
        txtCountGits = findViewById(R.id.txtCountGits)



        imgUser = findViewById(R.id.imgUser)
        btnBack = findViewById(R.id.btnBack)
        btnGitHub = findViewById(R.id.btnGitHub)


        btnBack!!.setOnClickListener {

            onBackPressed()

        }

        btnGitHub!!.setOnClickListener {

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(ghUser!!.html_url)
                )
            )

        }


        val bundle = intent.extras

        jsonData = bundle!!.getString("jsonData","")


        edtSearch = findViewById(R.id.edtSearch)
        lyRefresh = findViewById(R.id.lyRefresh)
        lyNest = findViewById(R.id.lyNest)
        lyData = findViewById(R.id.lyData)
        lyEmpty= findViewById(R.id.lyEmpty)
        txtEmpty= findViewById(R.id.txtEmpty)


        recyclerview = findViewById(R.id.recyclerview)
        recyclerview!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)



        lyRefresh!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                loadData()


            }
        })




        edtSearch!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                viewModel!!.search(s.toString())


            }
        })


        loadData()
    }

    fun loadData(){

        try {
            ghUser = Gson().fromJson(jsonData,GHUsers::class.java)

            if(ghUser != null){


                val userDetailViewModel = UserDetailViewModel(ghUser!!.login!!)

                userDetailViewModel.loadData()

                userDetailViewModel.ghUserDetail.observe(this, Observer {

                    if(it != null){

                        if(it.name != null){

                            txtName!!.text = "${it.name}"
                        }

                        txtCountRepos!!.text = "${it.public_repos}"
                        txtCountGits!!.text = "${it.public_gists}"
                    }


                })





                txtUser!!.text = ghUser!!.login

                Picasso.get()
                    .load(ghUser!!.avatar_url).resize(250, 250)
                    .centerCrop().into(imgUser, object : Callback {
                        override fun onSuccess() {


                        }

                        override fun onError(ex: Exception) {

                        }
                    })

                lyRefresh!!.isRefreshing = true

                viewModel = RepoViewModel(ghUser!!.login!!)
                viewModel!!.loadData()


                viewModel!!.listGHRepo.observe(this, Observer {

                    if(it.size > 0){

                        lyData!!.visibility= View.VISIBLE
                        lyEmpty!!.visibility= View.GONE

                    }else{

                        lyData!!.visibility= View.GONE
                        lyEmpty!!.visibility= View.VISIBLE
                    }


                    val adapter = GHRepoAdapter(this,it)
                    adapter.setRecyclerViewButtonOnClickListener(this)


                    adapter.submitList(it)

                    recyclerview!!.adapter = adapter

                    lyRefresh!!.isRefreshing = false

                })

                viewModel!!.error.observe(this) {

                    if(it){

                        lyRefresh!!.isRefreshing = false

                        CustomAlert.menssage(this@GHUserViewActivity,
                            "Algo deu errado",
                            "Não foi possivel carregar os dados.\n\nTente novamente mais tarde.","OK,FECHAR",
                            DialogInterface.OnClickListener { dialog, which ->

                                finish()
                            },false)

                    }

                }


            }

        }catch (e : Exception){

            Log.e("lof_log",e.message!!)
            Toast.makeText(this@GHUserViewActivity,"Algo deu errado",Toast.LENGTH_LONG).show()
        }



    }

    override fun onClickListener(view: View?, position: Int, `object`: Any?) {

    }

    override fun onLongClickListener(view: View?, position: Int, `object`: Any?) {

    }

    override fun onActionClick(view: View?, position: Int, `object`: Any?, button: Int) {

        val item: GHRepo? = `object` as GHRepo?

        if(button == 1){

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item!!.html_url)))
        }

    }
}