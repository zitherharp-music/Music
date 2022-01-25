package com.zitherharpmusic.zhmshort.ui.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Video;

import java.util.List;

public class VideoFullscreenFragment extends Fragment {
    private static List<Video> videos;

    public static VideoFullscreenFragment newInstance(List<Video> videos) {
        VideoFullscreenFragment.videos = videos;
        return new VideoFullscreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_fullscreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new VideoFullscreenAdapter(getActivity(), videos));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }
}
