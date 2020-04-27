package com.curiousapps.nyc_schoolnycschools.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PicObject implements Parcelable {

    private String user;//publisher
    private String tags;//ingredients
    private String id; //recipe_id
    private String webformatURL;// image_url
    private String pageURL;
    private int likes; //social_rank

    public PicObject(String user, String tags, String id, String webformatURL, String pageURL, int likes) {
        this.user = user;
        this.tags = tags;
        this.id = id;
        this.webformatURL = webformatURL;
        this.pageURL = pageURL;
        this.likes = likes;
    }

    public PicObject() {
    }

    protected PicObject(Parcel in) {
        user = in.readString();
        tags = in.readString();
        id = in.readString();
        webformatURL = in.readString();
        pageURL = in.readString();
        likes = in.readInt();
    }

    public static final Creator<PicObject> CREATOR = new Creator<PicObject>() {
        @Override
        public PicObject createFromParcel(Parcel in) {
            return new PicObject(in);
        }

        @Override
        public PicObject[] newArray(int size) {
            return new PicObject[size];
        }
    };

    public String getUser() {
        return user;
    }

    public String getTags() {
        return tags;
    }

    public String getId() {
        return id;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getPageURL() {
        return pageURL;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "PicObject{" +
                "user='" + user + '\'' +
                ", tags='" + tags + '\'' +
                ", id='" + id + '\'' +
                ", webformatURL='" + webformatURL + '\'' +
                ", pageUrl='" + pageURL + '\'' +
                ", likes=" + likes +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(tags);
        dest.writeString(id);
        dest.writeString(webformatURL);
        dest.writeString(pageURL);
        dest.writeInt(likes);
    }
}
