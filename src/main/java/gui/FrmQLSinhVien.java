/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.QLSinhVien;
import model.SinhVien;

/**
 *
 * @author nguye
 */
public class FrmQLSinhVien extends JFrame {

    private JTable tblSinhVien;
    private JButton btThem, btXoa, btChinhSua, btTimKiem;
    private JButton btDocFile, btGhiFile;

    private DefaultTableModel model;
    private JTextField txtMaSo, txtHoTen, txtDTB;

    private JRadioButton rdNam, rdNu;
    private JCheckBox chkSapXep;

    private static final String FILE_NAME = "data.txt";

    private QLSinhVien qlsv = new QLSinhVien();

    public FrmQLSinhVien(String title) {
        super(title);
        createGUI();
        createEventProcess();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createGUI() {

        //tạo JTable
        String[] columnNames = {"Mã số", "Họ tên", "Phái", "ĐTB", "Xếp loại"};
        model = new DefaultTableModel(null, columnNames);
        tblSinhVien = new JTable(model);
        //tạo thành phần quản lý cuộn cho Jtable
        JScrollPane scrollTable = new JScrollPane(tblSinhVien);

        //tạo các điều khiển nhập liệu  và các nút lệnh
        JPanel p = new JPanel();
        p.add(new JLabel("Mã sinh viên"));
        p.add(txtMaSo = new JTextField(5));
        p.add(new JLabel("Họ tên"));
        p.add(txtHoTen = new JTextField(10));

        p.add(rdNam = new JRadioButton("Nam"));
        p.add(rdNu = new JRadioButton("Nữ"));

        ButtonGroup btgPhai = new ButtonGroup();
        btgPhai.add(rdNam);
        btgPhai.add(rdNu);

        p.add(new JLabel("Điểm TB"));
        p.add(txtDTB = new JTextField(10));

        p.add(btDocFile = new JButton("Đọc File"));
        p.add(btThem = new JButton("Thêm"));
        p.add(btXoa = new JButton("Xoá"));
        p.add(btChinhSua = new JButton("Chỉnh sửa"));
        p.add(btTimKiem = new JButton("Tìm kiếm"));
        p.add(btGhiFile = new JButton("Ghi File"));

        JPanel p2 = new JPanel();
        p2.add(chkSapXep = new JCheckBox("Sắp xếp theo học lực"));

        //add các thành phần vào cửa sổ
        add(p, BorderLayout.NORTH);
        add(scrollTable, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);
    }

    private void createEventProcess() {
        btDocFile.addActionListener((e) -> {
            qlsv.DocDanhSachSinhVien(FILE_NAME);
            LoadDataToTable();
        });
        btThem.addActionListener((e) -> {
            String maSo = txtMaSo.getText();
            double diemTB = Double.parseDouble(txtDTB.getText());
            boolean exists = false;

            String error = "";
            if (maSo.length() == 0) {
                error = "Chưa nhập mã sinh viên";
            }
            if (txtHoTen.getText().length() == 0) {
                error += "\nChưa nhập họ tên sinh viên";
            }
            if (diemTB < 0 || diemTB > 10) {
                error += "\nĐiểm trung bình phải nằm trong khoảng từ 0 đến 10!";
            }

            if (error.length() > 0) {
                JOptionPane.showMessageDialog(this, error, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (SinhVien sinhvien : qlsv.getDsSinhVien()) {
                if (sinhvien.getMaso().equals(maSo)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                JOptionPane.showMessageDialog(this, "Thao tác thêm thất bại do trùng mã sinh viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                SinhVien sv = new SinhVien();
                sv.setMaso(maSo);
                sv.setHoten(txtHoTen.getText());
                sv.setGioitinh(rdNam.isSelected());
                sv.setDiemtb(Double.parseDouble(txtDTB.getText()));

                qlsv.themSV(sv);
                LoadDataToTable();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");

            }
        });
        btXoa.addActionListener((e) -> {
            int selectedRowIndex = tblSinhVien.getSelectedRow();
            if (selectedRowIndex >= 0) {
                if (JOptionPane.showConfirmDialog(this, "Có chắc muốn sinh viên này không?") == JOptionPane.YES_OPTION) {
                    String maso = tblSinhVien.getValueAt(selectedRowIndex, 0).toString();
                    qlsv.xoaSV(maso);
                    LoadDataToTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sinh viên cần xoá");
            }
        });
        chkSapXep.addItemListener((e) -> {
            if (chkSapXep.isSelected()) {
                qlsv.SapXepTheoHocLuc();
                LoadDataToTable();
            }
        });
        btGhiFile.addActionListener((e) -> {
            if (qlsv.GhiDanhSachSinhVien(FILE_NAME)) {
                JOptionPane.showMessageDialog(this, "Đã ghi dữ liệu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Đã ghi dữ liệu thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        });
        tblSinhVien.getSelectionModel().addListSelectionListener((e) -> {
            int selectedRowIndex = tblSinhVien.getSelectedRow();
            if (selectedRowIndex >= 0) {
                String maso = tblSinhVien.getValueAt(selectedRowIndex, 0).toString();
                String hoten = tblSinhVien.getValueAt(selectedRowIndex, 1).toString();
                boolean gioitinh = tblSinhVien.getValueAt(selectedRowIndex, 2).equals("Nam");
                String diemtb = tblSinhVien.getValueAt(selectedRowIndex, 3).toString();

                txtMaSo.setText(maso);
                txtHoTen.setText(hoten);
                rdNam.setSelected(gioitinh);
                rdNu.setSelected(!gioitinh);
                txtDTB.setText(diemtb);

                txtMaSo.setEditable(false);
            }
        });
        btChinhSua.addActionListener((e) -> {
            int selectedRowIndex = tblSinhVien.getSelectedRow();
            if (selectedRowIndex >= 0) {
                String maso = tblSinhVien.getValueAt(selectedRowIndex, 0).toString();
                chinhSuaSinhVien(maso);
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sinh viên cần chỉnh sửa");
            }
        });
        btTimKiem.addActionListener((e) -> {
            txtMaSo.setEditable(false);

            String maso = txtMaSo.getText().trim().toLowerCase();
            String hoten = txtHoTen.getText().trim().toLowerCase();

            Boolean gioitinh = null;
            if (rdNam.isSelected()) {
                gioitinh = true; // Nam
            } else if (rdNu.isSelected()) {
                gioitinh = false; // Nữ
            }

            String diemtb = txtDTB.getText().trim();

            String error = "";

            if (!maso.isEmpty()) {
                error += "Mã số sinh viên không được rỗng";
            }
            if (!hoten.isEmpty()) {
                error += "Họ tên sinh viên không được rỗng";
            }
            if (!rdNam.isSelected() || !rdNu.isSelected()) {
                error += "Giới tính sinh viên phải được chọn";
            }
            if (!diemtb.isEmpty()) {
                error += "Điểm trung bình sinh viên không được rỗng";
            }

            if (error.length() > 0) {
                JOptionPane.showMessageDialog(this, error, "Lỗi dũ liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.setRowCount(0);
            for (SinhVien sv : qlsv.getDsSinhVien()) {
                
            }

        });
    }

    private void LoadDataToTable() {
        model.setRowCount(0);
        for (SinhVien sv : qlsv.getDsSinhVien()) {
            model.addRow(new Object[]{sv.getMaso(), sv.getHoten(), sv.isGioitinh() == true ? "Nam" : "Nữ", sv.getDiemtb(), sv.getHocLuc()});
        }
    }

    private void chinhSuaSinhVien(String maso) {
        for (SinhVien sinhVien : qlsv.getDsSinhVien()) {
            if (sinhVien.getMaso().equals(maso)) {
                String newHoTen = txtHoTen.getText();
                boolean newGioiTinh = rdNam.isSelected();
                double newDiemTB = Double.parseDouble(txtDTB.getText());

                if (newDiemTB < 0 || newDiemTB > 10) {
                    JOptionPane.showMessageDialog(this, "Điểm trung bình phải nằm trong khoảng từ 0 đến 10!");
                    return;
                }

                sinhVien.setHoten(newHoTen);
                sinhVien.setGioitinh(newGioiTinh);
                sinhVien.setDiemtb(newDiemTB);

                LoadDataToTable();

                JOptionPane.showMessageDialog(this, "Cập nhật thông tin sinh viên thành công!");
                return;
            }
        }
    }
}
