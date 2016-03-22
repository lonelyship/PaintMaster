/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.aiiistudent.paintmaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeActivity extends GraphicsActivity implements
		ColorPickerDialog.OnColorChangedListener {

	int[] qusetion = { R.drawable.q1, R.drawable.q2, R.drawable.q3,
			R.drawable.q4, R.drawable.q5, R.drawable.q6, R.drawable.q7,
			R.drawable.q8, R.drawable.q9, R.drawable.q10, R.drawable.q11,
			R.drawable.q12, R.drawable.q13, R.drawable.q14 };

	

	Bitmap imgQuestionSrc;
	Bitmap imgQuestion;
	Bitmap imgSrc;
	Bitmap img;

	Bitmap imgHelp;
	Boolean openHint = false;

	Boolean flag = true;
	private Bitmap mBitmap;
	private Paint mBitmapPaint;
	private Paint mBitmapHelp;

	Canvas canvas;

	CountDownTimer countDownTimer;
	String str;

	int w1, h1;

	Menu menu;

	MenuItem bgItem;
	MenuItem hintItem;

	static Integer mYear;
	static Integer mMonth;
	static Integer mDay;
	static Integer mHour;
	static Integer mMinute;
	static Integer mSecond;

	// 存檔名的時間變數
	String mTime;

	MyView v;

	Gallery gallery;

	boolean pen = false;
	boolean shadow = false;

	int shadowLayerColor = Color.RED;

	int tempProgress = 255;
	int sizeTempProgress = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new MyView(this);
		setContentView(R.layout.activity_practice);

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);

		LinearLayout ll = (LinearLayout) findViewById(R.id.practice_layout);
		ll.addView(v);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		gallery = ((Gallery) findViewById(R.id.gallery01));
		gallery.setAdapter(new ImageAdapter(this));

		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				imgQuestionSrc = BitmapFactory.decodeResource(getResources(),
						qusetion[position]);

				imgQuestion = Bitmap.createScaledBitmap(imgQuestionSrc, w1 / 2,
						h1, true);

				imgHelp = Bitmap.createScaledBitmap(imgQuestionSrc, w1 / 2, h1,
						true);

				// flag = false;

			}

		});

		int num = (int) (Math.random() * qusetion.length);

		imgQuestionSrc = BitmapFactory.decodeResource(getResources(),
				qusetion[num]);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF444444);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(6);

		mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);

		mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

	}

	private Paint mPaint;
	private MaskFilter mEmboss;
	private MaskFilter mBlur;

	public class MyView extends View {

		private Canvas mCanvas;
		private Path mPath;

		public MyView(Context c) {
			super(c);

			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);

			mBitmapPaint.setTextSize(50);
			mBitmapPaint.setTextAlign(Align.CENTER);
			mBitmapPaint.setColor(0xFFFF0000);

			mBitmapHelp = new Paint(Paint.DITHER_FLAG);
			// mBitmapHelp.setAlpha(50);

		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			
			if(mBitmap!=null)mBitmap.recycle();
			if(imgQuestion!=null)imgQuestion.recycle();
			if(imgHelp!=null)imgHelp.recycle();
				
			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);

			imgQuestion = Bitmap.createScaledBitmap(imgQuestionSrc, w / 2, h,
					true);

			imgHelp = Bitmap.createScaledBitmap(imgQuestionSrc, w / 2, h, true);



			w1 = w;
			h1 = h;

		}

		@Override
		protected void onDraw(Canvas canvas) {

			if (flag == false) {

				mBitmap.eraseColor(Color.TRANSPARENT);

				canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

				flag = true;
				invalidate();

			}

			else {

				canvas.drawColor(0xFFFFFFFF);

				//canvas.drawBitmap(img, w1 / 2, 0, mBitmapPaint);

				if (openHint == true) {
					canvas.drawBitmap(imgHelp, w1 / 2, 0, mBitmapHelp);
				}
				canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

				canvas.drawBitmap(imgQuestion, 0, 0, mBitmapPaint);

				canvas.drawPath(mPath, mPaint);

				invalidate();
			}

		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}

		private void touch_up() {
			mPath.lineTo(mX, mY);
			// commit the path to our offscreen
			mCanvas.drawPath(mPath, mPaint);
			// kill this so we don't double draw
			mPath.reset();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			}
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		this.menu = menu;
		getMenuInflater().inflate(R.menu.practice, menu);

		bgItem = menu.findItem(R.id.BG_MENU_ID);
		hintItem = menu.findItem(R.id.HINT_MENU_ID);

		MenuItem Next = menu.findItem(R.id.NEXT_MENU_ID);

		Next.setVisible(false);

		return true;
	}

	private Intent createShareIntent(String time) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		Uri uri = Uri.parse("file:///sdcard/FingerPaint/" + mTime + ".png");
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		return shareIntent;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.HINT_MENU_ID:

			if (openHint == false) {

				openHint = true;
				ShowDialog();

				hintItem.setTitle(R.string.closeHint);

			} else {
				openHint = false;
				hintItem.setTitle(R.string.hint);
			}

			return true;

		case R.id.size:

			SizeDialog();

			return true;

		case R.id.COLOR_MENU_ID:
			pen = true;
			shadow = false;

			new ColorPickerDialog(this, this, mPaint.getColor()).show();
			return true;

		case R.id.NORMAL_MENU_ID:

			mPaint.clearShadowLayer();
			mPaint.setMaskFilter(null);
			mPaint.setXfermode(null);
			mPaint.setAlpha(0xFF);
			return true;

		case R.id.EMBOSS_MENU_ID:

			mPaint.clearShadowLayer();
			mPaint.setMaskFilter(mEmboss);
			mPaint.setXfermode(null);
			mPaint.setAlpha(0xFF);

			return true;

		case R.id.SHADOWLAYER_MENU_ID:

			mPaint.clearShadowLayer();
			mPaint.setMaskFilter(null);
			mPaint.setXfermode(null);
			mPaint.setAlpha(0xFF);

			pen = false;
			shadow = true;

			Dialog shadowLayerDialog = new ColorPickerDialog(this, this,
					shadowLayerColor);

			shadowLayerDialog.setTitle(R.string.dialog_ShadowLayer);

			shadowLayerDialog.show();

			return true;

		case R.id.BLUR_MENU_ID:

			mPaint.clearShadowLayer();
			mPaint.setMaskFilter(mBlur);
			mPaint.setXfermode(null);
			mPaint.setAlpha(0xFF);
			return true;

		case R.id.ERASE_MENU_ID:
			mPaint.clearShadowLayer();
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			// mPaint.setStrokeWidth(20);
			return true;

		case R.id.SRCATOP_MENU_ID:
			mPaint.clearShadowLayer();
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
			mPaint.setAlpha(0x80);

			return true;

		case R.id.CLEAR_MENU_ID:

			AlertDialog.Builder ab = new AlertDialog.Builder(this);

			ab.setMessage(R.string.clearPaths);
			ab.setNegativeButton(android.R.string.no, null);
			ab.setPositiveButton(android.R.string.ok, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					// Toast.makeText(getApplicationContext(), "清空",
					// Toast.LENGTH_SHORT)
					// .show();

					flag = false;

				}
			});

			ab.show();

			return true;

		case R.id.NEXT_MENU_ID:

			int num = (int) (Math.random() * qusetion.length);

			// Toast.makeText(getApplicationContext(), "下一關",
			// Toast.LENGTH_SHORT)
			// .show();
			
			if(imgQuestionSrc!=null)imgQuestionSrc.recycle();
			if(imgQuestion!=null)imgQuestion.recycle();
			if(imgHelp!=null)imgHelp.recycle();

			imgQuestionSrc = BitmapFactory.decodeResource(getResources(),
					qusetion[num]);
			imgQuestion = Bitmap.createScaledBitmap(imgQuestionSrc, w1 / 2, h1,
					true);

			imgHelp = Bitmap.createScaledBitmap(imgQuestionSrc, w1 / 2, h1,
					true);

			flag = false;
			return true;

			// 存檔按鈕
		case R.id.SAVE_MENU_ID:

			// ------------------以下開始存圖---------------------------
			final Calendar cc = Calendar.getInstance();
			mYear = cc.get(Calendar.YEAR);
			mMonth = cc.get(Calendar.MONTH);
			mDay = cc.get(Calendar.DAY_OF_MONTH);
			mHour = cc.get(Calendar.HOUR_OF_DAY);
			mMinute = cc.get(Calendar.MINUTE);
			mSecond = cc.get(Calendar.SECOND);

			String m = "";
			String d = "";

			if ((mMonth + 1) < 10)
				m += "0";
			if ((mDay) < 10)
				d += "0";

			String h = "";
			String min = "";
			String sec = "";

			if (mHour < 10)
				h += "0";
			if (mMinute < 10)
				min += "0";
			if (mSecond < 10)
				sec += "0";

			mTime = mYear.toString() + m + ((Integer) (mMonth + 1)).toString()
					+ d + mDay.toString() + h + mHour.toString() + min
					+ mMinute.toString() + sec + mSecond.toString();

			// ------------------以下開始存圖---------------------------
			Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
					Bitmap.Config.ARGB_8888);

			Canvas c = new Canvas(b);

			v.draw(c);

			File imageFile = new File(Environment.getExternalStorageDirectory()
					+ "/FingerPaint/" + mTime + ".png");
			OutputStream fout = null;

			try {
				fout = new FileOutputStream(imageFile);
				b.compress(Bitmap.CompressFormat.PNG, 80, fout);
				fout.flush();
				fout.close();
				Toast.makeText(getApplicationContext(),
						mTime + ".png" + getString(R.string.alreadySaved),
						Toast.LENGTH_SHORT).show();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// -------------------------------------------------------------------

			// Set file with share history to the provider and set the share
			// intent.
			MenuItem actionItem = menu
					.findItem(R.id.menu_item_share_action_provider_action_bar);
			ShareActionProvider actionProvider = (ShareActionProvider) actionItem
					.getActionProvider();
			actionProvider
					.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
			// Note that you can set/change the intent any time,
			// say when the user has selected an image.
			actionProvider.setShareIntent(createShareIntent(mTime));

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onColorChanged(int color) {
		if (pen == true)
			mPaint.setColor(color);
		if (shadow == true) {
			mPaint.setShadowLayer(15, 0, 0, color);
			shadowLayerColor = color;
		}

	}

	public void ShowDialog()

	{

		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

		final SeekBar seek = new SeekBar(this);

		seek.setMax(255);

		seek.setProgress(tempProgress);

		// popDialog.setIcon(android.R.drawable.btn_star_big_on);

		popDialog.setTitle(R.string.setTransparency);

		popDialog.setView(seek);

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				// Do something here with new value

				// txtView.setText("Value of : " + progress);

				mBitmapHelp.setAlpha(progress);
				tempProgress = progress;
			}

			public void onStartTrackingTouch(SeekBar arg0) {

			}

			// TODO Auto-generated method stub

			public void onStopTrackingTouch(SeekBar seekBar) {

				// TODO Auto-generated method stub

			}

		});

		// Button OK

		popDialog.setPositiveButton(android.R.string.ok,

		new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

			}

		});

		popDialog.create();

		popDialog.show();

	}

	private long lastClickTime = 0;

	// 再按一次退出
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

		if (lastClickTime <= 0) {
			Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
			lastClickTime = System.currentTimeMillis();

		} else {
			long currentClickTime = System.currentTimeMillis();

			if ((currentClickTime - lastClickTime) < 2000) {
				finish();
			} else {

				Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
				lastClickTime = currentClickTime;
			}

		}

	}

	public void SizeDialog()

	{

		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		final View seekView = inflater.inflate(R.layout.seek_bar_view, null);
		// final SeekBar seek = new SeekBar(this);
		final SeekBar seek = (SeekBar) seekView
				.findViewById(R.id.seekBarPenSize);
		final TextView txtSeek = (TextView) seekView
				.findViewById(R.id.textViewSeekBar);

		seek.setMax(49);

		seek.setProgress(sizeTempProgress);

		// popDialog.setIcon(android.R.drawable.btn_star_big_on);

		popDialog.setTitle(R.string.setTipSize);

		txtSeek.setText(getString(R.string.currentTipSize)
				+ (sizeTempProgress + 1));

		// popDialog.setMessage("目前筆尖大小:  "+sizeTempProgress+1);

		popDialog.setView(seekView);

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				// Do something here with new value

				txtSeek.setText(getString(R.string.currentTipSize)
						+ (progress + 1));

				mPaint.setStrokeWidth(progress + 1);
				sizeTempProgress = progress;

			}

			public void onStartTrackingTouch(SeekBar arg0) {

			}

			// TODO Auto-generated method stub

			public void onStopTrackingTouch(SeekBar seekBar) {

				// TODO Auto-generated method stub

			}

		});

		// Button OK

		popDialog.setPositiveButton(android.R.string.ok,

		new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();

			}

		});

		popDialog.create();
		popDialog.show();

	}

	@Override
	protected void onDestroy() {

		if(imgQuestionSrc!=null)imgQuestionSrc.recycle();
		if(imgQuestion!=null)imgQuestion.recycle();

		if(imgHelp!=null)imgHelp.recycle();
		if(mBitmap!=null)mBitmap.recycle();

		System.gc();
		super.onDestroy();
	}

}
