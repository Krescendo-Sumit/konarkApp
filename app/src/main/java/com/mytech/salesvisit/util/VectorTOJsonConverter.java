package com.mytech.salesvisit.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Vector;

public class VectorTOJsonConverter {
    public JsonArray getJsonArray(Vector[] v) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject;
        try {
            for (int i = 0; i < v.length; i++) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("ItemId", "" + v[i].get(9));
                jsonObject.addProperty("OrderId", 0);
                jsonObject.addProperty("SrNo", 1);
                jsonObject.addProperty("ProductId", Integer.parseInt(v[i].get(1).toString()));
                jsonObject.addProperty("Specification", v[i].get(6).toString());
                jsonObject.addProperty("Qty", Float.parseFloat(v[i].get(2).toString()));
                jsonObject.addProperty("UOMId", Integer.parseInt(v[i].get(3).toString()));
                jsonObject.addProperty("Rate", Double.parseDouble(v[i].get(4).toString()));
                jsonObject.addProperty("StatusId", 1);
                jsonObject.addProperty("IsRemoved", false);
                jsonObject.addProperty("OtherProduct", v[i].get(7).toString());
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }


    public JsonArray getJsonArrayTerms(Vector[] v) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject;
        try {
            for (int i = 0; i < v.length; i++) {
                jsonObject = new JsonObject();

                /*


      "TermId": 0,
      "SrNo": 0,
      "OrderId": 0,
      "ParticularId": 0,
      "Condition": "string",
      "IsRemoved": true
 int TermId=1;
                        int SrNo=1;
                        int OrderId=0;
                 */

                jsonObject.addProperty("TermId", Integer.parseInt(v[i].get(0).toString().trim()));
                jsonObject.addProperty("SrNo", Integer.parseInt(v[i].get(1).toString().trim()));
                jsonObject.addProperty("OrderId", Integer.parseInt(v[i].get(2).toString().trim()));
                jsonObject.addProperty("ParticularId", Integer.parseInt(v[i].get(3).toString()));
                jsonObject.addProperty("Condition", v[i].get(4).toString());
                jsonObject.addProperty("IsRemoved", Boolean.parseBoolean(v[i].get(5).toString()));

                jsonArray.add(jsonObject);
            }
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }
}
