package com.example.ergasia2024;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class RequestActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, form ,statistics, logout;

    Button btn_acc,btn_rej;
    PointRequestList prl ;

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        drawerLayout= findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        form = findViewById(R.id.file);
        statistics = findViewById(R.id.statistics);
        logout = findViewById(R.id.logout);

        prl = new PointRequestList("http://"+"192.168.56.1"+"/AppErgasia/getPendingRequests.php");

        TextView v1,v2,v3;

        v1 = findViewById(R.id.textView11);
        v2 = findViewById(R.id.textView12);

        name = findViewById(R.id.name1);

        name.setText(getIntent().getStringExtra("name"));




        btn_acc = findViewById(R.id.buttonapprove1);
        btn_rej = findViewById(R.id.buttondecline1);



        v1.setText(prl.getFirst());

        btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpHandler handler3 = new OkHttpHandler();
                try {
                    handler3.executePhpUrl("http://"+"192.168.56.1"+"/AppErgasia/updateRequest.php?request_id="+prl.get(0).getRequest_id()+"&status=ACCEPTED");
                    Toast.makeText(getApplicationContext(),"ACCEPTED",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                prl = new PointRequestList("http://"+"192.168.56.1"+"/AppErgasia/getPendingRequests.php");

                v1.setText(prl.getFirst());

            }
        });
        btn_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpHandler handler3 = new OkHttpHandler();
                try {
                    handler3.executePhpUrl("http://"+"192.168.56.1"+"/AppErgasia/updateRequest.php?request_id="+prl.get(0).getRequest_id()+"&status=REJECTED");
                    Toast.makeText(getApplicationContext(),"REJECTED",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                prl = new PointRequestList("http://"+"192.168.56.1"+"/AppErgasia/getPendingRequests.php");

                v1.setText(prl.getFirst());

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
                recreate();
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(RequestActivity.this, ParametrasationActivity.class);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(RequestActivity.this, BestActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(RequestActivity.this, MainActivity.class);
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
