package com.example.realestateapp.util

import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.SearchView


class ViewEx {
    inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
        this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listener(newText.orEmpty())
                return true
            }
        })
    }

    fun showToast(mContext: Context?, msg: String?) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }
}