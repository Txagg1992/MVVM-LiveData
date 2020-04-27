package com.curiousapps.nyc_schoolnycschools.requests.responses;

import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PicSearchResponse {

    @SerializedName("totalHits")
    @Expose
    private int totalHits;

    @SerializedName("hits")
    @Expose
    private List<PicObject> picObjects;

    public int getTotalHits() {
        return totalHits;
    }

    public List<PicObject> getPicObjects() {
        return picObjects;
    }

    @Override
    public String toString() {
        return "PicSearchResponse{" +
                "totalHits=" + totalHits +
                ", picObjects=" + picObjects +
                '}';
    }
}
