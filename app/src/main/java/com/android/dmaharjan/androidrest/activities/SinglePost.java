package com.android.dmaharjan.androidrest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.dmaharjan.androidrest.R;
import com.android.dmaharjan.androidrest.model.Post;
import com.android.dmaharjan.androidrest.service.PostService;
import com.android.dmaharjan.androidrest.util.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePost extends AppCompatActivity {
    private PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        Integer id = getIntent().getExtras().getInt("postId");
        postService = RestApi.getAPIService();
        postService.getPost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
                TextView textViewContent = (TextView) findViewById(R.id.textViewContent);
                textViewTitle.setText(response.body().getTitle().toUpperCase());
                textViewContent.setText(response.body().getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }
}
