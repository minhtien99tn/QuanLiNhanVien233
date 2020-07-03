package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout timKiem,themNV,danhSachNv,lichSu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        chuyenMan();
    }

    private void chuyenMan() {
        timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Tim_nv.class));
            }
        });
        danhSachNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Danh_Sach.class));
            }
        });
        themNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Them_Nv.class));
            }
        });
        lichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,lichSu.class));
            }
        });
    }

    private void anhXa() {
        timKiem = findViewById(R.id.timKiem);
        themNV = findViewById(R.id.themNV);
        danhSachNv = findViewById(R.id.danhSachNV);
        lichSu= findViewById(R.id.lichSu);
    }

}
