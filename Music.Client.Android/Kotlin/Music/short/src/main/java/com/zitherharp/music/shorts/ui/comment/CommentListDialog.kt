package com.zitherharp.music.shorts.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.shorts.databinding.CommentListDialogBinding

class CommentListDialog: BottomSheetDialogFragment() {
    private lateinit var binding: CommentListDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        CommentListDialogBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            sendComment.setOnClickListener {
                commentBox.setText(Spreadsheet.EMPTY_CHAR)
                Toast.makeText(it.context, "Không thể bình luận", Toast.LENGTH_SHORT).show()
            }
        }
    }
}