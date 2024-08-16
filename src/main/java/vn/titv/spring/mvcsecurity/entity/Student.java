package vn.titv.spring.mvcsecurity.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.sql.Date;

@Entity
@Table(name = "students")
public class Student {
    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "last_name", length = 45)
    private String lastName;
    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "sodienthoai", length = 45)
    private String soDienThoai;

    @Column(name = "diachi",length = 45)
    private String diachi;

    @Column(name = "lop",length = 45)
    private String lop;



    public Student() {
    }

    public Student(String lastName, String firstName, String email, Date ngaySinh, String soDienThoai, String diachi, String lop) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.diachi = diachi;
        this.lop = lop;

    }

    public Student(int id, String lastName, String firstName, String email, Date ngaySinh, String soDienThoai, String diachi, String lop) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.diachi = diachi;
        this.lop = lop;
    }

    // getter,setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diachi='" + diachi + '\'' +
                ", lop='" + lop + '\'' +
                '}';
    }
}
