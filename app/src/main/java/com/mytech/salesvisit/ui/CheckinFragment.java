package com.mytech.salesvisit.ui;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.JsonObject;
import com.mytech.salesvisit.ForegroundLocationService;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.adapter.CustomerAdapter;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.model.CheckingLocationModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.ValidateCheckin;
import com.mytech.salesvisit.model.VisitModel;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.Company;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.GeoLocation;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.UserCheckRequest;
import com.mytech.salesvisit.ui.checkin.CheckinAPI;
import com.mytech.salesvisit.ui.checkin.ResultOutput;
import com.mytech.salesvisit.util.AppConfig;
import com.mytech.salesvisit.util.RetrofitClient;
import com.mytech.salesvisit.util.Util;
import com.mytech.salesvisit.view.ordercreate.OrderCreateAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckinFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, CustomerAdapter.EventListener, ResultOutput {
    private static final String TAG = CheckinFragment.class.getName();
    AppCompatSpinner spinnerCompany;
    MaterialButton btnCheckin;
    TextInputLayout layoutCustomer;
    AppCompatAutoCompleteTextView textCustomer;
    Company selectedCompany;
    TextView textViewcustomerLbl,txt_customername;
    CheckinAPI api;
    Dialog customerSearching;
    List<CustomerModel> lst_customer;
    CustomerAdapter customerAdapter;
    RecyclerView rc_customer;
    LinearLayoutManager mManager;
    JsonObject customer_JsonObject;
    int usercode;
    AppDatabase db;
    String str_customerid;
    GeoLocation geoLocation;
    private ForegroundLocationService mService;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequestHighAccuracy;
    ProgressDialog progressDialog;
    public String getStr_accurecy() {
        return str_accurecy;
    }

    public void setStr_accurecy(String str_accurecy) {
        this.str_accurecy = str_accurecy;
    }

    String str_accurecy="";

    public String getStr_latLong() {
        return str_latLong;
    }

    public void setStr_latLong(String str_latLong) {
        this.str_latLong = str_latLong;
    }

    String str_latLong="";
    public CheckinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_checkin, container, false);
        spinnerCompany = inflate.findViewById(R.id.spinnerCompany);
        btnCheckin=inflate.findViewById(R.id.btn_checkin);
        textCustomer =inflate.findViewById(R.id.txtCustomer);
        txt_customername =inflate.findViewById(R.id.txt_customername);
        textCustomer.setOnItemClickListener(this);
        textViewcustomerLbl=inflate.findViewById(R.id.textViewCustomerlbl);
        btnCheckin.setOnClickListener(this);
        db = AppDatabase.getInstance(getContext());
        user = db.userDao().getUser();
        usercode=user.getUserID();
        collectCompany();
        api = new CheckinAPI(getContext(), this);
        selectedCompany=new Company();

        txt_customername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    customerSearching=new Dialog(getContext());
                    customerSearching.setContentView(R.layout.customer_search_list);
                    rc_customer=customerSearching.findViewById(R.id.rc_customerlist);
                    EditText search_customer=customerSearching.findViewById(R.id.search_customer);
                    mManager = new LinearLayoutManager(getContext());
                    rc_customer.setLayoutManager(mManager);
                    try {
                        customer_JsonObject = new JsonObject();
                        customer_JsonObject.addProperty("UserId", usercode);
                        customer_JsonObject.addProperty("CompanyId", "");
                        customer_JsonObject.addProperty("CustomerId", "");
                        customer_JsonObject.addProperty("CustomerCategoryId", 0);
                        customer_JsonObject.addProperty("QueryValue", "");
                        customer_JsonObject.addProperty("RowCount", 100);
                        customer_JsonObject.addProperty("IsActive", true);
                        api.getCustomer(customer_JsonObject);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    search_customer.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                customer_JsonObject = new JsonObject();
                                customer_JsonObject.addProperty("UserId", usercode);
                                customer_JsonObject.addProperty("CompanyId", "");
                                customer_JsonObject.addProperty("CustomerId", "");
                                customer_JsonObject.addProperty("CustomerCategoryId", 0);
                                customer_JsonObject.addProperty("QueryValue", s.toString());
                                customer_JsonObject.addProperty("RowCount", 100);
                                customer_JsonObject.addProperty("IsActive", true);
                                api.getCustomer(customer_JsonObject);
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    customerSearching.show();
                }catch(Exception e)
                {
                    Toast.makeText(getContext(), "ERROR IS "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



        return inflate;
    }


    private void collectCompany() {
        AuthService authService = NetworkService.getInstance().setToken(getUser().getApiAuthToken()).getAuthService();
        Call<List<Company>> company = authService.company(user.getUserID());
        company.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
               try {
                    if (response.isSuccessful()) {
                     //  fillSpinner(response.body(),spinnerCompany);
                       fillTextview(response.body());
                   } else if (response.errorBody() != null) {
                       ErrorMessage errorMessage;
                       String errstring = response.errorBody().string();
                       Log.d(TAG, errstring);
                       errorMessage = Util.getNetworkMapper().readValue(errstring, ErrorMessage.class);
                       showDialog(errorMessage.getMessage());
                   }
               }catch (IOException e){
                   Log.d(TAG, e.getMessage());
                   showNetWorkErrDialog();
               }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                    showNetWorkErrDialog();
            }
        });
    }

    private void fillTextview(List<Company> list) {
        ArrayAdapter<Company> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        textCustomer.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
       // Company selectedItem = (Company) spinnerCompany.getSelectedItem();
       if(selectedCompany!=null) {
          // Toast.makeText(getContext(),"Clicked",Toast.LENGTH_SHORT).show();
         requestUpdate();
           Log.i("Customer id",""+selectedCompany.getId());
           JsonObject jsonObject = new JsonObject();

           JsonObject jsonFinal = new JsonObject();

           try {
               jsonObject.addProperty("CustomerId",""+selectedCompany.getId());
               jsonFinal.addProperty("Accuracy",""+getStr_accurecy());
               jsonFinal.addProperty("LatLong", ""+getStr_latLong());
               jsonObject.add("GeoLocation",jsonFinal);

               /*jsonObject.addProperty("CustomerId","2");
               jsonFinal.addProperty("Accuracy",""+getStr_accurecy());
               jsonFinal.addProperty("LatLong", ""+getStr_latLong());
               jsonObject.add("GeoLocation",jsonFinal);*/


               api.getUserLocationStatus(jsonObject);

           } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
         //  submitCheckInCompany(selectedCompany.getId(), user.getUserID());
       }else {
           showDialog("Please select Customer!");
       }
    }


    private void submitCheckInCompany(String companyId,int userID) {
        AuthService authService = NetworkService.getInstance().setToken(getUser().getApiAuthToken()).getAuthService();
        UserCheckRequest uc = new UserCheckRequest();
        uc.setUserId(userID);
        uc.setCustomerId(companyId);
        uc.setGeoLocation(getCallback().getLocation());
        Call<String> checkin = authService.checkin(uc);
        checkin.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    if (response.isSuccessful()) {
                        showSuccessDialog();
                        getCallback().visitInProgress(true);
                        saveCheckin(companyId);
                    } else if (response.errorBody() != null) {
                        ErrorMessage errorMessage;
                        String errstring = response.errorBody().string();
                        Log.d(TAG, errstring);
                        errorMessage = Util.getNetworkMapper().readValue(errstring, ErrorMessage.class);
                        showDialog(errorMessage.getMessage());
                    }
                }catch (IOException e){
                    FirebaseCrashlytics.getInstance().recordException(e);
                    Log.d(TAG, e.getMessage());
                    showNetWorkErrDialog();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                    showNetWorkErrDialog();
            }
        });
    }

    private void saveCheckin(String companyId) {
        try {
            int i = Integer.parseInt(companyId);
            getCallback().checkIn(i);
        }catch (Exception e){

        }
    }

    void showSuccessDialog(){
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getActivity().getResources().getString(R.string.dialog_header))
                .setMessage(getActivity().getResources().getString(R.string.check_in_success))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCallback().moveToHome();
                    }
                })
                .show();
    }


    @Override
    public void fabClicked(FloatingActionButton button) {
        if(layoutCustomer.getVisibility()==View.VISIBLE) {
            layoutCustomer.setVisibility(View.GONE);
            spinnerCompany.setVisibility(View.VISIBLE);
            textViewcustomerLbl.setText(getResources().getString(R.string.lblselectcustomer));
            button.setImageResource(R.drawable.ic_plus);
        }else{
            layoutCustomer.setVisibility(View.VISIBLE);
            spinnerCompany.setVisibility(View.GONE);
            textViewcustomerLbl.setText(getResources().getString(R.string.lbladdcustomer));
            button.setImageResource(R.drawable.ic_clear);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position!= ListView.INVALID_POSITION){
            selectedCompany = (Company) textCustomer.getAdapter().getItem(position);
        }
    }

    @Override
    public void onCustomerSelected(CustomerModel customerModel) {
        Toast.makeText(getContext(), " Id = "+customerModel.getId()+"\nName :"+customerModel.getText(), Toast.LENGTH_SHORT).show();
        customerSearching.dismiss();
        str_customerid = customerModel.getId();
        txt_customername.setText(""+customerModel.getText());
        selectedCompany.setId(customerModel.getId());
        selectedCompany.setText(customerModel.getText());

    }

    @Override
    public void onListResponce_Customer(List result) {
        if (result != null) {

            try{
                if(result!=null) {

                    lst_customer=result;
                    customerAdapter = new CustomerAdapter((ArrayList) lst_customer, getContext(),this);
                    rc_customer.setAdapter(customerAdapter);

                }else
                {
                    Toast.makeText(getContext(), "No more records.", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e)
            {
                Toast.makeText(getContext(), "Erro in Respo"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onVersionResponse(String result) {

    }

    @Override
    public void onUserLocationStatus(String result) {
try{
    try{
        // Log.i("Result ",result);
        // Toast.makeText(getContext(), ""+result, Toast.LENGTH_SHORT).show();
        if(result.trim().equals("true")) {
            submitCheckInCompany(selectedCompany.getId(), user.getUserID());
        }
        else {
            Toast.makeText(getContext(),"Checking not allowed, You are not at client location",Toast.LENGTH_SHORT).show();
        }
    }catch(Exception e)
    {
        Toast.makeText(getContext(),"Something went wrong "+e.getMessage(),Toast.LENGTH_SHORT).show();
    }
}catch (Exception e)
{

}
    }


    /* public void processLocation() {
         Log.i("Process","Yes");
         if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

             ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
         }
         getLocation();

     }
     void getLocation() {
         LocationManager locationManager;
         try {
             Log.i("Location","YES");
             if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                 ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

             }
             locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
             locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
             Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

             double currentLatitude = myLocation.getLatitude();
             double currentLongitude = myLocation.getLongitude();
             Log.i("Latitude ", " " + currentLatitude);
             Log.i("Longitude ", " " + currentLongitude);

         } catch (SecurityException e) {
             Log.e("Error ", "" + e.getLocalizedMessage());
             e.printStackTrace();
         }
     }*/
   private void init() {

     mLocationRequestHighAccuracy = LocationRequest.create();
       mLocationRequestHighAccuracy.setPriority(LocationRequest.
              PRIORITY_HIGH_ACCURACY);
      mLocationRequestHighAccuracy.setInterval(20 * 1000);
   }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            Location location = locationResult.getLastLocation();
            if (AppConfig.TYPE.equalsIgnoreCase("debug")) {
                Log.d(TAG, "Location updated " + (location != null ? location.getLatitude() + "," + location.getLongitude() : null));
            }
            if (locationResult != null) {
                String ac = String.valueOf(location.getAccuracy());
                String lat = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
                // Log.i("AC = "+ac,"LatLong = "+lat);
                setStr_accurecy(ac);
                setStr_latLong(lat);
              /*  if(geoLocation!=null){
                    geoLocation.setAccuracy(ac);
                    geoLocation.setLatLong(lat);
                }else {
                    geoLocation =new GeoLocation(lat,ac);
                }*/
            }
            if(mService!=null & location!=null){
                mService.onNewLocation(location);
            }
            }
        }

        ;

        @Override
        public void onDestroy() {
            if (mFusedLocationClient != null) {
                mFusedLocationClient.removeLocationUpdates(locationCallback);
            }
            super.onDestroy();
        }

        private void requestUpdate() {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getRuntimePermissions();
            } else {
                Log.e(TAG, "GPS requesting");
                mFusedLocationClient = LocationServices.
                        getFusedLocationProviderClient(getContext());
                init();
                mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, locationCallback, Looper.myLooper());
            }

        }

        private void getRuntimePermissions() {
            List<String> allNeededPermissions = new ArrayList<>();
            for (String permission : getRequiredPermissions()) {
                if (!isPermissionGranted(getContext(), permission)) {
                    allNeededPermissions.add(permission);
                }
            }

            if (!allNeededPermissions.isEmpty()) {
                ActivityCompat.requestPermissions(
                        getActivity(), allNeededPermissions.toArray(new String[0]), 1);
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

        private static boolean isPermissionGranted(Context context, String permission) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission granted: " + permission);
                return true;
            }
            Log.i(TAG, "Permission NOT granted: " + permission);
            return false;
        }

}
