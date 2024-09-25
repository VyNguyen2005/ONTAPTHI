/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import util.FileHelper;
import gui.FrmQLSinhVien;
/**
 *
 * @author nguye
 */
public class QLSinhVien {

    private ArrayList<SinhVien> dsSinhVien;

    public QLSinhVien() {
        dsSinhVien = new ArrayList<>();
    }

    public QLSinhVien(ArrayList<SinhVien> dsSinhVien) {
        this.dsSinhVien = dsSinhVien;
    }

    public ArrayList<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }

    public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
        this.dsSinhVien = dsSinhVien;
    }

    public void DocDanhSachSinhVien(String filename) {
        dsSinhVien.clear();
        ArrayList<String> data = FileHelper.readFileText(filename);
        for (String item : data) {
            String[] arr = item.split(";");
            SinhVien sv = new SinhVien();
            sv.setMaso(arr[0]);
            sv.setHoten(arr[1]);
            sv.setGioitinh(Boolean.valueOf(arr[2]));
            sv.setDiemtb(Double.valueOf(arr[3]));
            dsSinhVien.add(sv);
        }
    }

    public boolean GhiDanhSachSinhVien(String filename) {
        ArrayList<String> data = new ArrayList<>();
        for (SinhVien sinhVien : dsSinhVien) {
            String info = sinhVien.getMaso() + ";" + sinhVien.getHoten() + ";" + sinhVien.isGioitinh() + ";" + sinhVien.getDiemtb();
            data.add(info);
        }
        return FileHelper.writeFileText(filename, data);
    }

    public void themSV(SinhVien sv) {
        dsSinhVien.add(sv);
    }

    public void xoaSV(String maso) {
        for (int i = 0; i < dsSinhVien.size(); i++) {
            if (dsSinhVien.get(i).getMaso().equals(maso)) {
                dsSinhVien.remove(i);
                break;
            }
        }
    }

    public void SapXepTheoHocLuc() {
        Comparator<SinhVien> compare = (sv1, sv2) -> {
            return Double.compare(sv1.getDiemtb(), sv2.getDiemtb());
        };
        Collections.sort(dsSinhVien, compare);
    }
}
