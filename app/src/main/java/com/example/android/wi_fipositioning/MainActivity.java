package com.example.android.wi_fipositioning;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.IARegion;

import java.util.Locale;

/**
 * Simple example that demonstrates basic interaction with {@link IALocationManager}.
 */
//@SdkExample(description = R.string.example_simple_description)
public class MainActivity extends AppCompatActivity
        implements IALocationListener, IARegion.Listener {

    static final String FASTEST_INTERVAL = "fastestInterval";
    static final String SHORTEST_DISPLACEMENT = "shortestDisplacement";
    Integer mCurrentFloorLevel = null;
    String mCurrentLocation = null;
    double mCurrentCertainty = 0.0;
    TextView mUiFloorLevel;
    TextView mUiLocation;
    TextView mUiCertainty;
    private IALocationManager mLocationManager;
    private TextView mLog;
    private ScrollView mScrollView;
    private long mRequestStartTime;
    private long mFastestInterval = -1L;
    private float mShortestDisplacement = -1f;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mFastestInterval = savedInstanceState.getLong(FASTEST_INTERVAL);
            mShortestDisplacement = savedInstanceState.getFloat(SHORTEST_DISPLACEMENT);
        }

        setContentView(R.layout.activity_main);
        mLog = (TextView) findViewById(R.id.text);
        mScrollView = (ScrollView) findViewById(R.id.scroller);
        mLocationManager = IALocationManager.create(this);

        mUiFloorLevel = (TextView) findViewById(R.id.text_view_floor_level);
        mUiLocation = (TextView) findViewById(R.id.text_view_location);
        mUiCertainty = (TextView) findViewById(R.id.text_view_certainty);

        updateUi();

        // Register long click for sharing traceId
//        ExampleUtils.shareTraceId(mLog, SimpleActivity.this, mLocationManager);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_simple, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        final int id = item.getItemId();
//        switch (id) {
//            case R.id.action_clear:
//                mLog.setText(null);
//                return true;
//            case R.id.action_share:
//                shareLog();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void requestUpdates(View view) {
        mRequestStartTime = SystemClock.elapsedRealtime();
        IALocationRequest request = IALocationRequest.create();

        mFastestInterval = -1L;
        mShortestDisplacement = -1f;

        mLocationManager.removeLocationUpdates(MainActivity.this);
        mLocationManager.requestLocationUpdates(request, MainActivity.this);

        log("Search started.");

        setText(mUiFloorLevel, "Searching...");
        setText(mUiLocation, "Searching...");
        setText(mUiCertainty, "0.0");
    }

    public void removeUpdates(View view) {
        log("Search stopped.");
        mLocationManager.removeLocationUpdates(this);
    }

    @Override
    public void onLocationChanged(IALocation location) {
        log(String.format(Locale.US, "%f,%f, accuracy: %.2f, certainty: %.2f",
                location.getLatitude(), location.getLongitude(), location.getAccuracy(),
                location.getFloorCertainty()));

        mCurrentFloorLevel = location.hasFloorLevel() ? location.getFloorLevel() : null;
        //mCurrentLocation = (String.format(Locale.US, "Lat: %f,%f", location.getLatitude(), location.getLongitude()));

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

        mCurrentCertainty = location.getAccuracy();
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
                log("Calibration change. Quality: " + quality);
                break;
            case IALocationManager.STATUS_AVAILABLE:
                log("onStatusChanged: Available");
                break;
            case IALocationManager.STATUS_LIMITED:
                log("onStatusChanged: Limited");
                break;
            case IALocationManager.STATUS_OUT_OF_SERVICE:
                log("onStatusChanged: Out of service");
                break;
            case IALocationManager.STATUS_TEMPORARILY_UNAVAILABLE:
                log("onStatusChanged: Temporarily unavailable");
        }
    }

    @Override
    public void onEnterRegion(IARegion region) {
        log("onEnterRegion: " + regionType(region.getType()) + ", " + region.getId());
    }

    @Override
    public void onExitRegion(IARegion region) {
        log("onExitRegion: " + regionType(region.getType()) + ", " + region.getId());
    }

    /**
     * Turn {@link IARegion#getType()} to human-readable name
     */
    private String regionType(int type) {
        switch (type) {
            case IARegion.TYPE_UNKNOWN:
                return "unknown";
            case IARegion.TYPE_FLOOR_PLAN:
                return "floor plan";
            case IARegion.TYPE_VENUE:
                return "venue";
            default:
                return Integer.toString(type);
        }
    }

    /**
     * Append message into log prefixing with duration since start of location requests.
     */
    private void log(String msg) {
        double duration = mRequestStartTime != 0
                ? (SystemClock.elapsedRealtime() - mRequestStartTime) / 1e3
                : 0d;
        mLog.append(String.format(Locale.US, "\n[%06.2f]: %s", duration, msg));
        mScrollView.smoothScrollBy(0, mLog.getBottom());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(FASTEST_INTERVAL, mFastestInterval);
        savedInstanceState.putFloat(SHORTEST_DISPLACEMENT, mShortestDisplacement);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Share current log content using registered apps.
     */
//    private void shareLog() {
//        Intent sendIntent = new Intent()
//                .setAction(Intent.ACTION_SEND)
//                .putExtra(Intent.EXTRA_TEXT, mLog.getText())
//                .setType("text/plain");
//        startActivity(sendIntent);
//    }

    void updateUi() {
        String level = "";
        String location = "";
        String certainty = "";
        if (mCurrentLocation != null) {
            location = mCurrentLocation.toString();
        }
        setText(mUiLocation, location);
        if (mCurrentFloorLevel != null) {
            level = mCurrentFloorLevel.toString();
        }
        setText(mUiFloorLevel, level);
        if (mCurrentCertainty != 0.0) {
            certainty = (String.format(Locale.US, "%.2f", mCurrentCertainty));
        }
        setText(mUiCertainty, certainty);
    }

    void setText(@NonNull TextView view, @NonNull String text) {
        if (!view.getText().toString().equals(text)) {
            view.setText(text);
        }
    }
}
