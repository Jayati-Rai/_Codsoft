package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
private static final int CAMERA_PERMISSION_REQUEST_CODE=1;//ACCESSING THE CAMERA(WITH TRUE)
  private CameraManager cameraManager;//Creating object of class Camera
    private String cameraId;//a variable of string type
    private boolean isFlashlightOn = false;
    private ToggleButton toggleButton;
    private ConstraintLayout backgroundLayout;
    int[] back_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        back_images=new int[]{R.drawable.lig,R.drawable.ligoff};
        backgroundLayout=findViewById(R.id.backgroundLayout);
        toggleButton=findViewById(R.id.flashbutton);
        cameraManager=(CameraManager) getSystemService(CAMERA_SERVICE);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                {
                    turnOnFlashlight();
                    backgroundLayout.setBackgroundResource(back_images[0]);
                }
                else {
                    turnOffFlashlight();
                    backgroundLayout.setBackgroundResource(back_images[1]);
                }
            }
        });
        //to check whether or not camera permissions are granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
                    }
        else {
            //if the permission is granted initialize the camera
            initializeCamera();
        }
    }
private void initializeCamera()
{
    try {
        cameraId=cameraManager.getCameraIdList()[0];
    }catch(CameraAccessException e)
    {
        e.printStackTrace();
    }
}
private void turnOnFlashlight(){
        if(cameraId!=null)
        {
            try {
                cameraManager.setTorchMode(cameraId,true);
                isFlashlightOn=true;
            }catch(CameraAccessException e){
                e.printStackTrace();
            }
        }
}
private void turnOffFlashlight()
{
    if(cameraId!=null)
    {
        try {
            cameraManager.setTorchMode(cameraId, false);
            isFlashlightOn=false;
        }catch(CameraAccessException e){
            e.printStackTrace();
        }
    }
}

  @Override
  protected void onStop(){
        super.onStop();
        //turn off light when app is stopped
      if(isFlashlightOn)
      {
          turnOffFlashlight();
      }
  }
}