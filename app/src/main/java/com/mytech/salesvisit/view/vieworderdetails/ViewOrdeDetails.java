package com.mytech.salesvisit.view.vieworderdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.ProductItems;
import com.mytech.salesvisit.model.ProductModel;
import com.mytech.salesvisit.model.TermConditionItems;
import com.mytech.salesvisit.view.FileDownload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ViewOrdeDetails extends AppCompatActivity implements ViewOrderDetailsListener {
    String id;
    ViewOrderDetailsAPI viewOrderDetailsAPI;
    int orderID;
    TableLayout tbl_grid, tbl_grid_terms;
    TextView
            txt_erporderno,
            txt_orderno,
            txt_quotation,
            txt_ordetype,
            txt_customer,
            txt_givenby,
            txt_folloeupwith,
            txt_delivery_address,
            txt_billing_address,
            txt_isthirdparty,
            txt_deliverydate,
            txt_transportnote,
            txt_dispatch_from,
            txt_sale_person,
            txt_remark,
            txt_ordecategory,
            txt_attachments,
            txt_status;
    TextView txt_bold;
    Context context;
    LinearLayout ll_attachment;
    DownloadManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orde_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = ViewOrdeDetails.this;
        setTitle("Order Details");
        init();


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


        txt_erporderno = findViewById(R.id.txt_erp_id_no);
        txt_orderno = findViewById(R.id.txt_orderno_);
        txt_quotation = findViewById(R.id.txt_quotation);
        txt_ordetype = findViewById(R.id.txt_order_type);
        txt_customer = findViewById(R.id.txt_cutomer_name);
        txt_givenby = findViewById(R.id.txt_given_by);
        txt_folloeupwith = findViewById(R.id.txt_followupwith);
        txt_delivery_address = findViewById(R.id.txt_deliveryaddress);
        txt_billing_address = findViewById(R.id.txt_billing_address);
        txt_isthirdparty = findViewById(R.id.txt_is_thirdparty);
        txt_deliverydate = findViewById(R.id.txt_deliverydate);
        txt_transportnote = findViewById(R.id.txt_transportnotes);
        txt_dispatch_from = findViewById(R.id.txt_dispatchfrom);
        txt_sale_person = findViewById(R.id.txt_salesperson);
        txt_remark = findViewById(R.id.txt_remark);
        txt_ordecategory = findViewById(R.id.txt_ordercategory);
        txt_attachments = findViewById(R.id.txt_attachements);
        txt_status = findViewById(R.id.txt_status);
        txt_bold = findViewById(R.id.txt_bold);
        tbl_grid = findViewById(R.id.tbl_grid);
        tbl_grid_terms = findViewById(R.id.tbl_grid_terms);
        ll_attachment = findViewById(R.id.ll_attachment);


        viewOrderDetailsAPI = new ViewOrderDetailsAPI(ViewOrdeDetails.this, this);
        id = getIntent().getExtras().getString("id").toString().trim();
        // Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        try {
            orderID = Integer.parseInt(id.trim());
            viewOrderDetailsAPI.getOrderData(orderID);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Order Number", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResult(String result) {

    }

    @Override
    public void onResponce(OrderDetailsModel result) {

        txt_erporderno.setText("" + result.getERPOrderNo());
        txt_orderno.setText("" + result.getOrderId());
        txt_quotation.setText("" + result.getCurrencyId());
        txt_ordetype.setText("" + result.getOrderTypeName());
        txt_customer.setText("" + result.getCustomerName());
        txt_givenby.setText("" + result.getOrderByName());
        txt_folloeupwith.setText("" + result.getFollowWithName());
        txt_delivery_address.setText("" + result.getDeliveryAddressName());
        txt_billing_address.setText("" + result.getBillingAddressName());
        if (result.getThirdPartyId() == 0) {
            txt_isthirdparty.setText("No");
            txt_isthirdparty.setText("-");
        } else {
            txt_isthirdparty.setText("Yes");
            txt_isthirdparty.setText("" + result.getThirdPartyName());
        }
        txt_deliverydate.setText("" + parseDateToddMMyyyy(result.getDeliveryDate().toString().trim()));
        txt_transportnote.setText("" + result.getTransportNote());
        txt_dispatch_from.setText("" + result.getDispatchFromName());
        txt_sale_person.setText("" + result.getSalesPersonName());
        txt_remark.setText("" + result.getRemark());
        txt_ordecategory.setText("" + result.getOrderCategoryName());

        //txt_attachments.setText("");
        for (String s : result.getAttachments()) {
            TextView textView = new TextView(context);
            textView.setPadding(10, 10, 10, 10);
            textView.setText("" + s);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FileDownload.class);
                    intent.putExtra("fname", s);
                    startActivity(intent);

                  /*  String url = "http://crm.konarkgroup.com:82/Uploads/Order/"+s;
                    DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle("My File");
                    request.setDescription("Downloading");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationUri(Uri.parse("file://konark/"+s));
                    downloadmanager.enqueue(request);*/
                }
            });

            ll_attachment.addView(textView);
        }

        txt_status.setText("" + result.getStatusName());

        try {

            tbl_grid.removeAllViews();
            List<ProductItems> v = result.getProductItems();
            if (v.size() > 0) {
                TableRow row1 = new TableRow(context);
                TextView txtqty1, txtrate1, txttotal1, txtsepcification1, txtuom1, txtproduct1;
                txtqty1 = new TextView(context);
                txtrate1 = new TextView(context);
                txttotal1 = new TextView(context);
                txtsepcification1 = new TextView(context);
                txtuom1 = new TextView(context);
                txtproduct1 = new TextView(context);

                txtqty1.setTypeface(txt_bold.getTypeface(), Typeface.BOLD);
                txtrate1.setTypeface(txt_bold.getTypeface(), Typeface.BOLD);
                txttotal1.setTypeface(txt_bold.getTypeface(), Typeface.BOLD);
                txtsepcification1.setTypeface(txt_bold.getTypeface(), Typeface.BOLD);
                txtuom1.setTypeface(txt_bold.getTypeface(), Typeface.BOLD);
                txtproduct1.setTypeface(txt_bold.getTypeface(), Typeface.BOLD);

                txtproduct1.setText("Product");
                txtsepcification1.setText("Spec.");
                txtqty1.setText("Qty");
                txtrate1.setText("Rate");
                txtuom1.setText("UOM");
                txttotal1.setText("Total");

                row1.addView(txtproduct1);
                row1.addView(txtsepcification1);
                row1.addView(txtqty1);
                row1.addView(txtrate1);
                row1.addView(txtuom1);
                row1.addView(txttotal1);

                tbl_grid.addView(row1);
            }
            for (int i = 0; i < v.size(); i++) {
                ProductItems productItems = v.get(i);
                TableRow row = new TableRow(context);
                TextView txtqty, txtrate, txttotal, txtsepcification, txtuom, txtproduct;
                txtqty = new TextView(context);
                txtrate = new TextView(context);
                txttotal = new TextView(context);
                txtsepcification = new TextView(context);
                txtuom = new TextView(context);
                txtproduct = new TextView(context);

                txtproduct.setText(productItems.getProductName());
                txtsepcification.setText(productItems.getSpecification());
                txtqty.setText("" + productItems.getQty());
                txtrate.setText("" + productItems.getRate());
                txtuom.setText("" + productItems.getUOMName());
                txttotal.setText("" + (productItems.getRate() * productItems.getQty()));

                row.addView(txtproduct);
                row.addView(txtsepcification);
                row.addView(txtqty);
                row.addView(txtrate);
                row.addView(txtuom);
                row.addView(txttotal);

                tbl_grid.addView(row);

            }

        } catch (Exception e) {
            Toast.makeText(context, "Errror is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {

            tbl_grid_terms.removeAllViews();
            List<TermConditionItems> v = result.getTermConditionItems();
            if (v.size() > 0) {
                TableRow row1 = new TableRow(context);
                TextView txt_perticular, txt_condition, txt_action;
                txt_perticular = new TextView(context);
                txt_condition = new TextView(context);
                txt_action = new TextView(context);


                txt_perticular.setTypeface(txt_isthirdparty.getTypeface(), Typeface.BOLD);
                txt_condition.setTypeface(txt_isthirdparty.getTypeface(), Typeface.BOLD);
                txt_action.setTypeface(txt_isthirdparty.getTypeface(), Typeface.BOLD);

                txt_perticular.setText("Perticular");
                txt_condition.setText("Condition");


                row1.addView(txt_perticular);
                row1.addView(txt_condition);


                tbl_grid_terms.addView(row1);
            }
            for (int i = 0; i < v.size(); i++) {
                TermConditionItems termConditionItems = v.get(i);
                TableRow row = new TableRow(context);
                TextView txt_perticular, txt_condition;
                txt_perticular = new TextView(context);
                txt_condition = new TextView(context);

                txt_perticular.setText(termConditionItems.getParticularName());
                txt_condition.setText(termConditionItems.getCondition());

                row.addView(txt_perticular);
                row.addView(txt_condition);

                tbl_grid_terms.addView(row);


            }

        } catch (Exception e) {
            Toast.makeText(context, "Errror is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    public String parseDateToddMMyyyy(String time) {
        time=time.replace("T"," ");
        String inputPattern = "yyyy-MM-dd HH:mm:ss"; //2022-06-07T00:00:00
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}