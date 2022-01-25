package com.zitherharpmusic.zhmshort.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.zitherharpmusic.zhmshort.MainActivity;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.User;
import com.zitherharpmusic.zhmshort.util.ListenerUtils;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view;
        User user = ((MainActivity) requireActivity()).getUser();
        if (user.isLoggedIn()) {
            view = inflater.inflate(R.layout.fragment_user, container, false);
            TabLayout tabLayout = view.findViewById(R.id.tab_layout);
            ViewPager2 viewPager = view.findViewById(R.id.view_pager);
            UserAdapter userAdapter = new UserAdapter(this, user);
            viewPager.setAdapter(userAdapter);
            userAdapter.attach(tabLayout, viewPager);

            TextView title = view.findViewById(R.id.title);
            TextView subtitle = view.findViewById(R.id.subtitle);
            TextView description = view.findViewById(R.id.description);

            title.setText(user.get(User.NAME));
            subtitle.setText(String.format("ID: %s", user.get(User.ID)));

            title.setOnClickListener(v -> {
                EditText editText = new EditText(requireContext());
                editText.setHint(title.getText());
                editText.setPadding(10, 10, 10, 10);
                AlertDialog.Builder ad = new AlertDialog.Builder(requireContext());
                ad.setTitle("Sửa tên người dùng");
                ad.setPositiveButton("Lưu", (dlg, which) -> {
                    user.replace(User.NAME, editText.getText().toString());
                    Toast.makeText(requireContext(), "Đổi tên thành công", Toast.LENGTH_SHORT).show();
                });
                ad.setNegativeButton("Huỷ", (dlg, which) -> dlg.cancel());
                ad.setNeutralButton("Đặt lại", (dlg, which) -> editText.setText(getString(R.string.app_name)));
                ad.setView(editText);
                ad.show();
            });
            subtitle.setOnClickListener(ListenerUtils.copyToClipboard(requireContext(), user.get(User.ID)));
            description.setOnClickListener(v -> Toast.makeText(requireContext(),
                    "Tài khoản này do Zither Harp tự động tạo và chỉ có giá trị sử dụng trên thiết bị này",
                    Toast.LENGTH_LONG).show());
        } else {
            view = inflater.inflate(R.layout.fragment_login, container, false);
        }
        return view;
    }
}
