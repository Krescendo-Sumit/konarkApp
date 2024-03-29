package com.mytech.salesvisit.util;

public class Constants {
 //UAT Api Access
 // public static final String BASE_URL = "http://crm.konarkgroup.com:83/";
// UAT File Download
//  public static final String BASE_URL_upload = "http://crm.konarkgroup.com:82/uploads/";

 //Live File Download
 public static final String BASE_URL_upload = "http://crm.konarkgroup.com/uploads/";
 //Live API Access
  public static final String BASE_URL = "http://crm.konarkgroup.com:8101/";

  public static final String GET_ORDER_TYPE ="v1/order/type/list" ;
  public static final String GET_CUSTOMER = "v1/customer/search/list";
  public static final String GET_CUSTOMER_CHECKIN = "v1/app/customer/search/list";
  public static final String GET_GIVEN_BY = "v1/customer/contact/list/{usercode}";
  public static final String GET_ADDRESS = "v1/customer/address/{usercode}/list";
  public static final String GET_SALES_PERSON ="v1/employee/list";
  public static final String GET_ORDER_CATEGORY = "v1/order/category/list";
  public static final String GET_PRODUCTLIST = "v1/product/search/list";
  public static final String GET_UOM = "v1/uom/list";
  public static final String ADD_ORDER ="v1/order" ;
  public static final String GET_ORDER_DATA ="v1/order/data" ;
  public static final String GET_ORDER_DETAILS ="v1/order/details/{usercode}" ;
  public static final String REMOVE_ORDER = "v1/order/{orderid}";
 public static final String GET_VERSION_CODE ="v1/app/version" ;
 public static final String GET_Companion_People ="v1/employee/list" ;
 public static final String GET_VISIT_REASON = "v1/visit/reason/list";
 public static final String GET_REMARKABOUT ="v1/visit/remark/category/list/{RemarkAboutType}" ;
 public static final String GET_REMARK_DETAILS = "v1/visit/remark/details/list/{RemarkCategoryId}";
 public static final String GET_MOM_PERTICULAR = "v1/visit/mom/particular/list";
 public static final String VERFY_GEO_LOCATION = "v1/app/customer/verify/geo/location";
}
