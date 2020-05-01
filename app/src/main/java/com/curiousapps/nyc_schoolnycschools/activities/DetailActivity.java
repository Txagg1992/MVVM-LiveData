package com.curiousapps.nyc_schoolnycschools.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.curiousapps.nyc_schoolnycschools.R;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.util.Testing;
import com.curiousapps.nyc_schoolnycschools.viewModels.DetailViewModel;

import java.util.List;

public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private AppCompatImageView mPicObjectImage;
    private TextView mPicObjectUser;
    private TextView mPicObjectLikes;
    private TextView mDetailTagContainer;
    private LinearLayout mContainer;
    private ScrollView mConstraintLayout;

    private DetailViewModel mDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mConstraintLayout = findViewById(R.id.parent);
        mPicObjectImage = findViewById(R.id.pic_detail_image);
        mPicObjectUser = findViewById(R.id.pic_detail_user);
        mPicObjectLikes = findViewById(R.id.pic_detail_likes);
        mDetailTagContainer = findViewById(R.id.detail_tags_container);
        mContainer = findViewById(R.id.error_container);

        mDetailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        showProgressBar(true);
        getIncomingIntentExtras();
        subscribeObservers();
    }

    private void getIncomingIntentExtras() {
        if (getIntent().hasExtra("picObject")) {
            PicObject picObject = getIntent().getParcelableExtra("picObject");
            Log.d(TAG, "getIncomingIntent: " + picObject.getId());
            Log.d(TAG, "getIncomingIntent: " + picObject.getPageURL());
            Log.d(TAG, "getIncomingIntent: " + picObject.getTags());
            mDetailViewModel.searchPicDetailApi(picObject.getId());
        }
    }

    private void subscribeObservers() {
        int position = 1;
        mDetailViewModel.getPicDetail().observe(this, new Observer<List<PicObject>>() {
            @Override
            public void onChanged(List<PicObject> picObjects) {
                if (picObjects != null) {
                    if (picObjects.get(position).getId().equals(mDetailViewModel.getDetailId())){
                        Testing.printDetailObject(picObjects, "Changed----------");
                        setDetailProperties(picObjects);
                        mDetailViewModel.setDidRetrievePicObj(true);
                    }
                }
            }
        });

        mDetailViewModel.isDetailRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mDetailViewModel.isDidRetrievePicObj()){
                    Log.d(TAG, "OnChangeTimeOut");
                    displayErrorScreen("Error retrieving data, check network connection.");
                }
            }
        });
    }

    private void displayErrorScreen(String errorMessage){
        mPicObjectUser.setText("Error retrieving this picture...");
        mPicObjectLikes.setText("");
        mDetailTagContainer.setText("");
        TextView textView = new TextView(this);
        if (!errorMessage.equals("")){
            textView.setText(errorMessage);
        }else {
            textView.setText("Error");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        mContainer.addView(textView);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_foreground)
                .into(mPicObjectImage);
        showParent();
        showProgressBar(false);
    }

    private void setDetailProperties(List<PicObject> picObject){
        int position = 1;
        if (picObject != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_foreground);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(picObject.get(position).getWebformatURL())
                    .into(mPicObjectImage);
            mPicObjectUser.setText(picObject.get(position).getUser());
            mDetailTagContainer.setText(picObject.get(position).getTags());
            mPicObjectLikes.setText(picObject.get(position).getLikes());

        }
        showParent();
        showProgressBar(false);
    }

    private void showParent(){
        mConstraintLayout.setVisibility(View.VISIBLE);
    }
}
