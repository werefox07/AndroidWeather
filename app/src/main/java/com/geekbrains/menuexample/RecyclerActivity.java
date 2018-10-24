package com.geekbrains.menuexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;


///////////////////////////////////////////////////////////////////////////
// Recycler Activity
///////////////////////////////////////////////////////////////////////////

public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton addView;
    RecyclerView recycleView;
    CustomElementsAdapter adapter;
    Toolbar toolbar;

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        initViews();
    }

    // Переопределение метода создания меню. Этот callback вызывается всегда, но он пустой, здесь мы
    //переопределением говорим системе создать наше меню.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    // Метод вызывается по нажатию на любой пункт меню. В качестве агрумента приходит item меню.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // обработка нажатий
        switch (item.getItemId()) {
            case R.id.menu_clear:
                adapter.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // init
    ///////////////////////////////////////////////////////////////////////////

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addView = findViewById(R.id.ar_fb_add);
        addView.setOnClickListener(this);

        recycleView = findViewById(R.id.listView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomElementsAdapter(getData());

        recycleView.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // On Click
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ar_fb_add: {
                adapter.addView("New");
                break;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Create data
    ///////////////////////////////////////////////////////////////////////////

    private List<String> getData(){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            result.add("Element " + i);
        }
        return result;
    }
}
