package com.curiousapps.nyc_schoolnycschools.requests;

import com.curiousapps.nyc_schoolnycschools.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.curiousapps.nyc_schoolnycschools.util.Constants.BASE_URL;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = retrofitBuilder.build();

    private static PictureApi pictureApi = retrofit.create(PictureApi.class);


    public static PictureApi getPictureApi() {
        return pictureApi;
    }
}
