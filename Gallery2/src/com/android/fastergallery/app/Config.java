/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.fastergallery.app;

import android.content.Context;
import android.content.res.Resources;

import com.android.fastergallery.ui.AlbumSetSlotRenderer;
import com.android.fastergallery.ui.AlbumSetTypeManager;
import com.android.fastergallery.ui.AlbumSetTypeSlotRenderer;
import com.android.fastergallery.ui.AlbumSetTypeSlotView;
import com.android.fastergallery.ui.SlotView;
import com.android.fastergallery.R;

final class Config {
	public static class AlbumSetPage {
		private static AlbumSetPage sInstance;

		public SlotView.Spec slotViewSpec;
		public AlbumSetTypeSlotView.Spec slotTypeViewSpec;
		public AlbumSetSlotRenderer.LabelSpec labelSpec;
		public AlbumSetTypeSlotRenderer.LabelSpec labelTypeSpec;
		public int paddingTop;
		public int paddingBottom;
		public int placeholderColor;

		public static synchronized AlbumSetPage get(Context context) {
			if (sInstance == null) {
				sInstance = new AlbumSetPage(context);
			}
			if (context != null) {
				sInstance.configSpecByType(context);
			}
			return sInstance;
		}

		private AlbumSetPage(Context context) {
			Resources r = context.getResources();

			placeholderColor = r.getColor(R.color.albumset_placeholder);

			slotViewSpec = new SlotView.Spec();
			slotViewSpec.rowsLand = r.getInteger(R.integer.albumset_rows_land);
			slotViewSpec.rowsPort = r.getInteger(R.integer.albumset_rows_port);
			slotViewSpec.slotGap = r
					.getDimensionPixelSize(R.dimen.albumset_slot_gap);
			slotViewSpec.slotHeightAdditional = 0;

			paddingTop = r.getDimensionPixelSize(R.dimen.albumset_padding_top);
			paddingBottom = r
					.getDimensionPixelSize(R.dimen.albumset_padding_bottom);

			labelSpec = new AlbumSetSlotRenderer.LabelSpec();
			labelSpec.labelBackgroundHeight = r
					.getDimensionPixelSize(R.dimen.albumset_label_background_height);
			labelSpec.titleOffset = r
					.getDimensionPixelSize(R.dimen.albumset_title_offset);
			labelSpec.countOffset = r
					.getDimensionPixelSize(R.dimen.albumset_count_offset);
			labelSpec.titleFontSize = r
					.getDimensionPixelSize(R.dimen.albumset_title_font_size);
			labelSpec.countFontSize = r
					.getDimensionPixelSize(R.dimen.albumset_count_font_size);
			labelSpec.leftMargin = r
					.getDimensionPixelSize(R.dimen.albumset_left_margin);
			labelSpec.titleRightMargin = r
					.getDimensionPixelSize(R.dimen.albumset_title_right_margin);
			labelSpec.iconSize = r
					.getDimensionPixelSize(R.dimen.albumset_icon_size);
			labelSpec.backgroundColor = r
					.getColor(R.color.albumset_label_background);
			labelSpec.titleColor = r.getColor(R.color.albumset_label_title);
			labelSpec.countColor = r.getColor(R.color.albumset_label_count);
			
			slotTypeViewSpec = 	new AlbumSetTypeSlotView.Spec();
			configSpecByType(context);
			slotTypeViewSpec.slotGap = r
					.getDimensionPixelSize(R.dimen.albumset_slot_gap);
			slotTypeViewSpec.slotHeightAdditional = 0;
			
			labelTypeSpec = new AlbumSetTypeSlotRenderer.LabelSpec();
			labelTypeSpec.labelBackgroundHeight = r
					.getDimensionPixelSize(R.dimen.albumset_label_background_height);
			labelTypeSpec.titleOffset = r
					.getDimensionPixelSize(R.dimen.albumset_title_offset);
			labelTypeSpec.countOffset = r
					.getDimensionPixelSize(R.dimen.albumset_count_offset);
			labelTypeSpec.titleFontSize = r
					.getDimensionPixelSize(R.dimen.albumset_title_font_size);
			labelTypeSpec.countFontSize = r
					.getDimensionPixelSize(R.dimen.albumset_count_font_size);
			labelTypeSpec.leftMargin = r
					.getDimensionPixelSize(R.dimen.albumset_left_margin);
			labelTypeSpec.titleRightMargin = r
					.getDimensionPixelSize(R.dimen.albumset_title_right_margin);
			labelTypeSpec.iconSize = r
					.getDimensionPixelSize(R.dimen.albumset_icon_size);
			labelTypeSpec.backgroundColor = r
					.getColor(R.color.albumset_label_background);
			labelTypeSpec.titleColor = r.getColor(R.color.albumset_label_title);
			labelTypeSpec.countColor = r.getColor(R.color.albumset_label_count);

		}
		
		private void configSpecByType(Context context) {
			Resources r = context.getResources();
			switch(AlbumSetTypeManager.get().getCurrentType()) {
			case FilterUtils.CLUSTER_BY_ALBUM:
				slotTypeViewSpec.rowsLand = r.getInteger(R.integer.albumset_rows_land);
				slotTypeViewSpec.rowsPort = r.getInteger(R.integer.albumset_rows_port);
				break;
			case FilterUtils.CLUSTER_BY_CASCADING:
				slotTypeViewSpec.rowsLand = r.getInteger(R.integer.albumset_rows_land);
				slotTypeViewSpec.rowsPort = r.getInteger(R.integer.albumset_rows_port);
				break;
			case FilterUtils.CLUSTER_BY_LIST:
				slotTypeViewSpec.rowsLand = r.getInteger(R.integer.albumset_list_rows_land);
				slotTypeViewSpec.rowsPort = r.getInteger(R.integer.albumset_list_rows_port);
				break;
			default:
				slotTypeViewSpec.rowsLand = r.getInteger(R.integer.albumset_rows_land);
				slotTypeViewSpec.rowsPort = r.getInteger(R.integer.albumset_rows_port);
				break;
			}
		}
	}

	public static class AlbumPage {
		private static AlbumPage sInstance;

		public SlotView.Spec slotViewSpec;
		public int placeholderColor;

		public static synchronized AlbumPage get(Context context) {
			if (sInstance == null) {
				sInstance = new AlbumPage(context);
			}
			return sInstance;
		}

		private AlbumPage(Context context) {
			Resources r = context.getResources();

			placeholderColor = r.getColor(R.color.album_placeholder);

			slotViewSpec = new SlotView.Spec();
			slotViewSpec.rowsLand = r.getInteger(R.integer.album_rows_land);
			slotViewSpec.rowsPort = r.getInteger(R.integer.album_rows_port);
			slotViewSpec.slotGap = r
					.getDimensionPixelSize(R.dimen.album_slot_gap);
		}
	}

	public static class ManageCachePage extends AlbumSetPage {
		private static ManageCachePage sInstance;

		public final int cachePinSize;
		public final int cachePinMargin;

		public static synchronized ManageCachePage get(Context context) {
			if (sInstance == null) {
				sInstance = new ManageCachePage(context);
			}
			return sInstance;
		}

		public ManageCachePage(Context context) {
			super(context);
			Resources r = context.getResources();
			cachePinSize = r.getDimensionPixelSize(R.dimen.cache_pin_size);
			cachePinMargin = r.getDimensionPixelSize(R.dimen.cache_pin_margin);
		}
	}
}
