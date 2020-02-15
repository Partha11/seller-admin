package com.supernova.selleradmin.service;

import com.supernova.selleradmin.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("get-users.php")
    Call<ApiResponse> getUsers(@Field("user_email") String userEmail,
                               @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("update-transactions.php")
    Call<ApiResponse> uploadTransaction(@Field("user_email") String userEmail,
                                        @Field("user_token") String userToken,
                                        @Field("trx_id") String trxId,
                                        @Field("trx_amount") double trxAmount,
                                        @Field("phone_number") String phoneNumber);

    @FormUrlEncoded
    @POST("create-offer.php")
    Call<ApiResponse> createOffer(@Field("user_email") String userEmail,
                                  @Field("user_token") String userToken,
                                  @Field("offer_title") String offerTitle,
                                  @Field("offer_content") String offerContent,
                                  @Field("chips_amount") double chipsAmount,
                                  @Field("chips_price") double chipsPrice);

    @FormUrlEncoded
    @POST("get-offers.php")
    Call<ApiResponse> getOffers(@Field("user_email") String userEmail,
                                @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("modify-offer.php")
    Call<ApiResponse> modifyOffer(@Field("user_email") String userEmail,
                                  @Field("user_token") String userToken,
                                  @Field("offer_id") int offerId,
                                  @Field("type") int type);

    @FormUrlEncoded
    @POST("modify-user.php")
    Call<ApiResponse> modifyUser(@Field("user_email") String userEmail,
                                 @Field("user_token") String userToken,
                                 @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("add-chips.php")
    Call<ApiResponse> addChipsToUser(@Field("user_email") String userEmail,
                                     @Field("user_token") String userToken,
                                     @Field("user_id") String userId,
                                     @Field("chips_amount") String chips);

    @FormUrlEncoded
    @POST("send-notification.php")
    Call<ApiResponse> notifyUsers(@Field("user_email") String userEmail,
                                  @Field("user_token") String userToken,
                                  @Field("id_list") String idList,
                                  @Field("offer_id") int offerId);

    @FormUrlEncoded
    @POST("admin-login.php")
    Call<ApiResponse> signInWithEmailAndPassword(@Field("user_email") String email,
                                                 @Field("user_password") String password);

    @FormUrlEncoded
    @POST("get-summary.php")
    Call<ApiResponse> getStats(@Field("user_email") String userEmail,
                               @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("get-all-transactions.php")
    Call<ApiResponse> getTransactions(@Field("user_email") String userEmail,
                                      @Field("user_token") String userToken);

    @FormUrlEncoded
    @POST("delete-transaction.php")
    Call<ApiResponse> deleteTransaction(@Field("user_email") String userEmail,
                                        @Field("user_token") String userToken,
                                        @Field("id") String id);

    @FormUrlEncoded
    @POST("update-chips-price.php")
    Call<ApiResponse> updateChipsPrice(@Field("user_email") String userEmail,
                                       @Field("user_token") String userToken,
                                       @Field("chips_price") String price);

    @FormUrlEncoded
    @POST("update-bonus-chips.php")
    Call<ApiResponse> updateBonusChips(@Field("user_email") String userEmail,
                                       @Field("user_token") String userToken,
                                       @Field("chips_amount") String amount);

    @FormUrlEncoded
    @POST("change-number.php")
    Call<ApiResponse> updateNumber(@Field("user_email") String userEmail,
                                   @Field("user_token") String userToken,
                                   @Field("number_type") String numberType,
                                   @Field("number") String number);

    @FormUrlEncoded
    @POST("change-password.php")
    Call<ApiResponse> updatePassword(@Field("user_email") String userEmail,
                                     @Field("user_token") String userToken,
                                     @Field("user_password") String userPassword);

    @FormUrlEncoded
    @POST("notify-users.php")
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
