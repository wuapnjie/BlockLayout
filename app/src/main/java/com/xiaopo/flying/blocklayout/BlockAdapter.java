package com.xiaopo.flying.blocklayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopo.flying.blockengine.widget.BlockLayout;

/**
 * @author wupanjie
 */

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder>{

  @Override
  public BlockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(BlockViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public static class BlockViewHolder extends RecyclerView.ViewHolder {
    BlockLayout blockLayout;

    public BlockViewHolder(View itemView) {
      super(itemView);
      blockLayout = (BlockLayout) itemView.findViewById(R.id.block_layout);
    }
  }
}
