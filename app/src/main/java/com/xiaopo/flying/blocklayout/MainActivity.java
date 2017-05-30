package com.xiaopo.flying.blocklayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.xiaopo.flying.blockengine.layout.Line;
import com.xiaopo.flying.blockengine.layout.PuzzleLayout;
import com.xiaopo.flying.blockengine.widget.BlockLayout;
import com.xiaopo.flying.blocklayout.feed.FeedActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
  private BlockLayout blockLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    blockLayout = (BlockLayout) findViewById(R.id.block_layout);

    blockLayout.setPuzzleLayout(new MainPuzzleLayout());
    blockLayout.post(new Runnable() {
      @Override public void run() {
        blockLayout.printInfo();
      }
    });
  }

  private String getLayoutInfo() {
    try {
      InputStream inputStream = getAssets().open("main_layout.json");

      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder builder = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }

      reader.close();
      inputStream.close();
      return builder.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }

  public void toFeed(View view) {
    startActivity(new Intent(this, FeedActivity.class));
  }

  private static class MainPuzzleLayout extends PuzzleLayout {
    @Override public void layout() {
      cutBlockEqualPart(0, 5, Line.Direction.HORIZONTAL);
    }
  }
}
