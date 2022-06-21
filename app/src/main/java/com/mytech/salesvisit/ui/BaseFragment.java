package com.mytech.salesvisit.ui;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.Visit;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseFragment extends Fragment implements FragmentCallback {
    private  static final String TAG = BaseFragment.class.getName();
    protected User user;
    @Override
    public void fabClicked(FloatingActionButton button) {

    }

    void showNetWorkErrDialog(){
        if(getContext()==null)return;
        showDialog(getContext().getResources().getString(R.string.dialog_header),getContext().getResources().getString(R.string.network_err_message));
    }


    void showDialog(final String message){
        showDialog(getContext().getResources().getString(R.string.dialog_header),message);
    }

    void showDialog(final String title, final String message){
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public User getUser() {
        if(user!=null){
            return this.user ;
        }
        user=getCallback().getCurrentUser();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    ActivityCallback getCallback(){
        if(getActivity() instanceof  ActivityCallback){
            return (ActivityCallback) getActivity();
        }
        return ActivityCallback.nullActivityCallback;
    }

    protected void collectVisits(int userID) {

        AuthService authService = NetworkService.getInstance().setToken(getUser().getApiAuthToken()).getAuthService();
        Call<List<Visit>> authenticate = authService.visits(userID);
        authenticate.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                try {
                    if(response.isSuccessful()){
                        List<Visit> body = response.body();
                        showVisits(body);

                    }else if(response.errorBody()!=null){
                        ErrorMessage errorMessage;
                        errorMessage = Util.getNetworkMapper().readValue(response.errorBody().string(),ErrorMessage.class);
                        showDialog(errorMessage.getMessage());
                    }
                } catch (IOException e) {
                    Log.d(TAG,"error while getting visit");

                }
            }
            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                Log.d(TAG,"error while getting visit");
                showNetWorkErrDialog();
            }
        });

    }

    protected void showVisits(List<Visit> body) {
    }

    protected  <T> void  fillSpinner(List<T> list, AppCompatSpinner spinner) {
        ArrayAdapter<T> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}
