package com.aiiistudent.paintmaster;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;




public class IndexActivity extends Activity {

	
	
	
	private FancyCoverFlow fancyCoverFlow;

	Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		createDirIfNotExists("FingerPaint");

	
		
//		Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
//		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//		try{
//			
//			startActivity(intent);
//		}catch(ActivityNotFoundException e){
//			Toast.makeText(this, "Couldn't launch the market !", Toast.LENGTH_SHORT).show();
//		}
//		

		this.fancyCoverFlow = (FancyCoverFlow) this
				.findViewById(R.id.fancyCoverFlow);
		this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter(this));
		this.fancyCoverFlow.setUnselectedAlpha(1.0f);
		this.fancyCoverFlow.setUnselectedSaturation(0.0f);
		this.fancyCoverFlow.setUnselectedScale(0.5f);
		this.fancyCoverFlow.setSpacing(50);
		this.fancyCoverFlow.setMaxRotation(100);
		
		
		this.fancyCoverFlow.setScaleDownGravity(0.2f);
		this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

		this.fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:

					it = new Intent(IndexActivity.this, MainActivity.class);
					startActivity(it);

					break;

				case 1:

					it = new Intent(IndexActivity.this, PracticeActivity.class);
					startActivity(it);

					break;

				case 2:

					it = new Intent(IndexActivity.this, FreeActivity.class);
					startActivity(it);

					break;
				case 3:

					it = new Intent(IndexActivity.this, PickActivity.class);
					startActivity(it);

					break;
				case 4:

					it = new Intent(IndexActivity.this, CameraActivity.class);
					startActivity(it);

					break;
				case 5:

					it = new Intent(IndexActivity.this,
							GalleryFileActivity.class);
					startActivity(it);

					break;
				case 6:

					AlertDialog.Builder aboutDialog = new AlertDialog.Builder(IndexActivity.this);
					
//					aboutDialog.setTitle(R.string.about);
					
					aboutDialog.setMessage(R.string.aboutMessage);
					
					aboutDialog.setPositiveButton(android.R.string.ok, null);
					
					
					aboutDialog.show();

					break;	
					

				default:
					break;
				}
				// Toast.makeText(getApplicationContext(),
				// ((Integer)(position)).toString(), Toast.LENGTH_SHORT).show();
				// AlphaDialog();

			}

		});
	}

	public static boolean createDirIfNotExists(String path) {
		boolean ret = true;

		File file = new File(Environment.getExternalStorageDirectory(), path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.e("TravellerLog :: ", "Problem creating Image folder");
				ret = false;
			}
		}
		return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

//		switch (item.getItemId()) {
//
//		case R.id.ABOUT__MENU_ID:
//			
//			
//			AlertDialog.Builder aboutDialog = new AlertDialog.Builder(IndexActivity.this);
//			
//			aboutDialog.setTitle(R.string.about);
//			
//			aboutDialog.setMessage(R.string.aboutMessage);
//			
//			aboutDialog.setPositiveButton(android.R.string.ok, null);
//			
//			
//			aboutDialog.show();
//			
//			return true;
//		}
 
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder ab = new AlertDialog.Builder(IndexActivity.this);

		ab.setMessage(R.string.exit);
		ab.setNegativeButton(android.R.string.no, null);
		ab.setPositiveButton(android.R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();

			}
		});

		ab.show();

	}

}
