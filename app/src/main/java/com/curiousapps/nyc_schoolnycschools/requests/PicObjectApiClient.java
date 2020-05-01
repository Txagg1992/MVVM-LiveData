package com.curiousapps.nyc_schoolnycschools.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.curiousapps.nyc_schoolnycschools.AppExecutors;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.requests.responses.PicResponse;
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
    private MutableLiveData<List<PicObject>> mPicDetail;
    private RetrievePicDetailRunnable mRetrievePicDetailRunnable;
    private MutableLiveData<Boolean> mPicRequestTimeout = new MutableLiveData<>();


    public static PicObjectApiClient getInstance() {
        if (instance == null) {
            instance = new PicObjectApiClient();
        }
        return instance;
    }

    private PicObjectApiClient() {
        mPicObject = new MutableLiveData<>();
        mPicDetail = new MutableLiveData<>();
    }

    public LiveData<List<PicObject>> getPicObjects() {
        return mPicObject;
    }
    public LiveData<List<PicObject>> getPicDetail() {
        return mPicDetail;
    }

    public LiveData<Boolean> isDetailRequestTimedOut() {
        return mPicRequestTimeout;
    }

    public void searchPicObjectsApi(String query, String perPage, int pageNumber) {
        if (mRetrievePicObjectsRunnable != null) {
            mRetrievePicObjectsRunnable = null;
        }
        mRetrievePicObjectsRunnable = new RetrievePicObjectsRunnable(query, perPage, pageNumber);

        final Future handler = AppExecutors.getInstance().networkIo().submit(mRetrievePicObjectsRunnable);
        AppExecutors.getInstance().networkIo().schedule(new Runnable() {

            @Override
            public void run() {
                //Let user know that network timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
    public void searchPicDetailApi(String detailId) {
        if (mRetrievePicDetailRunnable != null) {
            mRetrievePicDetailRunnable = null;
        }
        mRetrievePicDetailRunnable = new RetrievePicDetailRunnable(detailId);

        final Future handler = AppExecutors.getInstance().networkIo().submit(mRetrievePicDetailRunnable);
        AppExecutors.getInstance().networkIo().schedule(new Runnable() {

            @Override
            public void run() {
                //Let user know that network timed out
                mPicRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePicObjectsRunnable implements Runnable {

        private String query;
        private String perPage;
        private int pageNumber;
        boolean cancelRequest;

        public RetrievePicObjectsRunnable(String query, String perPage, int pageNumber) {
            this.query = query;
            this.perPage = perPage;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getPicObjects(query, perPage, pageNumber).execute();
                Log.d(TAG, "Response in Client: " + response.code());
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<PicObject> list = new ArrayList<>(((PicSearchResponse) response.body()).getPicObjects());
                    if (pageNumber == 1) {
                        mPicObject.postValue(list);
                    } else {
                        List<PicObject> currentPicObject = mPicObject.getValue();
                        currentPicObject.addAll(list);
                        mPicObject.postValue(currentPicObject);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error in Client: " + error);
                    mPicObject.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mPicObject.postValue(null);
            }

        }

        private Call<PicSearchResponse> getPicObjects(String query, String perPage, int pageNumber) {
            Log.d("Client is calling...", query.toString() + ": Page number " + pageNumber);
            return ServiceGenerator.getPictureApi().searchPics(
                    Constants.API_KEY,
                    query,
                    perPage,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: cancelling the search request.");
            cancelRequest = true;
        }
    }

    private class RetrievePicDetailRunnable implements Runnable {

        private String detailId;
        boolean cancelRequest;

        public RetrievePicDetailRunnable(String detailId) {
            this.detailId = detailId;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getPicDetail(detailId).execute();
                Log.d(TAG, "DetailResponse in Client: " + response.code());
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<PicObject> list = new ArrayList<>(((PicResponse) response.body()).getPicObject());
                    List<PicObject> currentPicObject = mPicDetail.getValue();
                    currentPicObject.addAll(list);
                    mPicDetail.postValue(currentPicObject);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error in DetailClient: " + error);
                    mPicDetail.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mPicDetail.postValue(null);
            }

        }

        private Call<PicResponse> getPicDetail(String detailId) {
            Log.d("DetailClient call...", detailId.toString());
            return ServiceGenerator.getPictureApi().getPic(
                    Constants.API_KEY,
                    detailId
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: cancelling the search request.");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrievePicObjectsRunnable != null) {
            mRetrievePicObjectsRunnable.cancelRequest();
        }
        if (mRetrievePicDetailRunnable != null) {
            mRetrievePicDetailRunnable.cancelRequest();
        }
    }
}
