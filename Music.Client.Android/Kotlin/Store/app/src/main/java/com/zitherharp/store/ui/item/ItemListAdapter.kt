package com.zitherharp.store.ui.item

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.store.Extension.setImageUrl
import com.zitherharp.store.databinding.ItemListContentBinding
import com.zitherharp.store.model.Item

class ItemListAdapter(
    private val context: Context,
    private val items: List<Item>):
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemListContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.image
        val title = binding.title
        val subtitle = binding.subtitle
        val version = binding.version
        val size = binding.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemListContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val item = items[position]
            title.text = item.name
            subtitle.text = item.description
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailActivity::class.java.name, item.id)
                })
            }
            item.field?.let {
                if (item.downloadUrl.isNotEmpty()) {
                    version.text = String.format("v${it.version}")
                    size.text = String.format("${it.size} MB")
                } else {
                    version.text = "(chưa được phát hành)"
                }
            }
            item.image?.let {
                image.setImageUrl(it.getIconUrl(100))
            }
        }
    }

    override fun getItemCount() = items.size
}