package com.zitherharp.music.photo.ui.photo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.core.Pinterest
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoMenuDialogBinding

class PhotoMenuDialog: BottomSheetDialogFragment() {
    private lateinit var binding: PhotoMenuDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        PhotoMenuDialogBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            Photo.repository[tag]?.let { photo ->
                downloadView.setOnClickListener {

                }
                shareView.setOnClickListener {
                    startActivity(Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, photo.getImageUrl(Pinterest.Image.ORIGINALS))
                        }, getString(com.zitherharp.music.R.string.share)))
                }
                blockView.setOnClickListener {

                }
            }
        }
    }
}