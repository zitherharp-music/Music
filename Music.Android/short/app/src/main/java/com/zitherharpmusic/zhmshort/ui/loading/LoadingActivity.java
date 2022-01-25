package com.zitherharpmusic.zhmshort.ui.loading;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zitherharpmusic.zhmshort.MainActivity;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.MusicProvider;
import com.zitherharpmusic.zhmshort.util.MainUtils;

public class LoadingActivity extends AppCompatActivity {
    private boolean isLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();
        onLoad(getCurrentFocus());
    }

    public void onLoad(View v) {
        if (MainUtils.isDeviceOnline(this)) {
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
