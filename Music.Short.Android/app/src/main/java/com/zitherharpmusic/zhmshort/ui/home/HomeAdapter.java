package com.zitherharpmusic.zhmshort.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.MusicProvider;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.ui.empty.EmptyFragment;
import com.zitherharpmusic.zhmshort.ui.login.LoginFragment;
import com.zitherharpmusic.zhmshort.model.User;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.video.VideoFullscreenFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeAdapter extends FragmentStateAdapter {
    private final Fragment fragment;
    private final User user;
    private List<Video> videos;

    public HomeAdapter(@NonNull Fragment fragment, User user) {
        super(fragment);
        this.fragment = fragment;
        this.user = user;
        videos = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (user.isLoggedIn()) {
                    for (Artist artist : user.getArtists()) {
                        videos.addAll(artist.getVideos());
                    }
                } else {
                    return new LoginFragment();
                }
                break;
            case 2:
                videos = MusicProvider.getVideos();
                Collections.shuffle(videos);
                break;
            case 1:
                videos = MusicProvider.getVideos();
                break;
        }
        if (videos.size() > 0) {
            return VideoFullscreenFragment.newInstance(videos);
        } else {
            return EmptyFragment.newInstance(fragment.getString(R.string.empty));
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void attach(TabLayout tabLayout, ViewPager2 viewPager) {
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(fragment.getString(R.string.follow));
            } else if (position == 1) {
                tab.setText(fragment.getString(R.string.recommend));
            } else {
                tab.setText(fragment.getString(R.string.recent));
            }
        }).attach();
        tabLayout.bringToFront();
        tabLayout.getTabAt(1).select();
    }
}