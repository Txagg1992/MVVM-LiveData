package com.curiousapps.nyc_schoolnycschools.repositories;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.requests.PicObjectApiClient;

import java.util.List;

public class PicObjectRepository {

    private static PicObjectRepository instance;
    private PicObjectApiClient mPicObjectApiClient;
    private String mQuery;
    private int mPageNumber;

    public static PicObjectRepository getInstance(){
        if (instance == null){
            instance = new PicObjectRepository();
        }
        return instance;
    }

    private PicObjectRepository(){
        mPicObjectApiClient = PicObjectApiClient.getInstance();
    }

    public LiveData<List<PicObject>> getPicObjects(){
        return mPicObjectApiClient.getPicObjects();
    }
    public void searchPicObjectsApi(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mPicObjectApiClient.searchPicObjectsApi(query, pageNumber);
    }

    public void searchNextPage(){
        searchPicObjectsApi(mQuery, mPageNumber +1);//mPagenumber +1
    }

    public void cancelRequest(){
        mPicObjectApiClient.cancelRequest();
    }
}
