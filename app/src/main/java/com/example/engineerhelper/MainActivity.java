package com.example.engineerhelper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.engineerhelper.customer.CustomerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取底部导航栏按钮
        TextView customerButton = findViewById(R.id.Customer);
        TextView productButton = findViewById(R.id.Product);
        TextView personalButton = findViewById(R.id.Personal);

        // 设置点击事件
        customerButton.setOnClickListener(onClickListener);
        productButton.setOnClickListener(onClickListener);
        personalButton.setOnClickListener(onClickListener);

        // 默认显示 HomeFragment
        if (savedInstanceState == null) {
            replaceFragment(new CustomerFragment());
        }
    }

    View.OnClickListener onClickListener = v -> {
        Fragment fragment = null;
        if (v.getId() == R.id.Customer) {
            fragment = new CustomerFragment();
        } /*else if (v.getId() == R.id.Product) {
            fragment = new ProductFragment();
        } else if (v.getId() == R.id.Personal) {
            fragment = new PersonalFragment();
        }*/

        if (fragment != null) {
            replaceFragment(fragment);
        }
    };

    // 切换Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null); // 可选
        transaction.commit();
    }
}