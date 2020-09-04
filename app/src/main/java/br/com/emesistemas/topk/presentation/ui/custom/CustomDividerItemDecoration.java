package br.com.emesistemas.topk.presentation.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable divider;
    private int height;

    /**
     * Custom divider will be used
     */
    @SuppressWarnings("unused")
    public CustomDividerItemDecoration(Context context, int resId) {
        divider = ContextCompat.getDrawable(context, resId);
        assert divider != null;
        height = divider.getIntrinsicHeight();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != Objects.requireNonNull(parent.getAdapter()).getItemCount() - 1) {
            outRect.bottom = height;
        }
    }
}
