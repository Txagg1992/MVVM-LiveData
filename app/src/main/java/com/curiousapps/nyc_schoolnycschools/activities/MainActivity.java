package com.curiousapps.nyc_schoolnycschools.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.curiousapps.nyc_schoolnycschools.R;
import com.curiousapps.nyc_schoolnycschools.adapters.OnPicObjectListener;
import com.curiousapps.nyc_schoolnycschools.adapters.PicObjAdapter;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.util.Testing;
import com.curiousapps.nyc_schoolnycschools.viewModels.MainListViewModel;

import java.util.List;

import static com.curiousapps.nyc_schoolnycschools.util.Constants.*;

public class MainActivity extends BaseActivity implements OnPicObjectListener {

    private MainListViewModel mMainListViewModel;
    private RecyclerView mRecyclerView;
    private PicObjAdapter mPicObjAdapter;
    private androidx.appcompat.widget.SearchView mSearchView;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showSearchTextView(true);
        mRecyclerView = findViewById(R.id.pic_obj_list);
        mSearchView = findViewById(R.id.search_view);
        mMainListViewModel = new ViewModelProvider(this).get(MainListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if (!mMainListViewModel.isViewingPictures()){
            //Display search Categories
            displaySearchCategories();
        }
        setSupportActionBar(findViewById(R.id.toolbar));
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

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)){
                    //search for pic objects in next page
                    mMainListViewModel.searchNextPage();
                }
            }
        });
    }

    private void subscribeObservers(){

        mMainListViewModel.getPicObjects().observe(this, new Observer<List<PicObject>>() {
            @Override
            public void onChanged(List<PicObject> picObjects) {
                if (picObjects != null){
                    if (mMainListViewModel.isViewingPictures()){
                        Testing.printPicObjects(picObjects, "ShortList...");
                        mMainListViewModel.setPerformingQuery(false);
                        mPicObjAdapter.setPicObjects(picObjects);
                    }
                }
            }
        });
    }
    private void searchPicObjectsApi(String query, String perPage, int pageNumber){
        mMainListViewModel.searchPicObjectsApi(query, perPage, pageNumber);
    }

    private void initSearchView(){

        mSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPicObjAdapter.displayLoading();
                mMainListViewModel.searchPicObjectsApi(query, NUMBER_PER_PAGE, 1);
                mSearchView.clearFocus();
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
//        Log.d(TAG, "Retrofit Start");
//        searchPicObjectsApi("australian shepherd puppy", 1);
//
//        PictureApi pictureApi = ServiceGenerator.getPictureApi();
//
//        Call<PicSearchResponse> responseCall = pictureApi.searchPics(
//                API_KEY, "australian shepherd", NUMBER_PER_PAGE, "1"
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
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("picObject", mPicObjAdapter.getSelectedPicObject(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        mPicObjAdapter.displayLoading();
        mMainListViewModel.searchPicObjectsApi(category, NUMBER_PER_PAGE, 1);
        mSearchView.clearFocus();
    }

    private void displaySearchCategories(){
        mMainListViewModel.setIsViewingPictures(false);
        mPicObjAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {

        if (mMainListViewModel.onBackPressed()){
            super.onBackPressed();
        }else displaySearchCategories();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_object_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_categories){
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }
}
