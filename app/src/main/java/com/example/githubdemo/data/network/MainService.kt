package com.example.githubdemo.data.network

import com.example.githubdemo.data.bean.AccessTokenBean
import com.example.githubdemo.data.bean.UserInfoBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:47
 */
interface MainService {
  @GET("user")
  suspend fun loginAccessToken(): UserInfoBean

  @FormUrlEncoded
  @POST("access_token")
  @Headers("Accept: application/json")
  suspend fun getAccessToken(
    @Field("code") code: String,
    @Field("client_id") clientId: String,
    @Field("client_secret") clientSecret: String,
    @Field("state") state: String,
    @Field("redirect_uri") redirectUrl: String
  ): AccessTokenBean
}