package com.rikesh.cinemaghar.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rikesh.cinemaghar.R;

public class Funct {
    public static boolean isNetworkConnected(Activity a) {
        ConnectivityManager cm = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void setImage(Context cx, ImageView imageView, Object imageFile) {
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.mipmap.logo);
        Glide.with(cx)
                .setDefaultRequestOptions(defaultOptions)
                .load(imageFile)
                .into(imageView);
    }

    public static void SwipeRefresh(Activity a, View root) {
        SwipeRefreshLayout mSwipeRefreshLayout = root.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setNestedScrollingEnabled(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.edittext);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            RefreshActivity(a);
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    public static void RefreshActivity(Activity a) {
        a.overridePendingTransition(0, 0);
        a.finish();
        a.overridePendingTransition(0, 0);
        a.startActivity(a.getIntent());
        a.overridePendingTransition(0, 0);
    }
}
