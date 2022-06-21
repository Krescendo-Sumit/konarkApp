package com.mytech.salesvisit.util;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.model.AddressModel;
import com.mytech.salesvisit.model.ContactModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.OrderCategoryModel;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.OrderTypeModel;
import com.mytech.salesvisit.model.ProductModel;
import com.mytech.salesvisit.model.UOMMOdel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {


    @GET(Constants.GET_ORDER_TYPE)
    Call<List<OrderTypeModel>> getOrderType();

    @POST(Constants.GET_CUSTOMER)
    Call<List<CustomerModel>> getCustomer(@Body JsonObject jsonObject);

    @GET(Constants.GET_GIVEN_BY)
    Call<List<ContactModel>> getGivenBY(@Path("usercode")int code);

    @GET(Constants.GET_ADDRESS)
    Call<List<AddressModel>> getAddress(@Path("usercode")int code);

    @GET(Constants.GET_SALES_PERSON)
    Call<List<EmployeeModel>> getSalesPerson();

    @GET(Constants.GET_ORDER_CATEGORY)
    Call<List<OrderCategoryModel>> getOrderCategory();

    @POST(Constants.GET_PRODUCTLIST)
    Call<List<ProductModel>> getProductList(@Body JsonObject jsonObject);

    @GET(Constants.GET_UOM)
    Call<List<UOMMOdel>> getUOM();

    @POST(Constants.ADD_ORDER)
    Call<String> addOrder(@Body JsonObject jsonObject);

    @PUT(Constants.ADD_ORDER)
    Call<String> updateOrder(@Body JsonObject jsonObject);

    @POST(Constants.GET_ORDER_DATA)
    Call<OrderDataModel> getOrderDcata(@Body JsonObject jsonObject);

    @GET(Constants.GET_ORDER_DETAILS)
    Call<OrderDetailsModel> getViewOrderData(@Path("usercode")int orderID);

    @DELETE(Constants.REMOVE_ORDER)
    Call<String> removeOrder(@Path("orderid")int orderId);


    @GET(Constants.GET_ORDER_DETAILS)
    Call<OrderDetailsModel> getViewOrderDatails(@Path("usercode")int orderID);

    @Multipart
    @POST("v1/attachments/upload?documentType=8")
    Call<String> uploadProductQualityImage(@Part MultipartBody.Part file, @Part("files") RequestBody items);



   /*
    @POST(Constants.SUB_COURSE_URL)
    Call<List<SubCourseModel>> getSubCourseList(@Query("mobile") String mobile, @Query("id") String id);

     @POST(Constants.CONTENT_LIST)
     Call<List<ContentModel>> getContentList(@Query("mobile") String mobile, @Query("id") String id);

    @POST(Constants.GET_USER_PROFILE)
    Call<List<UserModel>> getUserDetails(@Query("mobile") String mobile, @Query("id") String id, @Query("deviceid") String deviceid);

    @POST(Constants.GET_MATCH_PROFILE)
    Call<List<UserModel>> getMatches(@Query("mobile") String mobile, @Query("id") String id, @Query("deviceid") String deviceid);

    @POST(Constants.GET_TENDER_DETAILS)
    Call<List<TenderModel>> getTenders(@Query("mobile") String mobile, @Query("id") String id, @Query("deviceid") String deviceid, @Query("srcString") String srcString);


    @POST(Constants.CHECK_LOGIN)
    Call<List<SignInModel>> checkLogin(@Query("mobile") String mobile, @Query("pass") String pass, @Query("deviceid") String deviceid);

    @POST(Constants.GET_USER_FEES)
    Call<List<FeesModel>> getFeesDetails(@Query("mobile") String mobile, @Query("id") String id);

    @POST(Constants.GET_USER_FILES)
    Call<List<FileModel>> getFileDetails(@Query("mobile") String mobile, @Query("id") String id);

    @POST(Constants.INSERTUSER)
    Call<String> addUser(@Query("name") String name, @Query("email") String email, @Query("mobile") String mobile, @Query("course") String course, @Query("password") String password, @Query("confirmpassword") String confirmpassword, @Query("address") String address);

    @POST(Constants.VALIDATEFEES)
    Call<String> validateFees(@Query("sid") String uid);

    @POST(Constants.GETSWARANKUR)
    Call<List<SwarankurModel>> getSwarankur(@Query("mobile") String mobile, @Query("id") String userid);



    @POST(Constants.INSERT_REGISTRATION)
    Call<String> insertUser(@Query("id") String id, @Query("usercategory") String usercategory, @Query("fullname") String fullname, @Query("dob") String dob, @Query("age") String age, @Query("email") String email, @Query("mobile1") String mobile1, @Query("mobile2") String mobile2, @Query("isJanmpatrikaAvailable") String isJanmpatrikaAvailable, @Query("height") String height, @Query("color") String color, @Query("gotra") String gotra, @Query("isChashma") String isChashma, @Query("qualification") String qualification, @Query("jobdetails") String jobdetails, @Query("joblocation") String joblocation, @Query("monthlyIncome") String monthlyIncome, @Query("local_address") String local_address, @Query("permanent_Address") String permanent_Address, @Query("isWishToSeeJamnpatrika") String isWishToSeeJamnpatrika, @Query("isMangal") String isMangal, @Query("hobbies") String hobbies, @Query("partnerRequirment") String partnerRequirment, @Query("fathername") String fathername, @Query("fatherocupation") String fatherocupation, @Query("mothersname") String mothersname, @Query("totalbrothers") String totalbrothers, @Query("bothersmaritialStatas") String bothersmaritialStatas, @Query("totalsister") String totalsister, @Query("sistermaritialStatus") String sistermaritialStatus, @Query("MamacheKul") String mamacheKul, @Query("photopath") String photopath, @Query("createdDate") String createdDate, @Query("password") String password);

    @POST(Constants.UPDATE_REGISTRATION)
    Call<String> updateUser(@Query("id") String id, @Query("usercategory") String usercategory, @Query("fullname") String fullname, @Query("dob") String dob, @Query("age") String age, @Query("email") String email, @Query("mobile1") String mobile1, @Query("mobile2") String mobile2, @Query("isJanmpatrikaAvailable") String isJanmpatrikaAvailable, @Query("height") String height, @Query("color") String color, @Query("gotra") String gotra, @Query("isChashma") String isChashma, @Query("qualification") String qualification, @Query("jobdetails") String jobdetails, @Query("joblocation") String joblocation, @Query("monthlyIncome") String monthlyIncome, @Query("local_address") String local_address, @Query("permanent_Address") String permanent_Address, @Query("isWishToSeeJamnpatrika") String isWishToSeeJamnpatrika, @Query("isMangal") String isMangal, @Query("hobbies") String hobbies, @Query("partnerRequirment") String partnerRequirment, @Query("fathername") String fathername, @Query("fatherocupation") String fatherocupation, @Query("mothersname") String mothersname, @Query("totalbrothers") String totalbrothers, @Query("bothersmaritialStatas") String bothersmaritialStatas, @Query("totalsister") String totalsister, @Query("sistermaritialStatus") String sistermaritialStatus, @Query("MamacheKul") String mamacheKul, @Query("photopath") String photopath, @Query("createdDate") String createdDate, @Query("password") String password);

    @POST(Constants.INSERT_INTEREST)
    Call<String> addInterest(@Query("userid") String userid, @Query("interestedid") String interestid);

    @POST(Constants.INSERT_INTEREST_ACCEPT)
    Call<String> addInterest_Accept(@Query("userid") String userid, @Query("interestedid") String interestid, @Query("type") int type);

    @POST(Constants.INSERT_CHAT)
    Call<String> addChat(@Query("userid") String userid, @Query("interestedid") String interestid, @Query("message") String message);

    @POST(Constants.GET_CHAT)
    Call<List<ChatModel>> getChat(@Query("sender") String sender, @Query("receiver") String receiver);

    @POST(Constants.GET_MATECHES_REQUEST)
    Call<List<UserModel>> getMatchesRequest(@Query("mobile") String mobile, @Query("userid") String userid, @Query("deviceid") String deviceid, @Query("type") String type);

    @POST(Constants.GET_TENDER_SINGLE)
    Call<List<TenderModel>> getTenderDetails(@Query("userid") String userid, @Query("tenderid") String tenderid);

    @POST(Constants.GET_TYPES)
    Call<String> getTypes(@Query("mobile") String mobile, @Query("userid") String userid, @Query("type") String type);

    @POST(Constants.GET_PREFERENCE)
    Call<String> getPreference(@Query("mobile") String mobile, @Query("userid") String userid, @Query("type") String type);


    @POST(Constants.INSERT_SUGGESTIONS)
    Call<String> addSuggestion(@Query("userid") String userid, @Query("message") String message);


    @POST(Constants.GET_NOTIFICATION)
    Call<List<ChatModel>> getNotifications(@Query("sender") String sender);


    @POST(Constants.GET_TENDER_DETAILS_OFFLINE)
    Call<List<TenderModel>> getTenders_Offline(@Query("mobile") String mobile, @Query("id") String id, @Query("deviceid") String deviceid, @Query("srcString") String srcString);

    @POST(Constants.GET_NOTIFICATIONCOUNT)
    Call<String> getNotificatioCount(@Query("mobile") String mobile, @Query("id") String id);

  *//*  @POST(Constants.GET_CITIES)
    Call<String> getCity(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);

    @POST(Constants.GET_KEYWORD)
    Call<String> getCity(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);

    @POST(Constants.GET_CATEGORY)
    Call<String> getCity(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);
*//*

*//*
    @POST(police.bharti.katta.util.Constants.BHARTI_URL_INDIVISUAL)
    Call<List<BhartiModel>> getBhartiMenuDetails(@Query("mobile") String mobile,@Query("id")String id,@Query("type")String type);

    @POST(police.bharti.katta.util.Constants.SARAV_MENU_URL)
    Call<List<SaravMenuModel>> getSaravMenu(@Body JSONObject jsonObject);

    @POST(police.bharti.katta.util.Constants.CHALUGHADAMODI_MENU_URL)
    Call<List<ChaluGhadamodiModel>> getChalughadaModiMenu(@Body JSONObject jsonObject);

    @POST(police.bharti.katta.util.Constants.GET_BOOKS_LIST)
    Call<List<BookModel>> getBookList(@Body JSONObject jsonObject);

    */

}
