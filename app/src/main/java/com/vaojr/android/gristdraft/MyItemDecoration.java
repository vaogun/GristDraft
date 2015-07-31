package com.vaojr.android.gristdraft;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paintBlack, paintOrange;
    private int offset;

    public MyItemDecoration(){
        offset = 5;

        paintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBlack.setColor(0x1a000000);
        paintBlack.setStyle(Paint.Style.STROKE);
        paintBlack.setStrokeWidth(1);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        for(int i=0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);

            c.drawLine(
                    layoutManager.getDecoratedLeft(child) + 8,
                    layoutManager.getDecoratedBottom(child),
                    layoutManager.getDecoratedRight(child) - 8,
                    layoutManager.getDecoratedBottom(child),
                    paintBlack);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(offset, offset, offset, offset);
    }
}
