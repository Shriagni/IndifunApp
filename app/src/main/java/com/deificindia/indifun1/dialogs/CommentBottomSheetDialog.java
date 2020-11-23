package com.deificindia.indifun1.dialogs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun1.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentBottomSheetDialog extends BottomSheetDialogFragment {

    OnMessageSent _listener;

    ImageView btnSend;
    EditText commentBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comment_bottom_sheet_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSend = view.findViewById(R.id.imgsend);
        commentBox = view.findViewById(R.id.et_comment);

        btnSend.setOnClickListener(v->{
            String msg = commentBox.getText().toString();
            if(!msg.isEmpty()){
                if (_listener!=null){
                    _listener.onSend(msg);
                }
                dismiss();
            }else {
                Toast.makeText(getContext(), "Type some message", Toast.LENGTH_SHORT).show();
            }
        });

        commentBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    btnSend.setImageResource(R.drawable.ic_send_active);
                }else {
                    btnSend.setImageResource(R.drawable.ic_send_inactive);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public interface OnMessageSent{
        void onSend(String message);

    }

    public void setOnMessageSent(OnMessageSent listener){
        this._listener = listener;
    }




}
