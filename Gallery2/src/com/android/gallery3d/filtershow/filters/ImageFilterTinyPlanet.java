/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.gallery3d.filtershow.filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.android.gallery3d.app.Log;
import com.android.gallery3d.filtershow.presets.ImagePreset;

/**
 * An image filter which creates a tiny planet projection.
 */
public class ImageFilterTinyPlanet extends SimpleImageFilter {

	private static final String LOGTAG = ImageFilterTinyPlanet.class
			.getSimpleName();
	public static final String GOOGLE_PANO_NAMESPACE = "http://ns.google.com/photos/1.0/panorama/";
	FilterTinyPlanetRepresentation mParameters = new FilterTinyPlanetRepresentation();

	public static final String CROPPED_AREA_IMAGE_WIDTH_PIXELS = "CroppedAreaImageWidthPixels";
	public static final String CROPPED_AREA_IMAGE_HEIGHT_PIXELS = "CroppedAreaImageHeightPixels";
	public static final String CROPPED_AREA_FULL_PANO_WIDTH_PIXELS = "FullPanoWidthPixels";
	public static final String CROPPED_AREA_FULL_PANO_HEIGHT_PIXELS = "FullPanoHeightPixels";
	public static final String CROPPED_AREA_LEFT = "CroppedAreaLeftPixels";
	public static final String CROPPED_AREA_TOP = "CroppedAreaTopPixels";

	public ImageFilterTinyPlanet() {
		mName = "TinyPlanet";
	}

	@Override
	public void useRepresentation(FilterRepresentation representation) {
		FilterTinyPlanetRepresentation parameters = (FilterTinyPlanetRepresentation) representation;
		mParameters = parameters;
	}

	@Override
	public FilterRepresentation getDefaultRepresentation() {
		return new FilterTinyPlanetRepresentation();
	}

	native protected void nativeApplyFilter(Bitmap bitmapIn, int width,
			int height, Bitmap bitmapOut, int outSize, float scale, float angle);

	@Override
	public Bitmap apply(Bitmap bitmapIn, float scaleFactor, int quality) {
		int w = bitmapIn.getWidth();
		int h = bitmapIn.getHeight();
		int outputSize = (int) (w / 2f);
		ImagePreset preset = getImagePreset();
		Bitmap mBitmapOut = null;

		while (mBitmapOut == null) {
			try {
				mBitmapOut = getEnvironment().getBitmap(outputSize, outputSize);
			} catch (java.lang.OutOfMemoryError e) {
				System.gc();
				outputSize /= 2;
				Log.v(LOGTAG,
						"No memory to create Full Tiny Planet create half");
			}
		}
		nativeApplyFilter(bitmapIn, bitmapIn.getWidth(), bitmapIn.getHeight(),
				mBitmapOut, outputSize, mParameters.getZoom() / 100f,
				mParameters.getAngle());

		return mBitmapOut;
	}

	private Bitmap applyXmp(Bitmap bitmapIn, Object xmp, int intermediateWidth) {
		try {
			int croppedAreaWidth = getInt(xmp, CROPPED_AREA_IMAGE_WIDTH_PIXELS);
			int croppedAreaHeight = getInt(xmp,
					CROPPED_AREA_IMAGE_HEIGHT_PIXELS);
			int fullPanoWidth = getInt(xmp, CROPPED_AREA_FULL_PANO_WIDTH_PIXELS);
			int fullPanoHeight = getInt(xmp,
					CROPPED_AREA_FULL_PANO_HEIGHT_PIXELS);
			int left = getInt(xmp, CROPPED_AREA_LEFT);
			int top = getInt(xmp, CROPPED_AREA_TOP);

			if (fullPanoWidth == 0 || fullPanoHeight == 0) {
				return bitmapIn;
			}
			// Make sure the intermediate image has the similar size to the
			// input.
			Bitmap paddedBitmap = null;
			float scale = intermediateWidth / (float) fullPanoWidth;
			while (paddedBitmap == null) {
				try {
					paddedBitmap = Bitmap.createBitmap(
							(int) (fullPanoWidth * scale),
							(int) (fullPanoHeight * scale),
							Bitmap.Config.ARGB_8888);
				} catch (java.lang.OutOfMemoryError e) {
					System.gc();
					scale /= 2;
				}
			}
			Canvas paddedCanvas = new Canvas(paddedBitmap);

			int right = left + croppedAreaWidth;
			int bottom = top + croppedAreaHeight;
			RectF destRect = new RectF(left * scale, top * scale,
					right * scale, bottom * scale);
			paddedCanvas.drawBitmap(bitmapIn, null, destRect, null);
			bitmapIn = paddedBitmap;
		} catch (Exception ex) {
			// Do nothing, just use bitmapIn as is.
		}
		return bitmapIn;
	}

	private static int getInt(Object xmp, String key) {
		return 0;
	}
}
