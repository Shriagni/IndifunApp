package com.deificindia.indifun1.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.deificindia.indifun1.R;

public class FullScreenDialog extends DialogFragment {

    OnOkClickListener _listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.go_live_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btngotit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_listener!=null) _listener.onOkClick();
                dismiss();
            }
        });

        view.findViewById(R.id.imgClose).setOnClickListener(v->{
            dismiss();
        });
    }

    public interface OnOkClickListener{
        void onOkClick();
    }

    public void setOnOkClickListener(OnOkClickListener listener){
        this._listener = listener;
    }
}
