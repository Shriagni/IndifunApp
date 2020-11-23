package com.deificindia.indifun1.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.MySharePref.getData;
import static com.deificindia.indifun1.Utility.MySharePref.saveData;

import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.Utility.ImagePickerActivity;
import com.deificindia.indifun1.Utility.Indifun;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.KeyClass;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.pojo.userpojo.Result;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SingupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_age;
    private TextView edt_dob;
    Spinner spinnerCounry;

    DatePicker datePicker;
    private RelativeLayout btn_continue;
    private RadioGroup rb_group;
    private RadioButton male, female, checkedRadioButton;
    private String str_gender, number;
    private ImageView profile_image;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private static final int REQUEST_IMAGE = 100;
    private String picturePath;
    private TextView uploadphototv;
    private LinearLayout uploadll;

    String mycountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        findid();
        number = getData(getApplicationContext(), "number", null);
        Intent i = getIntent();
        number = i.getStringExtra(KeyClass.PHONE_NUM);
        System.out.println("number" + number);
        rb_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    str_gender = checkedRadioButton.getText().toString();
                }
            }
        });
    }

    private void findid() {
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_age = findViewById(R.id.edt_age);
        edt_dob = findViewById(R.id.edt_dob);
        btn_continue = findViewById(R.id.btn_continue);
        edt_dob.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        rb_group = findViewById(R.id.rb_group);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        profile_image = findViewById(R.id.profile_image);
        uploadphototv=findViewById(R.id.uploadphototv);
        uploadll=findViewById(R.id.uploadll);
        spinnerCounry=findViewById(R.id.countrySpinner);

        uploadll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMediaDialog();
            }

        });

        getCountryNames();
    }

    void initSpinner(ArrayList<String> arrayList){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCounry.setAdapter(arrayAdapter);
        spinnerCounry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mycountry = parent.getItemAtPosition(position).toString();
                Toast.makeText(SingupActivity.this, mycountry, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void openMediaDialog() {
        Dexter.withActivity(SingupActivity.this)
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
        Intent intent = new Intent(SingupActivity.this, ImagePickerActivity.class);
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
        Intent intent = new Intent(SingupActivity.this, ImagePickerActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SingupActivity.this);
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
                    //addAvatar.setVisibility(View.VISIBLE);
                    profile_image.setImageBitmap(bitmap);
                    /*imgUser.setImageBitmap ( bitmap );
                    strImg1=encodeTobase64(bitmap);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

      /* or use below if you want 32 bit images

       bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();
        //imageArr1 = imageArr;
        return Base64.encodeToString(imageArr, Base64.DEFAULT);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v == edt_dob) {
            calenderDialog();
        } else if (v == btn_continue) {
            validation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validation() {
        String name = edt_name.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String dob = edt_dob.getText().toString().trim();
        String age = edt_age.getText().toString().trim();
        if (profile_image.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.no_image).getConstantState()) {
            Toast.makeText(SingupActivity.this, getString(R.string.pleaseuploadimage), Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please fill Name", "e");
        } else if (email.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please fill email", "e");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Indifun.toast(SingupActivity.this, "Please fill valid email", "e");
        } else if (dob.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please select Date of Birthday", "e");
        } else if (age.isEmpty()) {
            Indifun.toast(SingupActivity.this, "Please fill age", "e");
        }else if(mycountry==null && mycountry.isEmpty()){
            Indifun.toast(SingupActivity.this, "Please select your country", "e");
        } else {
            signUpMethod1(picturePath,number, name, email, dob, str_gender, age);
        }
    }

    private void calenderDialog() {

        final Dialog dialog = new Dialog(SingupActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_calender);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        datePicker = dialog.findViewById(R.id.datePicker);

        ((RelativeLayout) dialog.findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                edt_dob.setText(getCurrentDate());

                //Converting String to Date
                SimpleDateFormat sdf = null;
                SimpleDateFormat sdf2 = null;
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                //System.out.println(sdf2.format(sdf.parse(date)));
                String dob1 = null;
                try {
                    dob1 = sdf2.format(sdf.parse(edt_dob.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = formatter.parse(dob1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Converting obtained Date object to LocalDate object
                /*Instant instant = date.toInstant();
                ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                LocalDate givenDate = zone.toLocalDate();*/
                /*LocalDate givenDate = Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();*/
                //Calculating the difference between given date to current date.
               // Period period = Period.between(givenDate, LocalDate.now());
                // System.out.print(period.getYears()+" years "+period.getMonths()+" and "+period.getDays()+" days");
              //  edt_age.setText(String.valueOf(period.getYears()));
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private String getCurrentDate() {
        StringBuilder builder = new StringBuilder();
        builder.append(datePicker.getYear() + "-");
        builder.append((datePicker.getMonth() + 1) + "-");//month is 0 based
        builder.append(datePicker.getDayOfMonth());
        return builder.toString();
    }

    protected void signUpMethod(String number, String name, final String email, String birthday, String gender, String age) {
//        ApiClient.getBaseApiMethods().signup(new SignupRequest(name, email, birthday, gender)).enqueue(new Callback<SignupResponse>() {
//            @Override
//            public void onResponse(Call<SignupResponse> call, retrofit2.Response<SignupResponse> response) {
//                ResponseBody errorBody = response.errorBody();
//                if (response.isSuccessful()) {
//                    SignupResponse signupResponse = response.body();
//                    System.out.println("ress:::"+signupResponse);
//                    if (signupResponse != null) {
//                        Toast.makeText(SingupActivity.this, "Sucessfully Register", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<SignupResponse> call, Throwable t) {
//
//            }
//        });

        Call<ResponseBody> call = AppConfig.loadInterface().sign_up(number, name, email, birthday, gender, age);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String resdata = response.body().string();
                    JSONObject object = new JSONObject(resdata);
                    String error = object.getString("status");
                    System.out.println("resdata:::" + resdata);
                    if (error.equals("1")) {
                        String res = object.getString("result");
                        JSONObject jsonObject = new JSONObject(res);
                        saveData(getApplicationContext(), "ldata", res + "");
                        String u_id = jsonObject.getString("id");
                        String u_userid = jsonObject.getString("uid");
                        String u_name = jsonObject.getString("full_name");
                        String u_email = jsonObject.getString("email");
                        String u_contact = jsonObject.getString("contact");
                        saveData(getApplicationContext(), "U_ID", u_id);
                        Intent intent = new Intent(SingupActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.d("ress", resdata);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    private void signUpMethod1(final String picturePath,final String number, final String name, final String email, final String birthday, final String gender, final String age) {

        myDialog = DialogUtils.showProgressDialog(SingupActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIGNUP,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                Result result = new Result();
                                result.setId(jsonObject.getString("id"));
                                result.setUid(jsonObject.getString("uid"));
                                result.setFullName(jsonObject.getString("full_name"));
                                result.setEmail(jsonObject.getString("email"));
                                result.setContact(jsonObject.getString("contact"));
                                result.setLatitude(jsonObject.getString("latitude"));
                                result.setLongitude(jsonObject.getString("longitude"));
                                result.setAge(jsonObject.getString("age"));
                                result.setDob(jsonObject.getString("dob"));
                                result.setProfilePicture(jsonObject.getString("profile_picture"));
                             //   result.setBio(jsonObject.getString("bio"));
                                result.setGender(jsonObject.getString("gender"));
                                //result.setLanguage(jsonObject.getString("language"));
                                result.setRelationship(jsonObject.getString("relationship"));
                                result.setCountry(jsonObject.getString("country"));
                                result.setState(jsonObject.getString("state"));
                                result.setCity(jsonObject.getString("city"));
                                result.setIsVerified(jsonObject.getString("is_verified"));
                                IndifunApplication.getInstance().saveCredential(result);
                                saveData(getApplicationContext(), "ldata", jsonObject + "");
                                String u_id = jsonObject.getString("id");
                                String u_userid = jsonObject.getString("uid");
                                String u_name = jsonObject.getString("full_name");
                                String u_email = jsonObject.getString("email");
                                String u_contact = jsonObject.getString("contact");
                                saveData(getApplicationContext(), "U_ID", u_id);
                                Intent intent = new Intent(SingupActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(SingupActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(SingupActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("contact", number);
                params.put("email", email);
                params.put("age", age);
                params.put("full_name", name);
                params.put("gender", gender);
                params.put("dob", birthday);
                params.put("image", picturePath);
                params.put("country", mycountry);
                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               *//* Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.TastyTiffinKEY, AppConstants.TastyTiffinKEYVALUE);
                return params;*//*
               return null;
            }*/
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    void getCountryNames(){
        Call<CountryNamesResult> call = AppConfig.loadInterface().getCountry();
        call.enqueue(new Callback<CountryNamesResult>() {
            @Override
            public void onResponse(Call<CountryNamesResult> call, retrofit2.Response<CountryNamesResult> response) {

                CountryNamesResult result = response.body();

                loge("SIGNUP", new Gson().toJson(result));
                if(result!=null &&  result.getStatus()==1 && result.getResult()!=null){
                    ArrayList<String> newdata = new ArrayList<>();

                    for (CountryNamesResult.MyCountry country:result.getResult()){
                        newdata.add(country.getCountry());
                    }
                    Collections.sort(newdata);
                    newdata.add("Other");
                    initSpinner(newdata);
                }
            }

            @Override
            public void onFailure(Call<CountryNamesResult> call, Throwable t) {

            }
        });
    }
}