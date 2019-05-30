package com.abel.miequipo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.abel.miequipo.objetos.OnListenerSwipe;

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "OPEN FRAGMENT " + TAG);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public abstract void initArguments();

    protected void hideKeyBoard() {
        if (getActivity() == null)
            return;
        View view = getActivity().getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    public void deslizarParaAbrirEscaner(View view) {

        view.setOnTouchListener(new OnListenerSwipe(getContext()) {
            public void onSwipeTop() {
                 Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(getContext(), "LEFT", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeBottom() {
                Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
            }
        });
    }
}