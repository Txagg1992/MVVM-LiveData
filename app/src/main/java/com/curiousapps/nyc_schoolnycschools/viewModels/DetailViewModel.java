package com.curiousapps.nyc_schoolnycschools.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.repositories.PicObjectRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private PicObjectRepository mDetailRepository;

    public DetailViewModel() {
        mDetailRepository = PicObjectRepository.getInstance();
    }

    public LiveData<List<PicObject>> getPicDetail(){
        return mDetailRepository.getPicDetail();
    }

    public void searchPicDetailApi(String detailId){
        mDetailRepository.searchPicDetailApi(detailId);
    }
}
