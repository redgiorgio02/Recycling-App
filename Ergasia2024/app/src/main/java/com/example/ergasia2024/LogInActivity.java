package com.example.ergasia2024;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbtn = (Button)findViewById(R.id.button_submit);
        EditText text_username;
        EditText text_password;

        text_username = findViewById(R.id.username);
        text_password = findViewById(R.id.password);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = text_username.getText().toString().trim();

                String password = text_password.getText().toString().trim();


                    String url = "http://"+"192.168.56.1"+"/AppErgasia/androidLogin.php?username=" + username ;
                    try{

                        OkHttpHandler okhttphandler = new OkHttpHandler();
                        User checkLogInUser = okhttphandler.getLoginUser(url);

                        if(checkLogInUser == null){
                            //user not found
                            Toast.makeText(getApplicationContext(), "user not found", Toast.LENGTH_SHORT).show();

                        } else if (password.equals(" ")) {
                            Toast.makeText(getApplicationContext(),"Please enter your password", Toast.LENGTH_SHORT);
                        }
                        else if (!password.equals(checkLogInUser.getPassword())){
                            //password incorrect
                            Toast.makeText(getApplicationContext(), "incorrect pass", Toast.LENGTH_SHORT).show();

                        }

                        else if (checkLogInUser.getRole().equals("admin")){
                            Intent intent = new Intent(LogInActivity.this,RequestActivity.class);
                            intent.putExtra("name",checkLogInUser.getName());
                            startActivity(intent);

                        } else{

                            Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                            intent.putExtra("username",checkLogInUser.getUsername());
                            intent.putExtra("name",checkLogInUser.getName());
                            intent.putExtra("total_points",checkLogInUser.getTotal_points());
                            intent.putExtra("material_points",checkLogInUser.getMaterial_pts());
                            intent.putExtra("role",checkLogInUser.getRole());
                            intent.putExtra("password",checkLogInUser.getPassword());
                            startActivity(intent);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }



            }
        });

        Button backbtn = (Button)findViewById(R.id.buttonback);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
            }
        });


    }
}
