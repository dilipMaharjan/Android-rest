package com.android.dmaharjan.androidrest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.dmaharjan.androidrest.R;
import com.android.dmaharjan.androidrest.model.Post;
import com.android.dmaharjan.androidrest.service.PostService;
import com.android.dmaharjan.androidrest.util.RestApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.dmaharjan.androidrest.R.id.users;

public class MainActivity extends AppCompatActivity {
    private PostService postService;
    private Map<String, Integer> hashMapTitleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postService = RestApi.getAPIService();
        hashMapTitleId = new HashMap<>();
        ListView listView = (ListView) findViewById(users);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getPosts());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this, SinglePost.class);
                intent.putExtra("postId", getId(text));
                startActivity(intent);
            }
        });
    }

    public ArrayList<String> getPosts() {
        final ArrayList<String> titles = new ArrayList<>();
        postService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("MainActivity", "Status Code : " + response.code() + "Data : " + response.body());
                List<Post> posts = response.body();
                for (Post post : posts) {
                    titles.add(post.getTitle().toUpperCase());
                    hashMapTitleId.put(post.getTitle().toUpperCase(), post.getId());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        return titles;
    }

    private Integer getId(String title) {
        return hashMapTitleId.containsKey(title) ? hashMapTitleId.get(title) : 1;
    }
}
