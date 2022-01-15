package com.zitherharpmusic.zhmtelevision.ui.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zitherharpmusic.zhmtelevision.R;
import com.zitherharpmusic.zhmtelevision.music.Song;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongListDialog extends BottomSheetDialogFragment {
    private static List<Song> songs;

    @NotNull
    @Contract("_ -> new")
    public static SongListDialog newInstance(List<Song> songs) {
        SongListDialog.songs = songs;
        return new SongListDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new SongListAdapter(getActivity(), songs));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
