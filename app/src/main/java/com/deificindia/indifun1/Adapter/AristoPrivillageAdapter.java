package com.deificindia.indifun1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.bindingmodals.aristocracycenterprivilage.AristoResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.deificindia.indifun1.Utility.URLs.PRIVILEGES;

public class AristoPrivillageAdapter extends RecyclerView.Adapter<AristoPrivillageAdapter.AristoHolder> {

    List<AristoResult> aristoResults;

    public AristoPrivillageAdapter(List<AristoResult> aristoResults) {
        this.aristoResults = aristoResults;
    }

    @NonNull
    @Override
    public AristoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AristoHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aristo_privillage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AristoHolder holder, int position) {
        holder.bind(aristoResults.get(position));
    }

    @Override
    public int getItemCount() {
        return aristoResults.size();
    }

    class AristoHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv;

        public AristoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tv = itemView.findViewById(R.id.text1);
        }

        void bind(AristoResult res){
            Picasso.get().load(PRIVILEGES+ res.getCover())
                    .into(imageView);
            tv.setText(res.getTitle());
        }
    }
}
