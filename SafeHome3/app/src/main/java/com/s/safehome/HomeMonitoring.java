package com.s.safehome;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeMonitoring extends AppCompatActivity {


    static int ok=0;
    int count=0;
    public boolean enable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_monitoring);
       // View inflatedView = getLayoutInflater().inflate(R.layout.activity_logged__data, null);


        final ImageView img=findViewById(R.id.imageView5);
        final TextView door_state=(TextView)findViewById(R.id.State);
        final Switch notifications_enable=(Switch)findViewById(R.id.switch1);
        final Button view_loggeddata=(Button)findViewById(R.id.button2);





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("DoorState");
        final DatabaseReference reference=database.getReference("Data");
        final DatabaseReference ref=database.getReference("Count");
        myRef.addValueEventListener(new ValueEventListener() {



            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
               // door_state.setText(value+" ");
                final Intent intent = new Intent(HomeMonitoring.this, Logged_Data.class);



                if(value == 1 && ok==0){

                    notifications_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked) {
                                enable=true;

                            } else {
                                enable=false;
                                // The toggle is disabled
                            }
                        }
                    });


                    if(enable){
                        String title="Door notification";
                        String body="Someone has opened the door and entered your house.";
                        String subject=" Door is open!";

                        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notify=new Notification.Builder
                                (getApplicationContext()).setContentTitle(title).setContentText(body).
                                setContentTitle(subject).setSmallIcon(R.drawable.ic_launcher_background).build();
                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        notif.notify(0, notify);
                    }
                    ref.addValueEventListener(new ValueEventListener(){

                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                      Integer c = dataSnapshot.getValue(Integer.class);
                                                      count=c;

                                                  }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              });
                    ok=1;


                    reference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            DatabaseReference user_ref = reference.child(count+"");
                            String data = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+Calendar.getInstance().get(Calendar.MONTH)+"/"+Calendar.getInstance().get(Calendar.YEAR)+","+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+","+Calendar.getInstance().get(Calendar.MINUTE);
                            user_ref.setValue(data);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    door_state.setTextColor(Color.BLACK);
                    door_state.setText("OPEN");
                    img.setImageResource(R.drawable.doors);
                    intent.putExtra("Value","OPEN");


                }
                else
                    {
                        intent.putExtra("Value","CLOSED");
                    door_state.setTextColor(Color.BLACK);
                    door_state.setText("CLOSED");
                    img.setImageResource(R.drawable.closed);
                    if(value==0)
                    {
                        count++;
                        ref.setValue(count);
                        ok=0;
                    }
                }

                view_loggeddata.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //startActivity(new Intent(HomeMonitoring.this, Logged_Data.class));
                        startActivity(intent);
                    }


                    });


                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });





        }
}
