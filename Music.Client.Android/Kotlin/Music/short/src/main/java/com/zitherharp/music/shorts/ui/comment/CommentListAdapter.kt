package com.zitherharp.music.shorts.ui.comment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.databinding.AudioListContentBinding
import com.zitherharp.music.shorts.databinding.CommentListDialogBinding
import com.zitherharp.music.shorts.ui.audio.AudioListAdapter
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class CommentListAdapter(private val context: Context,
                         private val audios: List<Audio>):
    RecyclerViewAdapter<AudioListAdapter.ViewHolder>(context, audios)  {

    inner class ViewHolder(binding: CommentListDialogBinding):
        RecyclerView.ViewHolder(binding.root) {

    }
}