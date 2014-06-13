package com.android.fastergallery.ui;

import com.android.fastergallery.glrenderer.GLCanvas;


public interface SlotRenderer {
	public void prepareDrawing();

	public void onVisibleRangeChanged(int visibleStart, int visibleEnd);

	public void onSlotSizeChanged(int width, int height);

	public int renderSlot(GLCanvas canvas, int index, int pass, int width,
			int height);
}
