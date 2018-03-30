package com.geekbrains.menuexample;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;


public class MultiListActivity extends AppCompatActivity {

    List<String> elements;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Создаем массив элементов для списка
        elements = new ArrayList<String>();
        for(int i = 0; i < 5; i++) {
            elements.add("Element " + i);
        }

        // Связываемся с ListView
        ListView listView = (ListView) findViewById(R.id.list);

        // создаем адаптер
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_activated_1, elements);

        // устанавливаем адаптер списку
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                //обработчик выделения пунктов списка ActionMode
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // обработка нажатия на пункт ActionMode
                // в данном случае просто закрываем меню
                mode.finish();
                return false;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Устанавливаем для ActionMode меню
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // вызывается при закрытии ActionMode
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // вызывается при обновлении ActionMode
                // true если меню или ActionMode обновлено иначе false
                return false;
            }
        });
    }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addElement();
                return true;
            case R.id.menu_clear:
                clearList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Метод, который создает инстанс pop-up menu, надувает его и показывает.
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add:
                        addElement();
                        return true;
                    case R.id.menu_clear:
                        clearList();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    private void clearList() {
        elements.clear();
        adapter.notifyDataSetChanged();
    }

    private void addElement() {
        elements.add("New element");
        adapter.notifyDataSetChanged();
    }
}
