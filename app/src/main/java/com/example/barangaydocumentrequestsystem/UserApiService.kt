package com.example.barangaydocumentrequestsystem

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET



interface UserApiService {
    @FormUrlEncoded
    @POST("signup.php")
    fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("contact_num") contactNumber: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("request.php")
    fun userRequest(
        @Field("username") username: String,
        @Field("address") address: String,
        @Field("purpose") purpose: String,
        @Field("contact_num") contactNum: String,
        @Field("docu") docu: String
    ): Call<ResponseBody>

    data class User(
        val id: String,
        val username: String,
        val email: String,
        val address: String,
        val contact: String
    )
    @FormUrlEncoded
    @GET("fetchUser.php") // Your PHP endpoint
    fun getUserProfile(@Query("user_id") userId: String): Call<User>
}
