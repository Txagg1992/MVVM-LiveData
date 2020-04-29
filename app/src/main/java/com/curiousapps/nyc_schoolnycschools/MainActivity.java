package com.curiousapps.nyc_schoolnycschools;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.curiousapps.nyc_schoolnycschools.adapters.OnPicObjectListener;
import com.curiousapps.nyc_schoolnycschools.adapters.PicObjAdapter;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.util.Testing;
import com.curiousapps.nyc_schoolnycschools.viewModels.MainListViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements OnPicObjectListener {

    private MainListViewModel mMainListViewModel;
    private RecyclerView mRecyclerView;
    private PicObjAdapter mPicObjAdapter;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showSearchTextView(true);
        mRecyclerView = findViewById(R.id.pic_obj_list);
        mMainListViewModel = new ViewModelProvider(this).get(MainListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if (!mMainListViewModel.isViewingPictures()){
            //Display search Categories
            displaySearchCategories();
        }
        //testRetrofitRequests();
//        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "Button Clicked");
//
//                testRetrofitRequests();
//                if (mProgressBar.getVisibility() == View.VISIBLE){
//                    showProgressBar(false);
//                }else {
//                    showProgressBar(true);
//                }
//            }
//        });
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPicObjAdapter = new PicObjAdapter(this);
        mRecyclerView.setAdapter(mPicObjAdapter);
    }

    private void subscribeObservers(){

        mMainListViewModel.getPicObjects().observe(this, new Observer<List<PicObject>>() {
            @Override
            public void onChanged(List<PicObject> picObjects) {
                if (picObjects != null){
                    Testing.printPicObjects(picObjects, "ShortList...");
                    mPicObjAdapter.setPicObjects(picObjects);
                }
            }
        });
    }
    private void searchPicObjectsApi(String query, int pageNumber){
    }

    private void initSearchView(){
        final androidx.appcompat.widget.SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPicObjAdapter.displayLoading();
                mMainListViewModel.searchPicObjectsApi(query, 1);
                //showSearchTextView(false);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void testRetrofitRequests(){
        Log.d(TAG, "Retrofit Start");
        searchPicObjectsApi("australian shepherd puppy", 1);

//        PictureApi pictureApi = ServiceGenerator.getPictureApi();
//
////        Call<PicSearchResponse> responseCall = pictureApi.searchPics(
////                API_KEY, "australian shepherd", "1"
////        );
////        Log.d(TAG+" make call", responseCall.toString());
////
////        responseCall.enqueue(new Callback<PicSearchResponse>() {
////            @Override
////            public void onResponse(Call<PicSearchResponse> call, Response<PicSearchResponse> response) {
////                Log.d(TAG, "ServerResponse: " + response.code());
////                if (response.code() == 200){
////                    Log.d(TAG, "onResponse: " + response.body().toString());
////                    List<PicObject> picObjects = new ArrayList<>(response.body().getPicObjects()) ;
////                    for (PicObject picObject: picObjects )
////                        Log.d(TAG, "Responding... "+ picObject.getUser());
////                }else {
////                    try {
////                        Log.d(TAG+ "error", response.errorBody().string());
////                    } catch (IOException ex) {
////                        ex.printStackTrace();
////                    }
////                }
////            }
////
////            @Override
////            public void onFailure(Call<PicSearchResponse> call, Throwable t) {
////                Log.d(TAG + "<FAIL>", call.toString() + t.getMessage());
////            }
////        });
//        Call<PicResponse> responseCall = pictureApi.getPic(
//                API_KEY, "4720181"
//        );
//        Log.d(TAG+" make call", responseCall.toString());
//
//        responseCall.enqueue(new Callback<PicResponse>() {
//            @Override
//            public void onResponse(Call<PicResponse> call, Response<PicResponse> response) {
//                Log.d(TAG, "ServerResponse: " + response.code());
//                if (response.code() == 200){
//                    Log.d(TAG, "onResponse: " + response.body().toString());
//                    List<PicObject> picObjects = new ArrayList<>(response.body().getPicObject()) ;
//                    for (PicObject picObject: picObjects )
//                        Log.d(TAG, "Responding... "+ picObject.getUser() + ": " + picObject.getTags());
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
//            public void onFailure(Call<PicResponse> call, Throwable t) {
//                Log.d(TAG + "<FAIL>", call.toString() + t.getMessage());
//            }
//        });

    }

    @Override
    public void onPicObjectClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mPicObjAdapter.displayLoading();
        mMainListViewModel.searchPicObjectsApi(category, 1);
    }

    private void displaySearchCategories(){
        mMainListViewModel.setIsViewingPictures(false);
        mPicObjAdapter.displaySearchCategories();
    }
}
