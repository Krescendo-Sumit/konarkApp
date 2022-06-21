package com.mytech.salesvisit;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.RecordDao;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.GeoRequest;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.NetworkTasks;
import com.mytech.salesvisit.net.UserCheckRequest;
import com.mytech.salesvisit.util.DbUtil;
import com.mytech.salesvisit.util.JobUtil;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class InternetService extends JobIntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPLOAD_RECORDS = "com.mytech.salesvisit.action.FOO";
    private static final String ACTION_BAZ = "com.mytech.salesvisit.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.mytech.salesvisit.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.mytech.salesvisit.extra.PARAM2";
    private static final String TAG = InternetService.class.getSimpleName();


    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, InternetService.class, JobUtil.JOB_UPLOAD_ID, work);
    }
    
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startUploadRecord(Context context, String param1, String param2) {
        Intent intent = new Intent(context, InternetService.class);
        intent.setAction(ACTION_UPLOAD_RECORDS);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        enqueueWork(context,intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, InternetService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(TAG, "Job executing");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_RECORDS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionUploadRecords(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUploadRecords(String param1, String param2) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        NetworkTasks tasks = new NetworkTasks(db);
        tasks.uploadRecords();

    }

    private void perfromAutoCheckout(AppDatabase db) {
        Record checkin = db.recordDao().checkin();
        Record lastGeo =db.recordDao().lastGeo();
        if(checkin==null || lastGeo==null){
            return;
        }
        float betweenDistance = Util.getdistanceBetween(checkin.getLocation(), lastGeo.getLocation());
        User user = db.userDao().getUser();
        float checkOutDistance = user.getCheckOutDistance();
        if(betweenDistance<checkOutDistance){
            return;
        }
        AuthService authService = NetworkService.getInstance().setToken(user.getApiAuthToken()).getAuthService();
        UserCheckRequest uc = new UserCheckRequest();
        uc.setUserId(user.getUserID());
        uc.setCustomerId(String.valueOf(checkin.getCustomerId()));
        uc.setGeoLocation(Util.fromDblocation(lastGeo.getLocation()));
        Call<String> checkout = authService.checkout(uc);
        checkout.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        DbUtil.clearCheckin(db);
                    } else if (response.errorBody() != null) {
                        String errstring = response.errorBody().string();
                        Log.e(TAG, errstring);
                    }
                }catch (IOException e){
                    FirebaseCrashlytics.getInstance().recordException(e);
                    Log.e(TAG, "error while checkout");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "error while checkout");
            }
        });


    }

    private void uploadGeoRecords(RecordDao recordDao,Integer userid) {
        NetworkService networkService = NetworkService.getInstance();
        AuthService authService = networkService.getAuthService();
        List<Record> geo = recordDao.geo();
            while (geo!=null && !geo.isEmpty()){
                Log.i(TAG, "record size: " + geo.size());
                List<GeoRequest> request = Util.convergeoRecords(geo,userid);
                Call<ResponseBody> stringCall = authService.geoLocation(request);
                try {
                    Response<ResponseBody> execute = stringCall.execute();
                    if(execute.isSuccessful()) {
                        recordDao.delete(geo.toArray(new Record[geo.size()]));
                    }else {
                        Log.i(TAG, "error while uploading: "+execute.errorBody().string());
                        break;
                    }
                } catch (IOException e) {
                    Log.i(TAG, "error while uploading records");
                    FirebaseCrashlytics.getInstance().recordException(e);
                    break;
                }
                geo = recordDao.geo();
                Log.i(TAG, "record after size: " + geo.size());
            }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
