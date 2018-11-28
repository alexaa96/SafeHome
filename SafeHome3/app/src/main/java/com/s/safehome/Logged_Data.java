package com.s.safehome;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Logged_Data extends AppCompatActivity {
    static ArrayList<String> dates=new ArrayList<String>();
    static ArrayList<String> days=new ArrayList<String>();
    static int count =0;
    int ok=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged__data);
        dates.clear();
        final ListView list =(ListView)findViewById(R.id.list_view);
        final TextView date=(TextView) findViewById(R.id.textView11);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Data");
        final DatabaseReference ref=database.getReference("Count");

        ArrayAdapter<String> adapter;
        Calendar c =  Calendar.getInstance();
        Bundle extras = getIntent().getExtras();
        String value = null;
        if (extras != null) {
              value = extras.getString("Value");


        }


        date.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)+"/"+Calendar.getInstance().get(Calendar.YEAR));
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        list.setAdapter(adapter);
        //days.add(c.get(Calendar.DAY_OF_MONTH));
        final String today_date=date.getText().toString();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Integer count = dataSnapshot.getValue(Integer.class);
                 Integer i;
                for( i=1;i<=count;i++) {
                    DatabaseReference log_reference = myRef.child(i.toString());
                    log_reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String logged_data=dataSnapshot.getValue(String.class);
                            if(logged_data.contains(today_date)) {


                                String[] tokens = logged_data.split(",");

                                String final_data = "Door was opened at: " + tokens[1] + ":" + tokens[2];
                                dates.add(final_data);
                            }







                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




       /* if(value.equals("OPEN") && ok==0) {
            final String data = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)+"/"+Calendar.getInstance().get(Calendar.YEAR)+","+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+","+Calendar.getInstance().get(Calendar.MINUTE);
            ok=1;


            myRef.addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                DatabaseReference user_ref = myRef.child(count+"");

                                                user_ref.setValue(data);



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                dates.add("Door opened at : " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ": " + Calendar.getInstance().get(Calendar.MINUTE));
            }
        else
        {

           if(value.equals("CLOSED")) {
               count++;
               ok = 0;
           }
        }*/

        }






    }


