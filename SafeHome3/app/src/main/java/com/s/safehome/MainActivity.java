package com.s.safehome;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        final Button log_in = findViewById(R.id.button6);
        final Button sign_up = findViewById(R.id.button7);
        final EditText username   = (EditText)findViewById(R.id.editText5);
        final EditText pass   = (EditText)findViewById(R.id.editText7);
        final TextView text   = (TextView)findViewById(R.id.textView5);


        log_in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (pass.getText().toString().equals("") || username.getText().toString().equals("")) {
                    text.setTextColor(Color.RED);
                    text.setText("You must fill all the fields.");

                } else

                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();


                    final DatabaseReference rootref = database.getReference().child("User");

                    rootref.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(username.getText().toString())) {
                                username.setTextColor(Color.BLACK);
                                DatabaseReference ref = rootref.child(username.getText().toString());
                                //String value = String.valueOf(ref.getKey());
                                // Read from the database
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        String value = dataSnapshot.getValue(String.class);

                                        String[] tokens = value.split(",");
                                       /* if(pass.getText().toString() == null || username.getText().toString() == null) {
                                            text.setTextColor(Color.RED);
                                            text.setText("You must fill all the fields.");

                                        }*/

                                        AESCrypt c=new AESCrypt();

                                        try {
                                            if (!pass.getText().toString().equals(c.decrypt(tokens[1]))) {
                                                username.setText("");
                                                pass.setText("");
                                                //text.setTextColor(255);
                                                text.setText("Password is wrong.Please try again !");


                                            } else {
                                                startActivity(new Intent(MainActivity.this, Main3Activity.class));

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        //Log.d(TAG, "Value is: " + value);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value
                                        Log.w("Failed to read value.", error.toException());
                                    }
                                });
                            } else {
                                username.setText("");
                                text.setTextColor(Color.RED);
                                text.setText("Please enter a valid username or sign up.");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));



            }

        });





    }
}
