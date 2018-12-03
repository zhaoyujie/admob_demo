package com.demo.admob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    public static final String TAG = "Recycle View";
    private Context mContext;
    private List<MyListModel> mList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, ADMOB_APP_ID);
        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mList = new ArrayList<MyListModel>();

        for (int i = 0; i < 100; i++) {
            MyListModel myString = new MyListModel();
            myString.setName(i + "- Blank Item");
            myString.setViewType(1);
            mList.add(myString);
        }

        //Place two Admob Ads at position index 1 and 5 in recyclerview
        MyListModel myString1 = new MyListModel();
        myString1.setViewType(2);
        mList.add(1, myString1);

        MyListModel myString2 = new MyListModel();
        myString2.setViewType(2);
        mList.add(5, myString2);

        MyListModel myString3 = new MyListModel();
        myString3.setViewType(2);
        mList.add(10, myString3);

        MyListModel myString4 = new MyListModel();
        myString4.setViewType(2);
        mList.add(15, myString4);

        MyListModel myString5 = new MyListModel();
        myString5.setViewType(2);
        mList.add(20, myString5);

        MyListModel myString6 = new MyListModel();
        myString6.setViewType(2);
        mList.add(30, myString6);

        MyListModel myString7 = new MyListModel();
        myString7.setViewType(2);
        mList.add(36, myString7);

        MyListModel myString8 = new MyListModel();
        myString8.setViewType(2);
        mList.add(41, myString8);

        MyListModel myString9 = new MyListModel();
        myString9.setViewType(2);
        mList.add(50, myString9);

        MyListModel myString10 = new MyListModel();
        myString10.setViewType(2);
        mList.add(60, myString10);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdaptor(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }
}