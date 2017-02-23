package com.example.mobi.greendao;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by hzf on 2016/12/15 0015.
 */

public interface IDataBase<T,K>{

    boolean insert(T t);

    boolean delete(T t);

    boolean deleteByKey(K k);

    boolean deleteList(List<T> mList);

    boolean deleteByKeyInTx(K... keys);//采用事务操作，删除key[ ]中每个key所对应的实体，并返回一个空的Observable

    boolean deleteAll();

    boolean insertOrReplace(@NonNull T t);

    boolean update(T t);

    boolean updateInTx(T... t);

    boolean updateList(List<T> mList);

    T selectByPrimaryKey(K key);

    List<T> loadAll();

    boolean refresh(T t);

    /**
     * 清理缓存
     */
    void clearDaoSession();

    /**
     * Delete all tables and content from our database
     */
    boolean dropDatabase();

    /**
     * 事务
     */
    void runInTx(Runnable runnable);

    /**
     * 添加集合
     *
     * @param mList
     */
    boolean insertList(List<T> mList);

    /**
     * 添加或更新集合
     *
     * @param mList
     */
    boolean insertOrReplaceList(List<T> mList);

    /**
     * 自定义查询
     *
     * @return
     */
    QueryBuilder<T> getQueryBuilder();

    /**
     * @param where
     * @param selectionArg
     * @return
     */
    List<T> queryRaw(String where, String... selectionArg);
}
