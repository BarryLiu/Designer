package com.barry.designer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.barry.designer.http.GKCallBack;
import com.barry.designer.http.HttpUtils;
import com.barry.designer.util.FileUtils;
import com.barry.designer.utils.AbBase64;
import com.barry.designer.utils.DialogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class UserActivity extends AppCompatActivity {

    private ImageView ivHead;
    private Button btnHead;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ivHead = (ImageView) findViewById(R.id.iv_head);
        ivHead.setDrawingCacheEnabled(true);
        btnHead = (Button) findViewById(R.id.btn_update);
        etName = (EditText) findViewById(R.id.email);


        Bitmap userIcon = FileUtils.getUserIcon(UserActivity.this);
        if (userIcon != null) {
            ivHead.setImageBitmap(userIcon);
        } else {
            ivHead.setImageResource(R.mipmap.ic_launcher);
        }

        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);

                builder.setItems(new String[]{"相册", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //隐式调用打开相册

                                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(albumIntent, 1);
                                break;
                            case 1:
                                /**
                                 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
                                 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
                                 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
                                 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
                                 */
                                Intent intent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                //下面这句指定调用相机拍照后的照片存储的路径
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                        .fromFile(new File(Environment
                                                .getExternalStorageDirectory(),
                                                "xiaoma.jpg")));
                                startActivityForResult(intent, 2);

                                break;
                        }
                    }
                });
                builder.show();
            }
        });


        btnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bitmap bitmap = ivHead.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                String imageStr = AbBase64.encode(baos.toByteArray());

                MyApplication.currUser.setImageData(imageStr);
                //发送请求
                HttpUtils.updater(MyApplication.currUser, new GKCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        DialogUtils.showTips(UserActivity.this, "修改成功");
                        //成功后保存到本地
                        try {
                            FileUtils.saveUserDat(UserActivity.this, MyApplication.currUser);
                            FileUtils.saveUserIcon(UserActivity.this, bitmap);

                            DialogUtils.showLog("保存文件成功");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            DialogUtils.showLog("保存文件失败");
                        }
                    }

                    @Override
                    public void onError(String result) {

                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                startPhotoZoom(data.getData());
                break;
            case 2:
                File temp = new File(FileUtils.getCacheDir(UserActivity.this)
                        + "/xiaoma.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case 3:

                Bundle extras = data.getExtras();
                if (data != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Drawable drawable = new BitmapDrawable(getResources(), photo);
                    ivHead.setImageDrawable(drawable);
                }
                break;
        }

    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
		 * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
		 * 制做的了...吼吼
		 */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "false");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

}
