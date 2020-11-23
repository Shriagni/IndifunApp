package com.deificindia.indifun1.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun1.Adapter.ImageListAdapter;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.dialogs.FilterBottomSheetDialog;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.ui.LoadingAnimView;

import com.deificindia.indifun1.ui.imagepicker.Config;
import com.deificindia.indifun1.ui.imagepicker.ImagePickerActivity;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;

public class PostFragment extends Fragment  {
    private static final int REQUEST_IMAGE = 100;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;

    ViewPager viewPager;
    TabLayout tabLayout;
    private ImageView  call_board;
    private RelativeLayout btn_continue;
    private EditText enterpostcontent;
    private ImageView postimages, opendialog;
    private AlertDialog alertDialog;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private List<String> picturePath;
    private ArrayList<String> preselected = new ArrayList<>();

    RecyclerView recyclerView;
    ImageListAdapter imageListAdapter;
    List<Hotpostmodel.MyResult.MyImages> images;

    LoadingAnimView loadingAnimView;

    int pageNumber = 0;

    public PostFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_post, container, false);

        tabLayout = v.findViewById(R.id.FragmentTab);
        viewPager = v.findViewById(R.id.viewpager);
        call_board = v.findViewById(R.id.call_board);
        opendialog = v.findViewById(R.id.opendialog);

        call_board.setVisibility(View.INVISIBLE);

        setupViewPager(viewPager);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.viewpager, new PostFollowFragment());
        transaction.commit();

        clicklisteners(v);

        picturePath = new ArrayList<String>();

        return v;
    }

    void clicklisteners(View v1){
        opendialog.setOnClickListener(v->{
            if(pageNumber==2){
                FilterBottomSheetDialog filterDialog = new FilterBottomSheetDialog();
                filterDialog.show(getChildFragmentManager(), "FilterDialog");
            }else {
                openPostDialog();
            }
        });
    }

    void openPostDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.layout_addpost, null, false);

        enterpostcontent = dialogView.findViewById(R.id.enterpostcontent);
        postimages = dialogView.findViewById(R.id.postimages);
        btn_continue = dialogView.findViewById(R.id.btn_continue);
        recyclerView = dialogView.findViewById(R.id.recyclerView2);

        //void bind(List<Hotpostmodel.MyResult.MyImages> images){
        imageListAdapter = new ImageListAdapter(picturePath, getContext(), 1);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(imageListAdapter);
        //}

        builder1.setView(dialogView);
        alertDialog = builder1.create();

        postimages.setOnClickListener(v1 -> openMediaDialog());

        btn_continue.setOnClickListener(v1 -> {
            if (enterpostcontent.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enterpostcontent), Toast.LENGTH_SHORT).show();
               /* } else  if (postimages.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.add_a_photo).getConstantState()) {
                    Toast.makeText(getActivity(), getString(R.string.pleaseuploadpostimage), Toast.LENGTH_SHORT).show();*/
            }else if(picturePath==null || picturePath.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.pleaseuploadpostimage), Toast.LENGTH_SHORT).show();
            }else {
                myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
                addpost2(enterpostcontent.getText().toString().trim(), picturePath);
            }

        });

        alertDialog.show();
    }

    void addpost2(final String content, final List<String> pictures){
        Call<PostModal> call = AppConfig.loadInterface().add_post(
                IndifunApplication.getInstance().getCredential().getId(),
                content,
                pictures);

        call.enqueue(new Callback<PostModal>() {
            @Override
            public void onResponse(Call<PostModal> call, retrofit2.Response<PostModal> response) {

                PostModal model = response.body();
                myDialog.dismiss();
                //loge("HOTFRAG",new Gson().toJson(model) );
                if(model!=null && model.getStatus()==1){
                    Toast.makeText(getContext(), "Posted successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getContext(), "Failed to submit post", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PostModal> call, Throwable t) {
                //swipe_friend.setRefreshing(false);
                loge("HotfragErr", t.getMessage());
                myDialog.dismiss();
                Toast.makeText(getContext(), "Some error in posting, try again later.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void openMediaDialog() {
        Config config = new Config();
        //config.setCameraHeight(R.dimen.app_camera_height);
        //config.setToolbarTitleRes(R.string.custom_title);
        config.setSelectionMin(1);
        config.setSelectionLimit(9);
        //config.setSelectedBottomHeight(R.dimen.bottom_height);

        ImagePickerActivity.setConfig(config);

        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    private void showImagePickerOptions() {
       /* ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent2();
            }
        });*/
    }

  /*  private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
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
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE);
    }*/

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    picturePath.add(encodeTobase64(bitmap));
                    //addAvatar.setVisibility(View.VISIBLE);
                    postimages.setImageBitmap(bitmap);
                    /*imgUser.setImageBitmap ( bitmap );
                    strImg1=encodeTobase64(bitmap);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == Activity.RESULT_OK ) {

            ArrayList<Uri>  image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

            loge("PostFrag", "onActivityResult", toGson(image_uris));

            //do something
        }

    }

    void parseImage(){

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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PostFollowFragment(), "Follow");
        adapter.addFragment(new PostPublicFragment(), "Public");
        adapter.addFragment(new PostRecommendedFragment(), "Recomended");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==2){
                    opendialog.setImageResource(R.drawable.ic_filter);
                }else {
                    opendialog.setImageResource(R.drawable.ic_send_message_svgrepo_com);
                }
                pageNumber = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


   /* @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
            loge("ExploreFrag","loadImage", imageUri.toString());
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        loge("ExploreFrag","onMultiImageSelected", uriList.toString());
        for(Uri u:uriList){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), u);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        picturePath.add(encodeTobase64(bitmap));
                    }
                }).start();

        }
        if(picturePath!=null && !picturePath.isEmpty()){
            postimages.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        imageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {
        loge("ExploreFrag", "onCancelled", ""+isMultiSelecting);
    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        loge("ExploreFrag","onSingleImageSelected", uri.toString());
    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}