package com.curiousapps.nyc_schoolnycschools.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.repositories.PicObjectRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private PicObjectRepository mDetailRepository;
    private String mDetailId;

    public DetailViewModel() {
        mDetailRepository = PicObjectRepository.getInstance();
    }

    public LiveData<List<PicObject>> getPicDetail(){
        return mDetailRepository.getPicDetail();
    }

    public void searchPicDetailApi(String detailId){
        mDetailId = detailId;
        mDetailRepository.searchPicDetailApi(detailId);
    }

    public String getDetailId() {
        return mDetailId;
    }
}
