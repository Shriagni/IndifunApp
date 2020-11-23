package com.deificindia.indifun1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.deificindia.indifun1.R;

public class BroadcasterLvFragment extends Fragment {

    public  static BroadcasterLvFragment getInstance(){
        BroadcasterLvFragment broadcasterLvFragment = new BroadcasterLvFragment();
        return broadcasterLvFragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.broadcasterlevel_layout, container,false);

        return  view;
    }
}
