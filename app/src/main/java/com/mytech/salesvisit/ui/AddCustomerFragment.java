package com.mytech.salesvisit.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.room.util.StringUtil;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.net.AddCustomerRequest;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.CityId;
import com.mytech.salesvisit.net.Company;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.StateId;
import com.mytech.salesvisit.net.UserCheckRequest;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddCustomerFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = AddCustomerFragment.class.getName();
    AppCompatSpinner citySpinner,stateSpinner;
    MaterialButton button;
    TextInputEditText textInputEditText;

    public AddCustomerFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_add_customer, container, false);
        stateSpinner=inflate.findViewById(R.id.spinnerState);
        citySpinner=inflate.findViewById(R.id.spinnerCity);
        textInputEditText=inflate.findViewById(R.id.txtNewCustomer);
        button=inflate.findViewById(R.id.btn_addCustomer);
        button.setOnClickListener(this);
        collectState(1);

        return inflate;
    }


    @Override
    public void onClick(View v) {
        if(validate()){
            return;
        }
        StateId stateId = (StateId) stateSpinner.getSelectedItem();
        CityId cityId = (CityId) citySpinner.getSelectedItem();
        String customerName = textInputEditText.getText().toString();
        addCustomer(stateId.getId(),cityId.getId(),getUser().getUserID(),customerName);

    }

    private boolean validate() {
        if(TextUtils.isEmpty(textInputEditText.getText().toString())){
            textInputEditText.setError("Please provide customer name");
            return true;
        }
        Object cityselectedItem = citySpinner.getSelectedItem();
        Object stateselectedItem = stateSpinner.getSelectedItem();
        if(!(stateselectedItem instanceof StateId && cityselectedItem instanceof CityId)){
            showDialog("Please select state and city");
            return true;
        }
        return false;
    }

    private void collectCity(int statecode) {
        AuthService authService = NetworkService.getInstance().getAuthService();
        Call<List<CityId>> company = authService.city(statecode);
        company.enqueue(new Callback<List<CityId>>() {
            @Override
            public void onResponse(Call<List<CityId>> call, Response<List<CityId>> response) {
                try {
                    if (response.isSuccessful()) {
                        fillSpinner(response.body(),citySpinner);
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
            public void onFailure(Call<List<CityId>> call, Throwable t) {
                showNetWorkErrDialog();
            }
        });
    }


    private void collectState(int contrycode) {
        AuthService authService = NetworkService.getInstance().getAuthService();
        Call<List<StateId>> company = authService.states(contrycode);
        company.enqueue(new Callback<List<StateId>>() {
            @Override
            public void onResponse(Call<List<StateId>> call, Response<List<StateId>> response) {
                try {
                    if (response.isSuccessful()) {
                        fillSpinner(response.body(),stateSpinner);
                        stateSpinner.setOnItemSelectedListener(AddCustomerFragment.this);
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
            public void onFailure(Call<List<StateId>> call, Throwable t) {
                showNetWorkErrDialog();
            }
        });
    }



    private void addCustomer(int stateId,int cityId,int userId,String customerName) {
        AuthService authService = NetworkService.getInstance().getAuthService();
        AddCustomerRequest ac = new AddCustomerRequest();
        ac.setUserId(userId);
        ac.setStateId(stateId);
        ac.setCityId(cityId);
        ac.setCustomerName(customerName);
        Call<String> checkin = authService.addCustomer(ac);
        checkin.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        showSuccessDialog();
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
            public void onFailure(Call<String> call, Throwable t) {
                showNetWorkErrDialog();
            }
        });
    }

    void showSuccessDialog(){
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getActivity().getResources().getString(R.string.dialog_header))
                .setMessage(getActivity().getResources().getString(R.string.add_customer_success))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCallback().moveToHome();
                    }
                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     fillcity();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        fillcity();
    }

    private void fillcity() {
        Object item = stateSpinner.getSelectedItem();
        StateId state= (StateId) item;
        collectCity(state.getId());
    }


}
