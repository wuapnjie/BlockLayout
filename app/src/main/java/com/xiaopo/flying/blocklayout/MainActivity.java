package com.xiaopo.flying.blocklayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.xiaopo.flying.blockengine.BlockLayout;

public class MainActivity extends AppCompatActivity {
  public static final String LAYOUT_JSON = "{\n"
      + "    \"steps\": [\n"
      + "        {\n"
      + "            \"method\": \"cutSpiral\",\n"
      + "            \"hRadio\": 0.5,\n"
      + "            \"vRadio\": 0.3\n"
      + "        }\n"
      + "    ]\n"
      + "}";
  private BlockLayout blockLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    blockLayout = (BlockLayout) findViewById(R.id.block_layout);

    blockLayout.setPuzzleLayout(new JsonTestLayout(LAYOUT_JSON));
    blockLayout.addViewAtBlock(new EditText(this), 4);
  }
}
