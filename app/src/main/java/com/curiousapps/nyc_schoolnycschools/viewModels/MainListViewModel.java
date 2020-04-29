package com.curiousapps.nyc_schoolnycschools.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.repositories.PicObjectRepository;

import java.util.List;

public class MainListViewModel extends ViewModel {
    private PicObjectRepository mPicObjectRepository;
    private boolean mIsViewingPictures;
    private boolean mIsPerformingQuery;

    public MainListViewModel() {
        mPicObjectRepository = PicObjectRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<List<PicObject>> getPicObjects(){
        return mPicObjectRepository.getPicObjects();
    }

    public void searchPicObjectsApi(String query, int pageNumber){
        mIsViewingPictures = true;
        mIsPerformingQuery = true;
        mPicObjectRepository.searchPicObjectsApi(query, pageNumber);
    }

    public boolean isViewingPictures(){
        return mIsViewingPictures;
    }

    public void setIsViewingPictures(boolean isViewingPictures){
        mIsViewingPictures = isViewingPictures;
    }

    public boolean isPerformingQuery(){
        return mIsPerformingQuery;
    }

    public void setPerformingQuery(boolean isPerformingQuery){
        mIsPerformingQuery = isPerformingQuery;
    }

    public boolean onBackPressed(){
        if (mIsPerformingQuery){
           //cancel the query
           mPicObjectRepository.cancelRequest();
           mIsPerformingQuery = false;
        }
        if (mIsViewingPictures){
            mIsViewingPictures = false;
            return false;
        }
        return true;
    }
}
