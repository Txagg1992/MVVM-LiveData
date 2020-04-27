package com.curiousapps.nyc_schoolnycschools.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.repositories.PicObjectRepository;

import java.util.List;

public class MainListViewModel extends ViewModel {
    private PicObjectRepository mPicObjectRepository;
    public MainListViewModel() {
        mPicObjectRepository = PicObjectRepository.getInstance();
    }

    public LiveData<List<PicObject>> getPicObjects(){
        return mPicObjectRepository.getPicObjects();
    }

    public void searchPicObjectsApi(String query, int pageNumber){
        mPicObjectRepository.searchPicObjectsApi(query, pageNumber);
    }
}
