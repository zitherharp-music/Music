package com.zitherharpmusic.zhmshort.ui.artist;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.Language;
import com.zitherharpmusic.zhmshort.model.MusicUtils;
import com.zitherharpmusic.zhmshort.model.PhotoQuality;
import com.zitherharpmusic.zhmshort.model.User;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;
import com.zitherharpmusic.zhmshort.util.MainUtils;
import com.zitherharpmusic.zhmshort.R;

public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        getSupportActionBar().hide();
        onViewCreated();
    }

    private void onViewCreated() {
        // TODO: find views
        ImageView photo = findViewById(R.id.photo);
        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        TextView description = findViewById(R.id.description);
        ImageButton followButton = findViewById(R.id.follow);
        ImageButton shareButton = findViewById(R.id.share);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        // TODO: load intent
        Artist artist = (Artist) getIntent().getSerializableExtra(ArtistActivity.class.getName());
        MainUtils.loadImage(this, photo, artist.getPhotoUrl(PhotoQuality.SMALL));
        // TODO: set text
        title.setText(artist.getName(Language.VIETNAMESE));
        subtitle.setText(artist.getName(Language.SIMPLIFIED_CHINESE));
        description.setText(artist.getDescription(Language.VIETNAMESE));
        // TODO: set button listeners
        User user = new User(this);
        if (user.isLoggedIn()) {
            if (!MusicUtils.contains(user.getArtists(), artist)) {
                followButton.setTag(getString(R.string.follow));
                followButton.setImageResource(R.drawable.ic_favourite_border_24);
            } else {
                followButton.setTag(getString(R.string.unfollow));
                followButton.setImageResource(R.drawable.ic_favourite_24);
            }
        }
        followButton.setOnClickListener(v -> {
            if (followButton.getTag().equals(getString(R.string.follow))) {
                user.put(User.ARTIST_IDS, artist.getId());
                followButton.setTag(getString(R.string.unfollow));
                followButton.setImageResource(R.drawable.ic_favourite_24);
                Toast.makeText(this, "Theo dõi thành công", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Bạn muốn bỏ theo dõi nghệ sĩ này?");
                alertDialog.setPositiveButton("Cho tôi nghĩ lại", (dlg, which) -> {
                    dlg.cancel();
                    Toast.makeText(this, "Khá lắm bạn trẻ", Toast.LENGTH_SHORT).show();
                });
                alertDialog.setNegativeButton("Tâm tôi đã quyết", (dlg, which) -> {
                    user.remove(User.ARTIST_IDS, artist.getId());
                    followButton.setTag(getString(R.string.follow));
                    followButton.setImageResource(R.drawable.ic_favourite_border_24);
                    Toast.makeText(this, "Đã bỏ theo dõi", Toast.LENGTH_SHORT).show();
                });
                alertDialog.show();
            }
        });
        photo.setOnClickListener(v -> {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ImageView photoView = new ImageView(this);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            MainUtils.loadImage(this, photoView, artist.getPhotoUrl(PhotoQuality.LARGE));
            ad.setView(photoView);
            ad.show();
        });
        shareButton.setOnClickListener(ListenerUtils.launchShareIntent(this, artist.getPlaylistUrl()));
        // TODO: others
        ArtistAdapter artistAdapter = new ArtistAdapter(this, artist);
        viewPager.setAdapter(artistAdapter);
        artistAdapter.attach(tabLayout, viewPager);
    }
}