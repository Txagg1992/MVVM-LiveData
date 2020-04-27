package com.curiousapps.nyc_schoolnycschools.requests.responses;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PicResponse {

    @SerializedName("totalHits")
    @Expose
    private int totalHits;

    @SerializedName("hits")
    @Expose
    private List<PicObject> picObject;

    public List<PicObject> getPicObject(){
        return picObject;
    }

    @Override
    public String toString() {
        return "PicResponse{" +
                "totalHits=" + totalHits +
                ", picObject=" + picObject +
                '}';
    }
}
