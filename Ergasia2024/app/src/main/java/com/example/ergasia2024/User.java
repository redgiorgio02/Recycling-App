package com.example.ergasia2024;

import java.util.ArrayList;

public class User {

    private String username, password, role, name, material_pts;
    private String total_points;
    private ArrayList<PointRequest> rewards;


    private double glassPoints, aluminumPoints, paperPoints, plasticPoints, ttl_pts;

    public User(String uname, String nname, String pass, String rl, String pts, String mtr_pts){
        username = uname;
        name = nname;
        password = pass;
        role = rl;
        total_points = pts;
        material_pts = mtr_pts;
        rewards = new ArrayList<PointRequest>();


        String[] material_pts = mtr_pts.split(",");
        paperPoints = Double.parseDouble(material_pts[0]);
        plasticPoints = Double.parseDouble(material_pts[1]);
        glassPoints = Double.parseDouble(material_pts[2]);
        aluminumPoints = Double.parseDouble(material_pts[3]);
    }

    public ArrayList<PointRequest> getRewards(){
        return rewards;
    }

    public double getGlassPoints() {
        return glassPoints;
    }

    public double getAluminumPoints() {
        return aluminumPoints;
    }

    public double getPaperPoints() {
        return paperPoints;
    }

    public double getPlasticPoints() {
        return plasticPoints;
    }

    public String getUsername() {
        return username;
    }

    public String getName(){return name;}

    public String getMaterial_pts() {
        return material_pts;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getTotal_points() {
        ttl_pts = paperPoints+plasticPoints+glassPoints+aluminumPoints;
        total_points = String.valueOf(ttl_pts);
        return total_points;
    }

    public String getStringMaterial(){
        return String.valueOf(paperPoints)+","+String.valueOf(plasticPoints)+","+String.valueOf(glassPoints)+","+ String.valueOf(aluminumPoints) ;
    }

    public double getTtl_pts(){
        ttl_pts = paperPoints+plasticPoints+glassPoints+aluminumPoints;
        return ttl_pts;}

    public void addPoints(int p){
        total_points +=p;
    }

    public void setRewards(ArrayList<PointRequest> r){
        rewards = r;
    }

    public void AddPointsTo(String str, String p){
        double point = Double.parseDouble(p);
        if (str.equals("Paper")){
            paperPoints += point;
        }else if (str.equals("Plastic")){
            plasticPoints += point;
        }else if (str.equals("Glass")){
            glassPoints += point;
        }else if (str.equals("Aluminium")){
            aluminumPoints += point;
        }
    }

}