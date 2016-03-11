package com.barry.designer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.barry.designer.fragment.ShouYeFragment;
import com.barry.designer.utils.BPUtil;

/**
 * Created by Barry on 2016/3/9.
 */
public class ShouYeActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* NavigationView navigationView  = (NavigationView) findViewById(R.id.nav_view);
        ImageView ivHead  = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_head);
        ivHead.setImageResource(R.drawable.ic_eye_grey_24dp);*/

        ShouYeFragment fragment = new ShouYeFragment();
        setContentFragment(fragment);

    }
}
