package com.xiaopo.flying.blocklayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

  private RecyclerView feedList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    feedList = (RecyclerView) findViewById(R.id.feed_list);
    feedList.setLayoutManager(new LinearLayoutManager(this));
    feedList.setHasFixedSize(true);


  }
}
