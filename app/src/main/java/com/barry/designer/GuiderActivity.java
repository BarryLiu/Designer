package com.barry.designer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barry.designer.util.FileUtils;
import com.barry.designer.utils.BPUtil;
import com.barry.designer.utils.BitmapUtil;
import com.barry.designer.utils.DialogUtils;

import org.w3c.dom.Text;

import java.io.File;

import github.chenupt.springindicator.SpringIndicator;
import me.relex.circleindicator.CircleIndicator;

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
    protected void onDestroy() {
        super.onDestroy();

        if(bitmaps[0]==null)
            return;

        bitmaps[0].recycle();
        bitmaps[1].recycle();
        bitmaps[2].recycle();
        bitmaps[0] = null;
        bitmaps[1] = null;
        bitmaps[2] = null;

        System.gc();
    }

    static Bitmap[] bitmaps = new Bitmap[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider);

        MyApplication.currUser = FileUtils.getDatUser(this);

        if(MyApplication.currUser !=null){//有登录缓存
            Intent intent = new Intent(this,ShouYeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

       /* bitmaps[0] = BitmapUtil.getCutBitmap(GuiderActivity.this, imageIds[0], width, height);
        bitmaps[1] = BitmapUtil.getCutBitmap(GuiderActivity.this, imageIds[1], width, height);
        bitmaps[2] = BitmapUtil.getCutBitmap(GuiderActivity.this, imageIds[2], width, height);*/

        bitmaps[0] = decoeSampleBitmap(getResources(), imageIds[0]);
        bitmaps[1] = decoeSampleBitmap(getResources(), imageIds[1]);
        bitmaps[2] = decoeSampleBitmap(getResources(),imageIds[2]);


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

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.ci_guider);
        indicator.setViewPager(viewPager);

        /*SpringIndicator indicator2 = (SpringIndicator) findViewById(R.id.indicator);
        indicator2.setViewPager(viewPager);
*/

        viewPager.setPageTransformer(true,new ViewPageTrans());
    }



    public Bitmap decoeSampleBitmap(Resources resources,int id){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,id,options);

        int height = options.outHeight;
        int width = options.outWidth;

        String imageType = options.outMimeType;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int windWidth = metrics.widthPixels;
        int windHeight= metrics.heightPixels;

        options.inSampleSize = 1;
        if(height >windHeight || width >windWidth){
            int heightRation = Math.round((float)height/(float)windWidth);
            int widthRation = Math.round((float)width/(float)windHeight);
            options.inSampleSize = heightRation <widthRation ? heightRation : widthRation;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources,id,options);
    }


    //登录
    public void login(View view) {

        Intent intent= new Intent(this,LoginActivity.class);
        startActivityForResult(intent,100);
    }

    //注册
    public void sign(View view) {

        Intent intent = new Intent(this,ResigerActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==100 && resultCode == 101){
            finish();
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //创建一个imageView
            View view = inflater.inflate(R.layout.frag_guider_vp_layout, null);
            //获取到传递过来的参数
            int position = getArguments().getInt("pos");

            //找到imageView
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guider);


            Bitmap oldBm = BitmapFactory.decodeResource(inflater.getContext().getResources(), imageIds[position]);
            // Bitmap bm = BitmapUtil.scaleImage(oldBm, 1280, 800);
            //Bitmap bm  = BitmapUtil.getCutBitmap(oldBm,480 ,800);
            imageView.setImageBitmap(bitmaps[position]);
            // imageView.setImageResource(imageIds[position]);

            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            //找到imageView
            TextView tvGrider = (TextView) view.findViewById(R.id.tv_guider_text);
            switch (position){
                case 0:
                    tvGrider.setText("在这里找到朋友");
                    break;
                case 1:
                    tvGrider.setText("他是技术宅");
                    break;
                case 2:
                    tvGrider.setText("他是老总");
                    break;
            }

            Animation animation =new TranslateAnimation(0,0,0,-50);
            animation.setDuration(1000);
            animation.setFillAfter(true);
            float f = (float) 0.2;
            float t = (float) 0.9;
            AlphaAnimation am = new AlphaAnimation(f,t);
            am.setFillAfter(true);
            am.setDuration(1000);

          //  tvGrider.setAnimation(am);
            return view;
        }
    }
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText phoneNumber;

    private View mProgressView;
    private View mLoginFormView;


    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public static class ViewPageTrans implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View page, float position) {
            TextView tv = (TextView) page.findViewById(R.id.tv_guider_text);
            TextView tv1 = (TextView) page.findViewById(R.id.tv_guider_text);



            DialogUtils.showLog(position+",");
            int count = (int) (60 * Math.abs(position));
            if (position < 0) {
                tv.setAlpha(1 - Math.abs(position) * 2);
                tv.setPadding(count, tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());
                tv1.setAlpha(1 - Math.abs(position) * 2);
                tv1.setPadding(count, tv1.getPaddingTop(), tv1.getPaddingRight(), tv1.getPaddingBottom());
            }
            if (position > 0) {
                tv.setAlpha(1 - Math.abs(Math.abs(position)));
                tv.setPadding(count, tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());
                tv1.setAlpha(1 - Math.abs(Math.abs(position)));
                tv1.setPadding(count, tv1.getPaddingTop(), tv1.getPaddingRight(), tv1.getPaddingBottom());
            }

        }
    }
}
