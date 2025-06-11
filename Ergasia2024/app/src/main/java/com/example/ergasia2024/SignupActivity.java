package com.example.ergasia2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signupbtn2 = (Button)findViewById(R.id.button_submit);
        EditText text_prof_name ;
        EditText text_username ;
        EditText text_password ;

        text_prof_name = findViewById(R.id.profile_name);
        text_username = findViewById(R.id.txt_glass);
        text_password = findViewById(R.id.password);

        signupbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String prof_name = text_prof_name.getText().toString().trim();
                String username = text_username.getText().toString().trim();
                String password = text_password.getText().toString().trim();






                String url = "http://"+"192.168.56.1"+"/AppErgasia/androidSignUp.php?username="+username + "&name="+prof_name+"&password="+password;


                try{



                    if (nameAlreadyExists(username)) {
                        Toast.makeText(getApplicationContext(),"User with this username already exists",Toast.LENGTH_SHORT).show();
                    } else {
                        OkHttpHandler okhttphandler = new OkHttpHandler();
                        okhttphandler.executePhpUrl(url);
                        startActivity(new Intent(SignupActivity.this, LogInActivity.class));
                        Toast.makeText(getApplicationContext(), "Selection Logged", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        Button backbtn = (Button)findViewById(R.id.buttonback2);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });



    }

    boolean nameAlreadyExists(String name){
        String url2 = "http://"+"192.168.56.1"+"/AppErgasia/getAllUsers.php";

        ArrayList<User> users = new ArrayList<User>();

        OkHttpHandler okhttphandler = new OkHttpHandler();
        try {
            users = okhttphandler.getAllUsers(url2);
            for(User username: users){
                if(username.getUsername().equals(name)){
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
