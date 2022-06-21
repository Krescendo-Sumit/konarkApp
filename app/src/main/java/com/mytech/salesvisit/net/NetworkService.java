package com.mytech.salesvisit.net;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytech.salesvisit.util.AppConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkService {

    private AuthService authService;
    private static NetworkService networkService;
    private static AuthInterceptor authInterceptor = new AuthInterceptor();


    private   NetworkService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                 .addInterceptor(authInterceptor)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASEURL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(client)
                .build();
        authService = retrofit.create(AuthService.class);
    }

    public static NetworkService getInstance(){
        if(networkService!=null){
            return networkService;
        }else {
            networkService = new NetworkService();
            return networkService;
        }
    }

    public  NetworkService setToken(String token){
        authInterceptor.setToken(token);
        return networkService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void logout(int userid,GeoLocation location) throws IOException {
        UserlogoutRequest request = new UserlogoutRequest();
        request.setUserId(userid);
        request.setGeoLocation(location);
        getInstance().authService.logout(request).execute();
    }




    static class AuthInterceptor implements Interceptor {
        private String token="";
                @Override
            public Response intercept(Chain chain) throws IOException {
                    Request request  = chain.request();

                    if(TextUtils.isEmpty(token)){
                        return chain.proceed(request);
                    }
                Request newRequest  = request.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
                    return chain.proceed(newRequest);
            }

        public void setToken(String token) {
            this.token = token;
        }
    }


}
