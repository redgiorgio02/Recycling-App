package com.example.ergasia2024;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, form , logout;



    private double pricePaper;
    private double pricePlastic;
    private double priceGlass;
    private double priceAlum;


    private double price;
    private double points;
    private double total_points,pPaper,pPlastic,pGlass,pAlum;

    private EditText weight;
    private String str = "";

    private Button button;
    private ArrayList<Double> kilopoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        drawerLayout= findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        form = findViewById(R.id.file);
        logout = findViewById(R.id.logout);


        button = findViewById(R.id.button_submit);
        weight = findViewById(R.id.txt_paper);
        button.setEnabled(false);

        button.addTextChangedListener(submitWatcher);
        weight.addTextChangedListener(submitWatcher);

        String username = getIntent().getStringExtra("username");



        OkHttpHandler handler = new OkHttpHandler();
        try {
            kilopoints = handler.getParameters("http://"+"192.168.56.1"+"/AppErgasia/getParams.php");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        pricePaper = kilopoints.get(0);
        pricePlastic = kilopoints.get(1);
        priceGlass = kilopoints.get(2);
        priceAlum = kilopoints.get(3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double w = Double.parseDouble(weight.getText().toString());
                points = w * price;
                if (str == "Paper"){
                    pPaper += points;
                }else if (str == "Plastic"){
                    pPlastic += points;
                }else if (str == "Glass"){
                    pGlass += points;
                }else if (str == "Aluminium"){
                    pAlum += points;
                }
                total_points+= points;
                if (str.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please enter valid material or weight",Toast.LENGTH_SHORT);
                }else {
                        String url = "http://" + "192.168.56.1" + "/AppErgasia/newRequest.php?username=" + username + "&material_type=" + str + "&material_points=" + points + "&material_weight=" + w;
                        OkHttpHandler Handler2 = new OkHttpHandler();
                        try {
                            Handler2.executePhpUrl(url);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        Toast.makeText(getApplicationContext(),"Submit sent",Toast.LENGTH_SHORT).show();
                    }

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
                redirectActivity(FormActivity.this, ProfileActivity.class);
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(FormActivity.this, MainActivity.class);
            }
        });



    }




    public void onRadioButtonClicked(View view) {


        boolean checked = ((RadioButton) view).isChecked();
        int i = view.getId();
        if (i == R.id.rdPaper) {
            if (checked) {
                str = "Paper";
                price = pricePaper;
            }
        } else if (i == R.id.rdPlastic) {
            if (checked) {
                str = "Plastic";
                price = pricePlastic;
            }
        } else if (i == R.id.rdGlass) {
            if (checked) {
                str = "Glass";
                price = priceGlass;
            }
        } else if (i == R.id.rdAlum) {
            if (checked) {
                str = "Aluminium";
                price = priceAlum;
            }
        } else
            str = "0";

    }


    TextWatcher submitWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String num1 = weight.getText().toString().trim();
            button.setEnabled(!str.isEmpty() && !num1.isEmpty() );


        }

        @Override
        public void afterTextChanged(Editable s) {



        }
    };



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
