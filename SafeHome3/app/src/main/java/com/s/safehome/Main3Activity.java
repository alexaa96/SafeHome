package com.s.safehome;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main3Activity extends AppCompatActivity {
   // private TextView min;
    //private TextView max;
    //private SeekBar seekBarmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final TextView text   = (TextView)findViewById(R.id.textView8);
        final TextView minim   = (TextView)findViewById(R.id.min);
        final TextView maxim   = (TextView)findViewById(R.id.max);
        final SeekBar seekmin=(SeekBar)findViewById(R.id.seekBar4) ;
        final SeekBar seekmax=(SeekBar)findViewById(R.id.seekBar5) ;



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Temp");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                text.setText(value.toString() +" C");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        seekmin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                minim.setText("Min: "+ progress );


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
                maxim.setText("Max: "+ progress );


            }
        });
        }



    }

