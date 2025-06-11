package com.example.ergasia2024;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class ParametrasationActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, form ,statistics, logout;

    ArrayList<Integer> points ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrasation);

        EditText paper, plastic, glass, alum;
        Button button;

        drawerLayout= findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        form = findViewById(R.id.file);
        statistics = findViewById(R.id.statistics);
        logout = findViewById(R.id.logout);


        paper = findViewById(R.id.txt_paper);
        plastic = findViewById(R.id.txt_plastic);
        glass = findViewById(R.id.txt_glass);
        alum = findViewById(R.id.txt_alum);
        button = findViewById(R.id.btn_paramSubmit);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Paper = paper.getText().toString().trim();
                String Plastic = plastic.getText().toString().trim();
                String Glass = glass.getText().toString().trim();
                String Alum = alum.getText().toString().trim();
                OkHttpHandler handler = new OkHttpHandler();
                try {
                    handler.executePhpUrl("http://"+"192.168.56.1"+"/AppErgasia/updateParams.php?paper_points="+Paper+"&plastic_points="+Plastic+"&glass_points="+Glass+"&alum_points="+Alum);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Toast.makeText(getApplicationContext(),"Changes Applied Successfully",Toast.LENGTH_LONG).show();


            }
        });



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ParametrasationActivity.this, RequestActivity.class);
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ParametrasationActivity.this, BestActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ParametrasationActivity.this, MainActivity.class);
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
