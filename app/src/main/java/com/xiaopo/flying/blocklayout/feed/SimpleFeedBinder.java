package com.xiaopo.flying.blocklayout.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopo.flying.blockengine.layout.PuzzleLayout;
import com.xiaopo.flying.blockengine.widget.BlockLayout;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author wupanjie
 */
public class SimpleFeedBinder extends ItemViewBinder<SimpleFeed, SimpleFeedBinder.ViewHolder> {
  public final PuzzleLayout puzzleLayout;

  public SimpleFeedBinder(PuzzleLayout puzzleLayout) {
    this.puzzleLayout = puzzleLayout;
  }

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater,
      @NonNull ViewGroup viewGroup) {
    BlockLayout layout = new BlockLayout(viewGroup.getContext());
    layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
    layout.setPuzzleLayout(puzzleLayout);
    return new ViewHolder(layout);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull SimpleFeed simpleFeed) {

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
