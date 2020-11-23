package com.deificindia.indifun1.agorlive.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.elvishew.xlog.XLog;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import com.deificindia.indifun1.agorlive.Config;
import com.deificindia.indifun1.agorlive.proxy.model.request.Request;
import com.deificindia.indifun1.agorlive.proxy.model.request.UserRequest;
import com.deificindia.indifun1.agorlive.proxy.model.response.AppVersionResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.CreateUserResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.LoginResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.MusicListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.Response;
import com.deificindia.indifun1.agorlive.ui.BaseActivity;
import com.deificindia.indifun1.agorlive.utils.Global;
import com.deificindia.indifun1.agorlive.utils.RandomUtil;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NETWORK_CHECK_INTERVAL = 10000;
    private static final int MAX_PERIODIC_APP_ID_TRY_COUNT = 5;

    private BottomNavigationView mNavView;
    private NavController mNavController;
    private int mAppIdTryCount;

    @Override
    protected void onSendGift(int position, GiftInfo2 info) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(true);
        setContentView(R.layout.activity_main);
        initUI();
        //initAsync();
    }

    private void initUI() {
        initNavigation();
    }

    private void initNavigation() {
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mNavView = findViewById(R.id.nav_view);
        mNavView.setItemIconTintList(null);
        changeItemHeight(mNavView);
        mNavView.setOnNavigationItemSelectedListener(item -> {
            int selectedId = item.getItemId();
            int currentId = mNavController.getCurrentDestination() == null ?
                    0 : mNavController.getCurrentDestination().getId();

            // Do not respond to this click event because
            // we do not want to refresh this fragment
            // by repeatedly selecting the same menu item.
            if (selectedId == currentId) return false;
            NavigationUI.onNavDestinationSelected(item, mNavController);
            hideStatusBar(getWindow(), true);
            return true;
        });
    }

    public int getSystemBarHeight() {
        return systemBarHeight;
    }

    public void setNavigationSelected(int resId, Bundle bundle) {
        mNavView.setSelectedItemId(resId);
        mNavController.navigate(resId, bundle);
    }

    private void changeItemHeight(@NonNull BottomNavigationView navView) {
        // Bottom navigation menu uses a hardcode menu item
        // height which cannot be changed by a layout attribute.
        // Change the item height using reflection for
        // a comfortable padding between icon and label.
        int itemHeight = getResources().getDimensionPixelSize(R.dimen.nav_bar_height);
        BottomNavigationMenuView menu =
                (BottomNavigationMenuView) navView.getChildAt(0);
        try {
            Field itemHeightField = BottomNavigationMenuView.class.getDeclaredField("itemHeight");
            itemHeightField.setAccessible(true);
            itemHeightField.set(menu, itemHeight);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
