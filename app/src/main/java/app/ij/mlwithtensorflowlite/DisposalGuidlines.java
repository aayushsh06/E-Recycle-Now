package app.ij.mlwithtensorflowlite;

import static app.ij.mlwithtensorflowlite.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisposalGuidlines extends AppCompatActivity {
  //  Button mapbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_disposal_guidlines);
      // mapbutton = findViewById(R.id.mapbutton);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(drawable.adobe);
       actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#40509a")));

      //  getSupportActionBar().setLogo(R.drawable.ewaste99);
     //   actionBar.setDisplayShowTitleEnabled(true);
     //   actionBar.setDisplayShowHomeEnabled(true);
       // actionBar.setDisplayUseLogoEnabled(true);

       getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        // Customize the back button
   //    getSupportActionBar().setLogo(R.drawable.ewaste99);

     /////////   actionBar.setHomeAsUpIndicator(R.drawable.ewaste99);
      //  getSupportActionBar().setTitle("Smart Way");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
   //     actionBar.setDisplayUseLogoEnabled(true);
        // showing the back button in action bar
     //  actionBar.setDisplayHomeAsUpEnabled(true);
       /* mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Intent i = new Intent(DisposalGuidlines.this, MapsActivity.class);
                //  i.putExtra("location",mLastLocation);
                //    Log.i(TAG, "mapsctivity location value1: " + mLastLocation.getLatitude());
                Intent i = new Intent(DisposalGuidlines.this, MapsActivity.class);
                startActivity(i);

            }
        });*/
    }

    // this event will enable the back
    // function to the button on press



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


}