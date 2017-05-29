package com.xiaopo.flying.blocklayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.xiaopo.flying.blockengine.widget.BlockLayout;
import com.xiaopo.flying.blockengine.JsonPuzzleLayout;
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

    blockLayout.setPuzzleLayout(new JsonPuzzleLayout(getLayoutInfo()));
    //blockLayout.addViewAtBlock(new EditText(this), 1);

    blockLayout.post(new Runnable() {
      @Override public void run() {
        blockLayout.printInfo();
      }
    });
  }

  private String getLayoutInfo() {
    try {
      InputStream inputStream = getAssets().open("layout.json");

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
}
