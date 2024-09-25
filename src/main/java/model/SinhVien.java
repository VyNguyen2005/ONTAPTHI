/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguye
 */
public class SinhVien {
    private String maso;
    private String hoten;
    private boolean gioitinh;
    private double diemtb;

    public SinhVien() {
    }

    public SinhVien(String maso, String hoten, boolean gioitinh, double diemtb) {
        this.maso = maso;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.diemtb = diemtb;
    }

    public SinhVien(String maso) {
        this.maso = maso;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public double getDiemtb() {
        return diemtb;
    }

    public void setDiemtb(double diemtb) {
        this.diemtb = diemtb;
    }
    
    public String getHocLuc(){
        String kq = "";
        if(diemtb<5)
       {
          kq="Yếu";
       }else if(diemtb<6.5)
       {
           kq="Trung bình";
       }else if(diemtb <7.5)
       {
           kq="Khá";
       }else if(diemtb<9)
       {
           kq="Giỏi";
       }else
       {
           kq="Xuất sắc";
       }       
       return kq;
    }
}
