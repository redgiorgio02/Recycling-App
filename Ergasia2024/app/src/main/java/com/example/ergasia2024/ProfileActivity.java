package com.example.ergasia2024;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ProfileActivity extends AppCompatActivity {



    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, form , logout;
    User user;


    TextView text_name,points;

    DonutProgress donutPaper,donutPlastic,donutGlass,donutAlum;
    ArrayList<PointRequest> rewards;

    private double currentProgress ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int pap=0,pl=0,gl=0,al=0;

        String username = getIntent().getStringExtra("username");
        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");
        String role = getIntent().getStringExtra("role");
        String total_points = getIntent().getStringExtra("total_points");
        String material_points = getIntent().getStringExtra("material_points");
        user = new User(username,name,password,role,total_points,material_points);

        text_name = findViewById(R.id.name_view);
        text_name.setText(name);

        points = findViewById(R.id.txt_points);

        drawerLayout= findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        form = findViewById(R.id.file);
        logout = findViewById(R.id.logout);

        OkHttpHandler handler = new OkHttpHandler();


        try {
            rewards = handler.getAllRequests("http://"+"192.168.56.1"+"/AppErgasia/getAcceptedRequests.php?username="+username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (PointRequest r:rewards){
            user.AddPointsTo(r.getMaterialName(),r.getMaterialPoints());
            try {
                handler.executePhpUrl("http://"+"192.168.56.1"+"/AppErgasia/updateRequest.php?request_id="+r.getRequest_id()+"&status=CLAIMED");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            handler.executePhpUrl("http://"+"192.168.56.1"+"/AppErgasia/updateUserPoints.php?username="+username+"&material_points="+user.getStringMaterial()+"&total_points="+user.getTotal_points());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        donutPaper = findViewById(R.id.progPaper);
        donutPlastic = findViewById(R.id.progPlastic);
        donutGlass = findViewById(R.id.progGlass);
        donutAlum = findViewById(R.id.progAlum);

        if (user.getPaperPoints() == 0.0) {
            donutPaper.setProgress((float) 0);
            pap = 1;

        } if (user.getPlasticPoints() == 0.0) {
            donutPlastic.setProgress((float) 0);
            pl = 1;

        }if (user.getGlassPoints() == 0.0) {
            donutGlass.setProgress((float) 0);
            gl = 1;

        }if (user.getAluminumPoints() == 0.0) {
            donutAlum.setProgress((float) 0);
            al = 1;

        }

        if (pap==0) {
            String paper = String.format("%.2f", (user.getPaperPoints() / user.getTtl_pts()) * 100);
            donutPaper.setProgress(Float.parseFloat(paper));
        }

        if (pl==0) {
            String plastic = String.format("%.2f", (user.getPlasticPoints() / user.getTtl_pts()) * 100);
            donutPlastic.setProgress(Float.parseFloat(plastic));
        }

        if (gl==0) {
            String glass = String.format("%.2f", (user.getGlassPoints() / user.getTtl_pts()) * 100);
            donutGlass.setProgress(Float.parseFloat(glass));
        }

        if (al==0) {
            String alum = String.format("%.2f", (user.getAluminumPoints() / user.getTtl_pts()) * 100);
            donutAlum.setProgress(Float.parseFloat(alum));
        }




        progressBar = findViewById(R.id.progressBar);
        currentProgress = user.getTtl_pts();
        //Toast.makeText(getApplicationContext(),"Your total points are: "+(int) currentProgress,Toast.LENGTH_LONG).show();
        progressBar.setProgress((int) currentProgress);

        if (user.getTtl_pts()<100) {
            currentProgress = 0;
            progressBar.setMax(100);
            points.setText("Points remaining for your first award: "+(100-user.getTtl_pts()));
        }
        else if (user.getTtl_pts()<150) {
            Toast.makeText(getApplicationContext(),"First award claimed! ",Toast.LENGTH_SHORT).show();
            currentProgress = 0;
            progressBar.setMax(150);
            points.setText("Points remaining for your second award: "+(150-user.getTtl_pts()));
        }
        else if (user.getTtl_pts()<200) {
            Toast.makeText(getApplicationContext(),"Second award claimed! ",Toast.LENGTH_SHORT).show();
            currentProgress = 0;
            progressBar.setMax(200);
            points.setText("Points remaining for your third award: "+(200-user.getTtl_pts()));
        }
        else if (user.getTtl_pts()<300) {
            Toast.makeText(getApplicationContext(),"Third award claimed! ",Toast.LENGTH_SHORT).show();
            currentProgress = 0;
            progressBar.setMax(300);
            points.setText("Points remaining for your fourth award: "+(300-user.getTtl_pts()));
        }
        else {
            Toast.makeText(getApplicationContext(),"Fourth award claimed! ",Toast.LENGTH_SHORT).show();
            currentProgress = 0;
            progressBar.setMax(1000);
            points.setText("Points remaining for your last award: "+(1000-user.getTtl_pts()));
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
               recreate();
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FormActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, MainActivity.class);
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
