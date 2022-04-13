package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView viewById;
    private MyDatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    //SimpleCursorAdapter所需要的参数
    String from[] = new String[]{"name", "author","pages","price","category_id"};
    int[] to = new int[]{R.id.name, R.id.author,R.id.pages,R.id.price,R.id.category};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.library);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        viewById = (ListView) findViewById(R.id.list_view);
        //进入程序时显示数据库中的数据
        Show();
    }
    //显示数据
    public void Show() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cursor = db.query("Book", null, null, null, null, null, null);
        adapter = new SimpleCursorAdapter(this, R.layout.list_layout, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        viewById.setAdapter(adapter);
        db.close();
    }
}