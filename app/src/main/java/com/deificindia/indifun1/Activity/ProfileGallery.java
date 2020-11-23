package com.deificindia.indifun1.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.ImagePickerActivity;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.pojo.getuserprofilegallerypojo.ResultItem;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileGallery extends AppCompatActivity {

    private ImageView img_back;
    private TextView txt_header_title;
    private ImageView userimage1, userimage2, userimage3, userimage4, userimage5, userimage6, userimage7, userimage8, userimage9;
    private static final int REQUEST_IMAGE=100;
    ArrayList<ResultItem> resultItems = new ArrayList<>();
    private String picturePath;
    private String image1,image2,image3,image4,image5,image6,image7,image8,image9;
    private String tag;
    private String click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_gallery);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);

        userimage1 = findViewById(R.id.userimage1);
        userimage2 = findViewById(R.id.userimage2);
        userimage3 = findViewById(R.id.userimage3);
        userimage4 = findViewById(R.id.userimage4);
        userimage5 = findViewById(R.id.userimage5);
        userimage6 = findViewById(R.id.userimage6);
        userimage7 = findViewById(R.id.userimage7);
        userimage8 = findViewById(R.id.userimage8);
        userimage9 = findViewById(R.id.userimage9);

        getuserprofilegallery();

        txt_header_title.setText("Profile Gallery");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="1";
                tag=image1;
                openMediaDialog();
            }
        });
        userimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="2";
                tag=image2;
                openMediaDialog();
            }
        });
        userimage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="3";
                tag=image3;
                openMediaDialog();
            }
        });
        userimage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="4";
                tag=image4;
                openMediaDialog();
            }
        });
        userimage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="5";
                tag=image5;
                openMediaDialog();
            }
        });
        userimage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="6";
                tag=image6;
                openMediaDialog();
            }
        });
        userimage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="7";
                tag=image7;
                openMediaDialog();
            }
        });
        userimage8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="8";
                tag=image8;
                openMediaDialog();
            }
        });
        userimage9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click="9";
                tag=image9;
                openMediaDialog();
            }
        });
    }



    private void openMediaDialog() {
        Dexter.withActivity(ProfileGallery.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(ProfileGallery.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(ProfileGallery.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);


        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileGallery.this);
        builder.setTitle(getString(R.string.givepermissions));
        builder.setMessage(getString(R.string.pleasegivepermissions));
        builder.setPositiveButton(getString(R.string.app_name), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    picturePath = encodeTobase64(bitmap);


                    if(click.equalsIgnoreCase("1")){
                        if (userimage1.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage1.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("2")){
                        if (userimage2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage2.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("3")){
                        if (userimage3.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage3.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("4")){
                        if (userimage4.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage4.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("5")){
                        if (userimage5.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage5.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("6")){
                        if (userimage6.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage6.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("7")){
                        if (userimage7.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage7.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("8")){
                        if (userimage8.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage8.setImageBitmap(bitmap);
                    }else  if(click.equalsIgnoreCase("9")){
                        if (userimage9.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.plus_1).getConstantState()) {
                            addaprofilegallery(picturePath);
                        }else{
                            updateaprofilegallery(picturePath,tag);
                        }
                        userimage9.setImageBitmap(bitmap);
                    }

                    //addAvatar.setVisibility(View.VISIBLE);
                    // profile_image.setImageBitmap(bitmap);
                    /*imgUser.setImageBitmap ( bitmap );
                    strImg1=encodeTobase64(bitmap);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getuserprofilegallery() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileGallery.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETUSEPROFILEGALLERY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                if (resultItems != null) {
                                    resultItems.clear();
                                }
                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("result");
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    ResultItem resultItem = new ResultItem();
                                    resultItem.setId(qstnArray.getString("id"));
                                    resultItem.setUserId(qstnArray.getString("user_id"));
                                    resultItem.setImage(qstnArray.getString("image"));
                                    resultItem.setEntryDate(qstnArray.getString("entry_date"));
                                    resultItems.add(resultItem);


                                }

                                if(resultItems!=null && resultItems.size()>=1) {
                                    if (resultItems.get(0).getId() != null && !resultItems.get(0).getId().isEmpty()) {
                                        image1 = resultItems.get(0).getId();
                                    }
                                    if (resultItems.get(0).getImage() != null && !resultItems.get(0).getImage().isEmpty()) {

                                        Picasso.get()
                                                .load(URLs.UserImageBaseUrl + resultItems.get(0).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage1);
                                    } else {
                                        userimage1.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=2) {
                                    if (resultItems.get(1).getId() != null && !resultItems.get(1).getId().isEmpty()) {

                                        image2 = resultItems.get(1).getId();
                                    }
                                    if (resultItems.get(1).getImage() != null && !resultItems.get(1).getImage().isEmpty()) {

                                        Picasso.get()
                                                .load(URLs.UserImageBaseUrl + resultItems.get(1).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage2);
                                    } else {
                                        userimage2.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=3) {
                                    if (resultItems.get(2).getId() != null && !resultItems.get(2).getId().isEmpty()) {

                                        image3 = resultItems.get(2).getId();
                                    }
                                    if (resultItems.get(2).getImage() != null && !resultItems.get(2).getImage().isEmpty()) {

                                        Picasso.get().load(URLs.UserImageBaseUrl + resultItems.get(2).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage3);
                                    } else {
                                        userimage3.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=4) {
                                    if (resultItems.get(3).getId() != null && !resultItems.get(3).getId().isEmpty()) {

                                        image4 = resultItems.get(3).getId();
                                    }
                                    if (resultItems.get(3).getImage() != null && !resultItems.get(3).getImage().isEmpty()) {

                                        Picasso.get()
                                                .load(URLs.UserImageBaseUrl + resultItems.get(3).getImage())
                                                .error(R.drawable.plus_1).into(userimage4);
                                    } else {
                                        userimage4.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=5) {
                                    if (resultItems.get(4).getId() != null && !resultItems.get(4).getId().isEmpty()) {

                                        image5 = resultItems.get(4).getId();
                                    }
                                    if (resultItems.get(4).getImage() != null && !resultItems.get(4).getImage().isEmpty()) {

                                        Picasso.get()
                                                .load(URLs.UserImageBaseUrl + resultItems.get(4).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage5);
                                    } else {
                                        userimage5.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=6) {
                                    if (resultItems.get(5).getId() != null && !resultItems.get(5).getId().isEmpty()) {

                                        image6 = resultItems.get(5).getId();
                                    }
                                    if (resultItems.get(5).getImage() != null && !resultItems.get(5).getImage().isEmpty()) {

                                        Picasso.get().load(URLs.UserImageBaseUrl + resultItems.get(5)
                                                .getImage()).error(R.drawable.plus_1)
                                                .into(userimage6);
                                    } else {
                                        userimage6.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>6) {
                                    if (resultItems.get(6).getId() != null && !resultItems.get(6).getId().isEmpty()) {

                                        image7 = resultItems.get(6).getId();
                                    }
                                    if (resultItems.get(6).getImage() != null && !resultItems.get(6).getImage().isEmpty()) {

                                        Picasso.get().load(URLs.UserImageBaseUrl + resultItems.get(6).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage7);
                                    } else {
                                        userimage7.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=7) {
                                    if (resultItems.get(7).getId() != null && !resultItems.get(7).getId().isEmpty()) {

                                        image8 = resultItems.get(7).getId();
                                    }
                                    if (resultItems.get(7).getImage() != null && !resultItems.get(7).getImage().isEmpty()) {

                                        Picasso.get().load(URLs.UserImageBaseUrl + resultItems.get(7).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage8);
                                    } else {
                                        userimage8.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }
                                if(resultItems!=null && resultItems.size()>=8) {
                                    if (resultItems.get(8).getId() != null && !resultItems.get(8).getId().isEmpty()) {

                                        image9 = resultItems.get(8).getId();
                                    }
                                    if (resultItems.get(8).getImage() != null && !resultItems.get(8).getImage().isEmpty()) {

                                        Picasso.get().load(URLs.UserImageBaseUrl + resultItems.get(8).getImage())
                                                .error(R.drawable.plus_1)
                                                .into(userimage9);
                                    } else {
                                        userimage9.setImageDrawable(getResources().getDrawable(R.drawable.plus_1));
                                    }
                                }

                            } else {
                                myDialog.dismiss();
                                if (resultItems != null) {
                                    resultItems.clear();
                                }

                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileGallery.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileGallery.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id",IndifunApplication.getInstance().getCredential().getId());
                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private void addaprofilegallery(String picturePath1) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileGallery.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADDUSEPROFILEGALLERY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                              //  getuserprofilegallery();

                            } else {
                                myDialog.dismiss();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileGallery.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileGallery.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                params.put("image",picturePath1 /*IndifunApplication.getInstance().getCredential().getId()*/);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void updateaprofilegallery(String picturePath1,String tag1) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(ProfileGallery.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATEUSEPROFILEGALLERY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                               // getuserprofilegallery();
                            } else {
                                myDialog.dismiss();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(ProfileGallery.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(ProfileGallery.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                params.put("image",picturePath1 /*IndifunApplication.getInstance().getCredential().getId()*/);
                params.put("profile_photos_id", tag1);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
