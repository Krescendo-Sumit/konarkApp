package com.mytech.salesvisit.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.view.orderdata.OrderDataList;

public class MessageBox {
    Dialog dialog;
    TextView txt_message;
    Button btn_okay;
    TextView txt_title;
    ImageView img_status, img_close;

    //               Context ,Title of Dialog,Message,status is it success or Fail/info
    public MessageBox(Context context, String title, String message, boolean status, boolean isFinish, Class redirectoTO) {
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.messagebox);
        txt_message = dialog.findViewById(R.id.txt_message);
        btn_okay = dialog.findViewById(R.id.btn_okay);
        txt_title = dialog.findViewById(R.id.txt_title);
        img_status = dialog.findViewById(R.id.img_status);
        img_close = dialog.findViewById(R.id.img_close);
        if (title != null)
            txt_title.setText(title);

        if (message != null)
            txt_message.setText("" + message);
        if (status)
            img_status.setImageResource(R.drawable.ic_success);
        else
            img_status.setImageResource(R.drawable.ic_fail);

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFinish)
                    ((Activity) context).finish();
                if (redirectoTO!=null) {
                    try {

                        context.startActivity(new Intent(context, redirectoTO));
                    } catch (Exception e) {
                        Log.i("Class", e.getMessage());
                    }
                }
                dialog.dismiss();

            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void show() {
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }

    public void dismiss() {
        try {
            dialog.dismiss();
        } catch (Exception e) {

        }
    }

}
