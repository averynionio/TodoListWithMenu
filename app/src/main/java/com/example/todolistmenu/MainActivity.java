package com.example.todolistmenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.todolistmenu.adapter.MyAdapter;
import com.example.todolistmenu.bean.RecordBean;
import com.example.todolistmenu.database.MyOpenHelper;
import com.example.todolistmenu.utils.MyConstant;


public class MainActivity extends AppCompatActivity {

    static final private int ADD_NEW_TODO = Menu.FIRST;
    static final private int CANCEL_ADD_TODO = Menu.FIRST + 1;

    private ListView myListView;
    private MyOpenHelper myOpenHelper;
    private List<RecordBean> recordBeanList;
    private MyAdapter myAdapter;
    private EditText myEditText;
    private boolean addingNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myListView = (ListView) findViewById(R.id.myListView);

        myOpenHelper = new MyOpenHelper(this);
        showData();

        myEditText = (EditText)findViewById(R.id.myEditText);

        myEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
                        String todoContent = myEditText.getText().toString();
                        String time = MyConstant.getTime();

                        if (todoContent.length() >0) {
                            myOpenHelper.insertRecord(todoContent, time);
                        } else {
                            Toast.makeText(MainActivity.this,"Empty input!", Toast.LENGTH_SHORT).show();
                        }
                        myEditText.setText("");
                        cancelAdd();
                        showData();
                        return true;
                    }
                return false;
            }
        });

    }

    private void showData() {
        if (recordBeanList != null) {
            recordBeanList.clear();
        }
        recordBeanList = myOpenHelper.queryRecord();
        myAdapter = new MyAdapter(this, recordBeanList);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete Item?")
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RecordBean recordBean = recordBeanList.get(position);
                                recordBeanList.remove(position);
                                myAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "OK!", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem itemAdd = menu.add(0, ADD_NEW_TODO, Menu.NONE, R.string.add_new);
        MenuItem itemRem = menu.add(0, CANCEL_ADD_TODO, Menu.NONE, R.string.remove);
        //itemAdd.setIcon(R.drawable.add_new_item);
        //itemRem.setIcon(R.drawable.remove_item);
        //itemAdd.setShortcut('0', 'a');
        //itemRem.setShortcut('1', 'r');
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        //int idx = myListView.getSelectedItemPosition();
        //String title = getString(addingNew ? R.string.cancel : R.string.remove);
        MenuItem cancelItem = menu.findItem(CANCEL_ADD_TODO);
        cancelItem.setTitle(R.string.cancel); //title
        cancelItem.setVisible(addingNew); //|| idx > -1

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int index = myListView.getSelectedItemPosition();
        switch (item.getItemId()) {
            case (CANCEL_ADD_TODO): {
                if (addingNew) { cancelAdd(); }
                //else { removeItem(index); }
                return true;
            }
            case (ADD_NEW_TODO): {
                addNewItem();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case (CANCEL_ADD_TODO): {
                AdapterView.AdapterContextMenuInfo menuInfo;
                menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int index = menuInfo.position;

                //removeItem(index);
                return true;
            }
        }
        return false;
    }

    private void cancelAdd() {
        addingNew = false;
        myEditText.setVisibility(View.GONE);
    }

    private void addNewItem() {
        addingNew = true;
        myEditText.setVisibility(View.VISIBLE);
        myEditText.requestFocus();
    }
}

