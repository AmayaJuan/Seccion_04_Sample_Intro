package com.jpvaapi.seccion_04_sample_intro;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button btnCreate;
    private Button btnDelete;

    private CarsSQLiteHelper carsHelper;
    private SQLiteDatabase db;

    private ListView listView;
    private MyAdapter adapter;

    private List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        cars = new ArrayList<Car>();

        btnCreate = findViewById(R.id.buttonCreate);
        btnDelete = findViewById(R.id.buttonDelete);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
                update();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();
                update();
            }
        });

        //Open the database 'DBTest1' in write mode
        carsHelper = new CarsSQLiteHelper(this, "DBTest1", null, 1);
        db = carsHelper.getWritableDatabase();

        adapter = new MyAdapter(this, cars, R.layout.itemdb);
        listView.setAdapter(adapter);

        update();
    }

    private List<Car> getAllCars() {
        //We select all the records in the Cars table
        Cursor cursor = db.rawQuery("select * from Cars", null);
        List<Car> list = new ArrayList<Car>();

        if (cursor.moveToFirst()){
            //iterate over the results cursor,
            //and we are filling the array that we will return later
            while (!cursor.isAfterLast()){
                int VIN = cursor.getInt(cursor.getColumnIndex("VIN"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String color = cursor.getString(cursor.getColumnIndex("color"));

                list.add(new Car(VIN, name, color));
                cursor.moveToNext();
            }
        }
        return list;
    }

    private void create() {
        //If we have successfully opened the database
        if (db != null) {
            //We create the record to insert as a ContentValues object
            ContentValues newRecord = new ContentValues();
            //The ID is self-increasing as stated in our Cars SQLiteHelper
            newRecord.put("name", "Seat");
            newRecord.put("color", "Black");

            //We insert the record in the database
            db.insert("Cars", null, newRecord);
        }
    }

    private void removeAll() {
        db.delete("Cars", "", null);
    }

    private void update() {
        //delete all the elements
        cars.clear();
        //we load all the elements
        cars.addAll(getAllCars());
        //cool the adapter
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        //close database connection before destroying the activity
        db.close();
        super.onDestroy();
    }
}
