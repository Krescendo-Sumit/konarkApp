package com.mytech.salesvisit.view.ordercreate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.service.carrier.CarrierMessagingService;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
//import com.jaiselrahman.filepicker.activity.FilePickerActivity;
//import com.jaiselrahman.filepicker.config.Configurations;
//import com.jaiselrahman.filepicker.model.MediaFile;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.adapter.CustomerAdapter;
import com.mytech.salesvisit.adapter.DispatchAdapter;
import com.mytech.salesvisit.adapter.OrderDataAdapter;
import com.mytech.salesvisit.adapter.ProductAdapter;
import com.mytech.salesvisit.adapter.SalesPersonAdapter;
import com.mytech.salesvisit.adapter.ThirdPartyAdapter;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.model.AddressModel;
import com.mytech.salesvisit.model.ContactModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.OrderCategoryModel;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.OrderTypeModel;
import com.mytech.salesvisit.model.ProductItems;
import com.mytech.salesvisit.model.ProductModel;
import com.mytech.salesvisit.model.TermConditionItems;
import com.mytech.salesvisit.model.UOMMOdel;
import com.mytech.salesvisit.util.Constants;
import com.mytech.salesvisit.util.MessageBox;
import com.mytech.salesvisit.util.MultipartUtility;
import com.mytech.salesvisit.util.PathUtil;
import com.mytech.salesvisit.util.RetrofitClient;
import com.mytech.salesvisit.util.SqlightDatabaseUtil;
import com.mytech.salesvisit.view.FileDownload;
import com.mytech.salesvisit.view.orderdata.OrderDataList;

import com.toptoche.searchablespinnerlibrary.SearchableListDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickResult;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

//82  website      83 API

public class OrderCreate extends AppCompatActivity implements ResultOutput, CustomerAdapter.EventListener, ProductAdapter.EventListener, DispatchAdapter.EventListener, ThirdPartyAdapter.EventListener, SalesPersonAdapter.EventListener, IPickResult {
    OrderCreateAPI api;
    Context context;
    JSONObject jsonObject;
    SqlightDatabaseUtil sqlightDatabaseUtil;
    List<OrderTypeModel> lst_OrderType;
    List<CustomerModel> lst_customer;
    List<ContactModel> lst_GivenBy;
    List<ContactModel> lst_followupwith;
    List<ContactModel> lst_IsThirdParty;
    List<AddressModel> lst_Address;
    List<AddressModel> lst_Address_delivery;
    List<EmployeeModel> lst_Employee;
    List<OrderCategoryModel> lst_OrderCategory;
    List<ProductModel> lst_Product;
    List<UOMMOdel> lst_UOM;
    String file_path = "";
    Intent intent;
    SearchableSpinner sp_ordertype, sp_customer, sp_givenby, sp_followup_with, sp_deliveryaddress, sp_billingaddress, sp_isthird_party, sp_dispatch_from, sp_sales_person, sp_ordercategory, sp_product, sp_uom;
    JsonObject customer_JsonObject, product_JsonObject;
    TextView txt_add, txt_customername, txt_product, txt_dispatchfrom, txt_isthirdparty, txt_sales_person;
    EditText et_qty, et_specification, et_rate, et_tot_amt;
    String str_qty, str_specification, str_rate, str_tot_amt, str_product, str_uom, strproductname, str_uomname;
    TableLayout tbl_grid, tbl_grid_terms;
    Button btn_order_create;
    RecyclerView rc_customer, rc_product, rc_dispatch, rc_thirdparty, rc_salesperson;
    LinearLayoutManager mManager;
    LinearLayout ll_attachment;
    // Terms and Condition

    EditText et_discribe;
    SearchableSpinner sp_terms_perticular;
    TextView txt_add_terms;

    ArrayAdapter adapter_terms_perticular;
    String[] perticular = {"Select", "Delivery", "Payment", "Freight", "Price", "Taxes"};


    String str_ordertypeid,
            str_customerid,
            str_givenbyid,
            str_followupid,
            str_deliveryaddressid,
            str_billingaddressid,
            str_isthirdpartyid,
            str_deliverydate,
            str_transportnote,
            str_dispatchfrom,
            str_saleperson,
            str_remark,
            str_ordercategoryid;

    String str_ordertypeid_name,
            str_customerid_name,
            str_givenbyid_name,
            str_followupid_name,
            str_deliveryaddressid_name,
            str_billingaddressid_name,
            str_isthirdpartyid_name,


    str_dispatchfrom_name,
            str_saleperson_name,

    str_ordercategoryid_name;

    EditText et_deliveryDate, et_trasportnote, et_remark, et_otherproductname;
    int mYear, mMonth, mDay;
    String action = "";
    String orderid = "";
    OrderDetailsModel orderDetailsModel;
    CustomerAdapter customerAdapter;
    ProductAdapter productAdapter;
    DispatchAdapter dispatchAdapter;
    ThirdPartyAdapter thirpartyAdpater;
    SalesPersonAdapter salesPersonAdapter;
    ArrayAdapter adapter_ordertype,
            adapter_customer,
            adapter_givenby,
            adapter_followup,
            adapter_deliveryaddress,
            adapter_billingaddress,
            adapter_isthirdparty,
            adapter_dispatchfrom,
            adapter_salesperson,
            adapter_ordercategory;
    CheckBox chk_isThirdParty;
    Dialog customerSearching;
    Dialog productSearching;
    Dialog dispatchSearching;
    Dialog thirdpartySearching;
    Dialog salesPersonSearching;
    boolean isThirdPartyChecked = false;
    AppDatabase db;
    User user;
    String username;
    int usercode;
    TextView txt_choosefile;
    Dialog dialog_fileOption;
    String str_UplaodedFileName = "";
    Button btn_uplaodFile;
    TextView txt_uploadedfilename;
    ProgressDialog progressDialog;
String upath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        setTitle("Create Order");
        context = OrderCreate.this;
        api = new OrderCreateAPI(context, this);
        action = getIntent().getExtras().getString("Type").trim();

