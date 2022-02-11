package com.zitherharp.music.shorts.ui.audio

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.Spreadsheet.Companion.SPLIT_CHAR
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.AudioListContentBinding
import com.zitherharp.music.shorts.databinding.ShortGridContentBinding

class AudioListAdapter(private val context: Context?,
                       private val audios: List<Audio>):
    RecyclerView.Adapter<AudioListAdapter.ViewHolder>() {

    inner class ViewHolder(binding: AudioListContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val audioImage = binding.audioImage
        val audioVietnameseName = binding.audioVietnameseName
        val audioChineseName = binding.audioChineseName
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = audios.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            AudioListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(audios[position]) {
                audioImage.setImageUrl(getImageUrl(Youtube.Image.DEFAULT))
                audioVietnameseName.text = getName(Language.VIETNAMESE)
                audioChineseName.text = getName(Language.CHINESE)
            }
        }
    }
}