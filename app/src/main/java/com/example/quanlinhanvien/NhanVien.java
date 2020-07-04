package com.example.quanlinhanvien;

public class NhanVien {
    public  int maNV;
    public String tenNV;
    public String diaChi;
    public String ngaysinh;
    public String sdt;
    public String congviec;
    public String phonglam;
    public int gioitinh;
    public String anh;

    public NhanVien(int maNV, String tenNV, String diaChi, String ngaysinh, String sdt, String congviec, String phonglam, int gioitinh, String anh) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.congviec = congviec;
        this.phonglam = phonglam;
        this.gioitinh = gioitinh;
        this.anh = anh;
    }

    public NhanVien(String tenNV, String diaChi, String ngaysinh, String sdt, String congviec, String phonglam, int gioitinh, String anh) {
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.congviec = congviec;
        this.phonglam = phonglam;
        this.gioitinh = gioitinh;
        this.anh = anh;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCongviec() {
        return congviec;
    }

    public void setCongviec(String congviec) {
        this.congviec = congviec;
    }

    public String getPhonglam() {
        return phonglam;
    }

    public void setPhonglam(String phonglam) {
        this.phonglam = phonglam;
    }

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
