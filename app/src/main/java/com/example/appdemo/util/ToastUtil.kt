package com.example.appdemo.util

import android.widget.Toast

object ToastUtil {

    fun show(text: String) {
        Toast.makeText(ApplicationUtil.getApplicationContext(), text, Toast.LENGTH_SHORT).show()
    }

}