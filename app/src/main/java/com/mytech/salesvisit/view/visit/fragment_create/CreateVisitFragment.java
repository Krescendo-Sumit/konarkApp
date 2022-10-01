package com.mytech.salesvisit.view.visit.fragment_create;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.model.ContactModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.MOMPerticularModel;
import com.mytech.salesvisit.model.RemarkAboutModel;
import com.mytech.salesvisit.model.RemarkDetailsModel;
import com.mytech.salesvisit.model.VisitModel;
import com.mytech.salesvisit.util.SqlightDatabaseUtil;

import java.util.List;

public class CreateVisitFragment extends Fragment implements CreateFragmentListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    View baseView;
    Context context;
    CreateFragmentAPI createFragmentAPI;
    JsonObject customer_JsonObject;
    int usercode;
 /*   AppDatabase db;
    User user;*/
    public CreateVisitFragment() {

    }
    public static CreateVisitFragment newInstance(String param1, String param2) {
        CreateVisitFragment fragment = new CreateVisitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseView= inflater.inflate(R.layout.fragment_create_visit, container, false);
        context = getContext();
        init();

        return  baseView;
    }

    private void init() {
        try{

         /*   db = AppDatabase.getInstance(context);

            user = db.userDao().getUser();
            usercode = user.getUserID();*/
            usercode = 2;
//========================================================================================
            createFragmentAPI=new CreateFragmentAPI(context,this);
          //  createFragmentAPI.getCompanionPeople();
//===========================================================================================
            customer_JsonObject = new JsonObject();
            customer_JsonObject.addProperty("UserId", usercode);
            customer_JsonObject.addProperty("CompanyId", "");
            customer_JsonObject.addProperty("CustomerId", "");
            customer_JsonObject.addProperty("CustomerCategoryId", 2);
            customer_JsonObject.addProperty("QueryValue", "");
            customer_JsonObject.addProperty("RowCount", 100);
            customer_JsonObject.addProperty("IsActive", true);
         //   createFragmentAPI.getCustomer(customer_JsonObject);
//==================================================================================================


        //    createFragmentAPI.getPersonVisited(2);
//==================================================================================================
        //    createFragmentAPI.getVisitReason();
//==================================================================================================
         //   createFragmentAPI.getVisitAttendee(3);
//==================================================================================================
            createFragmentAPI.getRemarkAbout("P");
//==================================================================================================
            createFragmentAPI.getRemarkDetails(23);
//==================================================================================================
            createFragmentAPI.getMOMPerticular();
//==================================================================================================
//==================================================================================================

         //   createFragmentAPI.getCompanionPeople();


        }catch(Exception e)
        {
            Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(String result) {

    }

    @Override
    public void onCompanionPeopleResult(List<EmployeeModel> result) {
        try {

            Toast.makeText(context, "" + result.size(), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public void onListResponce_Customer(List<CustomerModel> result) {
        try {
            Toast.makeText(context, "Customer : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPersonVisited(List<ContactModel> result) {
        try {
            Toast.makeText(context, "Visited Person : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVisitReason(List<VisitModel> result) {
        try {
            Toast.makeText(context, "Visit Reason  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetVisitAttendee(List<ContactModel> result) {
        try {
            Toast.makeText(context, "Visit Attendee  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPersonResponsibleResult(List<EmployeeModel> result) {
        try {
            Toast.makeText(context, "Responsible Person List  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemarkAbout(List<RemarkAboutModel> result) {
        try {
            Toast.makeText(context, "Remark About  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemarkDetails(List<RemarkDetailsModel> result) {
        try {
            Toast.makeText(context, "Remark Details  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMOMPerticularResult(List<MOMPerticularModel> result) {
        try {
            Toast.makeText(context, "MOMPerticular : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}