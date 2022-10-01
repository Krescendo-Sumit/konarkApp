package com.mytech.salesvisit.view.visit.fragment_create;

import com.mytech.salesvisit.model.ContactModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.MOMPerticularModel;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.RemarkAboutModel;
import com.mytech.salesvisit.model.RemarkDetailsModel;
import com.mytech.salesvisit.model.VisitModel;

import java.util.List;

public interface CreateFragmentListener {
    public void onResult(String result);
    public void onCompanionPeopleResult(List<EmployeeModel> result);
    public void onListResponce_Customer(List<CustomerModel> result);

    void onGetPersonVisited(List<ContactModel> videos);

    void onVisitReason(List<VisitModel> videos);

    void onGetVisitAttendee(List<ContactModel> videos);

    void onPersonResponsibleResult(List<EmployeeModel> result);

    void onRemarkAbout(List<RemarkAboutModel> videos);

    void onRemarkDetails(List<RemarkDetailsModel> videos);

    void onMOMPerticularResult(List<MOMPerticularModel> videos);
}
