package com.example.bduan1.chucnangAdminQuanLyPhongTo;

public class QuanLyPhongTroModel {
    private String id;
    private String moTaPhong;
    private String tenPhong;
    private Double tienPhong;
    private String trangThaiPhong;

    public QuanLyPhongTroModel(String moTaPhong, String tenPhong, Double tienPhong, String trangThaiPhong) {
        this.moTaPhong = moTaPhong;
        this.tenPhong = tenPhong;
        this.tienPhong = tienPhong;
        this.trangThaiPhong = trangThaiPhong;
    }

    public QuanLyPhongTroModel() {
    }

    public QuanLyPhongTroModel(String id, String moTaPhong, String tenPhong, Double tienPhong, String trangThaiPhong) {
        this.id = id;
        this.moTaPhong = moTaPhong;
        this.tenPhong = tenPhong;
        this.tienPhong = tienPhong;
        this.trangThaiPhong = trangThaiPhong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoTaPhong() {
        return moTaPhong;
    }

    public void setMoTaPhong(String moTaPhong) {
        this.moTaPhong = moTaPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public Double getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(Double tienPhong) {
        this.tienPhong = tienPhong;
    }

    public String getTrangThaiPhong() {
        return trangThaiPhong;
    }

    public void setTrangThaiPhong(String trangThaiPhong) {
        this.trangThaiPhong = trangThaiPhong;
    }
}