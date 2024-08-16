package vn.titv.spring.mvcsecurity.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "moneys")
public class Money {

    @Column(name = "tienhoc")
    private double tienHoc;

    @Column(name = "tienbaohiem")
    private double tienBaoHiem;

    @Column(name = "tiendongphuc")
    private double tienDongPhuc;
    @Column(name = "tienbantru")
    private double tienBanTru;
    @Column(name = "tienhocthem")
    private double tienHocThem;
    @Column(name = "tienquy")
    private double tienQuy;
    @Column(name = "tiensach")
    private double tienSach;
    @Column(name = "dichvukhac")
    private  double dichVuKhac;
    @Column(name = "tongtien")
    private  double tongTien;

    @Id
    @Column(name = "khoihoc")
    private String khoiHoc;


    public Money() {
    }

    public Money(double tienHoc, double tienBaoHiem, double tienDongPhuc, double tienBanTru, double tienHocThem, double tienQuy, double tienSach, double dichVuKhac, double tongTien) {
        this.tienHoc = tienHoc;
        this.tienBaoHiem = tienBaoHiem;
        this.tienDongPhuc = tienDongPhuc;
        this.tienBanTru = tienBanTru;
        this.tienHocThem = tienHocThem;
        this.tienQuy = tienQuy;
        this.tienSach = tienSach;
        this.dichVuKhac = dichVuKhac;
        this.tongTien = tongTien;
    }

    public Money(double tienHoc, double tienBaoHiem, double tienDongPhuc, double tienBanTru, double tienHocThem, double tienQuy, double tienSach, double dichVuKhac, double tongTien, String khoiHoc) {
        this.tienHoc = tienHoc;
        this.tienBaoHiem = tienBaoHiem;
        this.tienDongPhuc = tienDongPhuc;
        this.tienBanTru = tienBanTru;
        this.tienHocThem = tienHocThem;
        this.tienQuy = tienQuy;
        this.tienSach = tienSach;
        this.dichVuKhac = dichVuKhac;
        this.tongTien = tongTien;
        this.khoiHoc = khoiHoc;
    }

    public double getTienHoc() {
        return tienHoc;
    }

    public void setTienHoc(double tienHoc) {
        this.tienHoc = tienHoc;
    }

    public double getTienBaoHiem() {
        return tienBaoHiem;
    }

    public void setTienBaoHiem(double tienBaoHiem) {
        this.tienBaoHiem = tienBaoHiem;
    }

    public double getTienDongPhuc() {
        return tienDongPhuc;
    }

    public void setTienDongPhuc(double tienDongPhuc) {
        this.tienDongPhuc = tienDongPhuc;
    }

    public double getTienBanTru() {
        return tienBanTru;
    }

    public void setTienBanTru(double tienBanTru) {
        this.tienBanTru = tienBanTru;
    }

    public double getTienHocThem() {
        return tienHocThem;
    }

    public void setTienHocThem(double tienHocThem) {
        this.tienHocThem = tienHocThem;
    }

    public double getTienQuy() {
        return tienQuy;
    }

    public void setTienQuy(double tienQuy) {
        this.tienQuy = tienQuy;
    }

    public double getTienSach() {
        return tienSach;
    }

    public void setTienSach(double tienSach) {
        this.tienSach = tienSach;
    }

    public double getDichVuKhac() {
        return dichVuKhac;
    }

    public void setDichVuKhac(double dichVuKhac) {
        this.dichVuKhac = dichVuKhac;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getKhoiHoc() {
        return khoiHoc;
    }

    public void setKhoiHoc(String khoiHoc) {
        this.khoiHoc = khoiHoc;
    }
}
