package com.lofstudio.minhascolecoes.interfaces

import android.view.View

interface RecyclerViewButtonOnClickListener {
    fun onClickListener(view: View?, position: Int, `object`: Any?)
    fun onLongClickListener(view: View?, position: Int, `object`: Any?)
    fun onActionClick(view: View?, position: Int, `object`: Any?, button: Int)
}