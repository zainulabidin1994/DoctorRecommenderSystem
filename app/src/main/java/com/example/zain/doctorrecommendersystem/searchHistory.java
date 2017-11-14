package com.example.zain.doctorrecommendersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class searchHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    recyclerAdapter adapter;
    ArrayList<searchHistoryJava> dataList;
    Realm realm;
    Button clearHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        realm = Realm.getDefaultInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        clearHistory = (Button)findViewById(R.id.clearHistory);
        dataList = new ArrayList<searchHistoryJava>();
        layoutManager = new LinearLayoutManager(searchHistory.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getData();
        adapter = new recyclerAdapter(searchHistory.this,dataList);
        recyclerView.setAdapter(adapter);

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        realm.delete(searchHistoryJava.class);
                    }
                });
                Toast.makeText(searchHistory.this,"History Clear",Toast.LENGTH_SHORT).show();
                adapter = new recyclerAdapter();
                recyclerView.setAdapter(adapter);
            }
        });


    }

    private void getData() {

        RealmResults<searchHistoryJava> result = realm.where(searchHistoryJava.class).findAllAsync();
        result.load();

        for(searchHistoryJava obj:result){

            dataList.add(obj);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
