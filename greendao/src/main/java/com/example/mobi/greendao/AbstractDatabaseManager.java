package com.example.mobi.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;

/**
 * Created by hzf on 2016/12/15 0015.
 */

public abstract class AbstractDatabaseManager<T,K> implements IDataBase<T,K> {

    private static final String DEFAULT_DATABASE_NAME = Constants.DEFAULT_DB_NAME;

    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private static DaoMaster.DevOpenHelper mHelper;
    protected static DaoSession daoSession;

    /**
     * 初始化OpenHelper
     * @param context
     */
    public static void initOpenHelper(@NonNull Context context) {
        mHelper = getOpenHelper(context, DEFAULT_DATABASE_NAME);
        openWritableDb();
    }

    /**
     * 初始化OpenHelper     *
     * @param context
     * @param dataBaseName
     */
    public static void initOpenHelper(@NonNull Context context, @NonNull String dataBaseName) {
        mHelper = getOpenHelper(context, dataBaseName);
        openWritableDb();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    /**     * Query for readable DB     */
    protected static void openReadableDb() throws SQLiteException {
        daoSession = new DaoMaster(getReadableDatabase()).newSession();
    }

    /**     * Query for writable DB     */
    protected static void openWritableDb() throws SQLiteException {
        daoSession = new DaoMaster(getWritableDatabase()).newSession();
    }

    private static SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    private static SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }


    /**     * 在applicaiton中初始化DatabaseHelper     */
    private static DaoMaster.DevOpenHelper getOpenHelper(@NonNull Context context, @Nullable String dataBaseName) {
        closeDbConnections();//关闭数据库
        return new DaoMaster.DevOpenHelper(context, dataBaseName, null);//初始化数据库
    }

    /**
     * 只关闭helper就好,看源码就知道helper关闭的时候会关闭数据库
     */
    public static void closeDbConnections() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public boolean insert(T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().insert(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().delete(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K k) {
        try {
            if (TextUtils.isEmpty(k.toString()))
                return false;
            openWritableDb();
            getAbstractDao().deleteByKey(k);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<T> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().deleteInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTx(K... keys) {
        try {
            openWritableDb();
            getAbstractDao().deleteByKeyInTx(keys);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            openWritableDb();
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NonNull T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplace(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().update(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(T... t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().updateInTx(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(List<T> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().updateInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public T selectByPrimaryKey(K key) {
        try {
            openReadableDb();
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public List<T> loadAll() {
        openReadableDb();
        return getAbstractDao().loadAll();
    }

    @Override
    public boolean refresh(T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().refresh(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public boolean dropDatabase() {
        try {
            openWritableDb();
            // DaoMaster.dropAllTables(database, true); // drops all tables
            // mHelper.onCreate(database); // creates the tables
//          daoSession.deleteAll(BankCardBean.class); // clear all elements
            // from
            // a table
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            openWritableDb();
            daoSession.runInTx(runnable);
        } catch (SQLiteException e) {
        }
    }

    @Override
    public boolean insertList(List<T> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().insertInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplaceList(List<T> mList) {
        try {
            if (mList == null || mList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplaceInTx(mList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public QueryBuilder<T> getQueryBuilder() {
        openReadableDb();
        return getAbstractDao().queryBuilder();
    }

    @Override
    public List<T> queryRaw(String where, String... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRaw(where, selectionArg);
    }

    public Query<T> queryRawCreate(String where, Object... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    public Query<T> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
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
     * 获取Dao
     * @return
     */
    protected abstract AbstractDao<T, K> getAbstractDao();
}
