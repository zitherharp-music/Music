package com.zitherharpmusic.zhmtelevision.ui.start;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zitherharpmusic.zhmtelevision.MainActivity;
import com.zitherharpmusic.zhmtelevision.R;
import com.zitherharpmusic.zhmtelevision.music.MusicProvider;
import com.zitherharpmusic.zhmtelevision.music.MusicUtils;

public class StartActivity extends AppCompatActivity {
    private boolean isLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        getSupportActionBar().hide();
        onLoad(getCurrentFocus());
    }

    public void onLoad(View v) {
        if (MusicUtils.isNetworkConnected(this)) {
            if (!isLoaded) {
                isLoaded = true;
                AsyncTask.execute(() -> {
                    MusicProvider.initialize();
                    startActivity(new Intent(this, MainActivity.class));
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
