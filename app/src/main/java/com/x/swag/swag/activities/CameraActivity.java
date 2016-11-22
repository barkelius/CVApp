package com.x.swag.swag.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.x.swag.swag.R;
import com.x.swag.swag.camera.Preview;

public class CameraActivity extends AppCompatActivity {

    private Preview preview;
    Camera camera;
    int cameraCurrentlyLocked;
    int defaultCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setupActionBar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Adding new person...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null) //.setActionTextColor(Color.RED)
                            .show();
                    camera.takePicture(null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            Log.d("Camera", "" + data);
                        }
                    }, null);

                }
            });

        preview = new Preview(this);
        FrameLayout cameraContainer = (FrameLayout)findViewById(R.id.preview_view);
        cameraContainer.addView(preview);

        TextView personName = (TextView)findViewById(R.id.person_name);
        personName.setText("Axel");

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.autoFocus(null);
            }
        });

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            // TODO: If Settings has multiple levels, Up should navigate up
            // that hierarchy.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (camera != null) {
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
    }

    private static final int CAMERA_PERMISSION_REQUEST = 4;

    @Override
    protected void onResume() {
        super.onResume();

        // Open the default i.e. the first rear facing camera.

        initCamera();
    }

    private void initCamera() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            camera = Camera.open();
            cameraCurrentlyLocked = defaultCameraId;
            preview.setCamera(camera);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERMISSION_REQUEST) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                initCamera();
        }


    }
}
