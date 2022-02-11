package com.zitherharp.music.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.core.Spreadsheet

open class RecyclerViewAdapter<T>(private val context: Context,
                                  private val items: List<Spreadsheet>):
    RecyclerView.Adapter<T>() where T: RecyclerView.ViewHolder {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = items.size
}