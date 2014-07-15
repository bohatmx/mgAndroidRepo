package com.boha.beacon.util;


import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Statics {
	
	/*
	 * REMOTE URL - bohamaker back end - production
	 */	
	//
	//public static final String URL = "https://bohamaker.com/cm/";
	//public static final String IMAGE_URL = "https://bohamaker.com/coursemaker_images/";
	
	/*
	 * LOCAL URL - pecanwood dungeon
	 */
	public static final String URL = "http://192.168.1.111:8050/beacon/";
	public static final String IMAGE_URL = "http://192.168.1.111:8050/bp_images/";
	//
	/**
	 * Servlet URL strings
	 */

	public static final String SERVLET_ADMIN = "bp?JSON=";

    public static final String CRASH_REPORTS_URL = URL + "crash?";
	
	
	public static final String HEADER_FONT = "fonts/DroidSerif-BoldItalic.ttf";
	public static final String DROID_FONT = "DroidSans.ttf";
	public static final String DROID_FONT_BOLD = "DroidSans-Bold.ttf";
	//	
	public static void setHeaderFont(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				Statics.HEADER_FONT);
		txt.setTypeface(font);
	}

	public static void setDroidFont(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				Statics.DROID_FONT);
		txt.setTypeface(font);
	}

	public static void setDroidFontBold(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				"DroidSerif-Bold");
		txt.setTypeface(font);
	}
	public static void setRobotoFontBoldCondensed(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				"fonts/Roboto-BoldCondensed.ttf");
		txt.setTypeface(font);
	}
	public static void setRobotoFontRegular(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				"fonts/Roboto-Regular.ttf");
		txt.setTypeface(font);
	}
	public static void setRobotoFontBold(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				"fonts/Roboto-Bold.ttf");
		txt.setTypeface(font);
	}
	public static void setRobotoItalic(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				"fonts/Roboto-Italic.ttf");
		txt.setTypeface(font);
	}
	public static void setRobotoRegular(Context ctx, TextView txt) {
		Typeface font = Typeface.createFromAsset(ctx.getAssets(),
				"fonts/Roboto-Regular.ttf");
		txt.setTypeface(font);
	}
	
}
