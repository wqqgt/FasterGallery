package com.android.gallery3d.ui;


public final class AlbumSetTypeManager {
	private static AlbumSetTypeManager sInstance;
	private int mCurrentType = 0;

	public static synchronized AlbumSetTypeManager get() {
		if (sInstance == null) {
			sInstance = new AlbumSetTypeManager();
		}
		return sInstance;
	}
	
	public void setCurrentType(int type) {
		mCurrentType = type;
	}
	
	public int getCurrentType() {
		return mCurrentType;
	}

}
