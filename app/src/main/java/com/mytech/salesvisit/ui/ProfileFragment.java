package com.mytech.salesvisit.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.UserProfile;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {


    private static final String TAG = ProfileFragment.class.getName();


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);
        collectEmp();
        return inflate;
    }

    private void collectEmp() {
        AuthService authService = NetworkService.getInstance().setToken(getUser().getApiAuthToken()).getAuthService();
        Call<UserProfile> profile = authService.profile(getUser().getUserID());
        profile.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    if (response.isSuccessful()) {
                        fillEmpUi(response.body());
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
                    getCallback().moveToHome();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                showNetWorkErrDialog();
                getCallback().moveToHome();
            }
        });
    }

    private void fillEmpUi(UserProfile profile) {
       TextView textView=getView().findViewById(R.id.txtEName);
       textView.setText(profile.getName());
          textView=getView().findViewById(R.id.txtEDesignation);
        textView.setText(profile.getDesignation());
          textView=getView().findViewById(R.id.txtECompany);
        textView.setText(profile.getCompany());
        textView=getView().findViewById(R.id.txtEDepartment);
        textView.setText(profile.getDepartment());
        textView=getView().findViewById(R.id.txtEDevision);
        textView.setText(profile.getDivision());
        textView=getView().findViewById(R.id.txtEJdate);
        textView.setText(profile.getJoiningDate());
        textView=getView().findViewById(R.id.txtELocation);
        textView.setText(profile.getLocation());
    }

}
