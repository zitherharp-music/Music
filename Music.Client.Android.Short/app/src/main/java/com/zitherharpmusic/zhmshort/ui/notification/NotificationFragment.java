package com.zitherharpmusic.zhmshort.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zitherharpmusic.zhmshort.MainActivity;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.User;

public class NotificationFragment extends Fragment {
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        user = ((MainActivity) requireActivity()).getUser();
        if (user.isLoggedIn()) {
            if (user.getArtists().size() > 0) {
                return inflater.inflate(R.layout.fragment_notification, container, false);
            } else {
                return inflater.inflate(R.layout.fragment_empty, container, false);
            }
        } else {
            return inflater.inflate(R.layout.fragment_login, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (user.isLoggedIn()) {
            if (user.getArtists().size() > 0) {
                RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(new NotificationAdapter(this, user));
            } else {
                TextView textView = view.findViewById(R.id.empty);
                textView.setText(getString(R.string.no_notification));
            }
        } else {
            // TODO: login fragment
        }
    }
}