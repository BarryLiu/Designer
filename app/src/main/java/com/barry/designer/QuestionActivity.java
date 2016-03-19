package com.barry.designer;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.barry.designer.bean.QuestionBean;
import com.barry.designer.bean.ResponseBean;
import com.barry.designer.http.GKCallBack;
import com.barry.designer.http.GKRequestParams;
import com.barry.designer.http.HttpConfig;
import com.barry.designer.http.HttpUtils;
import com.barry.designer.utils.BitmapUtil;
import com.barry.designer.utils.DialogUtils;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class QuestionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText etQuestion;
    private EditText etQuestionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        etQuestion = (EditText) findViewById(R.id.et_question);
        etQuestionDetail = (EditText) findViewById(R.id.et_question_details);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_question, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_publish://发送
                QuestionBean qb = new QuestionBean();
                qb.setName(etQuestion.getText().toString());
                qb.setDetail(etQuestionDetail.getText().toString());
                DialogUtils.showLog("点击发送");
                DialogUtils.showTips(QuestionActivity.this,"点击发送");

                HttpUtils.makeQuestion(qb,new GKCallBack(){

                    @Override
                    public void onSuccess(String result) {

                        DialogUtils.showLog(result);


                       ResponseBean rb =  HttpUtils.paserResponse(result);
                        DialogUtils.showLog(rb.getJson_data());
                        DialogUtils.showTips(QuestionActivity.this, rb.getJson_reason());
                    }


                    @Override
                    public void onError(String result) {
                        DialogUtils.showLog("提问成功");
                        DialogUtils.showTips(QuestionActivity.this,"提问成功");
                    }
                });
                break;
            case R.id.action_image://选择图片
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        switch (requestCode){
            case 1:
                Bitmap originalBitmap = null;
                try {
                    //加载图片
                    // BitmapFactory.decodeFileDescriptor()
                    //originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(data.getData()));
                    FileDescriptor fd = resolver.openFileDescriptor(data.getData(), "rw").getFileDescriptor();
                    originalBitmap = BitmapUtil.getCutBitmap(fd, 300, 200);

                    Drawable drawable = new BitmapDrawable(getResources(),originalBitmap);
                    //Drawable d = getResources().getDrawable(R.drawable.ic_eye_grey_24dp);

                    Intent intent =new Intent();


                    drawable.setBounds(0, 0, originalBitmap.getWidth(),
                            originalBitmap.getHeight());
                    ImageSpan imageSpan = new ImageSpan(drawable);

                    //SpannableString spannableString = new SpannableString("<img src=>"+imageurl);
                    //将图片切换成String类型
                    SpannableString spannableString = new SpannableString(
                            "<img src=\""+BitmapUtil.getBitmapBase64(originalBitmap)+"\" />"
                    );
                    //spannableString.setSpan(new BackgroundColorSpan(Color.GREEN),0,spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                    spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    etQuestionDetail.append("\n");
                    etQuestionDetail.append(spannableString);
                    etQuestionDetail.append("\n");

                    // et_question.setText(et_detail.getText().toString());
                    // Toast.makeText(QuestionActivity.this,et_detail.getText().toString(),Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

}
