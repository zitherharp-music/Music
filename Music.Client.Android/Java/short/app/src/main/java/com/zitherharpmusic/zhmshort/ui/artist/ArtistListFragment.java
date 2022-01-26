package com.zitherharpmusic.zhmshort.ui.artist;

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
import com.zitherharpmusic.zhmshort.model.Artist;

import java.util.List;

public class ArtistListFragment extends Fragment {
    private static List<Artist> artists;

    public static ArtistListFragment newInstance(List<Artist> artists) {
        ArtistListFragment.artists = artists;
        return new ArtistListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new ArtistListAdapter(getActivity(), artists));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
