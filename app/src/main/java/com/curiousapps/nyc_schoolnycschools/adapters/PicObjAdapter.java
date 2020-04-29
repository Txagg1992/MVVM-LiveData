package com.curiousapps.nyc_schoolnycschools.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.curiousapps.nyc_schoolnycschools.R;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;

import java.util.ArrayList;
import java.util.List;

public class PicObjAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PIC_OBJECT_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<PicObject> mPicObjects;
    private OnPicObjectListener mOnPicObjectListener;

    public PicObjAdapter(OnPicObjectListener mOnPicObjectListener) {
        this.mOnPicObjectListener = mOnPicObjectListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case PIC_OBJECT_TYPE:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pic_obj_list_item, parent, false);
                return new PicObjViewHolder(view, mOnPicObjectListener);
            }
            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_loading_dots, parent, false);
                return new LoadingViewHolder(view);
            }
            default:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pic_obj_list_item, parent, false);
                return new PicObjViewHolder(view, mOnPicObjectListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == PIC_OBJECT_TYPE){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mPicObjects.get(position).getWebformatURL())
                    .into(((PicObjViewHolder)holder).image);

            ((PicObjViewHolder)holder).user.setText("Photographer: " + mPicObjects.get(position).getUser());
            ((PicObjViewHolder)holder).tags.setText("Tags: " + mPicObjects.get(position).getTags());
            ((PicObjViewHolder)holder).likes.setText("Likes: " +  String.valueOf(mPicObjects.get(position).getLikes()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPicObjects.get(position).getUser().equals("Loading...")){
            return LOADING_TYPE;
        }else {
            return PIC_OBJECT_TYPE;
        }
    }

    public void displayLoading(){
        if (!isLoading()){
            PicObject picObject = new PicObject();
            picObject.setUser("Loading...");
            List<PicObject> loadingList = new ArrayList<>();
            loadingList.add(picObject);
            mPicObjects = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if (mPicObjects != null){
            if (mPicObjects.size() > 0){
                if (mPicObjects.get(mPicObjects.size() - 1).getUser().equals("Loading...")){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (mPicObjects != null){
            return mPicObjects.size();
        }
        return 0;
    }

    public void setPicObjects(List<PicObject> picObjects){
        mPicObjects = picObjects;
        notifyDataSetChanged();
    }
}
