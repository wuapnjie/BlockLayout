package com.xiaopo.flying.blocklayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.xiaopo.flying.blockengine.widget.BlockLayout;
import com.xiaopo.flying.blockengine.JsonPuzzleLayout;

public class MainActivity extends AppCompatActivity {
  public static final String LAYOUT_JSON =
      "{\"steps\":[{\"method\":\"addLine\",\"direction\":0,\"radio\":0.5,\"position\":0}]}";
  private BlockLayout blockLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    blockLayout = (BlockLayout) findViewById(R.id.block_layout);

    blockLayout.setPuzzleLayout(new JsonPuzzleLayout(LAYOUT_JSON));
    blockLayout.addViewAtBlock(new EditText(this), 1);
  }
}
