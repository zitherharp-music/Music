package com.zitherharpmusic.zhmshort.ui.artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Artist;

import java.util.List;

public class ArtistListDialog extends BottomSheetDialogFragment {
    private static List<Artist> artists;

    public static ArtistListDialog newInstance(List<Artist> artists) {
        ArtistListDialog.artists = artists;
        return new ArtistListDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new ArtistListAdapter(getActivity(), artists));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
