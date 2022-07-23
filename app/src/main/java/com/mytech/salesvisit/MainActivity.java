package com.mytech.salesvisit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.GeoLocation;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.NetworkTasks;
import com.mytech.salesvisit.net.UserlogoutRequest;
import com.mytech.salesvisit.ui.ActivityCallback;
import com.mytech.salesvisit.ui.AddCustomerFragment;
import com.mytech.salesvisit.ui.BaseFragment;
import com.mytech.salesvisit.ui.CheckOutFragment;
import com.mytech.salesvisit.ui.CheckinFragment;
import com.mytech.salesvisit.ui.HomeFragment;
import com.mytech.salesvisit.ui.LoginFragment;
import com.mytech.salesvisit.ui.ProfileFragment;
import com.mytech.salesvisit.ui.checkin.CheckinAPI;
import com.mytech.salesvisit.ui.checkin.ResultOutput;
import com.mytech.salesvisit.util.AppConfig;
import com.mytech.salesvisit.util.DbUtil;
import com.mytech.salesvisit.util.JobUtil;
import com.mytech.salesvisit.util.Util;
import com.mytech.salesvisit.view.offlineorder.OfflineOrderSyncActivity;
import com.mytech.salesvisit.view.ordercreate.OrderCreate;
import com.mytech.salesvisit.view.orderdata.OrderDataList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityCallback, View.OnClickListener , ResultOutput {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUESTS = 1;
    private static final int REQUEST_CHECK_SETTINGS = 7;

    Toolbar toolbar;
    GeoLocation geoLocation;
    NavigationView navigationView;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequestHighAccuracy;
    AppDatabase db;
    TextView txtDesignation, txtFullname;
    private Looper mLooper;
    private ForegroundLocationService mService;
    private boolean mBound;
    private Location mLocation;
    CheckinAPI checkinAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        txtDesignation = headerView.findViewById(R.id.textDesignation);
        txtFullname = headerView.findViewById(R.id.textFullName);
        mFusedLocationClient = LocationServices.
                getFusedLocationProviderClient(this);
        db = AppDatabase.getInstance(getApplicationContext());
        showLogin(savedInstanceState);
        init();
        checkinAPI=new CheckinAPI(MainActivity.this,this);
        checkinAPI.getVersionCode();
    }

    private void init() {

        mLocationRequestHighAccuracy = LocationRequest.create();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.
                PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(20 * 1000);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            Location location= locationResult.getLastLocation();
            if(AppConfig.TYPE.equalsIgnoreCase("debug")){
                Log.d(TAG,"Location updated "+(location!=null?location.getLatitude()+","+location.getLongitude():null));
            }
            if(locationResult!=null) {
                String ac=String.valueOf(location.getAccuracy());
                String lat =String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude());
             if(geoLocation!=null){
                 geoLocation.setAccuracy(ac);
                 geoLocation.setLatLong(lat);
             }else {
                 geoLocation =new GeoLocation(lat,ac);
             }
            }
            if(mService!=null & location!=null){
                mService.onNewLocation(location);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
        super.onDestroy();
    }

    private void requestUpdate() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermissions();
        }else {
            Log.e(TAG, "GPS requesting");
            mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, locationCallback, Looper.myLooper());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NetworkTasks tasks = new NetworkTasks(db);
                    tasks.uploadRecords();
                }
            }).start();
            return true;
        }else if(id == R.id.action_about){
            StringBuilder message = new StringBuilder();
            message.append("Version: ").append(BuildConfig.VERSION_NAME).append("-"+ AppConfig.TYPE)
                    .append("\n")
                    .append("Server: ").append(AppConfig.BASEURL);

            showDialog(message.toString());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                moveToHome();
                break;
            case R.id.nav_myProfile:
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setUser(db.userDao().getUser());
                setFragment(profileFragment);
                break;
            case R.id.nav_checkIn:
                CheckinFragment checkinFragment = new CheckinFragment();
                checkinFragment.setUser(db.userDao().getUser());
                setFragment(checkinFragment);
                break;
            case R.id.nav_checkout:
                moveToCheckout();
                break;
            case R.id.nav_logout:
                showExitDialog();
                break;
            case R.id.nav_add_ustomer:
                AddCustomerFragment fragment = new AddCustomerFragment();
                fragment.setUser(db.userDao().getUser());
                setFragment(fragment);
                break;
            case R.id.nav_create_order:
                Intent intent = new Intent(MainActivity.this, OrderCreate.class);
                intent.putExtra("Type","New");
                startActivity(intent);
                break;
            case R.id.nav_view_order:
                Intent intent1 = new Intent(MainActivity.this, OrderDataList.class);
                startActivity(intent1);
                break;
            case R.id.nav_view_offline_order:
                Intent intent2 = new Intent(MainActivity.this, OfflineOrderSyncActivity.class);
                startActivity(intent2);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void moveToHome() {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setUser(db.userDao().getUser());
        setFragment(homeFragment);

    } @Override
    public void moveToLogin() {
        logout();
        User user =db.userDao().getUser();
        user.setApiAuthToken("");
        db.userDao().updateUsers(user);
        LoginFragment fragment = new LoginFragment();
        getSupportActionBar().hide();
        setFragment(fragment);
    }

    @Override
    public void moveToCheckout() {
        CheckOutFragment checkOutFragment = new CheckOutFragment();
        checkOutFragment.setUser(db.userDao().getUser());
        setFragment(checkOutFragment);
    }

    @Override
    public GeoLocation getLocation() {
        return geoLocation;
    }

    @Override
    public void checkIn(Integer custId) {
        DbUtil.saveCheckin(db,Util.toDblocation(getLocation()),custId);
    }

    private void setFragment(Fragment fragment) {
        fragment.setEnterTransition(new Slide(Gravity.LEFT));
        fragment.setExitTransition(new Slide(Gravity.END));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();

    }


    private void showLogin(Bundle savedInstanceState) {
        User user = db.userDao().getUser();
        BaseFragment fragment;
        if (user != null && !TextUtils.isEmpty(user.getApiAuthToken())) {
            updateHeader();
            fragment = new HomeFragment();
            fragment.setUser(user);
            JobUtil.scheduleJob(this);
            FirebaseCrashlytics.getInstance().setUserId(user.getUserName());
        } else {
            fragment = new LoginFragment();
            getSupportActionBar().hide();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }


    }

    @Override
    public void updateHeader() {
        User user = db.userDao().getUser();
        if (user != null) {
            txtFullname.setText(user.getFullName());
            txtDesignation.setText(user.getDesignation());
        }
    }

    @Override
    public void visitInProgress(boolean inProgress) {
        hideCheckin(!inProgress);
        hideCheckOut(inProgress);
        if(!inProgress){
            DbUtil.clearCheckin(db);
        }
    }

    private String[] getRequiredPermissions() {
        return new String[]{
        Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.VIBRATE,
        };
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {

            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    void showExitDialog() {


        new MaterialAlertDialogBuilder(this)
                .setTitle("Exit")
                .setMessage("Do you want to log off?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .show();
    }

    private void logout() {
        AuthService authService = NetworkService.getInstance().getAuthService();
        UserlogoutRequest request = new UserlogoutRequest();
        request.setUserId(db.userDao().getUser().getUserID());
        request.setGeoLocation(getLocation());
        Call<String> reset = authService.logout(request);
        reset.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        User user =db.userDao().getUser();
                        user.setApiAuthToken("");
                        db.userDao().updateUsers(user);
                        JobUtil.cancelJob(getApplicationContext());
                        finish();
                    } else if (response.errorBody() != null) {
                        ErrorMessage errorMessage;
                        String errstring = response.errorBody().string();
                        Log.d(TAG, errstring);
                        errorMessage = Util.getNetworkMapper().readValue(errstring, ErrorMessage.class);
                        showDialog(errorMessage.getMessage());
                    }
                } catch (IOException e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showNetWorkErrDialog();
            }
        });

    }

    @SuppressLint("MissingPermission")
    void checkin() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                Log.d(TAG, location.toString());
            }
        });

    }

    @Override
    public User storeUser(User userResponse) {
        if(db.userDao().getUser()!=null){
            userResponse.setId(db.userDao().getUser().getId());
            db.userDao().updateUsers(userResponse);
        }else {
            db.userDao().insertAll(userResponse);
        }
        return db.userDao().getUser();
    }

    @Override
    public User getCurrentUser() {
        return db.userDao().getUser();
    }


    void showNetWorkErrDialog() {
        showDialog(getResources().getString(R.string.dialog_header), getResources().getString(R.string.network_err_message));
    }


    void showDialog(final String message) {
        showDialog(getResources().getString(R.string.dialog_header), message);
    }

    void showDialog(final String title, final String message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
    }

    private void hideCheckin(boolean visible) {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_checkIn).setVisible(visible);
    }

    private void hideCheckOut(boolean visible) {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_checkout).setVisible(visible);
    }

    private void showLocationDialog() {
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequestHighAccuracy);
    Task<LocationSettingsResponse> task =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
    task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
        @Override
        public void onComplete (Task < LocationSettingsResponse > task) {
        try {
            LocationSettingsResponse response = task.getResult(ApiException.class);
            Log.e(TAG, "GPS setting are OK");
             requestUpdate();
        } catch (ApiException exception) {
            switch (exception.getStatusCode()) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the
                    // user a dialog.
                    try {
                        // Cast to a resolvable exception.
                        ResolvableApiException resolvable = (ResolvableApiException) exception;
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        resolvable.startResolutionForResult(
                                MainActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    } catch (ClassCastException e) {
                        // Ignore, should be an impossible error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    break;
            }
        }
    }
    });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Log.e(TAG, "RuntimePermissions required changes were successfully made");
                        requestUpdate();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was  asked to change settings, but chose not to
                        Log.e(TAG, "RuntimePermissions The user was  asked to change settings, but chose not to CANCELED Finishing() ");
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(Util.BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e(TAG, "GPS is Enabled in your device");
                    requestUpdate();
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();
                    Log.e(TAG, "GPS is Disabled in your device");
                }

            }
        }
    };

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showLocationDialog();
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ForegroundLocationService.LocalBinder binder = (ForegroundLocationService.LocalBinder) service;
            mService = binder.getService();
            mService.requestLocationUpdates();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!allPermissionsGranted()) {
            Log.e(TAG, "RuntimePermissions not granted");
            getRuntimePermissions();
        } else {
            showLocationDialog();
        }
        registerReceiver(gpsLocationReceiver, new IntentFilter(Util.BROADCAST_ACTION));

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, ForegroundLocationService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(gpsLocationReceiver);
    }

    @Override
    public void onListResponce_Customer(List result) {

    }

    @Override
    public void onVersionResponse(String result) {

        try {
            String vname = BuildConfig.VERSION_NAME;
            String version=result.replace("\"","").trim();
            if (!version.equals(vname.trim()))
            {
                Log.i("Result","New Vesrion "+vname +" "+version);
                Toast.makeText(MainActivity.this, "New Version available : "+version, Toast.LENGTH_SHORT).show();

                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.version_update);
                Button btn_cancel,btn_update;

                btn_cancel=dialog.findViewById(R.id.btncancel);
                btn_update=dialog.findViewById(R.id.btnupdate);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finish();
                    }
                });

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this, ""+getPackageName(), Toast.LENGTH_SHORT).show();
                        String appPackageName = getPackageName(); // package name of the app
                        try {

                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        finish();
                    }
                });


                dialog.show();

            }


       }catch(Exception e)
        {

        }
        }
}
