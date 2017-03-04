package com.android.dmaharjan.androidrest.service;

import com.android.dmaharjan.androidrest.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dmaharjan on 3/3/17.
 */

public interface PostService {
    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts/{id}")
    Call<Post> getPost(@Path("id") Integer id);

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title, @Field("body") String body, @Field("userId") long userId);

    @POST("/posts")
    Call<Post> saveNewPost(@Body Post post);

    @PUT("/posts/{id}")
    Call<Post> update(@Path("id") Integer id, @Body Post post);

    @DELETE("/posts/{id}")
    Call<Post> deletePost(@Path("id") Integer id);
}
