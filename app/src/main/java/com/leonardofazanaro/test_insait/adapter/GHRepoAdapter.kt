package com.lofstudio.listadelivros.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.cardview.widget.CardView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.leonardofazanaro.test_insait.R
import com.leonardofazanaro.test_insait.domain.GHRepo
import com.lofstudio.minhascolecoes.interfaces.RecyclerViewButtonOnClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class GHRepoAdapter(

    context: Context,
    private var mlist: MutableList<GHRepo>) :

    RecyclerView.Adapter<GHRepoAdapter.MyViewHolder>() {


    private val context: Context
    private val mLayoutInflater: LayoutInflater
    private var recyclerViewButtonOnClickListener: RecyclerViewButtonOnClickListener? = null

    init {

        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.context = context



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layout = R.layout.item_gh_repo
        val v = mLayoutInflater.inflate(layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {


        myViewHolder.txtName.text = "${mlist[position].name}"

        if(mlist[position].description != null){
            myViewHolder.txtDescription.text = "${mlist[position].description}"
        }



        myViewHolder.txtLanguage.text = "${mlist[position].language}"
        myViewHolder.txtStars.text = "${mlist[position].stargazers_count}"
        myViewHolder.txtForks.text = "${mlist[position].forks_count}"
        myViewHolder.txtwatchers.text = "${mlist[position].watchers_count}"



        myViewHolder.card.setOnClickListener { view ->
            if (recyclerViewButtonOnClickListener != null) {

                recyclerViewButtonOnClickListener!!.onClickListener(view, position, mlist[position])
            }
        }


        myViewHolder.card.setOnClickListener { view ->

            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mlist[position]!!.html_url))
            )
        }


    }


    override fun getItemCount(): Int {
        return mlist.size
    }

    fun addItem(item: GHRepo) {
        mlist.add(item)
        notifyDataSetChanged()
    }

    fun setRecyclerViewButtonOnClickListener(recyclerViewButtonOnClickListener: RecyclerViewButtonOnClickListener?) {
        this.recyclerViewButtonOnClickListener = recyclerViewButtonOnClickListener
    }

    fun submitList(it: MutableList<GHRepo>) {

        mlist = it
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var card: CardView
        var btnGitHub: CardView
        var txtName: TextView
        var txtDescription: TextView
        var txtLanguage: TextView
        var txtStars: TextView
        var txtForks: TextView
        var txtwatchers: TextView


        init {
            txtName = itemView.findViewById<View>(R.id.txtName) as TextView
            txtDescription = itemView.findViewById<View>(R.id.txtDescription) as TextView
            txtLanguage = itemView.findViewById<View>(R.id.txtLanguage) as TextView
            txtStars = itemView.findViewById<View>(R.id.txtStars) as TextView
            txtForks = itemView.findViewById<View>(R.id.txtForks) as TextView
            txtwatchers = itemView.findViewById<View>(R.id.txtwatchers) as TextView
            card = itemView.findViewById<View>(R.id.card) as CardView
            btnGitHub = itemView.findViewById<View>(R.id.btnGitHub) as CardView
        }
    }
}