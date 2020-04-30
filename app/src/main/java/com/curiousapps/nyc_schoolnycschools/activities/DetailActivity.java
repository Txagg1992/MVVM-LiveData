package com.curiousapps.nyc_schoolnycschools.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.curiousapps.nyc_schoolnycschools.R;
import com.curiousapps.nyc_schoolnycschools.models.PicObject;

public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private AppCompatImageView mPicObjectImage;
    private TextView mPicObjectUser;
    private ImageView mPicObjectLink;
    private LinearLayout mDetailTagContainer;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPicObjectImage = findViewById(R.id.pic_detail_image);
        mPicObjectUser = findViewById(R.id.pic_detail_user);
        mPicObjectLink = findViewById(R.id.pic_detail_link);
        mDetailTagContainer = findViewById(R.id.detail_tags_container);
        mScrollView = findViewById(R.id.parent);

        getIncomingIntentExtras();
    }

    private void getIncomingIntentExtras(){
        if (getIntent().hasExtra("picObject")){
            PicObject picObject = getIntent().getParcelableExtra("picObject");
            Log.d(TAG, "getIncomingIntent: " + picObject.getUser());
        }
    }
}
