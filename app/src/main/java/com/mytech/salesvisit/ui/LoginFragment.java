package com.mytech.salesvisit.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mytech.salesvisit.MainActivity;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.UserRequest;
import com.mytech.salesvisit.net.UserResponse;
import com.mytech.salesvisit.util.AppConfig;
import com.mytech.salesvisit.util.JobUtil;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getName();
    protected TextInputEditText textUserName,textPassWord;
    protected MaterialButton btnLogin,btnTnc;
    protected MaterialCheckBox chkIagree;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        textUserName=inflate.findViewById(R.id.txtUserName);
        textPassWord=inflate.findViewById(R.id.txtPassWard);
        btnLogin=inflate.findViewById(R.id.btnLogin);
        btnTnc=inflate.findViewById(R.id.btntnc);
        chkIagree=inflate.findViewById(R.id.chkagree);
        btnTnc.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnLogin:
                    auth();
                    break;
                case R.id.btntnc:
                    showTncDialog();
                    break;
                    default:
                        
                        
                        
            }
        

    }

    private void showTncDialog() {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Term and Conditions")
                .setMessage(getResources().getString(R.string.tnc))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }

    void auth(){
        if(textUserName.getText().toString().isEmpty()){
            textUserName.setError("Enter Username");
            return;
        }
        if(textPassWord.getText().toString().isEmpty()){
            textPassWord.setError("Enter Password");
            return;
        }
        if(!chkIagree.isChecked()){
            showDialog("Please Accept Term and Conditions");
            return;
        }

        AuthService authService = NetworkService.getInstance().getAuthService();
        UserRequest request = new UserRequest();
        String username=textUserName.getText().toString();
        String pass =textPassWord.getText().toString();
        request.setUserName(username);
        request.setPassword(pass);
        User currentUser = getCallback().getCurrentUser();
        final  String deviceUid = currentUser!=null?currentUser.getDeviceId():AppConfig.getDeviceUid();
        request.setDeviceId(deviceUid);
        request.setDeviceName(AppConfig.getDeviceName());
        request.setGeoLocation(getCallback().getLocation());
        Call<UserResponse> authenticate = authService.login(request);
        authenticate.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                if(response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    User userdb = Util.convertUser(userResponse);
                    userdb.setUserName(username);
                    userdb.setDeviceId(deviceUid);
                    perform(userdb);
                }else if(response.errorBody()!=null){
                    ErrorMessage errorMessage;
                    errorMessage = Util.getNetworkMapper().readValue(response.errorBody().string(),ErrorMessage.class);
                    showDialog(errorMessage.getMessage());
                }
                } catch (IOException e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(TAG,t.getMessage());
                showNetWorkErrDialog();
            }
        });



    }

    private void perform(User user) {
        ActivityCallback callback = (ActivityCallback) getActivity();
        callback.storeUser(user);
        JobUtil.scheduleJob(getActivity().getApplicationContext());
        callback.updateHeader();
        ((MainActivity)getActivity()).getSupportActionBar().show();
        callback.moveToHome();

    }

    void setFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();
    }


}
