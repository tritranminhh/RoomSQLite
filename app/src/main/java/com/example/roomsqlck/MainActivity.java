package com.example.roomsqlck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editname,editage;
    Button btnAdd,btnReset;
    RecyclerView recyclerView;
    List<Person> personList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    MainAdapter adapter;
    RoomDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        editname=findViewById(R.id.editTextName);
        editage=findViewById(R.id.editTextAge);
        btnAdd=findViewById(R.id.btnAdd);
        btnReset=findViewById(R.id.btnReset);
        recyclerView=findViewById(R.id.recyclerView);
        database=RoomDB.getInstance(this);
        personList=database.personDAO().getAll();
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new MainAdapter(personList,this);
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editname.getText().toString().trim();
                if(name.equals("") || editage.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this, "name or age incorrect", Toast.LENGTH_SHORT).show();
                }if(Integer.parseInt(editage.getText().toString().trim())<=0){
                    Toast.makeText(MainActivity.this, "tuoi pha lon hon 0", Toast.LENGTH_SHORT).show();
                }else if(!name.equals("")){
                    int tuoi=Integer.parseInt(editage.getText().toString().trim());
                    Person data=new Person();
                    data.setPersonName(name);
                    data.setTuoi(tuoi);
                    database.personDAO().insertPerson(data);
                    Toast.makeText(MainActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                    editage.setText("");
                    editname.setText("");
                    personList.clear();
                    personList.addAll(database.personDAO().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.personDAO().reset(personList);
                Toast.makeText(MainActivity.this, "reset successfully", Toast.LENGTH_SHORT).show();
            personList.clear();
            personList.addAll(database.personDAO().getAll());
            adapter.notifyDataSetChanged();
            }
        });
    }
}