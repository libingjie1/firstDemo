package cn.yhsh.recyclerviewadd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 轻飞扬
 * 2019年4月8日16:57:55
 * 点击加号添加新模块的功能
 */
public class MainActivity extends Activity {

    private RecyclerView rvAddNewView;
    private List<String> addViewed = new ArrayList<>(16);
    int i = 0;
    private AddDataAdapter addDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvAddNewView = findViewById(R.id.rv_add_new_view);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAddNewView.setLayoutManager(linearLayoutManager);
        addDataAdapter = new AddDataAdapter(addViewed);
        rvAddNewView.addItemDecoration(new RecyclerItemDecoration(20,4));
        rvAddNewView.setAdapter(addDataAdapter);
        addDataAdapter.setAddDataListener(new AddDataAdapter.addDataListener() {
            @Override
            public void onAddDataListener(int position) {
                i++;
                addViewed.add("【下一页5】" + i);
                addDataAdapter.notifyDataSetChanged();
            }
        });
        addDataAdapter.setLongClickListenerRemove(new AddDataAdapter.longClickListenerRemove() {
            @Override
            public void setLongClickListener(View view) {
                addViewed.remove(rvAddNewView.getChildLayoutPosition(view));
                addDataAdapter.notifyDataSetChanged();
            }
        });
    }
}
