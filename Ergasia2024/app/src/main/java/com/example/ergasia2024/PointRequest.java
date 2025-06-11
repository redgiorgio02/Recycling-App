package com.example.ergasia2024;

public class PointRequest {


    private final String request_id, username, materialName, materialWeight, materialPoints;
    private String status;

    public PointRequest(String rid, String uname, String mname, String weight, String points){
        request_id = rid;
        username = uname;
        materialName = mname;
        materialWeight = weight;
        materialPoints = points;

        status = "PENDING";
    }

    //getters


    public String getMaterialPoints() {
        return materialPoints;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getUsername() {
        return username;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialWeight() {
        return materialWeight;
    }

    public String getStatus() {
        return status;
    }

    //setters

}
