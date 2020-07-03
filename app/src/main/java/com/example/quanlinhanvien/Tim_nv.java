package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Tim_nv extends AppCompatActivity {

    public EditText getSearch;
    public ImageButton imgSearch;
    public String search;
    public ListView lvDanhSach;
    public ArrayList<NhanVien> arrayList;
    public ListNhanVienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_nv);

        lvDanhSach =  findViewById(R.id.listTimNV);
        getSearch =  findViewById(R.id.edtSearch);
        imgSearch =  findViewById(R.id.imgSearch);

        Search();
        clickListView();
    }

    private void clickListView() {
        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Tim_nv.this,ChiTietNhanVien.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",arrayList.get(position).getMaNV());
                intent.putExtras(bundle);
                startActivity(intent);
                Log.e("id gui", String.valueOf(arrayList.get(position).getMaNV()));
            }
        });

    }


    private void Search() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = getSearch.getText().toString();
                Log.e("id",search);
                setList(search);
            }
        });
    }

    private void setList(String search) {
        Database_NV database_nv = new Database_NV(Tim_nv.this);
        arrayList = new ArrayList<>();

        arrayList = database_nv.Search_NV(search);

        adapter = new ListNhanVienAdapter(Tim_nv.this,R.layout.item,arrayList);
        lvDanhSach.setAdapter(adapter);
        database_nv.close();
    }
}