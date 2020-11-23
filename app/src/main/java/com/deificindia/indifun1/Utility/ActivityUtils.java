package com.deificindia.indifun1.Utility;

import android.content.Context;
import android.content.Intent;

import com.deificindia.indifun1.Activity.UserDetailsActivityActivity;

public class ActivityUtils {


    public static void openUserDetailsActivity(Context cnx, String uuid, String username){
        Intent intent = new Intent(cnx, UserDetailsActivityActivity.class);

        intent.putExtra("UID", uuid);
        intent.putExtra("NAME", username);
        cnx.startActivity(intent);

    }





}
