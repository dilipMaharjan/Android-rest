package com.android.dmaharjan.androidrest.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        final Integer id = getIntent().getExtras().getInt("postId");
        postService = RestApi.getAPIService();
        post = new Post();
        postService.getPost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                post = response.body();
                TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
                TextView textViewContent = (TextView) findViewById(R.id.textViewContent);
                textViewTitle.setText(response.body().getTitle().toUpperCase());
                textViewContent.setText(response.body().getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
        findViewById(R.id.updatePost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SinglePost.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.new_post, null);
                dialogBuilder.setView(dialogView);
                final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.title);
                final EditText editTextContent = (EditText) dialogView.findViewById(R.id.content);
                final EditText editTextUserId = (EditText) dialogView.findViewById(R.id.userId);
                Button buttonSave = (Button) dialogView.findViewById(R.id.buttonSave);
                buttonSave.setText("Update");
                editTextTitle.setText(post.getTitle());
                editTextContent.setText(post.getBody());
                editTextUserId.setText(post.getUserId().toString());
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Post post = new Post();
                        post.setTitle(editTextTitle.getText().toString());
                        post.setBody(editTextContent.getText().toString());
                        post.setUserId(Integer.parseInt(editTextUserId.getText().toString()));
                        postService.update(id, post).enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, Response<Post> response) {
                                if (response.isSuccessful()) {
                                    Log.i("SinglePost", " Code: " + response.code());
                                    alertDialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });
        findViewById(R.id.deletePost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postService.deletePost(id).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Log.i("SinglePost", "Code : " + response.code());
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            }
        });
    }
}
