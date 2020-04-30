package com.curiousapps.nyc_schoolnycschools.requests;

import com.curiousapps.nyc_schoolnycschools.requests.responses.PicResponse;
import com.curiousapps.nyc_schoolnycschools.requests.responses.PicSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PictureApi {

    //Search
    @GET("api/")
    Call<PicSearchResponse> searchPics(
            @Query("key") String key,
            @Query("q") String query,
            @Query("per_page") String per_page,
            @Query("page") String page
    );

    //Get Picture Request
    @GET("api/")
    Call<PicResponse> getPic(
            @Query("key") String key,
            @Query("id") String id
    );
}
