package com.example.githubdemo.data.network

import com.example.githubdemo.data.bean.AccessTokenBean
import com.example.githubdemo.data.bean.RepositoryItem
import com.example.githubdemo.data.bean.SearchRepositoriesRst
import com.example.githubdemo.data.bean.UserInfoBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 13:47
 */
interface MainService {
  @GET("user")
  suspend fun getUserInfo(): UserInfoBean

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

  @GET("search/repositories")
  suspend fun searchRepositories(@Query("q") keyWord: String, @Query("sort") sort: String = "", @Query("order") o: String = "desc",
                                 @Query(value = "page") page: Int = 1): SearchRepositoriesRst

  @GET("user/repos")
  suspend fun getMyRepos(@Query(value = "page") page: Int): List<RepositoryItem>

  @GET("user/starred")
  suspend fun getMyStarred(@Query(value = "page") page: Int): List<RepositoryItem>
}