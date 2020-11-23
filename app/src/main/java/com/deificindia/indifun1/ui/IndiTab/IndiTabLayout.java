package com.deificindia.indifun1.ui.IndiTab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.R;

import java.util.ArrayList;
import java.util.List;

public class IndiTabLayout extends ScrollView {

    View parent;
    RecyclerView recyclerView;
    IndiTabAdapter adapte;

    List<IndiViewModel> _items = new ArrayList<>();

    public IndiTabLayout(Context context) {
        super(context);
        init(context);
    }

    public IndiTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndiTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context cnx){
        LayoutInflater.from(cnx).inflate(R.layout.inditab_layout, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        parent = findViewById(R.id.parentContainer);
        recyclerView = findViewById(R.id.tabRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        adapte = new IndiTabAdapter(_items, getContext());

        recyclerView.setAdapter(adapte);


    }

    public void setIndiTabItemClickListener(IndiTabItemClickListener listener){
        adapte.setIndiTabItemClickListener(listener);
    }

    public void setItems(List<IndiViewModel> items){
        _items.clear();
        _items.addAll(items);
        adapte.notifyDataSetChanged();
    }


}
