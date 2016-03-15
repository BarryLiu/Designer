package com.barry.designer.util;

import android.content.Context;
import android.os.Environment;

import com.barry.designer.MyApplication;
import com.barry.designer.bean.UserBean;

import java.io.File;
import java.io.FileInputStream;
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
        File userFile = getCacheUserFile(context);
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

}
