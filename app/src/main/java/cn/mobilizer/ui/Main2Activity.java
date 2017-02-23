
package cn.mobilizer.ui;

import android.graphics.Canvas;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mobi.watchplus.R;

public class Main2Activity extends AppCompatActivity {
    SwipeRefreshLayout swipeLayout;
    ListView lvMessage;private ArrayAdapter<String> mAdapter;
    private int mRefreshNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.common_refresh_swipe_container);
        lvMessage = (ListView) findViewById(R.id.common_refresh_list_lv);
        mAdapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1);
        lvMessage.setAdapter(mAdapter);
        initSwipeRefreshLayout();

    }
    private void initSwipeRefreshLayout() {
        //swipeLayout.setColorSchemeResources(R.color.colorAccent);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        mAdapter.insert("Refresh" + mRefreshNum, 0);
                        mRefreshNum++;
                    }
                }, 3000);
            }
        });
    }
}
