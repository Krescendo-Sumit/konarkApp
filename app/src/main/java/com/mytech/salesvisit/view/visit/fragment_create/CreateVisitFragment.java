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
import android.widget.ArrayAdapter;
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
import com.mytech.salesvisit.net.Visit;
import com.mytech.salesvisit.util.SqlightDatabaseUtil;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
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
    SearchableSpinner sp_companionperson,
            sp_visittype,sp_location_type,sp_visitingplace,sp_person_visited,sp_attendee,sp_remark,sp_remarkdetails,sp_remark_category,sp_sp_mom;

    ArrayAdapter adapter_companionperson,adapter_visittype,adapter_locationtype,adapter_visiting_place,adapter_personvisited,adapter_attendee;
    ArrayList<VisitModel> visitTypeList;
    ArrayList<VisitModel> locationTypeList;
    ArrayList<VisitModel> remarkList;
    ArrayAdapter adapter_remarkList,adapter_remark_category,adapter_remarkdetails,adapter_sp_mom;

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
            visitTypeList=new ArrayList<>();
            locationTypeList=new ArrayList<>();
            remarkList=new ArrayList<>();

            VisitModel v1=new VisitModel();
            v1.setText("Physical Visit");
            v1.setId(1);

            VisitModel v2=new VisitModel();
            v2.setText("Telephonic Call");
            v2.setId(2);

            visitTypeList.add(v1);
            visitTypeList.add(v2);


            VisitModel v11=new VisitModel();
            v11.setText("Local");
            v11.setId(1);

            VisitModel v22=new VisitModel();
            v22.setText("Outstation");
            v22.setId(2);
            locationTypeList.add(v11);
            locationTypeList.add(v22);


            VisitModel v111=new VisitModel();
            v111.setText("Customer");
            v111.setId(1);
            v111.setValue("C");

            VisitModel v222=new VisitModel();
            v222.setText("Person");
            v222.setId(2);
            v222.setValue("P");

            remarkList.add(v111);
            remarkList.add(v222);


            sp_companionperson=baseView.findViewById(R.id.sp_companionperson);
            sp_visittype=baseView.findViewById(R.id.sp_visittype);
            sp_location_type=baseView.findViewById(R.id.sp_location_type);
            sp_visitingplace=baseView.findViewById(R.id.sp_visitingplace);
            sp_person_visited=baseView.findViewById(R.id.sp_person_visited);
            sp_attendee=baseView.findViewById(R.id.sp_attendee);
            sp_remark=baseView.findViewById(R.id.sp_remark);
            sp_remark_category=baseView.findViewById(R.id.sp_remark_category);
            sp_remarkdetails=baseView.findViewById(R.id.sp_remark);
            sp_sp_mom=baseView.findViewById(R.id.sp_mom);

            adapter_visittype=new ArrayAdapter(context,R.layout.type_item,visitTypeList);
            sp_visittype.setAdapter(adapter_visittype);


            adapter_locationtype=new ArrayAdapter(context,R.layout.type_item,locationTypeList);
            sp_location_type.setAdapter(adapter_locationtype);

            adapter_remarkList=new ArrayAdapter(context,R.layout.type_item,remarkList);
            sp_remark.setAdapter(adapter_remarkList);

         /*   db = AppDatabase.getInstance(context);

            user = db.userDao().getUser();
            usercode = user.getUserID();*/
            usercode = 2;
//========================================================================================
            createFragmentAPI=new CreateFragmentAPI(context,this);
            createFragmentAPI.getCompanionPeople();
//===========================================================================================
            customer_JsonObject = new JsonObject();
            customer_JsonObject.addProperty("UserId", usercode);
            customer_JsonObject.addProperty("CompanyId", "");
            customer_JsonObject.addProperty("CustomerId", "");
            customer_JsonObject.addProperty("CustomerCategoryId", 0);
            customer_JsonObject.addProperty("QueryValue", "");
            customer_JsonObject.addProperty("RowCount", 100);
            customer_JsonObject.addProperty("IsActive", true);
            createFragmentAPI.getCustomer(customer_JsonObject);
//==================================================================================================
            createFragmentAPI.getPersonVisited(2);
//==================================================================================================
           createFragmentAPI.getVisitReason();
//==================================================================================================
            createFragmentAPI.getVisitAttendee(3);
//==================================================================================================
            createFragmentAPI.getRemarkAbout("P");
//==================================================================================================
            createFragmentAPI.getRemarkDetails(23);
//==================================================================================================
            createFragmentAPI.getMOMPerticular();
//==================================================================================================
//==================================================================================================

            createFragmentAPI.getCompanionPeople();


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
            adapter_companionperson=new ArrayAdapter(context, R.layout.type_item,result);
            sp_companionperson.setAdapter(adapter_companionperson);

            Toast.makeText(context, "" + result.size(), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public void onListResponce_Customer(List<CustomerModel> result) {
        try {
            adapter_visiting_place=new ArrayAdapter(context, R.layout.type_item,result);
            sp_visitingplace.setAdapter(adapter_visiting_place);
            Toast.makeText(context, "Customer : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPersonVisited(List<ContactModel> result) {
        try {
            adapter_personvisited=new ArrayAdapter(context, R.layout.type_item,result);
            sp_person_visited.setAdapter(adapter_personvisited);
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
            adapter_attendee=new ArrayAdapter(context, R.layout.type_item,result);
            sp_attendee.setAdapter(adapter_attendee);
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
            adapter_remark_category=new ArrayAdapter(context, R.layout.type_item,result);
            sp_remark_category.setAdapter(adapter_remark_category);
            Toast.makeText(context, "Remark About  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemarkDetails(List<RemarkDetailsModel> result) {
        try {
            adapter_remarkdetails=new ArrayAdapter(context, R.layout.type_item,result);
            sp_remarkdetails.setAdapter(adapter_remarkdetails);
            Toast.makeText(context, "Remark Details  : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMOMPerticularResult(List<MOMPerticularModel> result) {
        try {
            adapter_sp_mom=new ArrayAdapter(context, R.layout.type_item,result);
            sp_sp_mom.setAdapter(adapter_sp_mom);

            Toast.makeText(context, "MOMPerticular : "+result.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}