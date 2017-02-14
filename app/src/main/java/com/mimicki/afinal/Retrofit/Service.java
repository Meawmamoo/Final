package com.mimicki.afinal.Retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by DELL on 7/13/2016.
 */
public interface Service {

//    @FormUrlEncoded
//    @POST("/datatrafficarrangement/register/register.php")
//    Call<ServiceModel> postServiceModel(@Field("username") String email, @Field("password") String password, @Field("gender_eplsuser") String gender,
//                                        @Field("phone_eplsuser") String phone, @Field("emerphone_eplsuser") String emerphone,
//                                        @Field("address_eplsuser") String address, @Field("dob_eplsuser") String dob, @Field("firstname_eplsuser") String firstname,
//                                        @Field("lastname_eplsuser") String lastname);

    //ใช้ได้เหมือนกัน แล้วแต่จะใช้
    @Multipart
    @POST("/datatrafficarrangement/register/register.php")
    Call<ServiceModel> postServiceModel(@Part("username") RequestBody email,
                                        @Part("password") RequestBody password,
                                        @Part("gender_eplsuser") RequestBody gender,
                                        @Part("phone_eplsuser") RequestBody phone,
                                        @Part("emerphone_eplsuser") RequestBody emerphone,
                                        @Part("address_eplsuser") RequestBody address,
                                        @Part("dob_eplsuser") RequestBody dob,
                                        @Part("firstname_eplsuser") RequestBody firstname,
                                        @Part("lastname_eplsuser") RequestBody lastname);


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Multipart
    @POST("/datatrafficarrangement/login/eplsuser/login.php")
    Call<ServiceModelLogin> postServiceModelLogin(@Part("username") RequestBody email,
                                                  @Part("password") RequestBody password);



//    @FormUrlEncoded
//    @POST("/datatrafficarrangement/login/eplsuser/login.php")
//    Call<ServiceModelLogin> postServiceModelLogin(@Field("username") RequestBody email, @Field("password") RequestBody password);
//
    @GET("/datatrafficarrangement/login/eplsuser/login.php")
    Call<ServiceModelLogin> getServiceModelLogin();

    @Multipart
    @POST("/datatrafficarrangement/login/ambulance/login.php")
    Call<ServiceModelLoginDriver> postServiceModelLoginDriver(@Part("username") RequestBody email,
                                                        @Part("password") RequestBody password);

    @GET("/datatrafficarrangement/login/ambulance/login.php")
    Call<ServiceModelLoginDriver> getServiceModelLoginDriver();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Multipart
    @POST("/datatrafficarrangement/eplscall/eplscall.php")
    Call<ServiceModelCall> postServiceModelCall(@Part("username") RequestBody email,
                                                @Part("ideplsuser") RequestBody iduser,
                                                @Part("lat-call") RequestBody latcall,
                                                @Part("lng-call") RequestBody lngcall);

    @GET("/datatrafficarrangement/eplscall/eplscall.php")
    Call<ServiceModelCall> getServiceModelCall();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
