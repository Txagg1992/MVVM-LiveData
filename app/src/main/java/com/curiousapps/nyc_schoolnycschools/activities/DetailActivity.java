package com.curiousapps.nyc_schoolnycschools.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.curiousapps.nyc_schoolnycschools.R;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;
import com.curiousapps.nyc_schoolnycschools.util.Testing;
import com.curiousapps.nyc_schoolnycschools.viewModels.DetailViewModel;

import java.util.List;

public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private AppCompatImageView mPicObjectImage;
    private TextView mPicObjectUser;
    private ImageView mPicObjectLink;
    private LinearLayout mDetailTagContainer;
    private ScrollView mScrollView;

    private DetailViewModel mDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPicObjectImage = findViewById(R.id.pic_detail_image);
        mPicObjectUser = findViewById(R.id.pic_detail_user);
        mPicObjectLink = findViewById(R.id.pic_detail_link);
        mDetailTagContainer = findViewById(R.id.detail_tags_container);
        mScrollView = findViewById(R.id.parent);

        mDetailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

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
        mDetailViewModel.getPicDetail().observe(this, new Observer<List<PicObject>>() {
            @Override
            public void onChanged(List<PicObject> picObjects) {
                if (picObjects != null) {
                Testing.printDetailObject(picObjects, "Changed----------");
                }
            }
        });
    }
}
