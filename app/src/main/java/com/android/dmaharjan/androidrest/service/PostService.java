package com.android.dmaharjan.androidrest.service;

import com.android.dmaharjan.androidrest.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dmaharjan on 3/3/17.
 */

public interface PostService {
    @GET("/posts")
    Call<List<Post>> getPosts();
    @GET("/posts/{id}")
    Call<Post> getPost(@Path("id") Integer id);
}
