package com.s.safehome;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main3Activity extends AppCompatActivity {


    public boolean enable_check = false;
    public Integer value;
    public static int progress_minimvalue_flag=0;
    public static int progress_maxvalue_flag=0;
    public static Integer MinTemperature=0;
    public static Integer MaxTemperature=0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final TextView text = (TextView) findViewById(R.id.textView8);
        final TextView minim = (TextView) findViewById(R.id.min);
        final TextView maxim = (TextView) findViewById(R.id.max);
        final SeekBar seekmin = (SeekBar) findViewById(R.id.seekBar4);
        final SeekBar seekmax = (SeekBar) findViewById(R.id.seekBar5);
        final Switch notifications_enable = (Switch) findViewById(R.id.switch3);
        minim.setText("Min:" + MinTemperature);
        maxim.setText("Max:" + MaxTemperature);
        Integer minimum_value = 0;
        Integer maximum_value = 0;
        Integer current_temperature = 0;
        final int temperature_value;
        final int min_temperature = 10;
        int max_temperature = 35;
        seekmin.setMax(max_temperature);
        seekmax.setMax(max_temperature);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Temp");
        final DatabaseReference MinTemperature_Ref =database.getReference("MinTemperature");
        final DatabaseReference MaxTemperature_Ref=database.getReference("MaxTemperature");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(Integer.class);

                text.setText(value + " C");

                if(value< MinTemperature){
                    String title = "Temperature too low";
                    String body = "House temperature is lower than minimum temperature you set.";
                    String subject = " Temp is low!";


                    NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify = new Notification.Builder
                            (getApplicationContext()).setContentTitle(title).setContentText(body).
                            setContentTitle(subject).setSmallIcon(R.drawable.ic_launcher_background).build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                    notif.notify(0, notify);

                }
                if(value> MaxTemperature) {
                    String title = "Temperature too High";
                    String body = "House temperature is higher than maximum temperature you set.";
                    String subject = " Temp is high!";


                    NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify = new Notification.Builder
                            (getApplicationContext()).setContentTitle(title).setContentText(body).
                            setContentTitle(subject).setSmallIcon(R.drawable.ic_launcher_background).build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                    notif.notify(1, notify);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });



        seekmin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_minim = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress_minim = progresValue;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                minim.setText("Min:" + progress_minim);
                MinTemperature_Ref.setValue(progress_minim);



            }
        });
        seekmax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                maxim.setText("Max:" + progress);
                MaxTemperature_Ref.setValue(progress);


            }
        });
        notifications_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enable_check = true;

                } else {
                    enable_check = false;
                    // The toggle is disabled
                }
            }
        });



        MinTemperature_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MinTemperature = dataSnapshot.getValue(Integer.class);
                if (MaxTemperature < MinTemperature) {

                }



                else
                {
                    progress_minimvalue_flag=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        MaxTemperature_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MaxTemperature = dataSnapshot.getValue(Integer.class);
                if (MaxTemperature < MinTemperature) {

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
      /*  if (seekmin.getProgress() < value) {
            if (enable_check) {
                String title = "Temperature too low";
                String body = "House temperature is lower than minimum temperature you set.";
                String subject = " Temp is low!";

                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify = new Notification.Builder
                        (getApplicationContext()).setContentTitle(title).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.ic_launcher_background).build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }*/


            //}

            // else
            {
                /*Do nothing*/
                // }
                // if(maximum_value>value){
            /*
            if(enable){
                String title="Temperature too high";
                String body="House temperature is higher than maximum temperature you set.";
                String subject=" Temp is low!";

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(title).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.ic_launcher_background).build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }*/

                //}
          //  }


        }
    }
}


