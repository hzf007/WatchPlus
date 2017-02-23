package com.example.mobi.greendao.dbmanager;

import android.content.Context;

import com.example.mobi.greendao.BaseDao;
import com.example.mobi.greendao.entity.Goods;

/**
 * Created by hzf on 2016/12/15 0015.
 */

public class GoodsDbManager extends BaseDao<Goods> {

    public GoodsDbManager(Context context) {
        super(context);
    }  /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     * @param id
     * @return
     */
    private Goods loadById(int  id){
        return daoSession.getGoodsDao().load(id);
    }

    @Override
    public Goods QueryById(long id, Class object) {
        return super.QueryById(id, object);
    }

}
