package com.curiousapps.nyc_schoolnycschools.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.curiousapps.nyc_schoolnycschools.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView circleImage;
    TextView categoryTextView;
    OnPicObjectListener listener;

    public CategoryViewHolder(@NonNull View itemView, OnPicObjectListener listener) {
        super(itemView);

        this.listener = listener;
        circleImage = itemView.findViewById(R.id.category_image);
        categoryTextView = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        listener.onCategoryClick(categoryTextView.getText().toString());
    }
}
