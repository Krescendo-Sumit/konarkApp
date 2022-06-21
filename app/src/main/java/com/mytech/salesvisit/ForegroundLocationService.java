package com.mytech.salesvisit;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.UserlogoutRequest;
import com.mytech.salesvisit.util.AppConfig;
import com.mytech.salesvisit.util.DbUtil;
import com.mytech.salesvisit.util.Util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundLocationService extends Service {


    private static final String TAG = ForegroundLocationService.class.getSimpleName();

    private final IBinder mBinder = new LocalBinder();

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private static final int NOTIFICATION_ID = 12345678;

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;

    private NotificationManager mNotificationManager;

    /**
     * Contains parameters used by {@link com.google.android.gms.location.FusedLocationProviderApi}.
     */
    private LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Callback for changes in location.
     */
    private LocationCallback mLocationCallback;

    private Handler mServiceHandler;

    /**
     * The current location.
     */
    private Location mLocation;
    private AppDatabase db;
    private Timer timer;

    public ForegroundLocationService() {
    }
    @Override
    public void onCreate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };

        createLocationRequest();
        getLastLocation();

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(Util.CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }
        db = AppDatabase.getInstance(getApplicationContext());
        timer= new Timer();
        long oneMinuteInMills=1*60*1000;
        timer.schedule(task,oneMinuteInMills,oneMinuteInMills );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service started");
        boolean startedFromNotification = intent.getBooleanExtra(Util.EXTRA_STARTED_FROM_NOTIFICATION,
                false);

        // We got here because the user decided to remove location updates from the notification.
        if (startedFromNotification) {
            removeLocationUpdates();
            logout();
            stopSelf();
        }
        // Tells the system to not try to recreate the service after it has been killed.
        return START_NOT_STICKY;
    }

    private void logout() {
        AuthService authService = NetworkService.getInstance().getAuthService();
        UserlogoutRequest request = new UserlogoutRequest();
        request.setUserId(db.userDao().getUser().getUserID());
        request.setGeoLocation(Util.convertlatlong(mLocation));
        Call<String> logout = authService.logout(request);
         logout.enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 if(!response.isSuccessful()){
                     Log.i(TAG, "error while logout");
                 }
             }
             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 Log.i(TAG, "error while logout");
             }
         });
        User user = db.userDao().getUser();
        user.setApiAuthToken("");
        db.userDao().updateUsers(user);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        Log.i(TAG, "in onBind()");
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        Log.i(TAG, "in onRebind()");
        stopForeground(true);
        unregisterReceiver(gpsLocationReceiver);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Last client unbound from service");

        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration && DbUtil.isLocationEnabled(db.userDao().getUser())) {
            Log.i(TAG, "Starting foreground service");

            startForeground(NOTIFICATION_ID, ServiceNotification.getServiceNotification(this,Util.CHANNEL_ID));
            registerReceiver(gpsLocationReceiver, new IntentFilter(Util.BROADCAST_ACTION));
        }else {
            removeLocationUpdates();
            stopSelf();
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    @Override
    public void onDestroy() {
        mServiceHandler.removeCallbacksAndMessages(null);
        try {
            unregisterReceiver(gpsLocationReceiver);
            timer.cancel();
        }catch (Exception e){}

    }

    public void requestLocationUpdates() {
        Log.i(TAG, "Requesting location updates");
        //Utils.setRequestingLocationUpdates(this, true);
        startService(new Intent(getApplicationContext(), ForegroundLocationService.class));
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {
           // Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }


    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
          //  Utils.setRequestingLocationUpdates(this, false);
            stopSelf();
        } catch (SecurityException unlikely) {
            //Utils.setRequestingLocationUpdates(this, true);
            Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }


    private void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                            } else {
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }
    }

    public void onNewLocation(Location location) {
        Log.i(TAG, "New location: " + location);
        mLocation=mLocation==null?location:mLocation;
      if(DbUtil.saveGeoLog(db,mLocation,location)){
          Log.i(TAG, "Stored location: " + location);
          mLocation = location;
      }
    }

    /**
     * Sets the location request parameters.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        ForegroundLocationService getService() {
            return ForegroundLocationService.this;
        }
    }

    /**
     * Returns true if this is a foreground service.
     *
     * @param context The {@link Context}.
     */
    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(Util.BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e(TAG, "GPS is not Enabled in your device");
                    ServiceNotification.notifyGps(getApplicationContext());
                }else {
                    ServiceNotification.cancel(getApplicationContext());
                }

            }
        }
    };

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(AppConfig.TYPE.equalsIgnoreCase("debug")){
                Log.d(TAG, "Timed location collect:"+mLocation);
            }

            if(mLocation==null){
                getLastLocation();
                return;
            }
            if(DbUtil.saveGeoLog(db,mLocation,mLocation)){
                mLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            }
        }
    };
}
