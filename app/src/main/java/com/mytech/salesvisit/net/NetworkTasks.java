package com.mytech.salesvisit.net;

import android.os.SystemClock;
import android.util.Log;

import com.mytech.salesvisit.InternetService;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.RecordDao;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.util.AppConfig;
import com.mytech.salesvisit.util.DbUtil;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkTasks {
    private AppDatabase db;
    private static final String TAG = NetworkTasks.class.getSimpleName();

    public NetworkTasks(AppDatabase db) {
        this.db = db;
    }

    public void uploadRecords(){
        Log.i(TAG, "UploadRecords");
        User user = db.userDao().getuser();
        if(AppConfig.TYPE.equalsIgnoreCase("debug")) {
            Log.d(TAG, "User  = " + (user!=null?user.getUserName():null));
        }
        if(user==null)return;
        RecordDao recordDao = db.recordDao();
        perfromAutoCheckout(db);
        uploadGeoRecords(recordDao,user.getUserID());
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
        NetworkService networkService = NetworkService.getInstance().setToken(db.userDao().getUser().getApiAuthToken());
        AuthService authService = networkService.getAuthService();
        List<Record> geo = recordDao.geo();
        if(AppConfig.TYPE.equalsIgnoreCase("debug")) {
            Log.d(TAG, "Record size =" + (geo!=null?geo.size():0));
        }
        while (geo!=null && !geo.isEmpty()){
            Log.i(TAG, "record size: " + geo.size());
            List<GeoRequest> request = Util.convergeoRecords(geo,userid);
            Call<ResponseBody> stringCall = authService.geoLocation(request);
            try {
                Response<ResponseBody> execute = stringCall.execute();
                if(execute.isSuccessful()) {
                    recordDao.delete(geo.toArray(new Record[geo.size()]));
                }else {
                    Log.e(TAG, "error while uploading: "+execute.errorBody().string());
                    break;
                }
            } catch (IOException e) {
                Log.e(TAG, "error while uploading records");
                break;
            }
            geo = recordDao.geo();
            Log.d(TAG, "record after size: " + geo.size());
        }
    }



}
