package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Danh_Sach extends AppCompatActivity {
    public ListView lvDanhSach;
    public ListNhanVienAdapter adapter;
    private ArrayList<NhanVien> arrayList;
    String table_name = "nhanvien";
    public int maNV ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh__sach);
        lvDanhSach =  findViewById(R.id.lvDanhSach);
       loadData(table_name);
       clickListView();

    }

    private void clickListView() {
        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 int maNV = arrayList.get(position).getMaNV();
                ShowDiaLogConfirm(maNV);
            }
        });

    }

    private void ShowDiaLogConfirm(final  int maNV1) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button btSua = dialog.findViewById(R.id.lvSua);
        Button btXoa = dialog.findViewById(R.id.lvXoa);
        Button btchitiet = dialog.findViewById(R.id.lvchitiet);
        dialog.show();

        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Danh_Sach.this,ChiTietNhanVien.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",maNV1);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.e("id gui", String.valueOf(maNV1));
                dialog.cancel();
                adapter.notifyDataSetChanged();

            }
        });

        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(maNV1);
                dialog.cancel();
            }
        });

        btchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Danh_Sach.this,Chi_tiet_sv2.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",maNV1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void Xoa(int mmanv) {

        Database_NV database_nv = new Database_NV(this);
        database_nv.delete_ID_NV(mmanv,table_name);
        adapter.notifyDataSetChanged();
        arrayList.clear();
        loadData(table_name);

    }

    private void loadData(String table_name) {

        Database_NV database_nv = new Database_NV(this);
        arrayList  =  database_nv.getAllNhanVien(table_name);
        Log.e("id",String.valueOf( arrayList.get(0).getMaNV()));
        adapter = new ListNhanVienAdapter(this,R.layout.item,arrayList);
        lvDanhSach.setAdapter(adapter);
        database_nv.close();
    }
}
