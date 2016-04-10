package com.cotton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cotton.photo.AlbumStorageDirFactory;
import com.cotton.photo.BaseAlbumDirFactory;
import com.cotton.photo.FroyoAlbumDirFactory;
import com.cotton.ui.AvenirTextView;
import com.cotton.utils.GPUImageFilterTools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;


public class PhotoIntentActivity extends Activity {

	private static final int ACTION_TAKE_PHOTO_B = 1;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";

	private GPUImageFilter mFilter;
	private GPUImageFilterTools.FilterAdjuster mFilterAdjuster;
	private GPUImageView mImageView;

	private ImageView backButton;
	private AvenirTextView doneButton;

	private HorizontalScrollView scrollH;
	private LinearLayout filtersLayout;
	private Bitmap mImageBitmap;

	private String mCurrentPhotoPath;
	private Uri contentUri;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	
	/* Photo album for this application */
	private String getAlbumName() {
		return "Cotton Album";
	}

	
	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
	}

	private void setPic(GPUImageView img) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = img.getWidth();
		int targetH = img.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		/* Associate the Bitmap to the ImageView */
		img.setImage(bitmap);
		img.setVisibility(View.VISIBLE);
	}

	private void galleryAddPic() {
		    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			File f = new File(mCurrentPhotoPath);
		    contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
			mCurrentPhotoPath = null;
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}

	private void handleBigCameraPhoto() {

		if (mCurrentPhotoPath != null) {
			setPic(mImageView);
			//ConcurrentAsyncTask.execute(new SetFilters().execute());
			new SetFilters().execute();
			//galleryAddPic();
			//mCurrentPhotoPath = null;
		}

	}

	private void switchFilterTo(final GPUImageFilter filter) {
		if (mFilter == null
				|| (filter != null && !mFilter.getClass().equals(filter.getClass()))) {
			mFilter = filter;
			mImageView.setFilter(mFilter);
			mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(mFilter);

			/*findViewById(R.id.seekBar).setVisibility(
					mFilterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);*/
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_main);

		mImageView = (GPUImageView) findViewById(R.id.imageView1);
		mImageBitmap = null;
		backButton = (ImageView)findViewById(R.id.back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		doneButton = (AvenirTextView)findViewById(R.id.done_button);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				galleryAddPic();
				Intent intent = new Intent(PhotoIntentActivity.this, ShareActivity.class);
				intent.putExtra("Uri",contentUri.toString());
				startActivity(intent);
				finish();
			}
		});

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}

		dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);

		scrollH = (HorizontalScrollView)findViewById(R.id.horizontal_scroll);
		filtersLayout = (LinearLayout) findViewById(R.id.filters);

	}

	protected float range(final int percentage, final float start, final float end) {
		return (end - start) * percentage / 100.0f + start;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			if (resultCode == RESULT_OK) {
				handleBigCameraPhoto();
			}
			break;
		} // ACTION_TAKE_PHOTO_B

		} // switch
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		mImageView.setImage(mImageBitmap);
		mImageView.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);

	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 *
	 * @param context The application's environment.
	 * @param action The Intent action to check for availability.
	 *
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}



	private class SetFilters extends AsyncTask<String, Void, Boolean> {


		@Override
		protected void onPostExecute(Boolean result) {
			for (int i = 0; i < 4; i++) {
				final int count = i;
				RelativeLayout layout = (RelativeLayout)getLayoutInflater().inflate(R.layout.photo_filter, null, false);
				//ImageView img = (ImageView)layout.findViewById(R.id.image_filter);
				AvenirTextView text = (AvenirTextView)layout.findViewById(R.id.text);
				text.setText("Filtro");
				/*setPic(img);
				if(count == 0){
					GPUImageFilter filter = new GPUImageFilter();
					switchFilterTo(filter);
					text.setText("Filtro 0");
				}
				else if(count ==1) {
					GPUImageLookupFilter amatorka = new GPUImageLookupFilter();
					amatorka.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_amatorka));
					switchFilterTo(amatorka);
					text.setText("Filtro 1");
				}else if(count==2) {
					GPUImageWhiteBalanceFilter whiteB = new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
					whiteB.setTemperature(range(40, 2000.0f, 8000.0f));
					switchFilterTo(whiteB);
					text.setText("Filtro 2");
				}else if(count==3) {
					GPUImageContrastFilter contrast = new GPUImageContrastFilter(2.0f);
					contrast.setContrast(range(90, 0.0f, 2.0f));
					switchFilterTo(contrast);
					text.setText("Filtro 3");
				}
				img.requestRender(); */

				layout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						GPUImageFilter filter = null;
						if(count == 0){
							filter = new GPUImageFilter();
							switchFilterTo(filter);
						}
						else if(count ==1) {
							GPUImageLookupFilter amatorka = new GPUImageLookupFilter();
							amatorka.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_amatorka));
							switchFilterTo(amatorka);
						}else if(count==2) {
							GPUImageWhiteBalanceFilter whiteB = new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
							whiteB.setTemperature(range(40, 2000.0f, 8000.0f));
							switchFilterTo(whiteB);
						}else if(count==3) {
							GPUImageContrastFilter contrast = new GPUImageContrastFilter(3.0f);
							//GPUImageSaturationFilter saturation = new GPUImageSaturationFilter(2.0f);
							//saturation.setSaturation(range(90, 0.0f, 2.0f));
							switchFilterTo(contrast);
						}

						mImageView.requestRender();
					}
				});
				filtersLayout.addView(layout);
			}

			super.onPostExecute(result);
		}

		@Override
		protected Boolean doInBackground(String... arg) {

			return true;
		}
	}
}