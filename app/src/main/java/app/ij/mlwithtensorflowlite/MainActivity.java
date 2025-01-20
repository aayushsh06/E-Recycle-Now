/*
 * Created by ishaanjav
 * github.com/ishaanjav
*/

package app.ij.mlwithtensorflowlite;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.MotionEffect;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import app.ij.mlwithtensorflowlite.ml.ModelUnquant;
import android.location.Location;









public class MainActivity extends AppCompatActivity {

    TextView result, confidence;
    ImageView imageView;
    Button picture;
    int imageSize = 224;
    String TAG = null;
    Button readCourseBtn,addCourseBtn;
    EditText courseNameEdt;
    TextView classifiedtext;
    DBHandler dbHandler;
    Button guideline;
    EditText  courseTracksEdt, courseDurationEdt, courseDescriptionEdt;
    String item ="";
    Button mapbutton;











    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  getSupportActionBar().setDisplayShowTitleEnabled(true);
     //   getSupportActionBar().setTitle("Smart Way");
       // getSupportActionBar().setLogo(R.drawable.electronocewaste);
      //  actionBar.setLogo(R.drawable.adobe);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

     //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#40509a")));

     //  actionBar.setDisplayHomeAsUpEnabled(true);


       // actionBar.setDefaultDisplayHomeAsUpEnabled(R.drawable.ewaste99);

        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);
        readCourseBtn = findViewById(R.id.idBtnReadCourse);
        classifiedtext = findViewById(R.id.classified);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        dbHandler = new DBHandler(MainActivity.this);
        guideline = findViewById(R.id.idGuidelines);

        mapbutton = findViewById(R.id.mapbutton);
       // Set up Home button@Override
        //public boolean onSupportNavigateUp() {
        //    finish();
        //    return super.onSupportNavigateUp();
        //}

        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("ITem",result.getText());
                classifiedtext.setText(item);
                result.getText();


            //    Log.i(TAG, "mapsctivity location value1: " + mLastLocation.getLatitude());
                startActivity(i);


        }
        });

        Bundle extras = getIntent().getExtras();
        // String ITEM = null;
        if (extras != null) {
            String mapcheck = extras.getString("Text");
            Log.i(MotionEffect.TAG, "check VALUE: " + mapcheck);
            classifiedtext.setText("");
          //  result.setText(mapcheck);
            //The key argument here must match that used in the other activity
        }

        guideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(MainActivity.this, DisposalGuidlines.class);
         //       i.putExtra("text_valuee", (CharSequence) imageView.getDrawable());
         //       Log.i(TAG, "classifyImageonactivityRead: " + imageView.getDrawable());
           //    result.setText("");
           //     classifiedtext.setText("");
               // imageView.setVisibility(View.INVISIBLE);

                startActivity(i);
                imageView.setImageResource(R.drawable.recycle1);
              //  openDisposalGuidlines();
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
              //  classifiedtext.setText(item);

            }
        });

        addCourseBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // below line is to get data from all edit text fields.

                String courseName = courseNameEdt.getText().toString();

                /*String courseTracks = courseTracksEdt.getText().toString();

                String courseDuration = courseDurationEdt.getText().toString();

                String courseDescription = courseDescriptionEdt.getText().toString();*/


              /*  if (courseName.isEmpty() ) {

                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();

                    return;

                }*/

                dbHandler.addNewCourse(courseName);
                Log.i(TAG, "coursename: " + courseName);

                // after adding the data we are displaying a toast message.

         //       Toast.makeText(MainActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();

               courseNameEdt.setText("");
             //  imageView.setImageIcon(getDrawable(R.drawable.recycle1));


             //   courseDurationEdt.setText("");

             //   courseTracksEdt.setText("");

             //   courseDescriptionEdt.setText("");

            } });



        readCourseBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // opening a new activity via a intent.

                Intent i = new Intent(MainActivity.this, ViewCourses.class);
                i.putExtra("text_valuee",result.getText());
                Log.i(TAG, "classifyImageonactivityRead: " + result.getText());
              //  result.setText("");
            //    classifiedtext.setText("");
              //  imageView.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.recycle1);


                startActivity(i);




            }
        });
    }

    public void openDisposalGuidlines(){

        Intent intent = new Intent(this, DisposalGuidlines.class);
        startActivity(intent);
    }

    // this event will enable the back
    // function to the button on press


    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());
            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }}
            inputFeature0.loadBuffer(byteBuffer);
            // Runs model inference and gets result.
            ModelUnquant .Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }}
            String[] classes = {};
            result.setText(classes[maxPos]);
            String TAG = null;
            Log.i(TAG, "classifyImageonactivity: " + result.getText());

            String s = "";
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }
            confidence.setText(s);
         //   String TAG = null;
          //  Log.i(TAG, "classifyImage: " + s);


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        //    String TAG = null;
            Log.i(TAG, "onActivityResult: ");
            classifiedtext.setText(item);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}