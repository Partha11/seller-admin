package com.supernova.selleradmin.service;

import com.supernova.selleradmin.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("add-transaction.php")
    Call<ApiResponse> uploadTransaction(@Field("user_email") String userEmail,
                                        @Field("user_token") String userToken,
                                        @Field("trx_id") String trxId,
                                        @Field("trx_amount") String trxAmount,
                                        @Field("trx_number") String phoneNumber,
                                        @Field("trx_type") int type);

    @FormUrlEncoded
    @POST("add-chips.php")
    Call<ApiResponse> addChipsToUser(@Field("user_email") String userEmail,
                                     @Field("user_token") String userToken,
                                     @Field("user_id") String userId,
                                     @Field("chips_amount") String chips);

    @FormUrlEncoded
    @POST("admin-login.php")
    Call<ApiResponse> signInWithEmailAndPassword(@Field("user_email") String email,
                                                 @Field("user_password") String password);

    @FormUrlEncoded
    @POST("get-data.php")
    Call<ApiResponse> getData(@Field("user_email") String userEmail,
                              @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("get-graph.php")
    Call<ApiResponse> getGraphData(@Field("user_email") String userEmail,
                                   @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("get-summary.php")
    Call<ApiResponse> getStats(@Field("user_email") String userEmail,
                               @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("get-transactions.php")
    Call<ApiResponse> getTransactions(@Field("user_email") String userEmail,
                                      @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("search-transaction.php")
    Call<ApiResponse> searchTransaction(@Field("user_email") String userEmail,
                                        @Field("user_token") String userToken,
                                        @Field("key") String key);

    @FormUrlEncoded
    @POST("update-price.php")
    Call<ApiResponse> updatePrice(@Field("user_email") String userEmail,
                                  @Field("user_token") String userToken,
                                  @Field("chips_price") String price);

    @FormUrlEncoded
    @POST("update-number.php")
    Call<ApiResponse> updateNumber(@Field("user_email") String userEmail,
                                   @Field("user_token") String userToken,
                                   @Field("number_type") String numberType,
                                   @Field("number") String number);

    @FormUrlEncoded
    @POST("update-password.php")
    Call<ApiResponse> updatePassword(@Field("user_email") String userEmail,
                                     @Field("user_token") String userToken,
                                     @Field("user_password") String userPassword);

    @FormUrlEncoded
    @POST("send-notification.php")
    Call<ApiResponse> sendNotification(@Field("user_email") String userEmail,
                                       @Field("user_token") String userToken,
                                       @Field("notification_title") String title,
                                       @Field("notification_content") String content);

    @FormUrlEncoded
    @POST("update-balance.php")
    Call<ApiResponse> updateBalance(@Field("user_email") String userEmail,
                                    @Field("user_token") String userToken,
                                    @Field("chips_amount") String chipsAmount);
}
