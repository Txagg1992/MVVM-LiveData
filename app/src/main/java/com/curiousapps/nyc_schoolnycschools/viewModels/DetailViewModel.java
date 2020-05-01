package com.curiousapps.nyc_schoolnycschools.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.repositories.PicObjectRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private PicObjectRepository mDetailRepository;
    private String mDetailId;
    private boolean mDidRetrievePicObj;

    public DetailViewModel() {
        mDetailRepository = PicObjectRepository.getInstance();
        mDidRetrievePicObj = false;
    }

    public LiveData<List<PicObject>> getPicDetail(){
        return mDetailRepository.getPicDetail();
    }

    public LiveData<Boolean> isDetailRequestTimedOut() {
        return mDetailRepository.isDetailRequestTimedOut();
    }

    public void searchPicDetailApi(String detailId){
        mDetailId = detailId;
        mDetailRepository.searchPicDetailApi(detailId);
    }

    public String getDetailId() {
        return mDetailId;
    }

    public boolean isDidRetrievePicObj() {
        return mDidRetrievePicObj;
    }

    public void setDidRetrievePicObj(boolean mDidRetrievePicObj) {
        this.mDidRetrievePicObj = mDidRetrievePicObj;
    }
}
