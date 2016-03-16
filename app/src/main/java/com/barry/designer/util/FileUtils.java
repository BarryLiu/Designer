package com.barry.designer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.barry.designer.MyApplication;
import com.barry.designer.UserActivity;
import com.barry.designer.bean.UserBean;
import com.barry.designer.utils.BitmapUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Barry on 2016/3/13.
 */
public class FileUtils {

    private static final String USER_DAT="user.dat";

    //得到缓存的用户文件
    private static File getCacheUserFile(Context context){
        File dir = context.getCacheDir();
        return  new File(dir,USER_DAT);
    }
    public static File getCacheDir(Context context){
        return context.getCacheDir();
    }

    //保存登陆用户信息
    public static boolean saveUserDat(Context context,UserBean ub) {
        File userFile =getCacheUserFile(context);
        try {
            ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(userFile));

            oos.writeObject(ub);
            oos.flush();
            oos.close();
            oos = null;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 得到缓存的用户登陆的信息
     * @param context
     * @return
     */
    public static UserBean getDatUser(Context context){
        try {
            File file = getCacheUserFile(context);
            if (file.exists()){
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                UserBean ub = (UserBean) ois.readObject();
                return  ub;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除登陆的缓存信息
     * @param context
     */
    public static void delDatUser(Context context){
        File file = getCacheUserFile(context);
        if(file.exists())
            file.delete();
    }

    /**
     * 保存用户的头像
     * @param context
     * @param bitmap
     * @throws FileNotFoundException
     */
    public static void saveUserIcon(Context context, Bitmap bitmap) throws FileNotFoundException {
        File fileDir = FileUtils.getCacheDir(context);
        File file  = new File(fileDir,MyApplication.currUser.getName()+".png");
        bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
    }

    /**
     * 得到用户头像
     * @param context
     * @return
     */
    public static Bitmap getUserIcon(Context context){
        File fileDir = FileUtils.getCacheDir(context);
        File file  = new File(fileDir,MyApplication.currUser.getName()+".png");
        if(file.exists())
            return BitmapFactory.decodeFile(file.getPath());
        else
            return null;
    }
}
