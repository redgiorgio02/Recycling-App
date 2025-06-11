package com.example.ergasia2024;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;

public class BestActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, form ,statistics, logout;

    TextView best_name;

    DonutProgress donutPaper,donutPlastic,donutGlass,donutAlum;




    ArrayList<User> users = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best);

        drawerLayout= findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        form = findViewById(R.id.file);
        statistics = findViewById(R.id.statistics);
        logout = findViewById(R.id.logout);

        best_name = findViewById(R.id.name1);
        int pap=0,pl=0,gl=0,al=0;


        OkHttpHandler hndl = new OkHttpHandler();

        try {
            users  = hndl.getAllUsers("http://"+"192.168.56.1"+"/AppErgasia/getAllUsers.php");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User best = new User("", "", "","","0","0,0,0,0");
        for (User u : users){
            if (u.getTtl_pts()>best.getTtl_pts()){
                best = u;
            }
        }
        try {
            best = hndl.getLoginUser("http://"+"192.168.56.1"+"/AppErgasia/androidLogin.php?username=" + best.getUsername());

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        //Toast.makeText(getApplicationContext(),""+best.getName(),Toast.LENGTH_SHORT).show();
        best_name.setText(best.getName());

        donutPaper = findViewById(R.id.progPaperb);
        donutPlastic = findViewById(R.id.progPlasticb);
        donutGlass = findViewById(R.id.progGlassb);
        donutAlum = findViewById(R.id.progAlumb);


        if (best.getPaperPoints() == 0.0) {
            donutPaper.setProgress((float) 0);
            pap = 1;

        } if (best.getPlasticPoints() == 0.0) {
            donutPlastic.setProgress((float) 0);
            pl = 1;

        }if (best.getGlassPoints() == 0.0) {
            donutGlass.setProgress((float) 0);
            gl = 1;

        }if (best.getAluminumPoints() == 0.0) {
            donutAlum.setProgress((float) 0);
            al = 1;

        }

        if (pap==0) {
            String paper = String.format("%.2f", (best.getPaperPoints() / best.getTtl_pts()) * 100);
            donutPaper.setProgress(Float.parseFloat(paper));
        }

        if (pl==0) {
            String plastic = String.format("%.2f", (best.getPlasticPoints() / best.getTtl_pts()) * 100);
            donutPlastic.setProgress(Float.parseFloat(plastic));
        }

        if (gl==0) {
            String glass = String.format("%.2f", (best.getGlassPoints() / best.getTtl_pts()) * 100);
            donutGlass.setProgress(Float.parseFloat(glass));
        }

        if (al==0) {
            String alum = String.format("%.2f", (best.getAluminumPoints() / best.getTtl_pts()) * 100);
            donutAlum.setProgress(Float.parseFloat(alum));
        }





        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(BestActivity.this, RequestActivity.class);
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(BestActivity.this, ParametrasationActivity.class);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(BestActivity.this, MainActivity.class);
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
