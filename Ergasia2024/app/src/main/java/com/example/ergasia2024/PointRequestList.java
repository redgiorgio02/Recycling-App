package com.example.ergasia2024;

import java.util.ArrayList;

public class PointRequestList {
    private ArrayList<PointRequest> pendList;

    public PointRequestList(String url){
        OkHttpHandler okhttphandler = new OkHttpHandler();
        try {
            pendList = okhttphandler.getAllRequests(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PointRequest get(int i){
        return pendList.get(i);
    }



    public String getFirst(){
        if(pendList.size()>=1) {
            PointRequest r = pendList.get(0);
            return "Material:" + r.getMaterialName() + " Weight:" + r.getMaterialWeight() + " Points:" + r.getMaterialPoints();
        }
        return "";
    }
    public String getSecond(){
        if(pendList.size()>=2) {
            PointRequest r = pendList.get(1);
            return "Material:" + r.getMaterialName() + " Weight:" + r.getMaterialWeight() + " Points:" + r.getMaterialPoints();
        }
        return "";
    }
    public String getThird(){
        if(pendList.size()>=3) {
            PointRequest r = pendList.get(2);
            return "Material:" + r.getMaterialName() + " Weight:" + r.getMaterialWeight() + " Points:" + r.getMaterialPoints();
        }
        return "";
    }
}