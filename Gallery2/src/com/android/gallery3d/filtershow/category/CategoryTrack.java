/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.android.gallery3d.filtershow.category;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.gallery3d.R;

public class CategoryTrack extends LinearLayout {

	private CategoryAdapter mAdapter;
	private int mElemSize;

	public CategoryTrack(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CategoryTrack);
		mElemSize = a.getDimensionPixelSize(R.styleable.CategoryTrack_iconSize,
				0);
	}

	public void setAdapter(CategoryAdapter adapter) {
		mAdapter = adapter;
		mAdapter.setItemWidth(mElemSize);
		mAdapter.setItemHeight(LayoutParams.MATCH_PARENT);
		fillContent();
	}

	public void fillContent() {
		removeAllViews();
		int n = mAdapter.getCount();
		for (int i = 0; i < n; i++) {
			View view = mAdapter.getView(i, null, this);
			addView(view, i);
		}
		requestLayout();
	}

}
