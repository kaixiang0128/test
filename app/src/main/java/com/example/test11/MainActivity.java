package com.example.test11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private EditText ed_book, ed_price;
    private Button btn_query, btn_insert;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();

    private SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_book = findViewById(R.id.ed_book);
        ed_price = findViewById(R.id.ed_price);
        btn_insert = findViewById(R.id.btn_insert);

        Bundle b = new Bundle();
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);


        dbrw = new MyDBHelper(this).getWritableDatabase();
        btn_insert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                if (ed_book.length() < 1 || ed_price.length() < 1)
                    Toast.makeText(MainActivity.this, "欄位請勿留空",
                            Toast.LENGTH_SHORT).show();
                else {
                    try {
                        dbrw.execSQL("INSERT INTO myTable(book, price) VALUES(?,?)"
                                , new Object[]{ed_book.getText().toString(),
                                        ed_price.getText().toString()});
                        Toast.makeText(MainActivity.this, "新增書名"
                                        + ed_book.getText().toString()
                                        + "  價格" + ed_price.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        ed_book.setText("");
                        ed_price.setText("");
                        b.putString("book", ed_book.getText().toString());
                        b.putString("price", ed_price.getText().toString());
                        intent.putExtras(b);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "新增失敗:" +
                                e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbrw.close();
    }
}