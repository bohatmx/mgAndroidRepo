package com.boha.proximity.cms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.boha.proximity.data.BeaconDTO;
import com.boha.proximity.data.CompanyDTO;
import com.boha.proximity.data.PhotoUploadDTO;
import com.boha.proximity.data.ResponseDTO;
import com.boha.proximity.data.UploadBlobDTO;
import com.boha.proximity.util.BlobUpload;
import com.boha.proximity.util.ImageUpload;
import com.boha.proximity.util.ImageUtil;
import com.boha.proximity.util.SharedUtil;
import com.boha.proximity.volley.BaseVolley;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PictureActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ctx = getApplicationContext();
        beacon = (BeaconDTO)getIntent().getSerializableExtra("beacon");
        setFields();
        setTitle("Beacon Content");
        getActionBar().setSubtitle(beacon.getBeaconName());
    }

    private void setFields() {
        btnSave = (Button)findViewById(R.id.BAA_btnSave);
        btnTakePic = (Button)findViewById(R.id.BAA_btnTakePicture);
        image = (ImageView)findViewById(R.id.BAA_image);

        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendThumbnail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void sendThumbnail() {
        Log.e(LOG, "..........sendThumbnail ........: " + thumbUri.toString());
        CompanyDTO c = SharedUtil.getCompany(ctx);
        uploadImage(c.getCompanyID(), beacon.getBranchID(), beacon.getBeaconID(), thumbUri.toString());
    }
    private void sendBlobThumbnail() {
        Log.e(LOG, "..........sendThumbnail ........: " + currentThumbFile.getAbsolutePath());

        BaseVolley.getUploadUrl(ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                if (response.getStatusCode() > 0) {
                    Toast.makeText(ctx, response.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                String url = response.getUploadUrl().getUrl();
                BlobUpload.upload(url, currentThumbFile, ctx, new BlobUpload.BlobUploadListener() {
                    @Override
                    public void onImageUploaded(UploadBlobDTO response) {
                        Log.e(LOG, "###### Blob has been uploaded OK: servingUrl: "
                                + response.getServingUrl() +
                                " blobKey: " + response.getBlobKey());

                    }

                    @Override
                    public void onUploadError() {
                        Log.e(LOG, "###### Erroruploading blob");
                    }
                });
            }

            @Override
            public void onVolleyError(VolleyError error) {

            }
        });
    }
    Bitmap bitmapForScreen;

    class PhotoTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            //Log.e("pic", " file length: " + photoFile.length());
            ExifInterface exif = null;
            fileUri = Uri.fromFile(photoFile);
            if (fileUri != null) {
                try {
                    exif = new ExifInterface(photoFile.getAbsolutePath());
                    String orient = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    Log.e("pic", "Orientation says: " + orient);
                    float rotate = 0;
                    if (orient.equalsIgnoreCase("6")) {
                        rotate = 90f;
                    }
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bm = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
                        getLog(bm, "Raw Camera");
                        //scale and rotate for the screen
                        Matrix matrix = new Matrix();
                        matrix.postScale(1.0f, 1.0f);
                        matrix.postRotate(rotate);
                        bitmapForScreen = Bitmap.createBitmap
                                (bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                        getLog(bitmapForScreen, "Screen");
                        //get thumbnail for upload
                        Matrix matrixThumbnail = new Matrix();
                        matrixThumbnail.postScale(0.4f, 0.4f);
                        //matrixThumbnail.postRotate(rotate);
                        Bitmap thumb = Bitmap.createBitmap
                                (bitmapForScreen, 0, 0, bitmapForScreen.getWidth(),
                                        bitmapForScreen.getHeight(), matrixThumbnail, true);
                        getLog(thumb, "Thumb");
                        //get resized "full" size for upload
                        Matrix matrixF = new Matrix();
                        matrixF.postScale(0.6f, 0.6f);
                        //matrixF.postRotate(rotate);
                        Bitmap fullBm = Bitmap.createBitmap
                                (bitmapForScreen, 0, 0, bitmapForScreen.getWidth(),
                                        bitmapForScreen.getHeight(), matrixF, true);
                        getLog(fullBm, "Full");
                        currentFullFile = ImageUtil.getFileFromBitmap(fullBm, "m" + System.currentTimeMillis() + ".jpg");
                        currentThumbFile = ImageUtil.getFileFromBitmap(thumb, "t" + System.currentTimeMillis() + ".jpg");
                        thumbUri = Uri.fromFile(currentThumbFile);
                        fullUri = Uri.fromFile(currentFullFile);
                        getFileLengths();
                    } catch (Exception e) {
                        Log.e("pic", "Fuck it!", e);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }

            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result > 0) {
                //TODO - error
                return;
            }
            if (thumbUri != null) {
                image.setImageBitmap(bitmapForScreen);
                image.setAlpha(1.0f);
                btnSave.setVisibility(View.VISIBLE);
            }
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("pic", "Fuck!", ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void getLog(Bitmap bm, String which) {
        Log.e(LOG, which + " - bitmap: width: "
                + bm.getWidth() + " height: "
                + bm.getHeight() + " rowBytes: "
                + bm.getRowBytes());
    }

    private void getFileLengths() {
        Log.i(LOG, "Thumbnail file length: " + currentThumbFile.length());
        Log.i(LOG, "Full file length: " + currentFullFile.length());

    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        switch (requestCode) {
            case CAPTURE_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    if (resultCode == Activity.RESULT_OK) {
                        new PhotoTask().execute();
                    }
                }
                break;

        }
    }
    public  void uploadImage(int companyID, int branchID, int beaconID, String uri) {

        File imageFile = new File(Uri.parse(uri).getPath());
        Log.i("pic", "Uri for upload: " + uri);
        Log.w(LOG, "File to be uploaded - length: " + imageFile.length() + " - " + imageFile.getAbsolutePath());
        List<File> files = new ArrayList<File>();
        if (imageFile.exists()) {
            files.add(imageFile);
            PhotoUploadDTO dto = new PhotoUploadDTO();
            dto.setCompanyID(companyID);
            dto.setBranchID(branchID);
            dto.setBeaconID(beaconID);
            ImageUpload.upload(dto, files, ctx ,
                    new ImageUpload.ImageUploadListener() {
                        @Override
                        public void onUploadError() {

                            Log.e(LOG,
                                    "Error uploading - onUploadError");
                        }

                        @Override
                        public void onImageUploaded(ResponseDTO response) {
                            if (response.getStatusCode() == 0) {
                                Toast.makeText(ctx, "Content uploaded", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(LOG,
                                        "Error uploading - "
                                                + response.getMessage()
                                );
                            }
                        }
                    }
            );
        }
    }
    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }
    Context ctx;
    Uri fileUri;
    ImageView image;
    File currentThumbFile, currentFullFile;
    Uri thumbUri, fullUri;
    static final String LOG = "PictureActivity";
    String mCurrentPhotoPath;
    File photoFile;
    private BeaconDTO beacon;
    Button btnTakePic, btnSave;
    TextView beaconName;
    static final int CAPTURE_IMAGE = 11331;
}
