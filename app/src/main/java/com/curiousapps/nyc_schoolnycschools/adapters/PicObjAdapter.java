package com.curiousapps.nyc_schoolnycschools.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.curiousapps.nyc_schoolnycschools.R;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.curiousapps.nyc_schoolnycschools.util.Constants.RESOURCE_DRAWABLE;

public class PicObjAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PIC_OBJECT_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<PicObject> mPicObjects;
    private OnPicObjectListener mOnPicObjectListener;

    public PicObjAdapter(OnPicObjectListener mOnPicObjectListener) {
        this.mOnPicObjectListener = mOnPicObjectListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case PIC_OBJECT_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pic_obj_list_item, parent, false);
                return new PicObjViewHolder(view, mOnPicObjectListener);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_loading_dots, parent, false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, mOnPicObjectListener);
            }
            default: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pic_obj_list_item, parent, false);
                return new PicObjViewHolder(view, mOnPicObjectListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == PIC_OBJECT_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mPicObjects.get(position).getWebformatURL())
                    .into(((PicObjViewHolder) holder).image);

            ((PicObjViewHolder) holder).user.setText("Photographer: " + mPicObjects.get(position).getUser());
            ((PicObjViewHolder) holder).tags.setText("Tags: " + mPicObjects.get(position).getTags());
            ((PicObjViewHolder) holder).likes.setText("Likes: " + String.valueOf(mPicObjects.get(position).getLikes()));
        } else if (itemViewType == CATEGORY_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background);

            Uri path = Uri.parse(RESOURCE_DRAWABLE + mPicObjects.get(position).getWebformatURL());
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder) holder).circleImage);

            ((CategoryViewHolder) holder).categoryTextView.setText(mPicObjects.get(position).getUser());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPicObjects.get(position).getLikes() == -1) {
            return CATEGORY_TYPE;
        } else if (mPicObjects.get(position).getUser().equals("Loading...")) {
            return LOADING_TYPE;
        } else if (position == mPicObjects.size() - 1
                && position != 0
                && !mPicObjects.get(position).getUser().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return PIC_OBJECT_TYPE;
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            PicObject picObject = new PicObject();
            picObject.setUser("Loading...");
            List<PicObject> loadingList = new ArrayList<>();
            loadingList.add(picObject);
            mPicObjects = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mPicObjects != null) {
            if (mPicObjects.size() > 0) {
                if (mPicObjects.get(mPicObjects.size() - 1).getUser().equals("Loading...")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void displaySearchCategories() {
        List<PicObject> categories = new ArrayList<>();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++) {
            PicObject picObject = new PicObject();
            picObject.setUser(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            picObject.setWebformatURL(Constants.DEFAULT_SEARCH_IMAGES[i]);
            picObject.setLikes(-1);
            categories.add(picObject);
        }
        mPicObjects = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPicObjects != null) {
            return mPicObjects.size();
        }
        return 0;
    }

    public void setPicObjects(List<PicObject> picObjects) {
        mPicObjects = picObjects;
        notifyDataSetChanged();
    }

    public PicObject getSelectedPicObject(int position){
        if (mPicObjects != null){
            if (mPicObjects.size() > 0){
                return mPicObjects.get(position);
            }
        }
        return null;
    }
}
