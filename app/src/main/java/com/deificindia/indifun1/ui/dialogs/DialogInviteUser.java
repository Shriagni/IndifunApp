package com.deificindia.indifun1.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.Adapter.InviteUserAdapter;
import com.deificindia.indifun1.Model.ModalInviteUser;
import com.deificindia.indifun1.R;

import java.util.ArrayList;
import java.util.List;

public class DialogInviteUser extends DialogFragment {

    InviteUserAdapter adapter;
    List<ModalInviteUser> users = new ArrayList<>();
    private InviteUserAdapter.OnInviteClicked _listener;

    public DialogInviteUser(List<ModalInviteUser> users) {
        this.users = users;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        setStyle(style, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogBottomAnimation;
        View v  = inflater.inflate(R.layout.dialog_invite_user, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
    }

    private void initUI(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.recyclerInviteUser);
        adapter = new InviteUserAdapter(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnInviteClicked(this._listener);
        pulishData(users);

    }

    public void pulishData(List<ModalInviteUser> users){
        adapter.updateUser(users);
    }


    public static void showDialog(AppCompatActivity act, DialogInviteUser dialog) {
        FragmentTransaction frag  = act.getSupportFragmentManager().beginTransaction();
        Fragment prev = act.getSupportFragmentManager().findFragmentByTag("DialogInviteUser");
        if (prev != null) {
            frag.remove(prev);
        }
        frag.addToBackStack(null);
        dialog.setCancelable(true);
        dialog.show(frag, "QualitySelectDialog");
    }


    public void setOnInviteClicked(InviteUserAdapter.OnInviteClicked listener){
        this._listener = listener;
    }



}
