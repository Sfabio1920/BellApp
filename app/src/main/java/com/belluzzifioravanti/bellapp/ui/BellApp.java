package com.belluzzifioravanti.bellapp.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import com.belluzzifioravanti.bellapp.R;
import com.belluzzifioravanti.bellapp.base.BaseActivity;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class BellApp extends BaseActivity {
    private SmoothBottomBar bottomNavigation;
    private FragmentManager fragmentTransaction = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private NewsFragment newsFragment = new NewsFragment();
    private PodcastFragment podcastFragment = new PodcastFragment();
    private UtilitiesFragment utilitiesFragment = new UtilitiesFragment();
    private MenuFragment menuFragment = new MenuFragment();
    private Fragment active = homeFragment;
    private int activeIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch (PreferenceManager.getDefaultSharedPreferences(this).getString("theme", "2")) {
            case "0":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "1":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "2":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
        bottomNavigation = findViewById(R.id.bottom_navigation);
        fragments();
    }

    public void addFragments() {
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container, newsFragment).hide(newsFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container, podcastFragment).hide(podcastFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container, utilitiesFragment).hide(utilitiesFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container, menuFragment).hide(menuFragment).commit();
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container, homeFragment).hide(homeFragment).commit();
    }

    public void openFragment(Fragment fragment) {
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).hide(active).show(fragment).commit();
        active = fragment;
    }

    private void fragments() {
        bottomNavigation.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            switch (i) {
                case 0:
                    openFragment(homeFragment);
                    return true;
                case 1:
                    openFragment(newsFragment);
                    return true;
                case 2:
                    openFragment(podcastFragment);
                    return true;
                case 3:
                    openFragment(utilitiesFragment);
                    return true;
                case 4:
                    openFragment(menuFragment);
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && utilitiesFragment.isVisible() && utilitiesFragment.onKeyDown()) {
            return true;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && newsFragment.isVisible() && newsFragment.onKeyDown()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addFragments();
        switch (bottomNavigation.getItemActiveIndex()) {
            case 0:
                openFragment(homeFragment);
                break;
            case 1:
                openFragment(newsFragment);
                break;
            case 2:
                openFragment(podcastFragment);
                break;
            case 3:
                openFragment(utilitiesFragment);
                break;
            case 4:
                openFragment(menuFragment);
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switch (savedInstanceState.getInt("ACTIVE_INDEX", 0)) {
            case 0:
                openFragment(homeFragment);
                break;
            case 1:
                openFragment(newsFragment);
                break;
            case 2:
                openFragment(podcastFragment);
                break;
            case 3:
                openFragment(utilitiesFragment);
                break;
            case 4:
                openFragment(menuFragment);
                break;
        }
        bottomNavigation.setItemActiveIndex(savedInstanceState.getInt("ACTIVE_INDEX", 0));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ACTIVE_INDEX", bottomNavigation.getItemActiveIndex());
    }

    @Override
    protected void onStop() {
        fragmentTransaction.beginTransaction().setReorderingAllowed(true).remove(homeFragment).remove(newsFragment).remove(podcastFragment).remove(utilitiesFragment).remove(menuFragment).commit();
        activeIndex = bottomNavigation.getItemActiveIndex();
        switch (PreferenceManager.getDefaultSharedPreferences(this).getString("chache", "0")) {
            case "1":
                utilitiesFragment.clearWebCache();
                break;
            case "2":
                AppUtils.deleteCache(getApplicationContext());
                utilitiesFragment.clearWebCache();
                break;
        }
        super.onStop();
    }
}