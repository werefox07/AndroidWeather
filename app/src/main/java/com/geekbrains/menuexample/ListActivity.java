package com.geekbrains.menuexample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<String> elements;
    ArrayAdapter<String> adapter;
    ListView listView;

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
        listView = (ListView) findViewById(R.id.list);

        // создаем адаптер
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, elements);

        // устанавливаем адаптер списку
        listView.setAdapter(adapter);

        // регестрируем контекстное меню на список.
        registerForContextMenu(listView);
    }

    // Переопределение метода создания меню. Этот callback вызывается всегда, но он пустой, здесь мы
    //переопределением говорим системе создать наше меню.
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Метод вызывается по нажатию на любой пункт меню. В качестве агрумента приходит item меню.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // обработка нажатий
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

    // Метод, который вызывается не всего один раз как было с option menu, а каждый раз перед тем,
    // как context-ное меню будет показано.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    // Метод вызывается по нажатию на любой пункт меню. В качестве агрумента приходит item меню.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editElement(info.position);
                return true;
            case R.id.menu_delete:
                deleteElement(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Метод очищает лист полностью.
    private void clearList() {
        elements.clear();
        adapter.notifyDataSetChanged();
    }
    // Метод добавляет элемент в список.
    private void addElement() {
        elements.add("New element");
        adapter.notifyDataSetChanged();
    }

    // Метод переписывает текст пункта меню на другой.
    private void editElement(int id) {
        elements.set(id, "Edited");
        adapter.notifyDataSetChanged();
    }

    // Метод удаляет пункт из меню.
    private void deleteElement(int id) {
        elements.remove(id);
        adapter.notifyDataSetChanged();
    }
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

}
