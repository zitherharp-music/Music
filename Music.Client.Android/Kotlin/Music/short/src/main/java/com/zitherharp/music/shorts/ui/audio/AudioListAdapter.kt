package com.zitherharp.music.shorts.ui.audio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.databinding.AudioListContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class AudioListAdapter(private val context: Context,
                       private val audios: List<Audio>):
    RecyclerViewAdapter<AudioListAdapter.ViewHolder>(context, audios) {

    inner class ViewHolder(binding: AudioListContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val audioImage = binding.audioImage
        val audioVietnameseName = binding.audioVietnameseName
        val audioChineseName = binding.audioChineseName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            AudioListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(audios[position]) {
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, AudioDetailActivity::class.java).apply {
                        putExtra(AudioDetailActivity::class.simpleName, id)
                    })
                }
                audioImage.setImageUrl(getImageUrl(Youtube.Image.MQDEFAULT))
                audioVietnameseName.text = getName(Language.VIETNAMESE)
                audioChineseName.text = getName(Language.CHINESE)
            }
        }
    }
}