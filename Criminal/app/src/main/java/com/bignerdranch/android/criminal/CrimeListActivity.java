package com.bignerdranch.android.criminal;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminal.common.SingleFragmentActivity;

/**
 * Created by yoon on 2016. 10. 6..
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
