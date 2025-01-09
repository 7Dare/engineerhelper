package com.example.engineerhelper;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.WalkPath;
import com.example.engineerhelper.adapter.DriveSegmentListAdapter;
import com.example.engineerhelper.adapter.RideSegmentListAdapter;
import com.example.engineerhelper.adapter.WalkSegmentListAdapter;
import com.example.engineerhelper.databinding.ActivityRouteDetailBinding;
import com.example.engineerhelper.utils.MapUtil;

public class RouteDetailActivity extends AppCompatActivity {

    private ActivityRouteDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRouteDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        switch (intent.getIntExtra("type", 0)) {
            case 0://步行
                walkDetail(intent);
                break;
            case 1://骑行
                rideDetail(intent);
                break;
            case 2://驾车
                driveDetail(intent);
                break;
            case 3://公交

                break;
            default:
                break;
        }
    }
    /**
     * 步行详情
     * @param intent
     */
    private void walkDetail(Intent intent) {
        binding.toolbar.setTitle("步行路线规划");
        WalkPath walkPath = intent.getParcelableExtra("path");
        String dur = MapUtil.getFriendlyTime((int) walkPath.getDuration());
        String dis = MapUtil.getFriendlyLength((int) walkPath.getDistance());
        binding.tvTime.setText(dur + "(" + dis + ")");
        binding.rvRouteDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRouteDetail.setAdapter(new WalkSegmentListAdapter(walkPath.getSteps()));
    }

    /**
     * 骑行详情
     * @param intent
     */
    private void rideDetail(Intent intent) {
        binding.toolbar.setTitle("骑行路线规划");
        RidePath ridePath = intent.getParcelableExtra("path");
        String dur = MapUtil.getFriendlyTime((int) ridePath.getDuration());
        String dis = MapUtil.getFriendlyLength((int) ridePath.getDistance());
        binding.tvTime.setText(dur + "(" + dis + ")");
        binding.rvRouteDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRouteDetail.setAdapter(new RideSegmentListAdapter(ridePath.getSteps()));
    }
    /**
     * 驾车详情
     * @param intent
     */
    private void driveDetail(Intent intent) {
        binding.toolbar.setTitle("驾车路线规划");
        DrivePath drivePath = intent.getParcelableExtra("path");
        String dur = MapUtil.getFriendlyTime((int) drivePath.getDuration());
        String dis = MapUtil.getFriendlyLength((int) drivePath.getDistance());
        binding.tvTime.setText(dur + "(" + dis + ")");
        binding.rvRouteDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRouteDetail.setAdapter(new DriveSegmentListAdapter(drivePath.getSteps()));
    }


}
