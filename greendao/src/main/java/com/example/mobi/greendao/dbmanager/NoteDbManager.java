package com.example.mobi.greendao.dbmanager;

import com.example.mobi.greendao.AbstractDatabaseManager;
import com.example.mobi.greendao.entity.Note;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by hzf on 2016/12/15 0015.
 */

public class NoteDbManager extends AbstractDatabaseManager<Note,Long> {

    @Override
    public AbstractDao<Note, Long> getAbstractDao() {
        return daoSession.getNoteDao();
    }
}
