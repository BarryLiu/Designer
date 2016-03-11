package com.barry.designer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.barry.designer.fragment.DiscoverFragment;

public class DiscoverActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DiscoverFragment fragment = new DiscoverFragment();
        setContentFragment(fragment);

    }
}
