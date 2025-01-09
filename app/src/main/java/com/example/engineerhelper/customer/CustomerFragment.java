package com.example.engineerhelper.customer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.engineerhelper.MainActivity;
import com.example.engineerhelper.R;
import com.example.engineerhelper.customer.CustomerAdapter;
import com.example.engineerhelper.dao.CustomerDAO;
import com.example.engineerhelper.entity.Customer;
import com.example.engineerhelper.utils.scanner.CustomScannerActivity;
import com.example.engineerhelper.utils.scanner.CustomViewfinderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class CustomerFragment extends Fragment {
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    private RecyclerView recyclerViewCustomers;
    private FloatingActionButton fabMain, fabAdd, fabSearch;
    private boolean isFabOpen = false;

    private Animation scaleDown, scaleUp;
    private Animation fadeIn, fadeOut;
    private CustomerAdapter customerAdapter;
    private CustomerDAO customerDAO;
    private List<Customer> customerList;

    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_management, container, false);

        // 注册 ActivityResultLauncher
        barcodeLauncher = registerForActivityResult(
                new ScanContract(),
                result -> {
                    // 处理扫描结果
                    if (result.getContents() == null) {
                        Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "取消");
                    } else {
                        String resultStr = result.getContents() + ", " + result.getBarcodeImagePath();
                        Toast.makeText(getContext(), resultStr, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, resultStr);
                    }
                }
        );


        // 初始化 FABs
        fabMain = view.findViewById(R.id.fabMain);
        fabAdd = view.findViewById(R.id.fabAdd);
        fabSearch = view.findViewById(R.id.fabSearch);

        // 初始化 DAO
        customerDAO = new CustomerDAO(getContext());

        // 设置 RecyclerView
        recyclerViewCustomers = view.findViewById(R.id.recyclerViewCustomers);
        recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshCustomerList();

        // 加载动画
        scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        // 设置主 FAB 点击事件
        fabMain.setOnClickListener(v -> animateFAB());

        // 设置添加 FAB 点击事件
        fabAdd.setOnClickListener(v -> {

//            launchCustomScanner(getView());

            animateFAB(); // 关闭菜单
            showAddEditCustomerDialog(null);
        });

        // 设置查找 FAB 点击事件
        fabSearch.setOnClickListener(v -> {
            animateFAB(); // 关闭菜单
            showSearchCustomerDialog();
        });

        return view;
    }

    /**
     * 启动自定义扫描界面的方法
     *
     * @param view 被点击的视图
     */
    public void launchCustomScanner(View view) {
        // 配置扫描时的基本参数
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE); // 仅二维码
        options.setPrompt("请将条形码置于取景框内扫描"); // 设置提示语
        options.setCameraId(0); // 0 后置摄像头，1 前置摄像头
        options.setBeepEnabled(true); // 开启成功声音
        options.setTimeout(60 * 1000); // 设置超时时间（毫秒）
        options.setBarcodeImageEnabled(true); // 是否保存图片
        options.setCaptureActivity(CustomScannerActivity.class); // 自定义的扫描界面

        // 启动自定义扫描二维码界面
        barcodeLauncher.launch(options);
    }

    // 刷新客户列表的方法
    private void refreshCustomerList() {
        customerList = customerDAO.getAllCustomers();
        customerAdapter = new CustomerAdapter(getContext(), customerList);
        recyclerViewCustomers.setAdapter(customerAdapter);

        // 设置点击监听
        customerAdapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Customer customer) {
                showAddEditCustomerDialog(customer);
            }

            @Override
            public void onDeleteClick(Customer customer) {
                showDeleteConfirmationDialog(customer);
            }
        });
    }

    // 动画效果方法
    private void animateFAB() {
        if (isFabOpen) {
            // 关闭 FAB 菜单
            fabMain.startAnimation(scaleDown);

            fabAdd.startAnimation(fadeOut);
            fabSearch.startAnimation(fadeOut);

            // 设置动画监听器以在动画结束后隐藏子FAB
            scaleDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // 无需操作
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fabAdd.setVisibility(View.GONE);
                    fabSearch.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // 无需操作
                }
            });

            isFabOpen = false;
        } else {
            // 打开 FAB 菜单
            fabMain.startAnimation(scaleDown);
            fabMain.startAnimation(scaleUp);

            fabAdd.setVisibility(View.VISIBLE);
            fabSearch.setVisibility(View.VISIBLE);

            fabAdd.startAnimation(fadeIn);
            fabSearch.startAnimation(fadeIn);

            isFabOpen = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // 显示添加或编辑客户对话框
    private void showAddEditCustomerDialog(final Customer customer) {
        // 使用 AlertDialog 构建对话框
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle(customer == null ? "添加客户" : "编辑客户");

        // 加载对话框布局
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_customer, null);
        builder.setView(dialogView);

        // 获取输入框
        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextAddress = dialogView.findViewById(R.id.editTextAddress);
        EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);

        // 如果是编辑模式，预填充数据
        if (customer != null) {
            editTextName.setText(customer.getName());
            editTextAddress.setText(customer.getAddress());
            editTextPhone.setText(customer.getPhone());
            editTextEmail.setText(customer.getEmail());
        }

        // 设置按钮
        builder.setPositiveButton(customer == null ? "添加" : "保存", null);
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        // 创建对话框
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            androidx.appcompat.app.AlertDialog alertDialog = (androidx.appcompat.app.AlertDialog) dialogInterface;
            androidx.appcompat.app.AlertDialog finalDialog = alertDialog;

            // 设置点击事件，防止自动关闭对话框
            alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();

                // 数据验证
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    editTextAddress.setError("地址不能为空");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("邮箱不能为空");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("无效的邮箱格式");
                    return;
                }

                if (customer == null) {
                    // 添加新客户
                    long id = customerDAO.addCustomer(name, address, phone, email);
                    if (id != -1) {
                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        refreshCustomerList();
                        finalDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "添加失败，可能邮箱重复", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 更新现有客户
                    int rows = customerDAO.updateCustomer(customer.getCid(), name, address, phone, email);
                    if (rows > 0) {
                        Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                        refreshCustomerList();
                        finalDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "更新失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        dialog.show();
    }

    // 显示删除确认对话框
    private void showDeleteConfirmationDialog(final Customer customer) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("删除客户");
        builder.setMessage("确定要删除客户 " + customer.getName() + " 吗？");

        builder.setPositiveButton("删除", (dialog, which) -> {
            customerDAO.deleteCustomer(customer.getCid());
            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            refreshCustomerList();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    // 显示查找客户对话框
    private void showSearchCustomerDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("查找客户");

        // 加载对话框布局
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_search_customer, null);
        builder.setView(dialogView);

        // 获取输入框
        EditText editTextSearch = dialogView.findViewById(R.id.editTextSearch);

        // 设置按钮
        builder.setPositiveButton("搜索", null);
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        // 创建对话框
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            androidx.appcompat.app.AlertDialog alertDialog = (androidx.appcompat.app.AlertDialog) dialogInterface;
            androidx.appcompat.app.AlertDialog finalDialog = alertDialog;

            // 设置点击事件，防止自动关闭对话框
            alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String query = editTextSearch.getText().toString().trim();

                if (TextUtils.isEmpty(query)) {
                    editTextSearch.setError("请输入搜索内容");
                    return;
                }

                // 执行搜索
                List<Customer> searchResults = customerDAO.searchCustomers(query);
                if (searchResults.isEmpty()) {
                    Toast.makeText(getContext(), "未找到相关客户", Toast.LENGTH_SHORT).show();
                } else {
                    customerAdapter.setCustomerList(searchResults);
                    Toast.makeText(getContext(), "找到 " + searchResults.size() + " 个客户", Toast.LENGTH_SHORT).show();
                    finalDialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCustomerList();
    }
}
