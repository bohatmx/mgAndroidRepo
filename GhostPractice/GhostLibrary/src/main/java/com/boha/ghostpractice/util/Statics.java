package com.boha.ghostpractice.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Statics {

	//public static final String URL = "http://10.0.0.239:8080/GhostPractice-war/ghost?json=";
	//
	public static final String URL = "http://gpmobile.ghostpractice.com:7180/GhostPractice-war/ghost?json=";

	//public const string CONSOLE_URL = "http://gpmobile.ghostpractice.com:7180/GhostPractice-war/ghost?json=";
	public static final String HEADER_FONT = "BordeauxBlack Regular.ttf";
	public static final String DROID_FONT = "DroidSans.ttf";
	public static final String DROID_FONT_BOLD = "DroidSans-Bold.ttf";
	//http://69.89.1.149:7148/

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
				Statics.DROID_FONT_BOLD);
		txt.setTypeface(font);
	}

	

}
