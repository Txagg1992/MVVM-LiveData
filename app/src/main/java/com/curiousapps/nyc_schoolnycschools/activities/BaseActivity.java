package com.curiousapps.nyc_schoolnycschools.activities;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.curiousapps.nyc_schoolnycschools.R;

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;
    public TextView mTextView;
    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        mTextView = constraintLayout.findViewById(R.id.search_text_view);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
    }

    public void showSearchTextView(boolean isVisible){
        mTextView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    public void showProgressBar(boolean visibility) {
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

}
