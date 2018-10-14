package com.s.safehome;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Button signup = findViewById(R.id.button);
        final EditText username = (EditText) findViewById(R.id.Name);
        final EditText pass = (EditText) findViewById(R.id.Pass);
        final EditText mail = (EditText) findViewById(R.id.editText3);
        final EditText phone = (EditText) findViewById(R.id.editText4);
        final EditText verify_pass = (EditText) findViewById(R.id.password);
        final TextView error        =(TextView)findViewById(R.id.error);

        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AESCrypt crypt = new AESCrypt();
                if (username.getText().toString().equals("") || pass.getText().toString().equals("") || mail.getText().toString().equals("") ||
                phone.getText().toString().equals("") || verify_pass.getText().toString().equals(""))
                {

                }
                else{
                if (verify_pass.getText().toString().equals(pass.getText().toString())) {
                    String user = null;
                    try {
                        user = username.getText().toString() + "," + crypt.encrypt(pass.getText().toString()) + "," + mail.getText().toString() + "," + phone.getText().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    FirebaseDatabase database = FirebaseDatabase.getInstance();


                    DatabaseReference myRef = database.getReference("User");
                    DatabaseReference user_ref = myRef.child(username.getText().toString());
                    user_ref.setValue(user);
                } else {
                    pass.setTextColor(Color.RED);
                    pass.setText("Passwords don't match");
                    verify_pass.setText("");


                }
            }


            }


        });


    }

}

