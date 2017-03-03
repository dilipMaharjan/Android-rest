package com.android.dmaharjan.androidrest.util;

import com.android.dmaharjan.androidrest.retrofit.RetrofitClient;
import com.android.dmaharjan.androidrest.service.PostService;

/**
 * Created by dmaharjan on 3/3/17.
 */

public class RestApi {
    public RestApi() {
    }

    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    public static PostService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(PostService.class);
    }
}
