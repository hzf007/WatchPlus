package com.example.mobi.greendao;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;

/**
 * Created by hzf on 2016/12/14 0014.
 * 单例模式适用场合
 * 需要频繁的进行创建和销毁的对象；
 * 创建对象时耗时过多或耗费资源过多，但又经常用到的对象；
 * 工具类对象；
 * 频繁访问数据库或文件的对象。
 */

public class GreenDaoManager {
   private  static  GreenDaoManager mInstance;
   private DaoMaster daoMaster;
   private DaoSession daoSession;
   private  GreenDaoManager(){
       DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(),Constants.DB_NAME,null);
      daoMaster = new DaoMaster(helper.getWritableDatabase());
      daoSession = daoMaster.newSession();
   }
   //懒汉式(线程不安全)[不可用]
   public  static  GreenDaoManager getInstance(){
       if (mInstance == null) {
           mInstance = new GreenDaoManager();
       }
       return  mInstance;
   }
   //懒汉式，同步方法（不推荐）
   public static synchronized  GreenDaoManager getInstance4(){
       if (mInstance == null) {
           mInstance = new GreenDaoManager();
       }
       return  mInstance;
   }
   //懒汉式，双重检查[推荐用]
   public  static  GreenDaoManager getInstance2(){
       if(mInstance == null){
           synchronized (GreenDaoManager.class){
               if(mInstance == null){
                   mInstance = new GreenDaoManager();
               }
           }
       }
       return  mInstance;
   }
   //静态内部类[推荐用]
   private static class GreenDaoManagerInstance {
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
   }
   public  static  GreenDaoManager  getInstance3(){
       return GreenDaoManagerInstance.INSTANCE;
   }

    /**
     * 判断数据库是否存在，如果不存在则创建
     * @return
     */
//   public DaoMaster getDaoMaster() {
//       if(daoMaster == null) {
//           helper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(),Constants.DB_NAME,null);
//           daoMaster = new DaoMaster(helper.getWritableDatabase());
//       }
//       return daoMaster;
//    }

//    /**
//     * 完成对数据库的增删查找
//     * @return
//     */
    public DaoSession getDaoSession() {
//        if(daoSession == null){
//           if(daoMaster == null){
//              daoMaster = getDaoMaster();
//           }
//           daoSession = daoMaster.newSession();
//        }
        return daoSession;
    }

    public  DaoSession getNewDaoSession(){
        daoSession = daoMaster.newSession();
        return daoSession;
    }
    /**
     * 设置debug模式开启或关闭，默认关闭
     * @param flag
     */
    public void setDebug(boolean flag){
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }
    /**
     * 关闭数据库
     */
    public void closeDataBase(){
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession(){
        if (null != daoSession){
            daoSession.clear();
            daoSession = null;
        }
    }

    public  void  closeHelper(){
//        if (helper!=null){
//            helper.close();
//            helper = null;
//        }
    }
}
