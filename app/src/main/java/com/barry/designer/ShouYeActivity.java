package com.barry.designer;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.barry.designer.fragment.ShouYeFragment;

/**
 * Created by Barry on 2016/3/9.
 */
public class ShouYeActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ShouYeFragment fragment =new ShouYeFragment();

        setContentFragment(fragment);
    }
}
