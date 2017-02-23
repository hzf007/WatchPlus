package com.example.mobi.greendao.dbmanager;

import android.content.Context;

import com.example.mobi.greendao.BaseDao;
import com.example.mobi.greendao.entity.Note;

/**
 * Created by hzf on 2016/12/15 0015.
 */

public class NoteBase extends BaseDao<Note> {

    public NoteBase(Context context) {
        super(context);
    }

    @Override
    public boolean insertObject(Note object) {
        return super.insertObject(object);
    }
}
