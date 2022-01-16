package com.zitherharpmusic.zhmshort.ui.login;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.AccountPicker;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.User;

import java.util.Collections;

public class LoginFragment extends Fragment {
    private User user;

    private final ActivityResultLauncher<Intent> chooseAccount = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    String accountName = result.getData().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        //user.logIn(accountName);
                    }
                }
            });

    private final ActivityResultLauncher<String> requirePermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result -> {
                if (!result) {
                    Toast.makeText(requireContext(),
                            Resources.getSystem().getString(R.string.require_google_account),
                            Toast.LENGTH_LONG).show();
                }
    });

    private final View.OnClickListener loginByGoogle = v -> {
        Context context = v.getContext();
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            if (user.isLoggedIn()) {
                chooseAccount.launch(AccountPicker.newChooseAccountIntent(
                        new AccountPicker.AccountChooserOptions.Builder()
                        .setAllowableAccountsTypes(Collections.singletonList("com.google"))
                        .build()));
            } else {
                Toast.makeText(context, "Đã đăng nhập thành công với tài khoản " + user.get(User.ID), Toast.LENGTH_SHORT).show();
            }
        } else {
            requirePermission.launch(Manifest.permission.GET_ACCOUNTS);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user = new User(requireActivity());
        ImageButton mLoginByGoogle = view.findViewById(R.id.login_by_google_button);
        mLoginByGoogle.setOnClickListener(loginByGoogle);
    }
}
