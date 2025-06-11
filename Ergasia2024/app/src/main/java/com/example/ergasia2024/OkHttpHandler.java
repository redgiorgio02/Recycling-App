package com.example.ergasia2024;
import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.*;
import okhttp3.Request;

public class OkHttpHandler {

    public OkHttpHandler() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    User getLoginUser(String url) throws Exception {
        User user = null;
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        okhttp3.Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        //System.out.println("My Response: " + data);
        try {
            JSONObject json = new JSONObject(data);
            Iterator<String> keys = json.keys();
            if (keys.hasNext()) {
                String username = keys.next();
                String name = json.getJSONObject(username).getString("name").toString();
                String password = json.getJSONObject(username).getString("password").toString();
                String material_points = json.getJSONObject(username).getString("material_points").toString();
                String total_points = json.getJSONObject(username).getString("total_points").toString();
                String role = json.getJSONObject(username).getString("role").toString();
                user = new User(username, name, password, role, total_points, material_points);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;

    }

    public void executePhpUrl(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        System.out.println("My Response: " + response);
    }

    public ArrayList<User> getAllUsers(String url) throws Exception {
        ArrayList<User> users = new ArrayList<User>();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        okhttp3.Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        System.out.println("My Response: " + data);
        try {
            JSONObject json = new JSONObject(data);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String username = keys.next();
                users.add(getLoginUser("http://" + "192.168.56.1" + "/AppErgasia/androidLogin.php?username=" + username));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<PointRequest> getAllRequests(String url) throws Exception {
        ArrayList<PointRequest> requests = new ArrayList<PointRequest>();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        System.out.println("My Response: " + data);

        try {
            JSONObject json = new JSONObject(data);
            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {

                String request_id = keys.next();

                String username = json.getJSONObject(request_id).getString("username").toString();
                String material_type = json.getJSONObject(request_id).getString("material_type").toString();
                String material_points = json.getJSONObject(request_id).getString("material_points").toString();
                String material_weight = json.getJSONObject(request_id).getString("material_weight").toString();

                requests.add(new PointRequest(request_id, username, material_type, material_weight, material_points));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requests;
    }


    public ArrayList<Double> getParameters(String url) throws Exception {
        ArrayList<Double> points = new ArrayList<>();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        System.out.println("My Response: " + data);

        try {
            JSONObject json = new JSONObject(data);
            Iterator<String> keys = json.keys();

            while (keys.hasNext()) {

                String id = keys.next();

                String paper_points = json.getJSONObject(id).getString("paper_points").toString();
                points.add(0,Double.parseDouble(paper_points));

                String plastic_points = json.getJSONObject(id).getString("plastic_points").toString();
                points.add(1,Double.parseDouble(plastic_points));

                String glass_points = json.getJSONObject(id).getString("glass_points").toString();
                points.add(2,Double.parseDouble(glass_points));

                String alum_points = json.getJSONObject(id).getString("alum_points").toString();
                points.add(3,Double.parseDouble(alum_points));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return points;

    }



}