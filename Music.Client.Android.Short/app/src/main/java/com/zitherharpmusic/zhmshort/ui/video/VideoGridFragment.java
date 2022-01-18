package com.zitherharpmusic.zhmshort.ui.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Video;

import java.util.List;

public class VideoGridFragment extends Fragment {
    private static List<Video> videos;

    public static VideoGridFragment newInstance(List<Video> videos) {
        VideoGridFragment.videos = videos;
        return new VideoGridFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new VideoGridAdapter(getActivity(), videos));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }
}
