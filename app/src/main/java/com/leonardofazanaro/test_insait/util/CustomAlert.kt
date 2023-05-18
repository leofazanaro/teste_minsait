package com.leonardofazanaro.test_insait.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.leonardofazanaro.test_insait.R

class CustomAlert {

    companion object{

        fun menssage(
            context: Context,
            title: String?,
            msg: String?,
            titleButton: String?,
            positive: DialogInterface.OnClickListener?,
            cancelable: Boolean
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(msg)
            builder.setPositiveButton(titleButton, positive)
            builder.setCancelable(cancelable)

            //CRIA O DIOLOG
            val alertDialog = builder.create()
            alertDialog.show()
            val colorPositive = context.resources.getColor(R.color.black)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPositive)
        }
    }


}