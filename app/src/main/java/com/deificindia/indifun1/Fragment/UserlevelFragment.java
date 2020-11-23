package com.deificindia.indifun1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.deificindia.indifun1.Model.Userlevel_Response;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.rest.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserlevelFragment extends Fragment {
    TextView tonextlvl, MyEp;
//    private List<Userlevel_Result> ulmodelList = new ArrayList<>();

    public  static UserlevelFragment getInstance(){
        UserlevelFragment userlevelFragment = new UserlevelFragment();
        return userlevelFragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.userlevel_layout, container,false);
        tonextlvl = view.findViewById(R.id.tonextlvl);
        MyEp = view.findViewById(R.id.MyEp);
        getdata();
        return  view;
    }

    private void getdata()
    {
        String uid = MySharePref.getUserId();
        Call<Userlevel_Response> ulmodelList = AppConfig.loadInterface().getUserLevel(uid);
        ulmodelList.enqueue(new Callback<Userlevel_Response>() {
            @Override
            public void onResponse(Call<Userlevel_Response> call, Response<Userlevel_Response> response) {
                Userlevel_Response List = response.body();
                if(List!=null && List.getStatus()==1 && List.getResult()!=null) {
                    tonextlvl.setText(List.getResult().getToNextLevel());
                    MyEp.setText(List.getResult().getMyXp());
                }
//                likelist.setAdapter(new LikeAdapter(List.getResult(), getContext()));
                // Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Userlevel_Response> call, Throwable t) {
                // Toast.makeText(getContext(), "error occcured", Toast.LENGTH_LONG).show();

            }
        });
    }

}
