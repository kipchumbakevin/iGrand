package com.igrandbusiness.mybusinessplans.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.ViewPDF;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;

public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.MagazineView> {
    private final Context mContext;
    private final ArrayList<ReceiveData> mContentArray;
    private final LayoutInflater mLayoutInflator;
    public MagazineAdapter(Context context, ArrayList<ReceiveData>magazines){
        mContext = context;
        mContentArray = magazines;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public MagazineAdapter.MagazineView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_layouts,null);
        return new MagazineView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MagazineAdapter.MagazineView holder, int position) {
        ReceiveData receiveData = mContentArray.get(position);
        holder.title.setText(receiveData.getTitle());
        holder.uri = Constants.BASE_URL + "magazine/" + receiveData.getUrl();
        holder.titl = receiveData.getTitle();
    }

    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class MagazineView extends RecyclerView.ViewHolder {
        TextView title;
        String uri,titl;
        public MagazineView(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewPDF.class);
                    intent.putExtra("URI",uri);
                    intent.putExtra("TITLE",titl);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
