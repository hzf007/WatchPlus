package com.example.mobi.greendao;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mobi.greendao.dbmanager.GoodsDbManager;
import com.example.mobi.greendao.dbmanager.NoteBase;
import com.example.mobi.greendao.dbmanager.NoteDbManager;
import com.example.mobi.greendao.entity.Goods;
import com.example.mobi.greendao.entity.Note;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


import me.itangqi.greendao.NoteDao;

public class MainActivity extends ListActivity {
    private EditText editText;
    private Cursor cursor;
    public static final String TAG = "DaoExample";
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到text字段的行
        String textColumn = NoteDao.Properties.Text.columnName;
        //进行升序排列
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        //游标
        cursor  = getDb().query(getNoteDao().getTablename(),getNoteDao().getAllColumns(), null, null, null, null, orderBy);
        //两个参数
        String[] from = {textColumn, NoteDao.Properties.Content.columnName};
        //
        int[] to = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);
        editText = (EditText) findViewById(R.id.editTextNote);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    public NoteDao getNoteDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return ((BaseApplication)this.getApplicationContext()).getDaoSession().getNoteDao();
    }
    private SQLiteDatabase getDb() {
        // 通过 BaseApplication 类提供的 getDb() 获取具体 db
        //return ((BaseApplication) this.getApplicationContext()).getDb();
        return  ((BaseApplication)this.getApplicationContext()).getDb();
    }
    /**
     * Button 点击的监听事件
     *
     * @param view
     */
    public void onMyButtonClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                addNote();
                break;
            case R.id.buttonSearch:
                search();
                break;
            case R.id.buttonUpdate:
                updateNote();
                break;
            default:
                Log.d(TAG, "what has gone wrong ?");
                break;
        }
    }

    private void search() {
        // Query 类代表了一个可以被重复执行的查询
//        Query<Note> query = getNoteDao().queryBuilder()
//                .where(NoteDao.Properties.Text.eq("test1"))
//                .orderAsc(NoteDao.Properties.Date)
//                .build();
//        List<Note> list = query.list();
//        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
//        QueryBuilder.LOG_SQL = true;
//        QueryBuilder.LOG_VALUES = true;
        String noteText = editText.getText().toString();
        editText.setText("");
        if (noteText == null || noteText.equals("")) {
            ToastUtils.show(getApplicationContext(), "Please enter a note to query");
        } else {
            // Query 类代表了一个可以被重复执行的查询
            Query<Note> query = getNoteDao().queryBuilder()
                    .where(NoteDao.Properties.Text.eq(noteText))
                    .orderAsc(NoteDao.Properties.Date)
                    .build();
//                Query  query = GreenDaoManager.getInstance().getDaoSession().getNoteDao()
//                        .queryBuilder()
//                        .where(NoteDao.Properties.Text.eq(noteText))
//                        .orderAsc(NoteDao.Properties.Date)
//                        .build();
            // 查询结果以 List 返回
            List<Note> notes = query.list();
            ToastUtils.show(getApplicationContext(), "There have " + notes.size() + " records");
        }
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    private void addNote() {

        String noteText = editText.getText().toString();
        editText.setText("");
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String content = "Added on " + df.format(new Date());

        if (noteText == null || noteText.equals("")) {
            ToastUtils.show(getApplicationContext(), "Please enter a note to add");
        } else {
            // 插入操作，简单到只要你创建一个 Java 对象
            Note note = new Note(null, noteText, content, new Date());
            //官方的
            getNoteDao().insert(note);
            //第二套封装方案
            //new NoteBase(this).insertObject(note);
            //第一套封装方案
            //new NoteDbManager().insert(note);
            //new NoteDbManager().setDebug(true);
            //第三套封装方案
            //GreenDaoManager.getInstance2().getDaoSession().getNoteDao().insert(note);
            Log.d(TAG, "Inserted new note, ID: " + note.getId());
            cursor.requery();
        }
    }
    private  void  updateNote(){
        String noteText = editText.getText().toString();
        editText.setText("");
        if(!TextUtils.isEmpty(noteText)){
            Note note = getNoteDao().queryBuilder().where(
                    NoteDao.Properties.Text.eq(noteText)
            ).limit(1).orderAsc(NoteDao.Properties.Date).build().unique();
            if(note != null){
                note.setText("haha");
                getNoteDao().update(note);
                ToastUtils.show(this,"修改成功");
            }else {
                ToastUtils.show(this,"用户不存在");
            }
        }else {
            ToastUtils.show(getApplicationContext(), "Please enter a note to add");
        }
    }
    /**
     * ListView 的监听事件，用于删除一个 Item
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 删除操作，你可以通过「id」也可以一次性删除所有
        getNoteDao().deleteByKey(id);
//        getNoteDao().deleteAll();
        Log.d(TAG, "Deleted note, ID: " + id);
        cursor.requery();
    }
}
