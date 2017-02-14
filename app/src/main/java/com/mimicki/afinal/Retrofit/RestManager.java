package com.mimicki.afinal.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 6/9/2016.
 */
public class RestManager {

    private final Service service;

//    public static Retrofit getBuilder(Context context) {
        // Define the interceptor, add authentication headers
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder().build();
//                return chain.proceed(newRequest);
//            }
//        };

        // Add the interceptor to OkHttpClient
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.interceptors().add(interceptor);
//        OkHttpClient client = builder.build();



//        return new Builder()
//                .baseUrl(Constants.HTTP.BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//    }

    public RestManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HTTP.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(Service.class);

    }


    public void login(String username, String password, Callback<ServiceModelLogin> callback){
        RequestBody usernameFormData = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordFormData = RequestBody.create(MediaType.parse("text/plain"), password);

        service.postServiceModelLogin(usernameFormData, passwordFormData).enqueue(callback);
    }

    public void driver(String username, String password, Callback<ServiceModelLoginDriver> callback){
        RequestBody usernameFormData = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordFormData = RequestBody.create(MediaType.parse("text/plain"), password);

        service.postServiceModelLoginDriver(usernameFormData, passwordFormData).enqueue(callback);
    }


    public  void register(String username, String password, String gender_eplsuser,
                          String phone_eplsuser, String emerphone_eplsuser, String address_eplsuser,
                          String dob_eplsuser, String firstname_eplsuser, String lastname_eplsuser, Callback<ServiceModel> callback) {

        RequestBody UserRegis = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody PassRegis = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody Gender = RequestBody.create(MediaType.parse("text/plain"), gender_eplsuser);
        RequestBody Phone = RequestBody.create(MediaType.parse("text/plain"), phone_eplsuser);
        RequestBody Ephone = RequestBody.create(MediaType.parse("text/plain"),emerphone_eplsuser);
        RequestBody Address = RequestBody.create(MediaType.parse("text/plain"), address_eplsuser);
        RequestBody DatePicker = RequestBody.create(MediaType.parse("text/plain"), dob_eplsuser);
        RequestBody Firstname = RequestBody.create(MediaType.parse("text/plain"), firstname_eplsuser);
        RequestBody Lastname = RequestBody.create(MediaType.parse("text/plain"), lastname_eplsuser);

        service.postServiceModel(UserRegis,PassRegis,Gender,Phone,Ephone,Address,DatePicker,Firstname,Lastname).enqueue(callback);
    }

    public void call(String username, String iduser, String latcall, String lngcall, Callback<ServiceModelCall> callback) {

        RequestBody usernameFormData = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody idFormData = RequestBody.create(MediaType.parse("text/plain"), iduser);
        RequestBody latFormData = RequestBody.create(MediaType.parse("text/plain"), latcall);
        RequestBody lngFormData = RequestBody.create(MediaType.parse("text/plain"), lngcall);

        service.postServiceModelCall(usernameFormData,idFormData,latFormData,lngFormData).enqueue(callback);
    }

}
