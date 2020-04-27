package com.curiousapps.nyc_schoolnycschools.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.curiousapps.nyc_schoolnycschools.AppExecutors;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.requests.responses.PicSearchResponse;
import com.curiousapps.nyc_schoolnycschools.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.curiousapps.nyc_schoolnycschools.util.Constants.NETWORK_TIMEOUT;

public class PicObjectApiClient {

    private static final String TAG = "PicObjectApiClient";
    private MutableLiveData<List<PicObject>> mPicObject;
    private static PicObjectApiClient instance;
    private RetrievePicObjectsRunnable mRetrievePicObjectsRunnable;

    public static PicObjectApiClient getInstance() {
        if (instance == null) {
            instance = new PicObjectApiClient();
        }
        return instance;
    }

    private PicObjectApiClient() {
        mPicObject = new MutableLiveData<>();
    }

    public LiveData<List<PicObject>> getPicObjects() {
        return mPicObject;
    }

    public void searchPicObjectsApi(String query, int pageNumber) {
        if (mRetrievePicObjectsRunnable != null){
            mRetrievePicObjectsRunnable = null;
        }
        mRetrievePicObjectsRunnable = new RetrievePicObjectsRunnable(query, pageNumber);

        final Future handler = AppExecutors.getInstance().networkIo().submit(mRetrievePicObjectsRunnable);
        AppExecutors.getInstance().networkIo().schedule(new Runnable() {

            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePicObjectsRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrievePicObjectsRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getPicObjects(query, pageNumber).execute();
                Log.d(TAG, "Response in Client: " + response.code());
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200){
                    List<PicObject> list = new ArrayList<>(((PicSearchResponse)response.body()).getPicObjects());
                    if (pageNumber == 1){
                        mPicObject.postValue(list);
                    }else {
                        List<PicObject> currentPicObject = mPicObject.getValue();
                        currentPicObject.addAll(list);
                        mPicObject.postValue(currentPicObject);
                    }
                }else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error in Client: " + error);
                    mPicObject.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mPicObject.postValue(null);
            }

        }

        private Call<PicSearchResponse> getPicObjects(String query, int pageNumber) {
            Log.d("Client is calling...", query.toString() + ": " + pageNumber);
            return ServiceGenerator.getPictureApi().searchPics(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: cancelling the search request.");
            cancelRequest = true;
        }
    }
}
