package com.curiousapps.nyc_schoolnycschools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.requests.PictureApi;
import com.curiousapps.nyc_schoolnycschools.requests.ServiceGenerator;
import com.curiousapps.nyc_schoolnycschools.requests.responses.PicResponse;
import com.curiousapps.nyc_schoolnycschools.requests.responses.PicSearchResponse;
import com.curiousapps.nyc_schoolnycschools.util.Constants;
import com.curiousapps.nyc_schoolnycschools.viewModels.MainListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.curiousapps.nyc_schoolnycschools.util.Constants.API_KEY;

public class MainActivity extends BaseActivity {

    private MainListViewModel mMainListViewModel;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainListViewModel = new ViewModelProvider(this).get(MainListViewModel.class);

        subscribeObservers();
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Clicked");

                testRetrofitRequests();
//                if (mProgressBar.getVisibility() == View.VISIBLE){
//                    showProgressBar(false);
//                }else {
//                    showProgressBar(true);
//                }
            }
        });
    }

    private void subscribeObservers(){

        mMainListViewModel.getPicObjects().observe(this, new Observer<List<PicObject>>() {
            @Override
            public void onChanged(List<PicObject> picObjects) {
//                if (picObjects != null){
//                    for (PicObject picObject:picObjects){
//                        Log.d(TAG, "onChanged: " + picObject.getId());
//                    }
//                }
            }
        });
    }
    private void searchPicObjectsApi(String query, int pageNumber){
        mMainListViewModel.searchPicObjectsApi(query, pageNumber);
    }

    private void testRetrofitRequests(){
        Log.d(TAG, "Retrofit Start");
        //searchPicObjectsApi("cherry", 1);

        PictureApi pictureApi = ServiceGenerator.getPictureApi();

//        Call<PicSearchResponse> responseCall = pictureApi.searchPics(
//                API_KEY, "australian shepherd", "1"
//        );
//        Log.d(TAG+" make call", responseCall.toString());
//
//        responseCall.enqueue(new Callback<PicSearchResponse>() {
//            @Override
//            public void onResponse(Call<PicSearchResponse> call, Response<PicSearchResponse> response) {
//                Log.d(TAG, "ServerResponse: " + response.code());
//                if (response.code() == 200){
//                    Log.d(TAG, "onResponse: " + response.body().toString());
//                    List<PicObject> picObjects = new ArrayList<>(response.body().getPicObjects()) ;
//                    for (PicObject picObject: picObjects )
//                        Log.d(TAG, "Responding... "+ picObject.getUser());
//                }else {
//                    try {
//                        Log.d(TAG+ "error", response.errorBody().string());
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PicSearchResponse> call, Throwable t) {
//                Log.d(TAG + "<FAIL>", call.toString() + t.getMessage());
//            }
//        });
        Call<PicResponse> responseCall = pictureApi.getPic(
                API_KEY, "4720181"
        );
        Log.d(TAG+" make call", responseCall.toString());

        responseCall.enqueue(new Callback<PicResponse>() {
            @Override
            public void onResponse(Call<PicResponse> call, Response<PicResponse> response) {
                Log.d(TAG, "ServerResponse: " + response.code());
                if (response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    List<PicObject> picObjects = new ArrayList<>(response.body().getPicObject()) ;
                    for (PicObject picObject: picObjects )
                        Log.d(TAG, "Responding... "+ picObject.getUser() + ": " + picObject.getTags());
                }else {
                    try {
                        Log.d(TAG+ "error", response.errorBody().string());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PicResponse> call, Throwable t) {
                Log.d(TAG + "<FAIL>", call.toString() + t.getMessage());
            }
        });

    }
}
