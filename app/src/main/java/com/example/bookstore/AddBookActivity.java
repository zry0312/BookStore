package com.example.bookstore;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddBookActivity extends Activity {

    private SimpleCursorAdapter adapter;
    private MyDatabaseHelper dbHelper;
    private Cursor cursor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });
    }

    public void save(View v) {
        //获得可读写数据库对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        EditText name = (EditText) findViewById(R.id.et_name);
        EditText author = (EditText) findViewById(R.id.et_author);
        EditText pages = (EditText) findViewById(R.id.et_pages);
        EditText price = (EditText) findViewById(R.id.et_price);
        EditText category_id = (EditText) findViewById(R.id.et_category);
        values.put("name", name.getText().toString().trim());
        values.put("author", author.getText().toString().trim());
        values.put("pages", pages.getText().toString());
        values.put("price", price.getText().toString());
        values.put("category_id", category_id.getText().toString());
        long q = db.insert("Book", "name", values);
        Toast.makeText(this, "数据存入成功", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void clear(View v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Book", null, null);
        //使自增的_id归零
        db.delete("sqlite_sequence", null, null);
        Toast.makeText(this, "数据库清除成功", Toast.LENGTH_SHORT).show();
        cursor.requery();
        adapter.notifyDataSetChanged();
        db.close();
    }

}
