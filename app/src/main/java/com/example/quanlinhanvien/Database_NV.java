package com.example.quanlinhanvien;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database_NV extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME = "QUANLYNHANVIEN.db";
    private static  final String TABLE_NAME = "nhanvien";
    private static  final String ID = "ID";
    private static  final String TEN = "TEN";
    private static  final String SDT = "SDT";
    private static  final String DIACHI = "DIACHi";
    private static  final String CONGVIEC = "CONGVIEC";
    private static  final String NGAYSINH = "NGAYSINH ";
    private static  final String PHONG = "PHONG";
    private static  final String GIOITINH = "GIOITINH";
    private static final String ANH = "ANH";
    private Context context;

    public Database_NV (@Nullable Context myContext) {
        super(myContext,DATABASE_NAME, null, 1);
        this.context = myContext;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = " CREATE TABLE IF NOT EXISTS nhanvien(ID INTEGER PRIMARY KEY AUTOINCREMENT,  TEN TEXT,SDT TEXT,DIACHI TEXT, NGAYSINH TEXT, CONGVIEC TEXT, PHONG TEXT,GIOITINH TEXT, ANH BLOB )";
        sqLiteDatabase.execSQL(sql);
        String sql1 = " CREATE TABLE IF NOT EXISTS lichsu(ID INTEGER PRIMARY KEY AUTOINCREMENT,  TEN TEXT,SDT TEXT,DIACHI TEXT, NGAYSINH TEXT, CONGVIEC TEXT, PHONG TEXT,GIOITINH TEXT, ANH BLOB )";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public void themNhanVien(NhanVien mNhanVien){
        SQLiteDatabase database = this.getWritableDatabase();

        String sql = "INSERT INTO nhanvien VALUES(null,?,?,?,?,?,?,?,?)";
        SQLiteStatement stm = database.compileStatement(sql);
        stm.clearBindings();

        stm.bindBlob(8,mNhanVien.getAnh());
        stm.bindString(7, String.valueOf(mNhanVien.getGioitinh()));
        stm.bindString(6,mNhanVien.getPhonglam());
        stm.bindString(5,mNhanVien.getCongviec());
        stm.bindString(4,mNhanVien.getNgaysinh());
        stm.bindString(3,mNhanVien.getDiaChi());
        stm.bindString(2,mNhanVien.getSdt());
        stm.bindString(1,mNhanVien.getTenNV());
        stm.executeInsert();
        database.close();
    }


    public ArrayList<NhanVien> getAllNhanVien(String table_name) {
        ArrayList<NhanVien>  studentList = new ArrayList<>();
        String query = "SELECT * FROM " + table_name;
        Log.e("tb",table_name);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        NhanVien nhanVien;
        if(cursor!=null && cursor.moveToFirst()) {
            do{
                nhanVien = new NhanVien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getBlob(8)
                );
                studentList.add(nhanVien);
            }

            while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }
// tìm kiếm danh sách những nhân viên có tên gần giống
    public ArrayList<NhanVien> Search_NV(String search)
    {
        ArrayList<NhanVien>  arrayList = new ArrayList<>();
        search.trim();
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE TEN LIKE '%"+search+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor!=null)
        {
            while(cursor.moveToNext()) {
                NhanVien nhanVien = new NhanVien(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getBlob(8)
                );
                //trả lại 1 mảng danh sách
                arrayList.add(nhanVien);
            }
        }
        cursor.close();
        return arrayList;
    }
// trả về 1 nhân viên
    public NhanVien nhanVien(int id){
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE ID = +'"+id+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        NhanVien nhanVien = null;
        if (cursor!=null)
        {
            cursor.moveToFirst();
                nhanVien = new NhanVien(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                        cursor.getBlob(8)
            );
        }
        return nhanVien;
    }
    // sua tt
    public void update(int id, NhanVien mNhanVien) {
        SQLiteDatabase db = this.getWritableDatabase();

        String ten = mNhanVien.getTenNV();
        String sdt = mNhanVien.getSdt();
        String diachi = mNhanVien.getDiaChi();
        String ngaysinh = mNhanVien.getNgaysinh();
        String congviec = mNhanVien.getCongviec();
        String phong = mNhanVien.getPhonglam();
        Integer gioitinh =mNhanVien.getGioitinh();
        byte[] anh = mNhanVien.getAnh();

       String sql = "UPDATE  nhanvien SET TEN = '"+ten+"', SDT ='"+sdt+"'  ,DIACHI = '"+diachi+"' , NGAYSINH = '"+ngaysinh+"' ," +
               "CONGVIEC= '"+congviec+"', PHONG ='"+phong+"' ,GIOITINH = '"+gioitinh+"' , ANH ='"+anh+"' WHERE ID = '"+id+"'";

       SQLiteStatement stm = db.compileStatement(sql);
       stm.executeUpdateDelete();
        db.close();
    }
    public void deleteNV(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name,null ,null);
        db.close();
    }

    public void delete_ID_NV(int manv,String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, ID + " = ?", new String[] { String.valueOf(manv) });
        db.close();
    }

}
