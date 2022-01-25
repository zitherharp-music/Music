package com.zitherharpmusic.zhmshort.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Music;

import java.util.List;

public class ItemListFragment extends Fragment {
    private static List<? extends Music> musics;

    public static ItemListFragment newInstance(List<? extends Music> musics) {
        ItemListFragment.musics = musics;
        return new ItemListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new ItemListAdapter(getActivity(), musics));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
