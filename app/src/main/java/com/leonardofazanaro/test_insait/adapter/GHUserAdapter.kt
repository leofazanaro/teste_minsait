package com.lofstudio.listadelivros.adapter

import android.content.Context

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.cardview.widget.CardView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.leonardofazanaro.test_insait.R
import com.leonardofazanaro.test_insait.domain.GHUsers
import com.lofstudio.minhascolecoes.interfaces.RecyclerViewButtonOnClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class GHUserAdapter(

    context: Context,
    private var mlist: MutableList<GHUsers>) :

    RecyclerView.Adapter<GHUserAdapter.MyViewHolder>() {


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
        val layout = R.layout.item_gh_user
        val v = mLayoutInflater.inflate(layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {


        myViewHolder.txtName.text = "${mlist[position].login}"



        if (mlist[position].avatar_url!!.isEmpty()) {

            myViewHolder.imgUser.setImageResource(R.drawable.no_user)

            myViewHolder.imgUser.setColorFilter(context.resources.getColor(R.color.grey))

            // myViewHolder.imgProgress.setVisibility(View.GONE)



        } else {

            myViewHolder.imgUser.setColorFilter(null)

            Picasso.get()
                .load(mlist[position].avatar_url).resize(120, 120)
                .centerCrop().into(myViewHolder.imgUser, object : Callback {
                    override fun onSuccess() {

                        // myViewHolder.imgProgress.setVisibility(View.GONE)
                    }

                    override fun onError(ex: Exception) {

                        myViewHolder.imgUser.setImageResource(R.drawable.no_user)

                        myViewHolder.imgUser.setColorFilter(context.resources.getColor(R.color.grey))
                    }
                })


        }





        myViewHolder.card.setOnClickListener { view ->
            if (recyclerViewButtonOnClickListener != null) {

                recyclerViewButtonOnClickListener!!.onClickListener(view, position, mlist[position])
            }
        }


        myViewHolder.card.setOnLongClickListener(object : View.OnLongClickListener {

            override fun onLongClick(view: View): Boolean {
                if (recyclerViewButtonOnClickListener != null) {
                    recyclerViewButtonOnClickListener!!.onLongClickListener(view, position, mlist[position])
                }
                return true
            }
        })

    }


    override fun getItemCount(): Int {
        return mlist.size
    }

    fun addItem(item: GHUsers) {
        mlist.add(item)
        notifyDataSetChanged()
    }

    fun setRecyclerViewButtonOnClickListener(recyclerViewButtonOnClickListener: RecyclerViewButtonOnClickListener?) {
        this.recyclerViewButtonOnClickListener = recyclerViewButtonOnClickListener
    }

    fun submitList(it: MutableList<GHUsers>) {

        mlist = it
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var card: CardView
        var txtName: TextView
        var imgUser: ImageView



        init {
            imgUser = itemView.findViewById<View>(R.id.imgUser) as ImageView
            txtName = itemView.findViewById<View>(R.id.txtName) as TextView
            card = itemView.findViewById<View>(R.id.card) as CardView
        }
    }
}