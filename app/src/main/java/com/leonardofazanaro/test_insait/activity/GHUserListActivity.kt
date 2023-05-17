package com.leonardofazanaro.test_insait.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.leonardofazanaro.test_insait.R
import com.leonardofazanaro.test_insait.domain.GHUsers
import com.leonardofazanaro.test_insait.viewmodel.UsersViewModel
import com.lofstudio.listadelivros.adapter.GHUserAdapter
import com.lofstudio.minhascolecoes.interfaces.RecyclerViewButtonOnClickListener
import java.util.*

class GHUserListActivity : AppCompatActivity(), RecyclerViewButtonOnClickListener {


    var edtSearch: EditText? =null
    var lyRefresh: SwipeRefreshLayout? =null
    var lyNest: NestedScrollView? =null
    var lyData: LinearLayout? =null
    var lyEmpty: LinearLayout? =null
    var txtEmpty: TextView? =null


    var recyclerview: RecyclerView? = null


    var viewModel: UsersViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ghusers)


        viewModel = UsersViewModel()




        edtSearch = findViewById(R.id.edtSearch)
        lyRefresh = findViewById(R.id.lyRefresh)
        lyNest = findViewById(R.id.lyNest)
        lyData = findViewById(R.id.lyData)
        lyEmpty= findViewById(R.id.lyEmpty)
        txtEmpty= findViewById(R.id.txtEmpty)

        recyclerview = findViewById(R.id.recyclerview)
        recyclerview!!.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)



        lyRefresh!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                loadData()


            }
        })



        viewModel!!.listGHUsers!!.observe(this, Observer {


            if(it.size > 0){

                lyData!!.visibility= View.VISIBLE
                lyEmpty!!.visibility= View.GONE

            }else{

                lyData!!.visibility= View.GONE
                lyEmpty!!.visibility= View.VISIBLE
            }


            val adapter = GHUserAdapter(this,it)
            adapter.setRecyclerViewButtonOnClickListener(this)


            adapter.submitList(it)

            recyclerview!!.adapter = adapter

            lyRefresh!!.isRefreshing = false

        })




        viewModel!!.error.observe(this) {
            if (it) {

                Toast.makeText(this@GHUserListActivity, "${viewModel!!.errorText}" , Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this@GHUserListActivity, "Tudo ok" , Toast.LENGTH_SHORT).show()


            }
        }


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


        lyRefresh!!.isRefreshing = true


        viewModel!!.loadData()



    }


    override fun onResume() {
        super.onResume()



    }

    override fun onClickListener(view: View?, position: Int, `object`: Any?) {
        /*
              val item: GHUsers? = `object` as GHUsers?


              var bookIntent = Intent(baseContext,BookDetailsActivity::class.java)
              bookIntent.putExtra("jsonItem",Gson().toJson(item))
              startActivity(bookIntent)

              */

    }

    override fun onLongClickListener(view: View?, position: Int, `object`: Any?) {


    }

    override fun onActionClick(view: View?, position: Int, `object`: Any?, button: Int) {


    }


}
