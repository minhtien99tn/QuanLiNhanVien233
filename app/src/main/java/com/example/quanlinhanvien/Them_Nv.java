package com.example.quanlinhanvien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.InputMismatchException;

public class Them_Nv extends AppCompatActivity {
    private static final int REQUEST_CODE_FOLDER = 123 ;
    private EditText editTen,edtSDT,editDiaChi,editCongViec,editNgaySinh,editPhongLam;
    private RadioButton radNam,radNu;
    Button butNhapLai, butNhap;
    ImageButton btThemANh;
    ImageView impre;
    String imgSelected = "";
    final  int REQUEST_CHOOSE_PHOTO = 321;
    Database_NV database_nv;
    String table_name = "nhanvien";
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them__nv);
        mContext = this;
        imgSelected = "";
        anhXa();
        database_nv = new Database_NV(this);
        Event();
        themAnh();
    }

    private void themAnh() {
        btThemANh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                imgSelected = Utility.copyImageToCache(mContext, inputStream);
                Bitmap img = BitmapFactory.decodeStream(inputStream);
                impre.setImageBitmap(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhXa() {
        btThemANh =findViewById(R.id.btThemANh);
        impre = findViewById(R.id.impre);
        editTen = findViewById(R.id.editTen);
        edtSDT =  findViewById(R.id.edtSDT);
        editDiaChi = findViewById(R.id.editDiaChi);
        editCongViec= findViewById(R.id.editCongViec);
        editNgaySinh= findViewById(R.id.editNgaySinh);
        editPhongLam = findViewById(R.id.editPhongLam);
        radNam= findViewById(R.id.radNam);
        radNu = findViewById(R.id.radNu);
        butNhap = findViewById(R.id.butNhap);
        butNhapLai = findViewById(R.id.butNhapLai);
    }
    private  void Event(){
        butNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phongLam ;
                String ngaySinh;
                String tenNV;
                String diaChi ;
                String SDT ;
                String congViec;
                int gt = 0;
                try {
                     phongLam = editPhongLam.getText().toString();
                     ngaySinh = editNgaySinh.getText().toString();
                     tenNV = editTen.getText().toString().trim();
                     diaChi = editDiaChi.getText().toString();
                     SDT = edtSDT.getText().toString();
                     congViec = editCongViec.getText().toString();
                }catch (InputMismatchException e)
                {
                     phongLam ="";
                     ngaySinh = "";
                     tenNV = "";
                     diaChi ="";
                     SDT = "";
                     congViec ="";
                }

//                byte[] anh = getByteArrayFromImageView(impre);

                if (TextUtils.isEmpty(imgSelected)){
                    imgSelected = "anh2.PNG";
                    Utility.copyDrawableToCache(mContext, mContext.getResources().getDrawable(R.drawable.anh2), imgSelected);
                }

                if (radNam.isChecked() == true)
                    gt = 1;
                Log.e("Ten", tenNV);
                if (tenNV.equals("")) {
                    editTen.setError("Vui lòng nhập dữ liệu!");
                } else {
                    NhanVien nv = new NhanVien(tenNV, SDT, diaChi, ngaySinh, congViec, phongLam, gt, imgSelected);
                    database_nv.themNhanVien(nv, table_name);
                    database_nv.close();
                    imgSelected = "";
                    Toast.makeText(Them_Nv.this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show();
                }
            }
        });
        butNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Them_Nv.this,"Đã nhập lại",Toast.LENGTH_SHORT).show();
                edtSDT.setText("");
                editTen.setText("");
                editCongViec.setText("");
                editDiaChi.setText("");
                editNgaySinh.setText("");
                editPhongLam.setText("");
                radNam.setChecked(false);
                radNu.setChecked(false);
            }
        });

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
