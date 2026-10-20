package com.gathin.tvhome;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/** Draws the tvOS style lighter band behind the first (dock) row and adds a gap below it. */
public class DockDecoration extends RecyclerView.ItemDecoration {

    private final int dockSize;
    private final float density;
    private final Paint band = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint edge = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();

    /** @param translucent the cinematic shelf's band: the artwork and the glow show through it. */
    DockDecoration(int dockSize, float density, boolean translucent) {
        this.dockSize = dockSize;
        this.density = density;
        band.setColor(0xF0080D12);
        edge.setStyle(Paint.Style.STROKE);
        edge.setStrokeWidth(1.5f * density);
        edge.setColor(0xAA8CA7B9);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (pos >= 0 && pos < dockSize) {
            outRect.top = (int) (6 * density);
            outRect.bottom = (int) (12 * density);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        boolean any = false;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            if (pos < 0 || pos >= dockSize) continue;
            RecyclerView.ViewHolder vh = parent.getChildViewHolder(child);
            View card = vh instanceof AppAdapter.Holder ? ((AppAdapter.Holder) vh).card : child.findViewById(R.id.card);
            if (card == null) continue;
            float l = child.getLeft() + card.getLeft();
            float t = child.getTop() + card.getTop() + child.getTranslationY();
            float r = l + card.getWidth();
            float b = t + card.getHeight();
            if (!any) {
                rect.set(l, t, r, b);
                any = true;
            } else {
                rect.union(l, t, r, b);
            }
        }
        if (!any) return;
        float pad = 30 * density;
        rect.inset(-pad, -pad);
        float radius = 28 * density;
        band.setShader(new LinearGradient(rect.left, rect.top, rect.right, rect.bottom,
                new int[]{0xF21B2832, 0xF00A1017, 0xF218232D}, null, Shader.TileMode.CLAMP));
        c.drawRoundRect(rect, radius, radius, band);
        c.drawRoundRect(rect, radius, radius, edge);
        // A subtle luminous upper lip distinguishes the floating dock from a flat old-style band.
        Paint lip = new Paint(Paint.ANTI_ALIAS_FLAG);
        lip.setColor(0x889AB7CA);
        lip.setStrokeWidth(1.2f * density);
        c.drawLine(rect.left + radius, rect.top + density, rect.right - radius, rect.top + density, lip);
    }
}
