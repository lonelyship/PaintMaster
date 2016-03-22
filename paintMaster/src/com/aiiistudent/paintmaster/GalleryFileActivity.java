package com.aiiistudent.paintmaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import ru.truba.touchgallery.GalleryWidget.BasePagerAdapter.OnItemChangeListener;
import ru.truba.touchgallery.GalleryWidget.FilePagerAdapter;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ShareActionProvider;

public class GalleryFileActivity extends Activity {





	private static int filePosition = 0;
	private ArrayList<String> items;
	private ArrayList<String> itemsDefault;
	private FilePagerAdapter pagerAdapter;
	protected Menu myMenu;

	ShareActionProvider actionProvider;

	Boolean menuCreatedFlag = false;

	private GalleryViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_file);

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		items = new ArrayList<String>();
		itemsDefault = new ArrayList<String>();

		File mDirectory;
		String folderPath = Environment.getExternalStorageDirectory()
				+ "/FingerPaint";
		mDirectory = new File(folderPath);

		// Get the files in the directory
		try {
			File[] files = mDirectory.listFiles(); 

			// 有圖片時
			if (files != null && files.length > 0) {
				for (File f : files) {
                  
					if (f.getName().matches(".+\\.png")){
					
					items.add(f.getAbsolutePath());
					
					
					}
				}

				if(items.size()!=0){	
				
				
				
				mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
				//mViewPager.setOffscreenPageLimit(3);
				pagerAdapter = new FilePagerAdapter(this, items);
				mViewPager.setAdapter(pagerAdapter);
				pagerAdapter
						.setOnItemChangeListener(new OnItemChangeListener() {
							@Override
							public void onItemChange(int currentPosition) {
								filePosition = currentPosition;
								// Toast.makeText(GalleryFileActivity.this,
								// "Current item is " + currentPosition,
								// Toast.LENGTH_SHORT).show();

								// Toast.makeText(GalleryFileActivity.this,
								// items
								// .get(filePosition),
								// Toast.LENGTH_SHORT).show();

								if (menuCreatedFlag == true) {

									actionProvider
											.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
									// Note that you can set/change the intent
									// any time,
									// say when the user has selected an image.
									actionProvider
											.setShareIntent(createShareIntent(items
													.get(filePosition)));
									
									
									

								}

							}
						});

				} else {

					// Toast.makeText(getApplicationContext(), "!",
					// Toast.LENGTH_SHORT).show();

					String[] urls = null;

					urls = getAssets().list("");

					for (String filename : urls) {
						if (filename.matches(".+\\.jpg")) {
							String path = getFilesDir() + "/" + filename;
							copy(getAssets().open(filename), new File(path));
							itemsDefault.add(path);
						}
					}

					mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
				//	mViewPager.setOffscreenPageLimit(3);
					pagerAdapter = new FilePagerAdapter(this, itemsDefault);
					mViewPager.setAdapter(pagerAdapter);
					pagerAdapter
							.setOnItemChangeListener(new OnItemChangeListener() {
								@Override
								public void onItemChange(int currentPosition) {
									filePosition = currentPosition;
									

								}
							});
				}
				
				
				// 沒圖片時 顯示預設放在assets的圖片
			} else {

				// Toast.makeText(getApplicationContext(), "!",
				// Toast.LENGTH_SHORT).show();

				String[] urls = null;

				urls = getAssets().list("");

				for (String filename : urls) {
					if (filename.matches(".+\\.jpg")) {
						String path = getFilesDir() + "/" + filename;
						copy(getAssets().open(filename), new File(path));
						itemsDefault.add(path);
					}
				}

				mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
				//mViewPager.setOffscreenPageLimit(3);
				pagerAdapter = new FilePagerAdapter(this, itemsDefault);
				mViewPager.setAdapter(pagerAdapter);
				pagerAdapter
						.setOnItemChangeListener(new OnItemChangeListener() {
							@Override
							public void onItemChange(int currentPosition) {
								filePosition = currentPosition;
								

							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void copy(InputStream in, File dst) throws IOException {

		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		myMenu = menu;

		menuCreatedFlag = true;

		getMenuInflater().inflate(R.menu.gallery_file, menu);

		if (items.size() > 0) {
			menu.setGroupVisible(R.id.delete_menu_group, true);
		} else {
			menu.setGroupVisible(R.id.delete_menu_group, false);
		}

		if (items.size() > 0) {
			// Set file with share history to the provider and set the share
			// intent.
			MenuItem actionItem = myMenu
					.findItem(R.id.menu_item_share_action_provider_action_bar);
			actionProvider = (ShareActionProvider) actionItem
					.getActionProvider();
			actionProvider
					.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
			// Note that you can set/change the intent any time,
			// say when the user has selected an image.
			actionProvider.setShareIntent(createShareIntent(items
					.get(filePosition)));
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.deleteImage:

			AlertDialog.Builder ab = new AlertDialog.Builder(
					GalleryFileActivity.this);

			ab.setMessage(R.string.deletePictrue);
			ab.setNegativeButton(android.R.string.no, null);
			ab.setPositiveButton(android.R.string.ok, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					File fdelete = new File(items.get(filePosition));
					if (fdelete.exists()) {
						try {
							if (fdelete.delete()) {
								// 只剩一張時 砍完回復預設圖
								if (items.size() == 1) {

									items.remove(filePosition);
									GalleryFileActivity.filePosition = 0;

									String[] urls = null;

									urls = getAssets().list("");

									for (String filename : urls) {
										if (filename.matches(".+\\.jpg")) {
											String path = getFilesDir() + "/"
													+ filename;
											copy(getAssets().open(filename),
													new File(path));
											itemsDefault.add(path);
										}
									}

									mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
									//mViewPager.setOffscreenPageLimit(3);
									pagerAdapter = new FilePagerAdapter(
											GalleryFileActivity.this,
											itemsDefault);
									mViewPager.setAdapter(pagerAdapter);
									

									myMenu.setGroupVisible(
											R.id.delete_menu_group, false);

								} else {

									// MenuItem actionItem = myMenu
									// .findItem(R.id.menu_item_share_action_provider_action_bar);
									// actionProvider = (ShareActionProvider)
									// actionItem
									// .getActionProvider();
									// actionProvider
									// .setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

									items.remove(filePosition);
									GalleryFileActivity.filePosition = 0;

									// pagerAdapter = new FilePagerAdapter(
									// GalleryFileActivity.this, items);

									mViewPager.setAdapter(pagerAdapter);

									// pagerAdapter
									// .setOnItemChangeListener(new
									// OnItemChangeListener() {
									// @Override
									// public void onItemChange(
									// int currentPosition) {
									filePosition = 0;
									// Toast.makeText(GalleryFileActivity.this,
									// items
									// .get(filePosition),
									// Toast.LENGTH_SHORT).show();

									if (menuCreatedFlag == true) {

										actionProvider
												.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
										// Note that you can set/change the
										// intent any time,
										// say when the user has selected an
										// image.
										actionProvider
												.setShareIntent(createShareIntent(items
														.get(filePosition)));
										//
									}
									// }
									// });

									myMenu.setGroupVisible(
											R.id.delete_menu_group, true);

								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});

			ab.show();

			break;
		}

	
		return true;
	}

	private Intent createShareIntent(String path) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		Uri uri = Uri.parse(path);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		return shareIntent;
	}
	
	
	@Override
	protected void onDestroy() {
		
		
		System.gc();
		super.onDestroy();
		 
	}
	
	
}