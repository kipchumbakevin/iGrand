package com.igrandbusiness.mybusinessplans.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
    private final Context mContext;
    private final ArrayList<ReceiveData> mContentArray;
    private final LayoutInflater mLayoutInflator;

    public ContentAdapter(Context context, ArrayList<ReceiveData>contentArray){
        mContext = context;
        mContentArray = contentArray;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_layouts,null);
        return new ContentHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        ReceiveData receiveData = mContentArray.get(position);
        holder.title.setText(receiveData.getTitle());
    }
    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class ContentHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
