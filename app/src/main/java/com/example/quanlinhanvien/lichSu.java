package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class lichSu extends AppCompatActivity {

    LinearLayout xoalichsu;
    ListView lvLichSu;
    List<NhanVien> arrayList ;
    ListNhanVienAdapter adapter;
    String table_name = "lichsu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        lvLichSu = (ListView) findViewById(R.id.lvLichsu);
        xoalichsu = (LinearLayout) findViewById(R.id.xoatatca);
        arrayList = new ArrayList<>();
        adapter = new ListNhanVienAdapter(this,R.layout.item);
        lvLichSu.setAdapter(adapter);
        loadData();
        adapter.notifyDataSetChanged();
        xoalichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiaLogConfirm();

            }
        });


    }
    private void ShowDiaLogConfirm() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_delete_layout);
        Button btHuy = dialog.findViewById(R.id.lichsu_huy);
        Button btXoa = dialog.findViewById(R.id.lichsu_delete);
        dialog.show();
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database_NV database_nv = new Database_NV(lichSu.this);
                database_nv.deleteNV(table_name);
                adapter.notifyDataSetChanged();
                arrayList.clear();
                Toast.makeText(lichSu.this,"Đã xóa!",Toast.LENGTH_SHORT).show();
                database_nv.close();
                dialog.cancel();
            }
        });

    }

    private void loadData() {
        Database_NV database_nv = new Database_NV(this);
        database_nv.getAllNhanVien(table_name, arrayList);
        adapter.addAllStudent(arrayList);
        adapter.notifyDataSetChanged();
        database_nv.close();
    }


}
