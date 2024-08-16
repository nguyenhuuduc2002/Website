package vn.titv.spring.mvcsecurity.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scores")
public class Scores {

    @Id
    @Column(name = "id_scores")
    private int id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="students_id")
    private Student student;

    @Column(name = "diem_toan")
    private double diemToan;

    @Column(name = "diem_ly")
    private double diemLy;
    @Column(name = "diem_hoa")
    private double diemHoa;
    @Column(name = "diem_sinh")
    private double diemSinh;
    @Column(name = "diem_van")
    private double diemVan;
    @Column(name = "diem_su")
    private double diemSu;
    @Column(name = "diem_dia")
    private double diemDia;
    @Column(name = "diem_anh")
    private double diemAnh;

    @Column(name = "diem_tb")
    private double diemTrungBinh;


    @Column(name = "hoc_luc")
    private String hocLuc;


    public Scores() {
    }

    public Scores(Student student, double diemToan, double diemLy, double diemHoa, double diemSinh, double diemVan, double diemSu, double diemDia, double diemAnh,double diemTrungBinh,String hocLuc) {
        this.id = student.getId();
        this.student = student;
        this.diemToan = diemToan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
        this.diemSinh = diemSinh;
        this.diemVan = diemVan;
        this.diemSu = diemSu;
        this.diemDia = diemDia;
        this.diemAnh = diemAnh;
        this.diemTrungBinh = diemTrungBinh;
        this.hocLuc = hocLuc;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getDiemToan() {
        return diemToan;
    }

    public void setDiemToan(double diemToan) {
        this.diemToan = diemToan;
    }

    public double getDiemLy() {
        return diemLy;
    }

    public void setDiemLy(double diemLy) {
        this.diemLy = diemLy;
    }

    public double getDiemHoa() {
        return diemHoa;
    }

    public void setDiemHoa(double diemHoa) {
        this.diemHoa = diemHoa;
    }

    public double getDiemSinh() {
        return diemSinh;
    }

    public void setDiemSinh(double diemSinh) {
        this.diemSinh = diemSinh;
    }

    public double getDiemVan() {
        return diemVan;
    }

    public void setDiemVan(double diemVan) {
        this.diemVan = diemVan;
    }

    public double getDiemSu() {
        return diemSu;
    }

    public void setDiemSu(double diemSu) {
        this.diemSu = diemSu;
    }

    public double getDiemDia() {
        return diemDia;
    }

    public void setDiemDia(double diemDia) {
        this.diemDia = diemDia;
    }

    public double getDiemAnh() {
        return diemAnh;
    }

    public void setDiemAnh(double diemAnh) {
        this.diemAnh = diemAnh;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getHocLuc() {
        return hocLuc;
    }

    public void setHocLuc(String hocLuc) {
        this.hocLuc = hocLuc;
    }
}
