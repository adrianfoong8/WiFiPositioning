package com.example.android.wi_fipositioning;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.IARegion;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements IALocationListener, IARegion.Listener {

    static final String FASTEST_INTERVAL = "fastestInterval";
    static final String SHORTEST_DISPLACEMENT = "shortestDisplacement";
    private static final int location_permission = 0;
    String[] neededPermissions = {
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private IALocationManager mLocationManager;

    private long mFastestInterval = -1L;
    private float mShortestDisplacement = -1f;

    private String mCurrentStatus = null;
    private String mCurrentQuality = null;
    private String mCurrentLat = null;
    private String mCurrentLong = null;
    private String mCurrentLocation = null;
    private Integer mCurrentFloorLevel = null;

    private TextView mUiStatus;
    private TextView mUiQuality;
    private TextView mUiLat;
    private TextView mUiLong;
    private TextView mUiLocation;
    private TextView mUiFloorLevel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(MainActivity.this, neededPermissions, location_permission);

        if (savedInstanceState != null) {
            mFastestInterval = savedInstanceState.getLong(FASTEST_INTERVAL);
            mShortestDisplacement = savedInstanceState.getFloat(SHORTEST_DISPLACEMENT);
        }

        setContentView(R.layout.activity_main);
        mLocationManager = IALocationManager.create(this);

        mUiStatus = (TextView) findViewById(R.id.text_view_status);
        mUiQuality = (TextView) findViewById(R.id.text_view_calibration_quality);
        mUiLat = (TextView) findViewById(R.id.text_view_lat);
        mUiLong = (TextView) findViewById(R.id.text_view_long);
        mUiLocation = (TextView) findViewById(R.id.text_view_location);
        mUiFloorLevel = (TextView) findViewById(R.id.text_view_floor_level);

        mCurrentStatus = "-";
        mCurrentQuality = "-";
        mCurrentLat = "0.0";
        mCurrentLong = "0.0";
        mCurrentLocation = "-";
        mCurrentFloorLevel = 0;
        updateUi();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case location_permission: {
                if (grantResults.length > 0) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, neededPermissions, location_permission);
                    }
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationManager.registerRegionListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.unregisterRegionListener(this);
    }

    public void requestUpdates(View view) {
        mLocationManager.requestLocationUpdates(IALocationRequest.create(), MainActivity.this);
    }

    public void removeUpdates(View view) {
        mLocationManager.removeLocationUpdates(this);
    }

    @Override
    public void onLocationChanged(IALocation location) {
        mCurrentLat = (String.format(Locale.US, "%f", location.getLatitude()));
        mCurrentLong = (String.format(Locale.US, "%f", location.getLongitude()));
        if ((location.getLatitude() >= 4.583344 && location.getLatitude() <= 4.583449) && (location.getLongitude() >= 101.094404 && location.getLongitude() <= 101.094462)) {
            mCurrentLocation = "CSL4";
        } else if ((location.getLatitude() >= 4.583339 && location.getLatitude() <= 4.583446) && (location.getLongitude() >= 101.094464 && location.getLongitude() <= 101.094452)) {
            mCurrentLocation = "CSL3";
        } else if ((location.getLatitude() >= 4.583334 && location.getLatitude() <= 4.583439) && (location.getLongitude() >= 101.094519 && location.getLongitude() <= 101.094577)) {
            mCurrentLocation = "CSL2";
        } else if ((location.getLatitude() >= 4.583329 && location.getLatitude() <= 4.583435) && (location.getLongitude() >= 101.094579 && location.getLongitude() <= 101.094637)) {
            mCurrentLocation = "DELTA LAB";
        } else if ((location.getLatitude() >= 4.583314 && location.getLatitude() <= 4.583424) && (location.getLongitude() >= 101.094704 && location.getLongitude() <= 101.094779)) {
            mCurrentLocation = "LR1";
        } else if ((location.getLatitude() >= 4.583307 && location.getLatitude() <= 4.583416) && (location.getLongitude() >= 101.094780 && location.getLongitude() <= 101.094857)) {
            mCurrentLocation = "LR7";
        } else if ((location.getLatitude() >= 4.583487 && location.getLatitude() <= 4.583593) && (location.getLongitude() >= 101.094451 && location.getLongitude() <= 101.094516)) {
            mCurrentLocation = "EL4";
        } else if ((location.getLatitude() >= 4.583480 && location.getLatitude() <= 4.583588) && (location.getLongitude() >= 101.094517 && location.getLongitude() <= 101.094582)) {
            mCurrentLocation = "EL3";
        } else if ((location.getLatitude() >= 4.583474 && location.getLatitude() <= 4.583581) && (location.getLongitude() >= 101.094582 && location.getLongitude() <= 101.094646)) {
            mCurrentLocation = "EL2";
        } else if ((location.getLatitude() >= 4.583468 && location.getLatitude() <= 4.583576) && (location.getLongitude() >= 101.094648 && location.getLongitude() <= 101.094714)) {
            mCurrentLocation = "EL1";
        } else if ((location.getLatitude() >= 4.583463 && location.getLatitude() <= 4.583570) && (location.getLongitude() >= 101.094713 && location.getLongitude() <= 101.094778)) {
            mCurrentLocation = "STUDENT LOUNGE";
        } else if ((location.getLatitude() >= 4.583455 && location.getLatitude() <= 4.583563) && (location.getLongitude() >= 101.094780 && location.getLongitude() <= 101.094843)) {
            mCurrentLocation = "EXAM DIVISION";
        }
        mCurrentFloorLevel = location.hasFloorLevel() ? location.getFloorLevel() : null;
        updateUi();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case IALocationManager.STATUS_CALIBRATION_CHANGED:
                String quality = "unknown";
                switch (extras.getInt("quality")) {
                    case IALocationManager.CALIBRATION_POOR:
                        quality = "Poor";
                        break;
                    case IALocationManager.CALIBRATION_GOOD:
                        quality = "Good";
                        break;
                    case IALocationManager.CALIBRATION_EXCELLENT:
                        quality = "Excellent";
                        break;
                }
                mCurrentQuality = quality;
                break;
            case IALocationManager.STATUS_AVAILABLE:
                mCurrentStatus = "Available";
                break;
            case IALocationManager.STATUS_LIMITED:
                mCurrentStatus = "Limited";
                break;
            case IALocationManager.STATUS_OUT_OF_SERVICE:
                mCurrentStatus = "Out of service";
                break;
            case IALocationManager.STATUS_TEMPORARILY_UNAVAILABLE:
                mCurrentStatus = "Temporarily unavailable";
        }
        updateUi();
    }

    @Override
    public void onEnterRegion(IARegion region) {
    }

    @Override
    public void onExitRegion(IARegion region) {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(FASTEST_INTERVAL, mFastestInterval);
        savedInstanceState.putFloat(SHORTEST_DISPLACEMENT, mShortestDisplacement);
        super.onSaveInstanceState(savedInstanceState);
    }

    void updateUi() {
        String status = "";
        String quality = "";
        String lat = "";
        String lon = "";
        String location = "";
        String level = "";

        if (mCurrentStatus != null) {
            status = mCurrentStatus.toString();
        }
        setText(mUiStatus, status);
        if (mCurrentQuality != null) {
            quality = mCurrentQuality.toString();
        }
        setText(mUiQuality, quality);
        if (mCurrentLat != null) {
            lat = mCurrentLat.toString();
        }
        setText(mUiLat, lat);
        if (mCurrentLong != null) {
            lon = mCurrentLong.toString();
        }
        setText(mUiLong, lon);
        if (mCurrentLocation != null) {
            location = mCurrentLocation.toString();
        }
        setText(mUiLocation, location);
        if (mCurrentFloorLevel != null) {
            level = mCurrentFloorLevel.toString();
        }
        setText(mUiFloorLevel, level);
    }

    void setText(@NonNull TextView view, @NonNull String text) {
        if (!view.getText().toString().equals(text)) {
            view.setText(text);
        }
    }
}
