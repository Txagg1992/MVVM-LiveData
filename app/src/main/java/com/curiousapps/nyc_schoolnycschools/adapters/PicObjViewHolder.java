package com.curiousapps.nyc_schoolnycschools.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.curiousapps.nyc_schoolnycschools.R;

public class PicObjViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView user;
    TextView tags;
    TextView likes;
    ImageView image;
    OnPicObjectListener onPicObjectListener;

    public PicObjViewHolder(@NonNull View itemView, OnPicObjectListener onPicObjectListener) {
        super(itemView);

        this.onPicObjectListener = onPicObjectListener;

        user = itemView.findViewById(R.id.pic_obj_user);
        tags = itemView.findViewById(R.id.pic_obj_tags);
        likes = itemView.findViewById(R.id.pic_obj_likes);
        image = itemView.findViewById(R.id.pic_obj_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        onPicObjectListener.onPicObjectClick(getAdapterPosition());
    }
}
