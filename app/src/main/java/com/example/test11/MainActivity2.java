package com.example.test11;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private EditText ed_book, ed_price;
    private Button btn_query;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_query = findViewById(R.id.btn_query);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        Bundle b = this.getIntent().getExtras();
//        String book = b.getString(ed_book.getText().toString());
//        String price = b.getString(ed_price.getText().toString());

        dbrw = new MyDBHelper(this).getWritableDatabase();
        dbrw.execSQL("INSERT INTO myTable(book, price) VALUES(?,?)"
                , new Object[]{b.getString(ed_book.getText().toString()),
                        b.getString(ed_price.getText().toString())});
        btn_query.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Cursor c;
//                if (ed_book.length() < 1)
//                    c = dbrw.rawQuery("SELECT * FROM myTable", null);
//                else
                    c = dbrw.rawQuery("SELECT * FROM myTable WHERE book LIKE ' "
                            + ed_book.getText().toString() + " ' ", null);

                c.moveToFirst();
                items.clear();
                Toast.makeText(MainActivity2.this, "共有" + c.getCount()
                        + "筆資料", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < c.getCount(); i++) {
                    items.add("書名:" + c.getString(0) +
                            "\t\t\t\t 價格:" + c.getString(1));
                    c.moveToNext();
                }
                adapter.notifyDataSetChanged();

                c.close();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
}