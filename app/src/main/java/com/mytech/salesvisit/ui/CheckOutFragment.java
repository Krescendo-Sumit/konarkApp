package com.mytech.salesvisit.ui;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.UserCheckRequest;
import com.mytech.salesvisit.net.Visit;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOutFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = CheckOutFragment.class.getName();
    MaterialButton btnCheckin;
    TextView textViewCustomername,getTextViewCustomerTime;
    String customerId="";


    public CheckOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_check_out, container, false);
        btnCheckin=inflate.findViewById(R.id.btnCheckout);
        textViewCustomername=inflate.findViewById(R.id.textViewlCustomerName);
        getTextViewCustomerTime=inflate.findViewById(R.id.textViewlCustomerTime);
        btnCheckin.setOnClickListener(this);
        collectVisits(getUser().getUserID());
        return inflate;
    }

    @Override
    public void onClick(View v) {
        submitCheckout(user.getUserID());
    }
    public void setUser(User user) {
        this.user = user;
    }

    private void submitCheckout(int userID) {
        AuthService authService = NetworkService.getInstance().setToken(getUser().getApiAuthToken()).getAuthService();
        UserCheckRequest uc = new UserCheckRequest();
        uc.setUserId(userID);
        uc.setCustomerId(customerId);
        uc.setGeoLocation(getCallback().getLocation());
        Call<String> checkout = authService.checkout(uc);
        checkout.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        showSuccessDialog();
                        getCallback().visitInProgress(false);
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

    void showSuccessDialog(){
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getActivity().getResources().getString(R.string.dialog_header))
                .setMessage(getActivity().getResources().getString(R.string.check_out_success))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCallback().moveToHome();
                    }
                })
                .show();
    }

    @Override
    protected void showVisits(List<Visit> visits) {
            for(Visit visit:visits){
                if(TextUtils.isEmpty(visit.getCheckOut())){
                    customerId= String.valueOf(visit.getCustomerId());
                    textViewCustomername.setText(visit.getCustomerName());
                    getTextViewCustomerTime.setText("Check-in Time :"+Util.getTimeString(visit.getCheckIn()));
                }
            }

    }
}
