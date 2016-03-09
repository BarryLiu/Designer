package com.barry.designer;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.barry.designer.utils.BitmapUtil;

/**
 * 向导页面： 只有软件第一次用的时候会有向导页面，进入后会生成一个xml
 * 文件，如果没有表示没有登陆
 */
public class GuiderActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private static int[] imageIds = {
            R.drawable.intro_1,
            R.drawable.intro_2,
            R.drawable.intro_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider);

        viewPager = (ViewPager) findViewById(R.id.vp);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return SectionFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    //登录
    public void login(View view) {

        View loginView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        Button signBtn = (Button) loginView.findViewById(R.id.email_sign_in_button);
        signBtn.setText("登陆");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(loginView);
        builder.show();

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuiderActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    //注册
    public void sign(View view) {
        View loginView = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        Button signBtn = (Button) loginView.findViewById(R.id.email_sign_in_button);
        signBtn.setText("注册");
        TextInputLayout phoneLayout = (TextInputLayout) loginView.findViewById(R.id.phone_layout);
        phoneLayout.setVisibility(View.VISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(loginView);
        builder.show();
    }

    //创建一个Fragment的类
    public static class SectionFragment extends Fragment {

        public static SectionFragment newInstance(int position) {
            SectionFragment fragment = new SectionFragment();
            Bundle b = new Bundle();
            b.putInt("pos", position);
            //创建一个bundle用于传递
            fragment.setArguments(b);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //创建一个imageView
            View view = inflater.inflate(R.layout.frag_guider_vp_layout, null);
            //获取到传递过来的参数
            int position = getArguments().getInt("pos");

            //找到imageView
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guider);

            Bitmap oldBm = BitmapFactory.decodeResource(inflater.getContext().getResources(),imageIds[position]);
            Bitmap bm = BitmapUtil.scaleImage(oldBm,1280,800);
            imageView.setImageBitmap(bm);
           // imageView.setImageResource(imageIds[position]);

            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            return view;
        }
    }
}
