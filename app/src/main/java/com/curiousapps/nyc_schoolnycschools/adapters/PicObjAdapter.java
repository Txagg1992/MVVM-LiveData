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

import java.util.List;

public class PicObjAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PicObject> mPicObjects;
    private OnPicObjectListener mOnPicObjectListener;

    public PicObjAdapter(OnPicObjectListener mOnPicObjectListener) {
        this.mOnPicObjectListener = mOnPicObjectListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pic_obj_list_item, parent, false);
        return new PicObjViewHolder(view, mOnPicObjectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
