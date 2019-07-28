package com.projecr.semicolon.newz;

import android.app.Application;
import android.content.Context;

import com.projecr.semicolon.newz.rest.RestApi;
import com.projecr.semicolon.newz.rest.RetrofitClient;

public class AppController extends Application {
    private RestApi restApi;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private static AppController get(Context context){
        return (AppController) context.getApplicationContext();
    }

    public static AppController create(Context context){
        return AppController.get(context);
    }

    public RestApi getRestApi(){
        if (restApi == null){
            restApi = RetrofitClient.create();
        }

        return restApi;
    }


}
