package com.mytech.salesvisit.net;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {
    @POST("/v1/app/login")
    Call<UserResponse> login(@Body UserRequest request);
    @POST("/v1/app/visit/checkin")
    Call<String> checkin(@Body UserCheckRequest request);
    @POST("/v1/app/visit/checkout")
    Call<String> checkout(@Body UserCheckRequest request);
    @GET("/v1/app/customer/list/{userId}")
    Call<List<Company>> company(@Path(value = "userId") int id);
    @POST("/v1/app/logout")
    Call<String> logout(@Body UserlogoutRequest request);
    @GET("/v1/app/visit/today/{userId}")
    Call<List<Visit>> visits(@Path(value = "userId") int id);
    @GET("/v1/City/list/{stateId}")
    Call<List<CityId>> city(@Path(value = "stateId") int id);
    @GET("/v1/State/list/{countryId}")
    Call<List<StateId>> states(@Path(value = "countryId") int id);
    @POST("/v1/app/customer")
    Call<String> addCustomer(@Body AddCustomerRequest request);
    @POST("/v1/app/user/profile/{userId}")
    Call<UserProfile> profile(@Path(value = "userId") int id);
    @POST("/v1/app/geo/location")
    Call<ResponseBody> geoLocation(@Body List<GeoRequest> request);

}
