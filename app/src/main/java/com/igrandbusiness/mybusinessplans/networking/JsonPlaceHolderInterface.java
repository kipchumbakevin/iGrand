package com.igrandbusiness.mybusinessplans.networking;

import androidx.core.content.res.FontResourcesParserCompat;

import com.igrandbusiness.mybusinessplans.models.MessagesModel;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.models.UserDocs;
import com.igrandbusiness.mybusinessplans.models.UsersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderInterface {
    //send code
    @FormUrlEncoded
    @POST("api/sendcode")
    Call<MessagesModel> code(
            @Field("phone")String phone,
            @Field("appSignature")String appSignature

    );
    //check signup details
    @FormUrlEncoded
    @POST("api/checkdetails")
    Call<MessagesModel> checkDetails(
            @Field("phone")String pho,
            @Field("email")String mail

    );
    //register user
    @FormUrlEncoded
    @POST("api/auth/signup")
    Call<UsersModel> register(
            @Field("email")String email,
            @Field("phone")String phone,
            @Field("password")String password,
            @Field("password_confirmation")String confirmPassword,
            @Field("code")String code

    );
    //change forgot pass
    @FormUrlEncoded
    @POST("api/forgotpassword")
    Call<UsersModel> forgotSign(
            @Field("phone")String phone,
            @Field("new_password")String password,
            @Field("code")String code

    );
    //change password
    @FormUrlEncoded
    @POST("api/changepass")
    Call<MessagesModel> changePass(
            @Field("oldpass")String oldp,
            @Field("newpass")String newp

    );
    //login user
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UsersModel> login(
            @Field("phone")String pho,
            @Field("password")String pass
    );

    //check number forgot password
    @FormUrlEncoded
    @POST("api/check")
    Call<MessagesModel> check(
            @Field("phone")String pho
    );

    //check change phone number
    @FormUrlEncoded
    @POST("api/checkphone")
    Call<MessagesModel> checkpho(
            @Field("phone")String oldpho,
            @Field("newPhone")String newpho
    );
    //change phone number
    @FormUrlEncoded
    @POST("api/changephone")
    Call<MessagesModel> changePP(
            @Field("newPhone")String phone,
            @Field("code")String code

    );
    //getDocs
    @GET("api/getdocs")
    Call<List<UserDocs>> getDocs();
    //getvideo
    @GET("api/getvid")
    Call<List<ReceiveData>> getVids();
    //getmagazine
    @GET("api/getmag")
    Call<List<ReceiveData>> getMags();
    //getpod
    @GET("api/getpod")
    Call<List<ReceiveData>> getPod();
}
