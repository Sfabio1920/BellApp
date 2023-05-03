package com.belluzzifioravanti.bellapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.belluzzifioravanti.bellapp.R;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.Date;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private MaterialFadeThrough materialFadeThrough = new MaterialFadeThrough();
    private CalendarView calendarView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialFadeThrough.setDuration(500);
        setEnterTransition(materialFadeThrough);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = requireActivity().findViewById(R.id.calendar);
        refresh();
    }

    private void refresh() {
        refreshLayout = requireActivity().findViewById(R.id.swipeRefreshHome);
        refreshLayout.setColorSchemeColors(ResourcesCompat.getColor(getResources(), R.color.primaryColor, null), ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null), ResourcesCompat.getColor(getResources(), R.color.primaryLightColor, null), ResourcesCompat.getColor(getResources(), R.color.secondaryLightColor, null));
        refreshLayout.setOnRefreshListener(() -> {
            calendarView.setDate(new Date().getTime());
            refreshLayout.setRefreshing(false);
        });
    }
}