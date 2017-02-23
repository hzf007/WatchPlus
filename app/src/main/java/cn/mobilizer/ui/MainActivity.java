package cn.mobilizer.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.mobi.watchplus.R;
import com.zhy.http.okhttp.OkHttpUtils;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {
    public  static  final  String TAG = MainActivity.class.getSimpleName();
    private SwipeToLoadLayout swipeToLoadLayout;
    private ListView listView;
    private ArrayAdapter<String> mAdapter;
    private int mRefreshNum;
    private int mLoadMoreNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView = (ListView)findViewById(R.id.swipe_target);
        mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.ABOVE);
        loadData();
    }



    private void loadData() {

    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                mAdapter.add("Load More" + mLoadMoreNum);
                mLoadMoreNum++;
            }
        }, 3000);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                mAdapter.insert("Refresh" + mRefreshNum, 0);
                mRefreshNum++;
            }
        }, 3000);
    }
}
