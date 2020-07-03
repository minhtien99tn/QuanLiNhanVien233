package com.example.quanlinhanvien;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChiTietNhanVien extends AppCompatActivity {
    private static final int REQUEST_CODE_FOLDER = 1234 ;
    EditText ten,sdt,ngaysinh,diachi,congviec,plamviec;
    RadioButton rdNam,rdNu;
    Button luu,nhaplai;
    String table_name = "lichsu";
    ImageView imv;
    ImageButton chitietThemAnh;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nhan_vien);
        connect();
        // lấy id từ danh sách
        SQLiteDatabase db = openOrCreateDatabase("QUANLYNHANVIEN.db", MODE_PRIVATE, null);
        String sql = " CREATE TABLE IF NOT EXISTS lichsu(ID INTEGER PRIMARY KEY AUTOINCREMENT,  TEN TEXT,SDT TEXT," +
                " DIACHI TEXT, NGAYSINH TEXT, CONGVIEC TEXT, PHONG TEXT,GIOITINH INTEGER , ANH BLOB )";
        db.execSQL(sql);
        db.close();
        getDataFromDanhSach();
        themAnh();
        setText();
        click();


    }

    private void setText() {
        Database_NV database_nv = new Database_NV(ChiTietNhanVien.this);
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
//        database_nv.themNhanVien(nv);
//        database_nv.close();
    }
// lấy id gửi từ bên danh sách nhân viên
    private void getDataFromDanhSach() {

            Bundle bundle = getIntent().getExtras();

            if (bundle!=null)
            {
                id = bundle.getInt("id",0);
               // Log.e("id nhan", String.valueOf(id));
            }
    }
    private void click() {
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database_NV database_nv = new Database_NV(ChiTietNhanVien.this);
                int gt = 1;
                String phongLam = plamviec.getText().toString();
                String ngaySinh = ngaysinh.getText().toString();
                String tenNV = ten.getText().toString();
                String diaChi = diachi.getText().toString();
                String SDT = sdt.getText().toString();
                String congViec = congviec.getText().toString();


                BitmapDrawable bitmapDrawable = (BitmapDrawable) imv.getDrawable();

                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);

                byte[] anh = byteArray.toByteArray();
//                if( imv == null){
//                    imv.setBackgroundResource(R.drawable.man);
//                }

                if(rdNu.isChecked()==true)
                {
                    gt =0;
                }
              //  Log.e("Ten",tenNV);
                NhanVien nv = new NhanVien(tenNV,SDT,diaChi,ngaySinh,congViec,phongLam,gt,anh);
                database_nv.update(id,nv);
                database_nv.close();
                Toast.makeText(ChiTietNhanVien.this,"Đã lưu!",Toast.LENGTH_SHORT).show();

            }
        });
         nhaplai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdt.setText("");
                ten.setText("");
                congviec.setText("");
                diachi.setText("");
                ngaysinh.setText("");
                plamviec.setText("");
                rdNam.setChecked(false);
                rdNu.setChecked(false);
            }
        });


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

        luu = (Button) findViewById(R.id.chitietLuu);
        nhaplai = (Button) findViewById(R.id.chitietNhapLai);

        imv = findViewById(R.id.chitietImpre);
        chitietThemAnh =findViewById(R.id.chitietThemANh);


    }
    private byte[] getByteArrayFromImageView(ImageView imv) {
        BitmapDrawable drawable = (BitmapDrawable) imv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void themAnh() {
        chitietThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
                Toast.makeText(ChiTietNhanVien.this,"Theemmm",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
