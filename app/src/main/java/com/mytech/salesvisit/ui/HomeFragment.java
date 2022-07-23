package com.mytech.salesvisit.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.net.AuthService;
import com.mytech.salesvisit.net.ErrorMessage;
import com.mytech.salesvisit.net.NetworkService;
import com.mytech.salesvisit.net.Visit;
import com.mytech.salesvisit.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = HomeFragment.class.getName();
    private RecyclerView mRecyclerView;
    private DashBoardAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = inflate.findViewById(R.id.rvDashboard);
        ArrayList<Visit> objects = new ArrayList<>();
        adapter = new DashBoardAdapter(objects);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        collectVisits(getUser().getUserID());
        return inflate;
    }


    @Override
    protected void showVisits(List<Visit> visits) {
        boolean inProgress = false;
        if (visits.isEmpty()) {
            getCallback().visitInProgress(inProgress);
            setupHeaderView();
            return;
        }


        for (Visit visit : visits) {
            if (TextUtils.isEmpty(visit.getCheckOut())) {
                inProgress = true;
                break;
            }
        }
        getCallback().visitInProgress(inProgress);
        setupDashboard(visits);
    }

    private void setupDashboard(List<Visit> visits) {
        getView().findViewById(R.id.header_layout).setVisibility(View.GONE);
        Collections.sort(visits, new Comparator<Visit>() {
            @Override
            public int compare(Visit f, Visit s) {
                long fv = TextUtils.isEmpty(f.getCheckOut()) ? 0 : Util.getTime(f.getCheckOut());
                long sv = TextUtils.isEmpty(s.getCheckOut()) ? 0 : Util.getTime(s.getCheckOut());
                return Long.compare(fv, sv);
            }
        });
        HeaderDecoration headerDecoration = new HeaderDecoration(getActivity(), mRecyclerView, R.layout.list_header_home, visits.size());
        mRecyclerView.addItemDecoration(headerDecoration);
        adapter.addItems(visits);
        adapter.notifyDataSetChanged();
        adapter.setOnClickListener(this);
    }

    private void setupHeaderView() {
        getView().findViewById(R.id.header_layout).setVisibility(View.VISIBLE);
        TextView viewById = getView().findViewById(R.id.txtNoVisit);
        viewById.setText("0");
    }

    @Override
    protected void collectVisits(int userID) {
        AuthService authService = NetworkService.getInstance().setToken(getUser().getApiAuthToken()).getAuthService();
        Call<List<Visit>> authenticate = authService.visits(userID);
        authenticate.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                try {
                    // Toast.makeText(getContext(), ""+response.body(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        List<Visit> body = response.body();
                        showVisits(body);

                    } else if (response.code() >= 400 && response.code() <= 403) {
                        getCallback().moveToLogin();
                    } else if (response.errorBody() != null) {
                        // Toast.makeText(getContext(), ""+response.errorBody().string(), Toast.LENGTH_SHORT).show();
                       /* ErrorMessage errorMessage;
                        errorMessage = Util.getNetworkMapper().readValue(response.errorBody().string(),ErrorMessage.class);
                        showDialog(errorMessage.getMessage());*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                Log.d(TAG, "error while getting visit");
                showNetWorkErrDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        getCallback().moveToCheckout();
    }
}
