package com.barry.designer;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barry.designer.bean.QuestionBean;
import com.barry.designer.http.GKCallBack;
import com.barry.designer.http.HttpUtils;
import com.barry.designer.utils.BitmapUtil;
import com.barry.designer.utils.DialogUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionDetailActivity extends AppCompatActivity {

    public static InputStream currIs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        QuestionBean qb = (QuestionBean) getIntent().getSerializableExtra("qb");

        DialogUtils.showLog(qb.toString());
        initView(qb);
    }

    private void initView(QuestionBean qb) {
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        final TextView tvDetail = (TextView) findViewById(R.id.tv_detail);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        final ImageView ivQuesPic = (ImageView) findViewById(R.id.iv_ques_pic);

        tvName.setText(qb.getName());
        tvDetail.setText(qb.getDetail());
        tvTime.setText(qb.getTime());


        String fileNames[] = getImgs(qb.getDetail());
        DialogUtils.showLog("图片的数量+" + fileNames.length);
        if (fileNames != null) {
            for (int i = 0; i < fileNames.length; i++) {

                HttpUtils.getPictrueForPic(fileNames[i], new GKCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        DialogUtils.showLog("的图片成功+" + result);

                        Bitmap originalBitmap = null;

                        ivQuesPic.setImageResource(R.drawable.ic_eye_grey_24dp);

                        originalBitmap = BitmapFactory.decodeStream(currIs);


                        ImageSpan imageSpan = new ImageSpan(QuestionDetailActivity.this,originalBitmap);
                        //SpannableString spannableString = new SpannableString("<img src=>"+imageurl);
                        //将图片切换成String类型
                        SpannableString spannableString = new SpannableString(
                                "<img src=\"" + BitmapUtil.getBitmapBase64(originalBitmap) + "\" />"
                        );


                        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                        Message msg = handler.obtainMessage();
                        Object objs[] = new Object[2];
                        objs[0] = tvDetail;
                        objs[1] = spannableString;

                        msg.obj = objs;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(String result) {
                    }
                });
            }


        }


    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object objs[] = (Object[]) msg.obj;

            TextView tvDetail = (TextView) objs[0];
            SpannableString spannableString = (SpannableString) objs[1];

            tvDetail.append("\n");
            tvDetail.append(spannableString);
            tvDetail.append("\n");


        }
    };
    /**
     * 返回首页
     *
     * @param view
     */
    public void backHome(View view) {
        finish();
    }

    // 从<img>中解析出src中的文字d
    public static String[] getImgs(String content) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String str = "";
        String[] images = null;
        // String regEx_img ="<img.*src\s*=\s*(.*?)[^>]*?>";
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";

        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)")
                    .matcher(img);
            // Matcher m =
            // Pattern.compile("src\s*=\s*"?(.*?)("|>|\s+)").matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);

                if ("".equals(str)) {

                    str = tempSelected;

                } else {

                    String temp = tempSelected;

                    str = str + "," + temp;
                }
            }
        }
        if (!"".equals(str)) {
            images = str.split(",");
        }
        return images;
    }

}
