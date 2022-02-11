package com.zitherharp.music.shorts.ui.shorts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getId
import com.zitherharp.music.shorts.databinding.ShortGridContentBinding

class ShortGridAdapter(private val context: Context,
                       private val shorts: List<Short>):
    RecyclerView.Adapter<ShortGridAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ShortGridContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val shortImage = binding.shortImage
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = shorts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ShortGridContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ShortFullscreenActivity::class.java).apply {
                    putExtra(ShortFullscreenActivity::class.simpleName, position)
                    putExtra(ShortFullscreenActivity::class.qualifiedName, shorts.getId())
                })
            }
            with(shorts[position]) {
                shortImage.setImageUrl(getImageUrl(Youtube.Image.HQDEFAULT))
            }
        }
    }
}