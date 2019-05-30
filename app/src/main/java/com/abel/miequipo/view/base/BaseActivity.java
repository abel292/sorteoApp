package com.abel.miequipo.view.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abel.miequipo.BuildConfig;
import com.abel.miequipo.R;


public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    protected Fragment currentFragment;
    private AlertDialog progressBarDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OPEN ACTIVITY " + TAG);
        super.onCreate(savedInstanceState);
    }

    public void replaceFragment(@NonNull Fragment fragment, @NonNull int layoutContainer, boolean addStack) {
        if (fragment.isVisible()) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "El fragmento ya se encuentra visible");
            return;
        }
        FragmentTransaction obj = getSupportFragmentManager()
                .beginTransaction()
                .replace(layoutContainer, fragment);
        if (addStack) obj.addToBackStack(fragment.getClass().getSimpleName());
        obj.commit();
        currentFragment = fragment;
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentFragment != null)
            currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    protected boolean checkPermission(String permissionCheck, @NonNull String[] permissionsList, @NonNull int[] grantResults) {

        for (int i = 0; i < permissionsList.length; i++) {

            if (permissionCheck.equals(permissionsList[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }

        }

        return false;
    }
}