        //  Toast.makeText(context, "" + action + " Order Number :" + orderid, Toast.LENGTH_SHORT).show();
        init();
        if (action.equals("Edit")) {
            Log.i("Enterr ", "Yes" + getIntent().getExtras().getString("oid"));
            setTitle("Update Order");
            orderid = getIntent().getExtras().getString("oid") != null ? getIntent().getExtras().getString("oid") : "0";
            api.getOrderData(Integer.parseInt(orderid.trim()));
        }
        checkPermission();
        requestPermission();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {

        try {
            sqlightDatabaseUtil = new SqlightDatabaseUtil(context);
            db = AppDatabase.getInstance(getApplicationContext());
            user = db.userDao().getUser();
            usercode = user.getUserID();
            username = user.getFullName();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please File Uploading...");

            sp_ordertype = findViewById(R.id.sp_order_type);
            txt_choosefile = findViewById(R.id.txt_choosefile);
            sp_customer = findViewById(R.id.sp_customer);
            sp_givenby = findViewById(R.id.sp_given_by);
            sp_followup_with = findViewById(R.id.sp_followup_with);
            sp_deliveryaddress = findViewById(R.id.sp_deliveryaddress);
            sp_billingaddress = findViewById(R.id.sp_billingaddress);
            sp_isthird_party = findViewById(R.id.sp_isthirdparty);
            sp_dispatch_from = findViewById(R.id.sp_dispatchfrom);
            sp_sales_person = findViewById(R.id.sp_salesperson);
            sp_ordercategory = findViewById(R.id.sp_order_category);
            sp_product = findViewById(R.id.sp_product);
            sp_uom = findViewById(R.id.sp_uom);
            txt_add = findViewById(R.id.btn_add);
            txt_customername = findViewById(R.id.txt_customername);
            txt_product = findViewById(R.id.txt_product);
            txt_dispatchfrom = findViewById(R.id.txt_dispatchfrom);
            txt_sales_person = findViewById(R.id.txt_salesperson);
            txt_isthirdparty = findViewById(R.id.txt_is_thirdparty);
            et_qty = findViewById(R.id.et_qty);
            et_specification = findViewById(R.id.et_specification);
            et_rate = findViewById(R.id.et_rate);
            et_tot_amt = findViewById(R.id.et_tot_amt);
            tbl_grid = findViewById(R.id.tbl_grid);
            tbl_grid_terms = findViewById(R.id.tbl_grid_terms);
            btn_order_create = findViewById(R.id.btn_create_order);
            ll_attachment = findViewById(R.id.ll_attachment);

            et_discribe = findViewById(R.id.et_describe);
            et_otherproductname = findViewById(R.id.et_otherproductname);
            txt_add_terms = findViewById(R.id.btn_add_terms);
            sp_terms_perticular = findViewById(R.id.sp_perticular);
            adapter_terms_perticular = new ArrayAdapter(context, android.R.layout.simple_spinner_item, perticular);
            adapter_terms_perticular.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_terms_perticular.setAdapter(adapter_terms_perticular);

            btn_uplaodFile = findViewById(R.id.btn_uploadfile);
            txt_uploadedfilename = findViewById(R.id.txt_selectedFileName);


            if (user != null) {
                txt_sales_person.setText(username);
                str_saleperson = "" + usercode;
            }

            if (action.equals("New")) {
                btn_order_create.setText("Create Order");
                sqlightDatabaseUtil.clearProductList();
                sqlightDatabaseUtil.clearTermsList();
            } else if (action.equals("Edit"))
                btn_order_create.setText("Update Order");

            chk_isThirdParty = findViewById(R.id.chk_isthirdparty);

            et_deliveryDate = findViewById(R.id.et_deliverydate);
            et_trasportnote = findViewById(R.id.et_transportnote);
            et_remark = findViewById(R.id.et_remark);

            chk_isThirdParty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sp_isthird_party.setEnabled(true);

                    } else {
                        sp_isthird_party.setEnabled(false);
                        txt_isthirdparty.setText("Choose Third Party");
                        str_isthirdpartyid = "0";
                    }
                    isThirdPartyChecked = isChecked;
                }
            });


            sp_ordertype.setTitle("Select Order Type");
            sp_ordertype.setPositiveButton("Close");

            sp_customer.setTitle("Select Customer");
            sp_customer.setPositiveButton("Close");

            sp_givenby.setTitle("Select Given By");
            sp_givenby.setPositiveButton("Close");

            sp_followup_with.setTitle("Follow Up with");
            sp_followup_with.setPositiveButton("Close");

            sp_deliveryaddress.setTitle("Delivery Address");
            sp_deliveryaddress.setPositiveButton("Close");

            sp_billingaddress.setTitle("Billing Address");
            sp_billingaddress.setPositiveButton("Close");

            sp_isthird_party.setTitle("Is Third Party");
            sp_isthird_party.setPositiveButton("Close");

            sp_dispatch_from.setTitle("Dispatch From");
            sp_dispatch_from.setPositiveButton("Close");

            sp_sales_person.setTitle("Sales Person");
            sp_sales_person.setPositiveButton("Close");

            sp_ordercategory.setTitle("Order Category");
            sp_ordercategory.setPositiveButton("Close");

            sp_product.setTitle("Select Product");
            sp_product.setPositiveButton("Close");

            sp_uom.setTitle("Select UOM");
            sp_uom.setPositiveButton("Close");

            if (action.equals("New")) {
                loadSpinners(action, null);
            }
            et_deliveryDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //To show current date in the datepicker
                    Calendar mcurrentDate = Calendar.getInstance();
                    mYear = mcurrentDate.get(Calendar.YEAR);
                    mMonth = mcurrentDate.get(Calendar.MONTH);
                    mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            // TODO Auto-generated method stub
                            /*      Your code   to get date and time    */

                            String ssm = "", ssd = "";
                            if ((selectedmonth + 1) < 10)
                                ssm = "0" + (selectedmonth + 1);
                            else
                                ssm = "" + (selectedmonth + 1);
                            if ((selectedday) < 10)
                                ssd = "0" + selectedday;
                            else
                                ssd = "" + selectedday;

                            String dd = selectedyear + "-" + (ssm) + "-" + ssd;
                            et_deliveryDate.setText(dd);

                        }
                    }, mYear, mMonth, mDay);
                    //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mDatePicker.setTitle("Delivery Date");
                    mDatePicker.show();

                }
            });

            sp_ordertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    OrderTypeModel orderTypeModel = lst_OrderType.get(parent.getSelectedItemPosition());
                    //Toast.makeText(context, "Selected Order Type :" + orderTypeModel.getId() + "=" + orderTypeModel.getText(), Toast.LENGTH_SHORT).show();
                    str_ordertypeid = orderTypeModel.getId();
                    str_ordertypeid_name = orderTypeModel.getText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            txt_customername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        customerSearching = new Dialog(context);
                        customerSearching.setContentView(R.layout.customer_search_list);
                        rc_customer = customerSearching.findViewById(R.id.rc_customerlist);
                        EditText search_customer = customerSearching.findViewById(R.id.search_customer);
                        mManager = new LinearLayoutManager(context);
                        rc_customer.setLayoutManager(mManager);
                        try {
                            customer_JsonObject = new JsonObject();
                            customer_JsonObject.addProperty("UserId", usercode);
                            customer_JsonObject.addProperty("CompanyId", "");
                            customer_JsonObject.addProperty("CustomerId", "");
                            customer_JsonObject.addProperty("CustomerCategoryId", 2);
                            customer_JsonObject.addProperty("QueryValue", "");
                            customer_JsonObject.addProperty("RowCount", 100);
                            customer_JsonObject.addProperty("IsActive", true);
                            api.getCustomer(customer_JsonObject);
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    customer_JsonObject.addProperty("CustomerCategoryId", 2);
                                    customer_JsonObject.addProperty("QueryValue", s.toString());
                                    customer_JsonObject.addProperty("RowCount", 100);
                                    customer_JsonObject.addProperty("IsActive", true);
                                    api.getCustomer(customer_JsonObject);
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        customerSearching.show();
                    } catch (Exception e) {
                        Toast.makeText(context, "ERROR IS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            txt_choosefile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filechooserOption();
                }
            });

            txt_dispatchfrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dispatchSearching = new Dialog(context);
                        dispatchSearching.setContentView(R.layout.customer_search_list);
                        rc_dispatch = dispatchSearching.findViewById(R.id.rc_customerlist);
                        EditText search_customer = dispatchSearching.findViewById(R.id.search_customer);
                        mManager = new LinearLayoutManager(context);
                        rc_dispatch.setLayoutManager(mManager);
                        try {
                            customer_JsonObject = new JsonObject();
                            customer_JsonObject.addProperty("UserId", usercode);
                            customer_JsonObject.addProperty("CompanyId", "");
                            customer_JsonObject.addProperty("CustomerId", "");
                            customer_JsonObject.addProperty("CustomerCategoryId", 1);
                            customer_JsonObject.addProperty("QueryValue", "");
                            customer_JsonObject.addProperty("RowCount", 100);
                            customer_JsonObject.addProperty("IsActive", true);
                            api.getDispatchCustomer(customer_JsonObject);
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    customer_JsonObject.addProperty("CustomerCategoryId", 1);
                                    customer_JsonObject.addProperty("QueryValue", s.toString());
                                    customer_JsonObject.addProperty("RowCount", 100);
                                    customer_JsonObject.addProperty("IsActive", true);
                                    api.getDispatchCustomer(customer_JsonObject);
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dispatchSearching.show();
                    } catch (Exception e) {
                        Toast.makeText(context, "ERROR IS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            txt_sales_person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        salesPersonSearching = new Dialog(context);
                        salesPersonSearching.setContentView(R.layout.customer_search_list);
                        rc_salesperson = salesPersonSearching.findViewById(R.id.rc_customerlist);
                        EditText search_customer = salesPersonSearching.findViewById(R.id.search_customer);
                        mManager = new LinearLayoutManager(context);
                        rc_salesperson.setLayoutManager(mManager);
                        try {
                            api.getSalesPerson();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        salesPersonSearching.show();
                    } catch (Exception e) {
                        Toast.makeText(context, "ERROR IS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            txt_isthirdparty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        thirdpartySearching = new Dialog(context);
                        thirdpartySearching.setContentView(R.layout.customer_search_list);
                        rc_thirdparty = thirdpartySearching.findViewById(R.id.rc_customerlist);
                        EditText search_customer = thirdpartySearching.findViewById(R.id.search_customer);
                        mManager = new LinearLayoutManager(context);
                        rc_thirdparty.setLayoutManager(mManager);
                        try {
                            customer_JsonObject = new JsonObject();
                            customer_JsonObject.addProperty("UserId", usercode);
                            customer_JsonObject.addProperty("CompanyId", "");
                            customer_JsonObject.addProperty("CustomerId", "");
                            customer_JsonObject.addProperty("CustomerCategoryId", 2);
                            customer_JsonObject.addProperty("QueryValue", "");
                            customer_JsonObject.addProperty("RowCount", 100);
                            customer_JsonObject.addProperty("IsActive", true);
                            api.getThirdPartyCustomer(customer_JsonObject);
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    rc_thirdparty.setAdapter(null);
                                    customer_JsonObject = new JsonObject();
                                    customer_JsonObject.addProperty("UserId", usercode);
                                    customer_JsonObject.addProperty("CompanyId", "");
                                    customer_JsonObject.addProperty("CustomerId", "");
                                    customer_JsonObject.addProperty("CustomerCategoryId", 2);
                                    customer_JsonObject.addProperty("QueryValue", s.toString());
                                    customer_JsonObject.addProperty("RowCount", 100);
                                    customer_JsonObject.addProperty("IsActive", true);
                                    api.getThirdPartyCustomer(customer_JsonObject);
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        thirdpartySearching.show();
                    } catch (Exception e) {
                        Toast.makeText(context, "ERROR IS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            txt_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        productSearching = new Dialog(context);
                        productSearching.setContentView(R.layout.customer_search_list);
                        rc_product = productSearching.findViewById(R.id.rc_customerlist);
                        EditText search_customer = productSearching.findViewById(R.id.search_customer);
                        mManager = new LinearLayoutManager(context);
                        rc_product.setLayoutManager(mManager);
                        try {
                            product_JsonObject = new JsonObject();
                            product_JsonObject.addProperty("CompanyId", 8);
                            product_JsonObject.addProperty("QueryValue", "");
                            product_JsonObject.addProperty("IsActive", true);
                            api.getProducts(product_JsonObject);
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    product_JsonObject = new JsonObject();
                                    product_JsonObject.addProperty("CompanyId", 8);
                                    product_JsonObject.addProperty("QueryValue", "" + s.toString());
                                    product_JsonObject.addProperty("IsActive", true);
                                    api.getProducts(product_JsonObject);
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        productSearching.show();
                    } catch (Exception e) {
                        Toast.makeText(context, "ERROR IS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            sp_givenby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ContactModel customerModel = lst_GivenBy.get(parent.getSelectedItemPosition());
                    //Toast.makeText(context, "Given By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                    str_givenbyid = customerModel.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            sp_followup_with.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ContactModel customerModel = lst_GivenBy.get(parent.getSelectedItemPosition());
                    //      Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                    str_followupid = customerModel.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            sp_deliveryaddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AddressModel customerModel = lst_Address.get(parent.getSelectedItemPosition());
                    //Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                    str_deliveryaddressid = customerModel.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            sp_billingaddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AddressModel customerModel = lst_Address.get(parent.getSelectedItemPosition());
                    //Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                    str_billingaddressid = customerModel.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /*sp_isthird_party.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  if(lst_customer!=null) {
                      CustomerModel customerModel = lst_customer.get(parent.getSelectedItemPosition());
                      //Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                      str_isthirdpartyid = customerModel.getId();
                  }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

           /* sp_dispatch_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   if(lst_customer!=null) {
                       CustomerModel customerModel = lst_customer.get(parent.getSelectedItemPosition());
                       //Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                       str_dispatchfrom = customerModel.getId();
                   }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            sp_sales_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    EmployeeModel customerModel = lst_Employee.get(parent.getSelectedItemPosition());
                    //Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                    str_saleperson = customerModel.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            sp_ordercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    OrderCategoryModel customerModel = lst_OrderCategory.get(parent.getSelectedItemPosition());
                    //Toast.makeText(context, "Follow By :" + customerModel.getId() + "=" + customerModel.getText(), Toast.LENGTH_SHORT).show();
                    str_ordercategoryid = customerModel.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            txt_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    str_qty = et_qty.getText().toString().trim();
                    str_rate = et_rate.getText().toString().trim();
                    str_tot_amt = et_tot_amt.getText().toString().trim();
                    str_specification = et_specification.getText().toString().trim();
                    if (str_product.trim().equals("0")) {
                        strproductname = et_otherproductname.getText().toString().trim();
                    }

                    if (str_product == null || str_product.equals("")) {
                        Toast.makeText(context, "Please choose product", Toast.LENGTH_SHORT).show();
                    }/*else if(str_specification.equals(""))
                    {
                        et_specification.setError("Required");
                    }*/ else if (str_qty.equals("")) {
                        et_qty.setError("Required");
                    } else if (str_rate.equals("")) {
                        et_rate.setError("Required");
                    } else if (str_uomname.equals("") || str_uomname.equals("Select")) {
                        Toast.makeText(context, "Please choose UOM.", Toast.LENGTH_SHORT).show();
                    } else if (str_tot_amt.equals("")) {
                        et_tot_amt.setError("Required");
                    } else if (strproductname.equals("")) {
                        et_otherproductname.setError("Required");
                    } else {
                        if (sqlightDatabaseUtil.addProduct(str_product, str_rate, str_qty, str_uom, str_specification, str_tot_amt, strproductname, str_uomname, 0)) {
                            Toast.makeText(context, "Product Added in List.", Toast.LENGTH_SHORT).show();
                            showProductDetails();
                            str_qty = "";
                            et_qty.setText("");
                            str_rate = "";
                            et_rate.setText("");
                            str_tot_amt = "";
                            et_tot_amt.setText("");
                            str_specification = "";
                            et_specification.setText("");
                            txt_product.setText("Choose Product");
                            str_product = "";
                        }
                    }
                }
            });

            txt_add_terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        int TermId = 0;
                        int SrNo = 0;
                        int OrderId = 0;
                        int ParticularId = sp_terms_perticular.getSelectedItemPosition();
                        String name = sp_terms_perticular.getSelectedItem().toString().trim();
                        String Condition = et_discribe.getText().toString().trim().trim();
                        boolean IsRemoved = false;
                        if (name.equals("") || name.equals("Select")) {
                            Toast.makeText(context, "Please Select Particular.", Toast.LENGTH_SHORT).show();
                        } else if (Condition.equals("")) {
                            et_discribe.setError("Required.");
                        } else {

                            if (sqlightDatabaseUtil.addTerms(TermId, SrNo, OrderId, ParticularId, Condition, IsRemoved, name)) {
                                Toast.makeText(context, "Terms Added Successfully", Toast.LENGTH_SHORT).show();
                                showTermsDetails();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

           /* sp_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ProductModel productModel = lst_Product.get(parent.getSelectedItemPosition());
                    str_product = productModel.getId();
                    strproductname = productModel.getText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
           /* sp_product.setOnSearchTextChangedListener(new SearchableListDialog.OnSearchTextChanged() {
                @Override
                public void onSearchTextChanged(String strText) {
                    try {
                        if (strText.length() > 2) {
                            product_JsonObject = new JsonObject();
                            product_JsonObject.addProperty("CompanyId", 8);
                            product_JsonObject.addProperty("QueryValue", strText);
                            product_JsonObject.addProperty("IsActive", true);
                            api.getProducts(product_JsonObject);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });*/

            sp_uom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    UOMMOdel uommOdel = lst_UOM.get(parent.getSelectedItemPosition());
                    str_uom = uommOdel.getId();
                    str_uomname = uommOdel.getText();
                    // Toast.makeText(context, str_uomname+"12 Selected :"+str_uom, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btn_order_create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    str_deliverydate = "";
                    str_remark = et_remark.getText().toString().trim();
                    if (str_deliverydate.contains("T")) {
                        str_deliverydate = str_deliverydate.substring(0, str_deliverydate.indexOf("T"));
                        et_deliveryDate.setText(str_deliverydate);
                    }
                    str_deliverydate = et_deliveryDate.getText().toString().trim();
                    str_transportnote = et_trasportnote.getText().toString().trim();
                    // str_isthirdpartyid="1";
                    if (str_ordertypeid == null || str_ordertypeid.equals("")) {
                        Toast.makeText(context, "Order Type Missing.", Toast.LENGTH_SHORT).show();
                    } else if (str_customerid == null || str_customerid.equals("")) {
                        Toast.makeText(context, "Customer Missing.", Toast.LENGTH_SHORT).show();

                    } else if (str_givenbyid == null || str_givenbyid.equals("")) {
                        Toast.makeText(context, "Given By Missing.", Toast.LENGTH_SHORT).show();

                    } else if (str_followupid == null || str_followupid.equals("")) {
                        Toast.makeText(context, "Follow Up Missing.", Toast.LENGTH_SHORT).show();

                    } else if (str_deliveryaddressid == null || str_deliveryaddressid.equals("")) {
                        Toast.makeText(context, "Delivery Missing.", Toast.LENGTH_SHORT).show();

                    } else if (str_billingaddressid == null || str_billingaddressid.equals("")) {
                        Toast.makeText(context, "Billing Address Missing.", Toast.LENGTH_SHORT).show();

                    } else if (str_deliverydate == null || str_deliverydate.equals("")) {
                        et_deliveryDate.setError("Required");
                        Toast.makeText(context, "Delivery Date Missing.", Toast.LENGTH_SHORT).show();

                    }
                          /*  else if(str_transportnote==null||str_transportnote.equals("")){
                                et_trasportnote.setError("Required");
                               Toast.makeText(context, "Transport Note Missing.", Toast.LENGTH_SHORT).show();

                           }*/
                    else if (str_dispatchfrom == null || str_dispatchfrom.equals("")) {
                        Toast.makeText(context, "Dispatch From Missing.", Toast.LENGTH_SHORT).show();

                    } else if (str_saleperson == null || str_saleperson.equals("")) {
                        Toast.makeText(context, "Sales Person Missing.", Toast.LENGTH_SHORT).show();

                    }
                          /*  else if(str_remark==null||str_remark.equals("")){
                                et_remark.setError("Required");
                               Toast.makeText(context, "Remark Missing.", Toast.LENGTH_SHORT).show();

                           }*/
                    else if (str_ordercategoryid == null || str_ordercategoryid.equals("")) {
                        Toast.makeText(context, "Order Category Missing.", Toast.LENGTH_SHORT).show();
                    } else if (sqlightDatabaseUtil.getAllProducts().length == 0) {

                        new MessageBox(context, "Product Details", "Please Added Product", false, false, null).show();

                    } else {

                        JsonObject jsonObject_local = new JsonObject();

                        jsonObject_local.addProperty("OrderTypeName", str_ordertypeid_name);
                        jsonObject_local.addProperty("OrderCategoryName", str_ordercategoryid_name);
                        jsonObject_local.addProperty("CustomerName", str_customerid_name);
                        jsonObject_local.addProperty("DeliveryAddressName", str_deliveryaddressid_name);
                        jsonObject_local.addProperty("BillingAddressName", str_billingaddressid_name);
                        jsonObject_local.addProperty("SalesPersonName", str_saleperson_name);
                        jsonObject_local.addProperty("DispatchFromName", str_dispatchfrom_name);
                        jsonObject_local.addProperty("OrderByName", str_dispatchfrom_name);
                        jsonObject_local.addProperty("FollowWithName", str_followupid_name);
                        jsonObject_local.addProperty("StatusName", "New");
                        jsonObject_local.addProperty("ThirdPartyName", str_isthirdpartyid_name);

                        if (action.trim().equals("New")) {
                            api.addOrder(str_ordertypeid,
                                    str_customerid,
                                    str_givenbyid,
                                    str_followupid,
                                    str_deliveryaddressid,
                                    str_billingaddressid,
                                    str_isthirdpartyid,
                                    str_deliverydate,
                                    str_transportnote,
                                    str_dispatchfrom,
                                    str_saleperson,
                                    str_remark,
                                    str_ordercategoryid, sqlightDatabaseUtil.getAllProducts(), sqlightDatabaseUtil.getAllTerms(), isThirdPartyChecked, str_UplaodedFileName, jsonObject_local);
                        } else if (action.trim().equals("Edit")) {
                            //  Toast.makeText(context, "Update API CALLED" + orderDetailsModel.getOrderId(), Toast.LENGTH_SHORT).show();
                            int str_orderid = orderDetailsModel.getOrderId();
                            String str_ERPID = orderDetailsModel.getERPOrderNo();
                            String str_ERPDate = orderDetailsModel.getERPEntryDate();
                            api.updateOrder(
                                    str_orderid,
                                    str_ERPID,
                                    str_ERPDate,
                                    str_ordertypeid,
                                    str_customerid,
                                    str_givenbyid,
                                    str_followupid,
                                    str_deliveryaddressid,
                                    str_billingaddressid,
                                    str_isthirdpartyid,
                                    str_deliverydate,
                                    str_transportnote,
                                    str_dispatchfrom,
                                    str_saleperson,
                                    str_remark,
                                    str_ordercategoryid, sqlightDatabaseUtil.getAllProducts(), sqlightDatabaseUtil.getAllTerms(), isThirdPartyChecked, str_UplaodedFileName);
                        }
                    }
                }
            });
            et_rate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Toast.makeText(context, ""+s.toString(), Toast.LENGTH_SHORT).show();
                    str_qty = et_qty.getText().toString();
                    if (str_qty != null && !str_qty.isEmpty()) {

                        if (s.toString() != null && !s.toString().isEmpty()) {
                            int qt = Integer.parseInt(str_qty.trim());
                            int rate = Integer.parseInt(s.toString().trim());

                            et_tot_amt.setText("" + (qt * rate));
                        }
                    }
                }
            });
        } catch (Exception e) {

        }


        btn_uplaodFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedFileName = txt_uploadedfilename.getText().toString().trim();
                if (!(selectedFileName.equals(""))) {
                   // uploadFile(file_path);
                    uploadFileVideo(file_path);
                }
            }
        });


    }

    private void filechooserOption() {


        try {
            dialog_fileOption = new Dialog(context);
            dialog_fileOption.setContentView(R.layout.filechooserdialog);
            Button btn_photo, btn_file;
            btn_photo = dialog_fileOption.findViewById(R.id.btn_takephoto);
            btn_file = dialog_fileOption.findViewById(R.id.btn_choosefile);

            btn_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Photo Take", Toast.LENGTH_SHORT).show();
                    PickImageDialog.build(new PickSetup().setPickTypes(EPickType.CAMERA,EPickType.GALLERY)).show(OrderCreate.this);


                }
            });

            btn_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "File Chooser", Toast.LENGTH_SHORT).show();
    /*                new ChooserDialog(context)
                            .withFilter(false, false, "jpg", "jpeg", "png")
                            .withStartFile("path")
                            .withChosenListener(new ChooserDialog.Result() {
                                @Override
                                public void onChoosePath(String path, File pathFile) {
                                    Toast.makeText(context, "FILE: " + path, Toast.LENGTH_SHORT).show();
                                    file_path=path;
                                    txt_uploadedfilename.setText(""+file_path);
                                    dialog_fileOption.dismiss();
                                }
                            })
                            // to handle the back key pressed or clicked outside the dialog:
                            .withOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    Log.d("CANCEL", "CANCEL");
                                    dialog.cancel(); // MUST have
                                }
                            })
                            .build()
                            .show();*/
                       try {

                        /*   final StorageChooser chooser = new StorageChooser.Builder()
                                   // Specify context of the dialog
                                   .withActivity(OrderCreate.this)
                                   .withFragmentManager(getFragmentManager())
                                   .withMemoryBar(true)
                                   .allowCustomPath(true)
                                   // Define the mode as the FILE CHOOSER
                                   .setType(StorageChooser.FILE_PICKER)
                                   .build();

// 2. Handle what should happend when the user selects the directory !
                           chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                               @Override
                               public void onSelect(String path) {
                                   // e.g /storage/emulated/0/Documents
                                   Log.i("SELECTED", "" + path);
                                   file_path = path;
                                   txt_uploadedfilename.setText("" + file_path);
                                   dialog_fileOption.dismiss();
                               }
                           });

// 3. Display File Picker whenever you need to !
                           chooser.show();*/



//
//                           intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                           intent.setType("*/*");
//                           intent=Intent.createChooser(intent,"Choose Files");
//                           intentActivityResultLauncher.launch(intent);

                           Intent intent = new Intent(context, FilePickerActivity.class);
                           intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                   .setCheckPermission(true)
                                   .setShowImages(true)
                                   .enableImageCapture(true)
                                   .setMaxSelection(1)
                                   .setSkipZeroSizeFiles(true)
                                   .setShowFiles(true)
                                   .setRootPath("")
                                   .setSingleChoiceMode(true)
                                   .setSuffixes(new String[]{"doc", "docx", "pdf"})
                                   .build());
                           startActivityForResult(intent, 2301);
                        //   intentActivityResultLauncher.launch(intent);
                        //   startActivityForResult(intent, 7);

                       }catch(Exception e)
                       {
                           Log.d("Error is",e.getMessage());
                       }
                }
            });

            dialog_fileOption.show();

        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    ActivityResultLauncher<Intent> intentActivityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Toast.makeText(context, ""+(result.getResultCode()==RESULT_OK), Toast.LENGTH_SHORT).show();
                    if(result.getResultCode()==RESULT_OK)
                    {
                        try {
                            Toast.makeText(context, ""+(result.getData()), Toast.LENGTH_SHORT).show();

                            Intent data = result.getData();
                            Uri uri = data.getData();
                            if (!Uri.EMPTY.equals(uri)) {
                                //handle followUri
                                Toast.makeText(context, "URI is not EMpty", Toast.LENGTH_SHORT).show();
                                String id = DocumentsContract.getDocumentId(uri);
                                InputStream inputStream = getContentResolver().openInputStream(uri);

                                File file = new File(getCacheDir().getAbsolutePath()+"/"+id);
                                writeFile(inputStream, file);
                                String filePath = file.getPath();
                                Toast.makeText(context, file.getName()+"FilePath : " + filePath, Toast.LENGTH_SHORT).show();


                                Log.i("File path",filePath);
                            }
                            String filePath = PathUtil.getPath(context, uri);
                        //    String filePath = "";
                        //    file_path = uri.getPath();
                            file_path = filePath;
                            txt_uploadedfilename.setText(filePath);
                            Toast.makeText(context, "FilePath : " + filePath, Toast.LENGTH_SHORT).show();
                        }catch(Exception e)
                        {
                            Toast.makeText(context, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
    );
    void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( out != null ) {
                    out.close();
                }
                in.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 2301:

                if (resultCode == RESULT_OK) {
                   ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                    for (MediaFile m:files) {
                        Log.i("Filename ",m.getPath());
                        file_path = m.getPath();
                        txt_uploadedfilename.setText(file_path);
                    }
                    dialog_fileOption.dismiss();
                   // txt_uploadedfilename.setText(builder.toString());
                    //Toast.makeText(context, ""+builder.toString(), Toast.LENGTH_SHORT).show();
                }

                break;
            case 7:

                if(resultCode==RESULT_OK){
                    try {
                        Uri uri = data.getData();
                        File file = new File(uri.getPath());//create path from uri
                        final String[] split = file.getPath().split(":");//split the path.
                        String PathHolder = file.getAbsolutePath();
                        Log.i("FilePath",PathHolder);
                        Toast.makeText(OrderCreate.this, PathHolder + " " + file.exists(), Toast.LENGTH_LONG).show();
                        txt_uploadedfilename.setText("" + PathHolder);
                    }catch(Exception e)
                    {

                    }
                }
                break;

        }

    }

   /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQ_UNICORN_FILE && resultCode == RESULT_OK){
            if(data!=null){
                ArrayList<String> files = data.getStringArrayListExtra("filePaths");
                for(String file : files){
                    Log.e("Selected", file);
                }
            }
        }
    }*/

    public void loadSpinners(String action, OrderDetailsModel orderDetailsModela) {
        try {

            try {
                if (orderDetailsModel.getProductItems() != null && orderDetailsModel.getProductItems().size() >= 1) {
                    boolean b = sqlightDatabaseUtil.clearProductList();
                    for (ProductItems items : orderDetailsModel.getProductItems()) {
                        str_qty = "" + items.getQty();
                        str_rate = "" + items.getRate();
                        str_tot_amt = "" + (items.getQty() * items.getRate());
                        str_specification = "" + items.getSpecification();
                        str_uom = "" + items.getUOMId();
                        strproductname = items.getProductName();
                        str_uomname = items.getUOMName();
                        if (sqlightDatabaseUtil.addProduct("" + items.getProductId(), str_rate, str_qty, str_uom, str_specification, str_tot_amt, strproductname, str_uomname, items.getItemId())) {
                            // Toast.makeText(context, "Product Added in List.", Toast.LENGTH_SHORT).show();
                            //showProductDetails();
                            Log.i("Productid", "" + items.getItemId());
                        }
                    }
                }

                if (orderDetailsModel.getAttachments() != null) {
                    if (orderDetailsModel.getAttachments().size() > 0) {
                        try {
                            str_UplaodedFileName = orderDetailsModel.getAttachments().get(0);
                        } catch (Exception e) {
                            str_UplaodedFileName = "";
                        }
                    }

                    for (String s : orderDetailsModel.getAttachments()) {
                        TextView textView = new TextView(context);
                        textView.setTextColor(Color.RED);
                        textView.setPadding(10, 15, 10, 15);
                        textView.setText("" + s);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, FileDownload.class);
                                intent.putExtra("fname", s);
                                startActivity(intent);

                            }
                        });

                        ll_attachment.addView(textView);
                    }
                }

            } catch (Exception e) {
                Log.i("Error is", e.getMessage());
                //  Toast.makeText(context, "122" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {
                if (orderDetailsModel.getTermConditionItems() != null && orderDetailsModel.getTermConditionItems().size() >= 1) {
                    boolean b = sqlightDatabaseUtil.clearTermsList();
                    for (TermConditionItems items : orderDetailsModel.getTermConditionItems()) {
                        int TermId = items.getTermId();
                        int SrNo = items.getSrNo();
                        int OrderId = items.getOrderId();
                        int ParticularId = items.getParticularId();
                        String name = items.getParticularName();
                        String Condition = items.getCondition();
                        boolean IsRemoved = items.isRemoved();
                        if (sqlightDatabaseUtil.addTerms(TermId, SrNo, OrderId, ParticularId, Condition, IsRemoved, name)) {
                            Toast.makeText(context, "Terms Added Successfully", Toast.LENGTH_SHORT).show();
                            showTermsDetails();
                        }

                    }
                }

            } catch (Exception e) {
                Log.i("Error is", e.getMessage());
                // Toast.makeText(context, "123" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            showProductDetails();
            showTermsDetails();
            api.getOrderType();
            //api.getGivenBy(2);
            //api.getAddress(2);
            // api.getSalesPerson();
            api.getOrderCategory();
            api.getUOM();

            try {
                str_customerid = "" + orderDetailsModel.getCustomerId();
                api.getGivenBy(Integer.parseInt(str_customerid.trim()));
                api.getAddress(Integer.parseInt(str_customerid.trim()));
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            //       }

    /*        try {
                customer_JsonObject = new JsonObject();
                customer_JsonObject.addProperty("UserId", 1);
                customer_JsonObject.addProperty("CompanyId", "");
                customer_JsonObject.addProperty("CustomerId", "");
                customer_JsonObject.addProperty("CustomerCategoryId", 2);
                customer_JsonObject.addProperty("QueryValue", "");
                customer_JsonObject.addProperty("RowCount", 5);
                customer_JsonObject.addProperty("IsActive", true);
                api.getCustomer(customer_JsonObject);
            } catch (Exception e) {
                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {
                product_JsonObject = new JsonObject();
                product_JsonObject.addProperty("CompanyId", 8);
                product_JsonObject.addProperty("QueryValue", "");
                product_JsonObject.addProperty("IsActive", true);
                api.getProducts(product_JsonObject);
            } catch (Exception e) {
                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }*/


        } catch (Exception e) {

        }
    }

    private void showProductDetails() {
        try {
            double totalamt = 0;
            tbl_grid.removeAllViews();
            Vector v[] = sqlightDatabaseUtil.getAllProducts();
            if (v.length > 0) {
                TableRow row1 = new TableRow(context);
                TextView txtqty1, txtrate1, txttotal1, txtsepcification1, txtuom1, txtproduct1, txt_action;
                txtqty1 = new TextView(context);
                txtrate1 = new TextView(context);
                txttotal1 = new TextView(context);
                txtsepcification1 = new TextView(context);
                txtuom1 = new TextView(context);
                txtproduct1 = new TextView(context);
                txt_action = new TextView(context);

                txtqty1.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txtrate1.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txttotal1.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txtsepcification1.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txtuom1.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txtproduct1.setTypeface(txt_add.getTypeface(), Typeface.BOLD);

                txtproduct1.setText("Product");
                txtsepcification1.setText("Spec.");
                txtqty1.setText("Qty");
                txtrate1.setText("Rate");
                txtuom1.setText("UOM");
                txttotal1.setText("Amt");

                row1.addView(txt_action);
                row1.addView(txtproduct1);
                row1.addView(txtsepcification1);
                row1.addView(txtqty1);
                row1.addView(txtrate1);
                row1.addView(txtuom1);
                row1.addView(txttotal1);

                tbl_grid.addView(row1);
            }
            for (int i = 0; i < v.length; i++) {
                TableRow row = new TableRow(context);
                TextView txtqty, txtrate, txttotal, txtsepcification, txtuom, txtproduct;
                TextView btn_remove = new TextView(context);
                btn_remove.setId(Integer.parseInt(v[i].get(1).toString().trim()));
                btn_remove.setText("Remove ");
                btn_remove.setGravity(Gravity.CENTER);
                btn_remove.setTextColor(Color.RED);
                btn_remove.setPadding(5, 5, 5, 5);

                // btn_remove.setLayoutParams (new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sqlightDatabaseUtil.clearProductList(v.getId())) {
                            Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                            showProductDetails();
                        }
                    }
                });
                txtqty = new TextView(context);
                txtrate = new TextView(context);
                txttotal = new TextView(context);
                txtsepcification = new TextView(context);
                txtuom = new TextView(context);
                txtproduct = new TextView(context);

                txtproduct.setText(v[i].get(7).toString());
                txtsepcification.setText(v[i].get(6).toString());
                txtqty.setText(v[i].get(2).toString());
                txtrate.setText(v[i].get(4).toString());
                txtuom.setText(v[i].get(8).toString());
                txttotal.setText(v[i].get(5).toString());
                row.addView(btn_remove);
                row.addView(txtproduct);
                row.addView(txtsepcification);
                row.addView(txtqty);
                row.addView(txtrate);
                row.addView(txtuom);
                row.addView(txttotal);
                try {
                    totalamt += Double.parseDouble(txttotal.getText().toString().trim());
                } catch (NumberFormatException e) {
                }
                tbl_grid.addView(row);

            }
            TextView txtTotalAmt = new TextView(context);
            txtTotalAmt.setGravity(Gravity.RIGHT);
            txtTotalAmt.setPadding(5, 5, 5, 5);
            txtTotalAmt.setText(Html.fromHtml("<h5>Total  : Rs. " + totalamt + "</h5>"));
            tbl_grid.addView(txtTotalAmt);

        } catch (Exception e) {

        }
    }

    private void showTermsDetails() {
        try {

            tbl_grid_terms.removeAllViews();
            Vector v[] = sqlightDatabaseUtil.getAllTerms();
            if (v.length > 0) {
                TableRow row1 = new TableRow(context);
                TextView txt_perticular, txt_condition, txt_action;
                txt_perticular = new TextView(context);
                txt_condition = new TextView(context);
                txt_action = new TextView(context);


                txt_perticular.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txt_condition.setTypeface(txt_add.getTypeface(), Typeface.BOLD);
                txt_action.setTypeface(txt_add.getTypeface(), Typeface.BOLD);

                txt_perticular.setText("Perticular");
                txt_condition.setText("Condition");

                txt_action.setText("Action");

                row1.addView(txt_action);
                row1.addView(txt_perticular);
                row1.addView(txt_condition);


                tbl_grid_terms.addView(row1);
            }
            for (int i = 0; i < v.length; i++) {
                TableRow row = new TableRow(context);
                TextView txt_perticular, txt_condition, txt_action;

                TextView btn_remove = new TextView(context);
                btn_remove.setId(Integer.parseInt(v[i].get(3).toString().trim()));
                btn_remove.setText("Remove ");

                btn_remove.setTextColor(Color.RED);
                btn_remove.setPadding(5, 5, 5, 5);
                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sqlightDatabaseUtil.clearTermsList(v.getId())) {
                            Toast.makeText(context, "Terms Removed", Toast.LENGTH_SHORT).show();
                            showTermsDetails();
                        }
                    }
                });


                txt_perticular = new TextView(context);
                txt_condition = new TextView(context);


                txt_perticular.setText(v[i].get(6).toString());
                txt_condition.setText(v[i].get(4).toString());


                row.addView(btn_remove);
                row.addView(txt_perticular);
                row.addView(txt_condition);


                tbl_grid_terms.addView(row);

            }


        } catch (Exception e) {

        }
    }

    public void customer(View w) {
        try {
            customerSearching = new Dialog(context);
            customerSearching.setContentView(R.layout.customer_search_list);
            rc_customer = customerSearching.findViewById(R.id.rc_customerlist);
            EditText search_customer = customerSearching.findViewById(R.id.search_customer);
            mManager = new LinearLayoutManager(context);
            rc_customer.setLayoutManager(mManager);
            try {
                customer_JsonObject = new JsonObject();
                customer_JsonObject.addProperty("UserId", usercode);
                customer_JsonObject.addProperty("CompanyId", "");
                customer_JsonObject.addProperty("CustomerId", "");
                customer_JsonObject.addProperty("CustomerCategoryId", 2);
                customer_JsonObject.addProperty("QueryValue", "");
                customer_JsonObject.addProperty("RowCount", 10);
                customer_JsonObject.addProperty("IsActive", true);
                api.getCustomer(customer_JsonObject);
            } catch (Exception e) {
                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        customer_JsonObject.addProperty("CustomerCategoryId", 2);
                        customer_JsonObject.addProperty("QueryValue", s.toString());
                        customer_JsonObject.addProperty("RowCount", 10);
                        customer_JsonObject.addProperty("IsActive", true);
                        api.getCustomer(customer_JsonObject);
                    } catch (Exception e) {
                        Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            customerSearching.show();
        } catch (Exception e) {
            Toast.makeText(context, "ERROR IS " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(String result) {

    }

    @Override
    public void onListResponce(List result) {
        //  Toast.makeText(context, "Length : " + result.size(), Toast.LENGTH_SHORT).show();
        if (result != null) {
            lst_OrderType = result;
            if (action.equals("Edit")) {

                // changeValuesSalesPersonSpinner(adapter,result);
                OrderTypeModel tempModel = new OrderTypeModel();
                tempModel.setId("" + orderDetailsModel.getOrderTypeId());
                tempModel.setText("" + orderDetailsModel.getOrderTypeName());
                lst_OrderType.add(0, tempModel);
            }
            ArrayAdapter adapter = new ArrayAdapter(context,
                    android.R.layout.simple_spinner_item, api.getTitleFromList(result));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sp_ordertype.setAdapter(adapter);
        /*    if(action.equals("Edit")) {
                adapter_ordertype=adapter;
                changeValuesOrderTypeSpinner(adapter,result);
            }*/
        }
    }


    String getOrderTypeTitleById(List<OrderTypeModel> lst, int id) {
        String title = "";
        for (OrderTypeModel orderTypeModel : lst) {

            if (orderTypeModel.getId().equals("" + id)) {
                title = orderTypeModel.getText();
                Log.i("Enter=======>>", orderTypeModel.getText());
                // break;
            }

        }
        return title;
    }

    @Override
    public void onListResponce_Customer(List result) {
        if (result != null) {

            try {
                if (result != null) {

                    lst_customer = result;
                    customerAdapter = new CustomerAdapter((ArrayList) lst_customer, context, this);
                    rc_customer.setAdapter(customerAdapter);

                } else {
                    Toast.makeText(context, "No more records.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Erro in Respo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onListResponce_Contact(List result) {
        //  if (result != null) {
       // Toast.makeText(context, "Help", Toast.LENGTH_SHORT).show();
        sp_givenby.setAdapter(null);
        sp_followup_with.setAdapter(null);
        sp_isthird_party.setAdapter(null);

        lst_GivenBy = new ArrayList<>(result);
        lst_followupwith = new ArrayList<>(result);
        lst_IsThirdParty = new ArrayList<>(result);

        if (action.equals("Edit")) {

            // changeValuesSalesPersonSpinner(adapter,result);
            if (orderDetailsModel.getOrderByName() != null) {
                ContactModel tempModel = new ContactModel();
                tempModel.setId("" + orderDetailsModel.getOrderBy());
                tempModel.setText("" + orderDetailsModel.getOrderByName());
                Toast.makeText(context, "Order By Name" + orderDetailsModel.getOrderByName(), Toast.LENGTH_SHORT).show();
                lst_GivenBy.add(0, tempModel);
            }
            Log.i("Order BY Name", "" + lst_GivenBy.size());
            if (orderDetailsModel.getFollowWithName() != null) {

                ContactModel tempModel_followup = new ContactModel();
                tempModel_followup.setId("" + orderDetailsModel.getFollowWith());
                tempModel_followup.setText("" + orderDetailsModel.getFollowWithName());
                lst_followupwith.add(0, tempModel_followup);
            }
            Log.i("Order BY Name", "" + lst_followupwith.size());
            if (orderDetailsModel.getThirdPartyName() != null) {
                ContactModel tempModel_IsThirdParty = new ContactModel();
                tempModel_IsThirdParty.setId("" + orderDetailsModel.getThirdPartyId());
                tempModel_IsThirdParty.setText("" + orderDetailsModel.getThirdPartyName());
                lst_IsThirdParty.add(0, tempModel_IsThirdParty);
            }
            Log.i("Order BY Name", "" + lst_followupwith.size());
        }
        Log.i("given BY ", "" + lst_IsThirdParty.size());

        ArrayAdapter adapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, api.getTitleFromList_For_Contact(lst_GivenBy));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter_followup = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, api.getTitleFromList_For_Contact(lst_followupwith));
        adapter_followup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter_IsthirdParty = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, api.getTitleFromList_For_Contact(lst_IsThirdParty));
        adapter_IsthirdParty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_givenby.setAdapter(adapter);
        sp_followup_with.setAdapter(adapter_followup);
        sp_isthird_party.setAdapter(adapter_IsthirdParty);

        //  }
    }

    @Override
    public void onListResponce_Address(List result) {
        if (result != null) {
            lst_Address = new ArrayList<>(result);
            lst_Address_delivery = new ArrayList<>(result);


            //  loadBillingAddress(lst_Address'');
            if (action.equals("Edit")) {

                // changeValuesSalesPersonSpinner(adapter,result);
                AddressModel employeeModel = new AddressModel();
                employeeModel.setId("" + orderDetailsModel.getBillingAddressId());
                employeeModel.setText("" + orderDetailsModel.getBillingAddressName());
                lst_Address.add(0, employeeModel);

                AddressModel employeeModel_delivery = new AddressModel();
                employeeModel_delivery.setId("" + orderDetailsModel.getDeliveryAddressId());
                employeeModel_delivery.setText("" + orderDetailsModel.getDeliveryAddressName());
                lst_Address_delivery.add(0, employeeModel_delivery);

            }

            ArrayAdapter adapter = new ArrayAdapter(context,
                    android.R.layout.simple_spinner_item, api.getTitleFromList_For_Address(lst_Address));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            ArrayAdapter adapter_delivery = new ArrayAdapter(context,
                    android.R.layout.simple_spinner_item, api.getTitleFromList_For_Address(lst_Address_delivery));
            adapter_delivery.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            sp_billingaddress.setAdapter(adapter);

            sp_deliveryaddress.setAdapter(adapter_delivery);
        }
    }

    @Override
    public void onListResponce_Employee(List result) {
        if (result != null) {
            lst_Employee = result;
            salesPersonAdapter = new SalesPersonAdapter((ArrayList) lst_Employee, context, this);
            rc_salesperson.setAdapter(salesPersonAdapter);

        }
    }

    @Override
    public void onListResponce_OrderCategory(List result) {
        if (result != null) {
            lst_OrderCategory = result;
            if (action.equals("Edit")) {

                // changeValuesSalesPersonSpinner(adapter,result);
                OrderCategoryModel tempModel = new OrderCategoryModel();
                tempModel.setId("" + orderDetailsModel.getOrderCategoryId());
                tempModel.setText("" + orderDetailsModel.getOrderCategoryName());
                lst_OrderCategory.add(0, tempModel);
            }
            ArrayAdapter adapter = new ArrayAdapter(context,
                    android.R.layout.simple_spinner_item, api.getTitleFromList_For_OrderCategory(result));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_ordercategory.setAdapter(adapter);
//            if(action.equals("Edit")) {
//
//                changeValuesOrderCategorySpinner(adapter,result);
//            }
        }
    }

    @Override
    public void onListResponce_Product(List<ProductModel> result) {
        if (result != null) {
            lst_Product = result;
           /* ArrayAdapter adapter = new ArrayAdapter(context,
                    R.layout.type_item, api.getTitleFromList_For_Product(result));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_product.setAdapter(adapter);*/
            ProductModel productModel = new ProductModel();
            productModel.setId("0");
            productModel.setText("Other");
            lst_Product.add(0, productModel);

            productAdapter = new ProductAdapter((ArrayList) lst_Product, context, this);
            rc_product.setAdapter(productAdapter);


        }
    }

    @Override
    public void onListResponce_UOM(List result) {
        if (result != null) {

            lst_UOM = result;
            UOMMOdel select = new UOMMOdel();
            select.setId("0");
            select.setText("Select");
            lst_UOM.add(0, select);


            ArrayAdapter adapter = new ArrayAdapter(context,
                    R.layout.type_item, api.getTitleFromList_For_UOM(lst_UOM));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_uom.setAdapter(adapter);

        }
    }

    @Override
    public void onResponce(OrderDetailsModel result) {
        //   Toast.makeText(context, "---->>>" + result.getCustomerName(), Toast.LENGTH_SHORT).show();

        try {
            this.orderDetailsModel = result;

            et_remark.setText(orderDetailsModel.getRemark());
            str_deliverydate = orderDetailsModel.getDeliveryDate();
            if (str_deliverydate.contains("T")) {
                str_deliverydate = str_deliverydate.substring(0, str_deliverydate.indexOf("T"));
                et_deliveryDate.setText(str_deliverydate);
            } else {
                et_deliveryDate.setText(orderDetailsModel.getDeliveryDate());
            }
            et_trasportnote.setText(orderDetailsModel.getTransportNote());
            str_dispatchfrom = "" + orderDetailsModel.getDispatchFrom();
            txt_dispatchfrom.setText(orderDetailsModel.getDispatchFromName());
            txt_customername.setText(orderDetailsModel.getCustomerName());
            txt_isthirdparty.setText(orderDetailsModel.getThirdPartyName());
            str_isthirdpartyid = "" + orderDetailsModel.getThirdPartyId();
            str_customerid = "" + orderDetailsModel.getCustomerId();
            str_dispatchfrom = "" + orderDetailsModel.getDispatchFrom();
            txt_dispatchfrom.setText(orderDetailsModel.getDispatchFromName());
            str_givenbyid = "" + orderDetailsModel.getOrderBy();
            boolean b = orderDetailsModel.isThirdParty();

            txt_sales_person.setText(orderDetailsModel.getSalesPersonName());
            str_saleperson = "" + orderDetailsModel.getSalesPerson();

            if (b)
                chk_isThirdParty.setChecked(true);
            else
                chk_isThirdParty.setChecked(false);


            //  loadSpinners(action,result);
            loadSpinners(action, null);

        } catch (Exception e) {

        }


    }

    @Override
    public void onOrderPlaced(String body) {
        try {
            sqlightDatabaseUtil.clearProductList();
            sqlightDatabaseUtil.clearTermsList();
            if (action.equals("New")) {
                new MessageBox(context, "Create Order", "Order Placed Your Order id is :" + body, true, true, OrderDataList.class).show();
                Toast.makeText(context, "Order Placed Your Order id is :" + body, Toast.LENGTH_SHORT).show();
            } else if (action.equals("Edit")) {
                Toast.makeText(context, "Order Update" + body, Toast.LENGTH_SHORT).show();
                new MessageBox(context, "Order Update", "Order Update" + body, true, true, OrderDataList.class).show();

            } else {
                if (body != null)
                    new MessageBox(context, "Order Update", body, false, true, null).show();

            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onListResponce_Dispatch(List result) {
        if (result != null) {


            try {
                if (result != null) {
                    // lst_customer=result;
                    dispatchAdapter = new DispatchAdapter((ArrayList) result, context, this);
                    rc_dispatch.setAdapter(dispatchAdapter);
                } else {
                    Toast.makeText(context, "No more records.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Erro in Respo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onListResponce_thirdParty(List<CustomerModel> result) {
        if (result != null) {


            try {
                if (result != null) {
                    // lst_customer=result;
                    thirpartyAdpater = new ThirdPartyAdapter((ArrayList) result, context, this);
                    rc_thirdparty.setAdapter(thirpartyAdpater);
                } else {
                    Toast.makeText(context, "No more records.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Erro in Respo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    void changeValuesOrderTypeSpinner(ArrayAdapter adapter, List list) {
        try {
            Toast.makeText(context, "" + orderDetailsModel.getOrderTypeId(), Toast.LENGTH_SHORT).show();
            int p = adapter.getPosition(getOrderTypeTitleById(list, orderDetailsModel.getOrderTypeId()));
            sp_ordertype.setSelection(p);
        } catch (Exception e) {

        }
    }

    void changeValuesSalesPersonSpinner(ArrayAdapter adapter, List list) {
        try {
            //   Toast.makeText(context, ""+orderDetailsModel.getOrderTypeId(), Toast.LENGTH_SHORT).show();
            int p = adapter.getPosition(getSalesPersonTitleById(list, orderDetailsModel.getSalesPerson()));
            Toast.makeText(context, "Sales Person :" + p, Toast.LENGTH_SHORT).show();
            sp_sales_person.setSelection(p);
        } catch (Exception e) {

        }
    }

    void changeValuesOrderCategorySpinner(ArrayAdapter adapter, List list) {
        try {
            Toast.makeText(context, "oc :" + orderDetailsModel.getOrderCategoryId(), Toast.LENGTH_SHORT).show();
            int p = adapter.getPosition(getOrderCategoryTitleById(list, orderDetailsModel.getOrderCategoryId()));
            Toast.makeText(context, "Order Category:" + p, Toast.LENGTH_SHORT).show();
            sp_ordercategory.setSelection(p);
        } catch (Exception e) {

        }
    }

    String getOrderCategoryTitleById(List<OrderCategoryModel> lst, int id) {
        String title = "";
        for (OrderCategoryModel model : lst) {
            if (model.getId().equals("" + id)) {
                title = model.getText();
                Log.i("Enter=======>>", model.getText());
                // break;
            }
        }
        return title;
    }

    String getSalesPersonTitleById(List<EmployeeModel> lst, int id) {
        String title = "";
        for (EmployeeModel model : lst) {

            if (model.getId().equals("" + id)) {
                title = model.getText();
                Log.i("Enter=======>>", model.getText());
                // break;
            }

        }
        return title;
    }

    @Override
    public void onCustomerSelected(CustomerModel customerModel) {
        Toast.makeText(context, " Id = " + customerModel.getId() + "\nName :" + customerModel.getText(), Toast.LENGTH_SHORT).show();
        customerSearching.dismiss();
        str_customerid = customerModel.getId();
        str_customerid_name = customerModel.getText();
        txt_customername.setText("" + customerModel.getText());

        //   sp_isthird_party.setAdapter(null);
        sp_followup_with.setAdapter(null);
        sp_givenby.setAdapter(null);
        sp_deliveryaddress.setAdapter(null);
        sp_billingaddress.setAdapter(null);


        api.getGivenBy(Integer.parseInt(customerModel.getId().trim()));
        api.getAddress(Integer.parseInt(customerModel.getId().trim()));
    }

    @Override
    public void onProductSelected(ProductModel productModel) {
        productSearching.dismiss();

        if (productModel.getId().trim().equals("0")) {
            et_otherproductname.setVisibility(View.VISIBLE);
            str_product = productModel.getId();
            strproductname = et_otherproductname.getText().toString().trim();
        } else {
            et_otherproductname.setVisibility(View.GONE);
            str_product = productModel.getId();
            strproductname = productModel.getText();
            txt_product.setText("" + strproductname);
        }


    }

    @Override
    public void onDispatchSelected(CustomerModel customerModel) {
        dispatchSearching.dismiss();
        str_dispatchfrom = customerModel.getId();
        str_dispatchfrom_name = customerModel.getText();
        txt_dispatchfrom.setText("" + customerModel.getText());
    }

    @Override
    public void onThirdPartySelected(CustomerModel customerModel) {
        thirdpartySearching.dismiss();
        str_isthirdpartyid = customerModel.getId();
        str_isthirdpartyid_name = customerModel.getText();
        txt_isthirdparty.setText("" + customerModel.getText());
    }

    @Override
    public void onSalesPersonSelected(EmployeeModel dataModel) {
        salesPersonSearching.dismiss();
        str_saleperson = dataModel.getId();
        str_saleperson_name = dataModel.getText();
        txt_sales_person.setText("" + dataModel.getText());
    }

    @Override
    public void onPickResult(PickResult r) {
        try {
            //image_adharcard.setImageBitmap(r.getBitmap());
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            //r.getBitmap().compress(Bitmap.CompressFormat.PNG, 30, out);
/*

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String encoded=Base64.encode(b);
*/

            //  Log.i("Base64 ee:", encoded);
           /* Uri uri = r.getUri();
            File file = new File(uri.getPath());//create path from uri
            final String[] split = file.getPath().split(":");//split the path.
            String filePath = split[0];
            Log.i("Image Path",split.length+"Path : "+filePath);*/
            //    Log.i("Base64 :", MyApplicationUtil.getImageDatadetail(r.getPath()));
            file_path = r.getPath();
            // Toast.makeText(context, ""+file_path, Toast.LENGTH_SHORT).show();
            txt_uploadedfilename.setText("" + file_path);
            dialog_fileOption.dismiss();
            // uri = r.getUri();
            // base64_image = MyApplicationUtil.getImageDatadetail(r.getPath());
        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
        }
    }

    private void uploadFile(String path) {
        try {
         //   Toast.makeText(context, "File path" + path, Toast.LENGTH_SHORT).show();
            if (!progressDialog.isShowing())
                progressDialog.show();

            File file1 = new File(path);
        //    Toast.makeText(context, "File path" + file1.exists(), Toast.LENGTH_SHORT).show();

            //    File file1 = null;
            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileBody1 = null;
            Bitmap bitmap = null;
            try {
                if (file1 != null) {
                    ///      MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("PrimaryImage", file1.getName(), requestBody);
                    //u    bitmap = BitmapFactory.decodeFile(file1.getPath());
                    // bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(file1));
                    requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                    imageFileBody1 = MultipartBody.Part.createFormData("PrimaryImage", file1.getName(), requestBody1);
                }
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }


            Call<String> call = RetrofitClient.getInstance().getMyApi().uploadProductQualityImage(imageFileBody1, requestBody1);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {

                        Toast.makeText(context, " Success " + response.body(), Toast.LENGTH_SHORT).show();

                        str_UplaodedFileName = response.body();
                        str_UplaodedFileName = str_UplaodedFileName.replace("[\"", "");
                        str_UplaodedFileName = str_UplaodedFileName.replace("\"]", "");
                        str_UplaodedFileName = str_UplaodedFileName.substring(str_UplaodedFileName.lastIndexOf("/") + 1);
                        txt_uploadedfilename.setText("" + str_UplaodedFileName);
                        progressDialog.dismiss();

                    } else {

                        Toast.makeText(context, "Fail " + response.body(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        txt_uploadedfilename.setText("Fail to Upload.");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(context, " Error -" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    txt_uploadedfilename.setText("Fail to Upload.");
                }
            });


            // return true;
        } catch (Exception e) {
            progressDialog.dismiss();
            Log.i("Error is", e.getMessage());

            txt_uploadedfilename.setText("Fail to Upload.");
            //  return false;
        }
            /*File file=new File(path);
            base64_image=file.getName();
            try{
                RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("files",file.getName(), RequestBody.create(MediaType.parse("image/*"),file))
                        .addFormDataPart("some_key","some_value")
                        .addFormDataPart("submit","submit")
                        .build();

                Request request=new Request.Builder()
                        .url("http://crm.konarkgroup.com:83/v1/attachments/upload?moduleType=5")
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new okhttp3.Callback() {


                    @Override
                    public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                          Log.i("On Respo",""+response.body().string());
                        Toast.makeText(context, ""+response.body().string(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                        Log.i("On Respo",""+ e.getMessage());
                    }
                });
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }*/

    }

    private void uploadFileVideo(String path) {
         upath=path;
         new UplaodFile().execute(path);

    }

    public class UplaodFile extends AsyncTask{
        String fname="";
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Uploading File ....");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            txt_uploadedfilename.setText(o.toString().trim());
        }

        @Override
        protected Object doInBackground(Object[] path) {

            try {
                Log.i("Path ",upath);
                Log.i("Path ",""+((new File(upath)).length()/1024)/1024 +" MB");
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                String charset = "UTF-8";
                File uploadFile1 = new File(upath.trim());
                String requestURL = Constants.BASE_URL+"v1/attachments/upload?documentType=8";

                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                multipart.addHeaderField("documentType", "8");
//            multipart.addHeaderField("Test-Header", "Header-Value");

                // multipart.addFormField("friend_id", "Cool Pictures");
                multipart.addFormField("documentType", "8");

                multipart.addFilePart("file", uploadFile1);

                List<String> response = multipart.finish();

                Log.v("rht", "SERVER REPLIED:");

                for (String line : response) {
                    Log.v("rht", "Line : "+line);
                    str_UplaodedFileName = line;
                    str_UplaodedFileName = str_UplaodedFileName.replace("[\"", "");
                    str_UplaodedFileName = str_UplaodedFileName.replace("\"]", "");
                    str_UplaodedFileName = str_UplaodedFileName.substring(str_UplaodedFileName.lastIndexOf("/") + 1);
                    fname=str_UplaodedFileName;
                    // txt_uploadedfilename.setText(line);
                }
                return fname;
                //txt_uploadedfilename.setText("File Uploaded Successfully..");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 111);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                    } else {

                        Toast.makeText(context, "Please allow the permission.", Toast.LENGTH_SHORT).show();
                        checkPermission();
                    }
                }


                break;
        }
    }
}