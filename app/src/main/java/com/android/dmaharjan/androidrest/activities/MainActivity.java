package com.android.dmaharjan.androidrest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.dmaharjan.androidrest.R;
import com.android.dmaharjan.androidrest.model.Post;
import com.android.dmaharjan.androidrest.service.PostService;
import com.android.dmaharjan.androidrest.util.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.dmaharjan.androidrest.R.id.users;

public class MainActivity extends AppCompatActivity {
    private PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postService = RestApi.getAPIService();

        ListView listView = (ListView) findViewById(users);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getPosts()));
    }

    public ArrayList<String> getPosts() {
        final ArrayList<String> titles = new ArrayList<>();
        postService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("MainActivity", "Status Code" + response.code());
                List<Post> posts = response.body();
                for (Post post : posts) {
                    titles.add(post.getTitle().toUpperCase());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        return titles;
    }
}
