package com.zitherharpmusic.zhmshort.ui.video;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Video;

import java.util.List;

public class VideoFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fullscreen);
        getSupportActionBar().hide();
        onViewCreated();
    }

    private void onViewCreated() {
        Class<?>[] classes = VideoFullscreenActivity.class.getClasses();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new VideoFullscreenAdapter(this,
                (List<Video>) getIntent().getSerializableExtra((classes[0].getName()))));
        recyclerView.scrollToPosition(getIntent().getIntExtra((classes[1].getName()), 0));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }
}
