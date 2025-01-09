package com.example.engineerhelper.utils.scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.engineerhelper.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;


public class CustomScannerActivity extends Activity implements DecoratedBarcodeView.TorchListener {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeView;
    private ImageButton btnFlashOn, btnFlashOff, btnCancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        // 初始化按钮和扫描视图
        btnFlashOn = findViewById(R.id.btn_flash_on);
        btnFlashOff = findViewById(R.id.btn_flash_off);
        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        barcodeView.setTorchListener(this);
        btnCancel = findViewById(R.id.btn_cancel);

        // 检查设备是否支持闪光灯
        if (!hasFlash()) {
            btnFlashOn.setVisibility(View.GONE);
        }

        // 按钮点击监视器
        btnFlashOn.setOnClickListener(view -> {
            barcodeView.setTorchOn();
        });

        btnFlashOff.setOnClickListener(view -> {
            barcodeView.setTorchOff();
        });

        // 取消按钮
        btnCancel.setOnClickListener(view -> {
            // 关闭扫描页面
            finish();
        });


        // 绑定 CaptureManager 与 DecoratedBarcodeView，处理扫描结果、回调等逻辑
        capture = new CaptureManager(this, barcodeView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.setShowMissingCameraPermissionDialog(false);
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * 检查手机是否带闪光灯
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {
        btnFlashOff.setVisibility(View.VISIBLE);
        btnFlashOn.setVisibility(View.GONE);
    }

    @Override
    public void onTorchOff() {
        btnFlashOff.setVisibility(View.GONE);
        btnFlashOn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
