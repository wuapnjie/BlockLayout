package com.xiaopo.flying.blocklayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.xiaopo.flying.blockengine.BlockLayout;

public class MainActivity extends AppCompatActivity {
  private BlockLayout blockLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    blockLayout = (BlockLayout) findViewById(R.id.block_layout);

    blockLayout.setPuzzleLayout(new TestLayout());
    blockLayout.addViewAtBlock(new EditText(this), 2);
  }
}
