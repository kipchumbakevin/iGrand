package com.igrandbusiness.mybusinessplans.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.models.UserDocs;

import java.util.ArrayList;

public class Clientdetailsadapter extends RecyclerView.Adapter<Clientdetailsadapter.ClientViewHolder> {

    private final Context mContext;
    private final ArrayList<UserDocs> mDocsArrayList;
    private final LayoutInflater mLayoutInflator;

    public Clientdetailsadapter(Context context, ArrayList<UserDocs>docsArrayList){
        mContext = context;
        mDocsArrayList = docsArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_layouts,null);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        UserDocs userDocs = mDocsArrayList.get(position);
        holder.title.setText(userDocs.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDocsArrayList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
