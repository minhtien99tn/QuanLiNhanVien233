package com.example.quanlinhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Chi_tiet_sv2 extends AppCompatActivity {
        EditText ten,sdt,ngaysinh,diachi,congviec,plamviec;
        RadioButton rdNam,rdNu;
       // Button luu,nhaplai;
        String table_name = "lichsu";
        ImageView imv;
        int id;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chi_tiet_sv2);
            connect();
            // lấy id từ danh sách
            SQLiteDatabase db = openOrCreateDatabase("QUANLYNHANVIEN.db", MODE_PRIVATE, null);
            String sql = " CREATE TABLE IF NOT EXISTS lichsu(ID INTEGER PRIMARY KEY AUTOINCREMENT,  TEN TEXT,SDT TEXT," +
                    " DIACHI TEXT, NGAYSINH TEXT, CONGVIEC TEXT, PHONG TEXT,GIOITINH INTEGER , ANH BLOB )";
            db.execSQL(sql);
            db.close();
            getDataFromDanhSach();
            setText();

        }

        private void setText() {
            Database_NV database_nv = new Database_NV(com.example.quanlinhanvien.Chi_tiet_sv2.this);
            NhanVien nv = database_nv.nhanVien(id);
            ten.setText(nv.getTenNV());
            sdt.setText(nv.getSdt());
            ngaysinh.setText(nv.getNgaysinh());
            diachi.setText(nv.getDiaChi());
            congviec.setText(nv.getCongviec());
            plamviec.setText(nv.getPhonglam());
            byte[] t = nv.getAnh();
            Bitmap bp= BitmapFactory.decodeByteArray(t, 0, t.length);
            imv.setImageBitmap(bp);
            if (nv.getGioitinh()==0)
            {
                rdNu.setChecked(true);
            }
            else{
                rdNam.setChecked(true);
            }

        }
        // lấy id gửi từ bên danh sách nhân viên
        private void getDataFromDanhSach() {

            Bundle bundle = getIntent().getExtras();

            if (bundle!=null)
            {
                id = bundle.getInt("id",0);
                Log.e("id nhan", String.valueOf(id));
            }
        }

        private void connect() {
            ten = (EditText) findViewById(R.id.chitietTen);

            sdt = (EditText) findViewById(R.id.chitietSDT);
            ngaysinh = (EditText) findViewById(R.id.chitietNS);

            diachi = (EditText) findViewById(R.id.chitietDC);

            congviec = (EditText) findViewById(R.id.chitietCV);
            plamviec = (EditText) findViewById(R.id.chitietPLV);

            rdNam = (RadioButton) findViewById(R.id.chitietNam);
            rdNu = (RadioButton) findViewById(R.id.chitietNu);

            imv = findViewById(R.id.chitietImpre);

        }
        private byte[] getByteArrayFromImageView(ImageView impre) {
            BitmapDrawable drawable = (BitmapDrawable) impre.getDrawable();
            Bitmap bmp = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        }


    }