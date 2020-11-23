package com.deificindia.indifun1.ui.IndiTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.R;

import java.util.List;

public class IndiTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<IndiViewModel> items;
    IndiTabItemClickListener _listener;
    int selectedItemPost = 0;
    Context _context;

    int selectedColor = R.color.blue_dialog;
    int unselectedColor = R.color.blue_cancel;

    public IndiTabAdapter(List<IndiViewModel> items, Context _context) {
        this.items = items;
        this._context = _context;
    }

    void setIndiTabItemClickListener(IndiTabItemClickListener listener){
        this._listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inditab, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tabtext;
        View parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tabtext = itemView.findViewById(R.id.tabText);
            parent = itemView.findViewById(R.id.itemcontainer);

            itemView.setOnClickListener(v->{
                selectedItemPost = getAdapterPosition();
                if(_listener!=null) _listener.onItemClick(selectedItemPost);
                notifyDataSetChanged();
            });
        }

        public void bind(IndiViewModel item){
            tabtext.setText(item.title);
            tabtext.setTextColor(ContextCompat.getColor(_context, R.color.white));

            if(getAdapterPosition() == selectedItemPost){
                parent.setBackgroundColor(ContextCompat.getColor(_context, selectedColor));
            }else {
                parent.setBackgroundColor(ContextCompat.getColor(_context, unselectedColor));
            }
        }
    }


}
