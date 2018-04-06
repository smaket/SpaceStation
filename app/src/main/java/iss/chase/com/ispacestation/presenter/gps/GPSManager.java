package iss.chase.com.ispacestation.presenter.gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import iss.chase.com.ispacestation.view.ILocationChanged;


/**
 * Created by Bikash on 3/9/2018.
 * Get the GPS Lat long of the current location which will be send it to the server to get the satellite passing information on that current location.
 */

public class GPSManager extends Service implements LocationListener {

    /**
     * Activity Context to get the current location lat long
     */
    private final Activity mContext;

    // flag for GPS status
    private boolean isGPSEnabled = false;

    // flag for network status
    private boolean isNetworkEnabled = false;
    // GPS Provider details
    private String provider;

    // flag for GPS status
    private boolean canGetLocation = false;

    private Location location; // location
    private double latitude; // latitude
    private double longitude; // longitude

    // The minimum distance to change Updates in meters . 30 meter to reduce polling to avoid battery drain
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 30; // 30 meters

    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 3 minute to avoid battery drain
    public static final long FASTEST_INTERVAL = 1000 * 30 * 1; // 30sec to avoid battery drain
    private static String permissionNeed =
            "Manifest.permission.ACCESS_FINE_LOCATION"
    ;
    private static final int REQUEST_CODE_PERMISSION = 2;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    /**
     * Location change Listener
     */
    private static  ILocationChanged mLocaChangedListener;

    /**
     * Register listener to receive location change updates.
     * @param aLocationChanged ILocationChanged instance to register to receive the call back.
     */
    public static void registerListener(ILocationChanged aLocationChanged){
        mLocaChangedListener = aLocationChanged;
    }

    /**
     * GPS manager constructor for object initialization
     * @param context Activity context to get the location
     */
    public GPSManager(Activity context) {
        this.mContext = context;
        //Get the current location
        getLocation();
    }

    /**
     * Return current location details
     * @return Location current location details e.g. Lat, Long etc.
     */
    public Location getLocation() {
     /*   if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(mContext,"Permission is not granted so returning back ",Toast.LENGTH_LONG).show();
//            showSettingsAlert();
            return null ;
        }*/

        try {

            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            if(provider != null) {
                Location location = locationManager.getLastKnownLocation(provider);

                // Initialize the location fields
                if (location != null) {
                    System.out.println("Provider " + provider + " has been selected.");
                    onLocationChanged(location);
                    resumeGpsService();
                }
            }else {
//                ISSUtils.checkPermission(mContext);
            }


        }catch (SecurityException secx) {
            secx.printStackTrace();
//            ISSUtils.checkPermission(mContext);
            showSettingsAlert();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSManager.this);
        }
    }

    /**
     * Resume back gps service if interrupted.
     */
    public void resumeGpsService() {
        if(locationManager != null) {
            try {
                locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }catch (SecurityException secx) {
                showSettingsAlert();
                secx.printStackTrace();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * Function to get latitude
     * */

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

        if(mLocaChangedListener != null) {
            mLocaChangedListener.onLocationChanged(location);
        }

//        Toast.makeText(mContext , "Onlocation Changed called" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
