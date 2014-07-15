package com.boha.beacon;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

import static com.estimote.sdk.Utils.Proximity;
import static com.estimote.sdk.Utils.computeAccuracy;
import static com.estimote.sdk.Utils.computeProximity;
import static com.estimote.sdk.Utils.isBeaconInRegion;

/**
 * Visualizes distance from beacon to the device.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class DistanceBeaconActivity extends Activity {

  private static final String TAG = DistanceBeaconActivity.class.getSimpleName();

  // Y positions are relative to height of bg_distance image.
  private static final double RELATIVE_START_POS = 320.0 / 1110.0;
  private static final double RELATIVE_STOP_POS = 885.0 / 1110.0;

  private BeaconManager beaconManager;
  private Beacon beacon;
  private Region region;

  private View dotView;
  private int startY = -1;
  private int segmentLength = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.distance_view);
      Log.e(TAG, "############# onCreate");
    dotView = findViewById(R.id.dot);

    beacon = getIntent().getParcelableExtra(ListBeaconsActivity.EXTRAS_BEACON);
    region = new Region("regionid", beacon.getProximityUUID(), beacon.getMajor(), beacon.getMinor());
    if (beacon == null) {
      Toast.makeText(this, "Beacon not found in intent extras", Toast.LENGTH_LONG).show();
      finish();
    } else {
        Log.e(TAG, "############# Beacon has been found in extras");
    }

    beaconManager = new BeaconManager(this);
    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
      @Override
      public void onBeaconsDiscovered(Region region, final List<Beacon> rangedBeacons) {
        // Note that results are not delivered on UI thread.
          Log.w(TAG, "############# onBeaconsDiscovered ....");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "############# run runOnUiThread");
                // Just in case if there are multiple beacons with the same uuid, major, minor.
                Beacon foundBeacon = null;
                for (Beacon rangedBeacon : rangedBeacons) {
                    if (rangedBeacon.getMacAddress().equals(beacon.getMacAddress())) {
                        foundBeacon = rangedBeacon;
                    }
                }
                if (foundBeacon != null) {
                    double distance = Math.min(computeAccuracy(beacon), 6.0);
                    Log.d(TAG, "############# distance from phone: " + distance);
                    updateDistanceView(foundBeacon);
                }
            }
        });
      }
    });

    final View view = findViewById(R.id.sonar);
    view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        startY = (int) (RELATIVE_START_POS * view.getMeasuredHeight());
        int stopY = (int) (RELATIVE_STOP_POS * view.getMeasuredHeight());
        segmentLength = stopY - startY;

        dotView.setVisibility(View.VISIBLE);
        dotView.setTranslationY(computeDotPosY(beacon));
      }
    });
  }

  private void updateDistanceView(Beacon foundBeacon) {
      Log.e(TAG, "############# updateDistanceView");
    if (segmentLength == -1) {
      return;
    }

    dotView.animate().translationY(computeDotPosY(foundBeacon)).start();
  }

  private int computeDotPosY(Beacon beacon) {
    // Let's put dot at the end of the scale when it's further than 6m.

    double accuracy = computeAccuracy(beacon);
    Proximity prox = computeProximity(beacon);
    boolean isBeaconInRegion = isBeaconInRegion(beacon, region);


    Log.d(TAG, "############# computeDotPosY, accuracy: " + accuracy + "\nproximity.name: " + prox.name()
        + " isBeaconInRegion: " + isBeaconInRegion + " name: " + beacon.getName() +
        "\nmac address: " + beacon.getMacAddress() + "\nproximityUUID: " + beacon.getProximityUUID());
    double distance = Math.min(computeAccuracy(beacon), 6.0);
    Log.i(TAG, "computeDotPosY ====> distance from phone: " + distance);
    return startY + (int) (segmentLength * (distance / 6.0));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart() {
    super.onStart();
      Log.e(TAG, "############# onStart");
    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
        @Override
        public void onServiceReady() {
            Log.w(TAG, "############# onServiceReady - start ranging");
            try {
                beaconManager.startRanging(region);
            } catch (RemoteException e) {
                Toast.makeText(DistanceBeaconActivity.this, "Cannot start ranging, something terrible happened",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Cannot start ranging", e);
            }
        }
    });
  }

  @Override
  protected void onStop() {
    beaconManager.disconnect();
      Log.e(TAG, "############# onStop beaconManager.disconnect");
    super.onStop();
  }
}
