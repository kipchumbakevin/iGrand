package com.igrandbusiness.mybusinessplans.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.ViewPDF;
import com.igrandbusiness.mybusinessplans.models.UserDocs;
import com.igrandbusiness.mybusinessplans.utils.Constants;

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
        holder.uri = Constants.BASE_URL + "clientDocs/" +userDocs.getUrl();
        holder.titl = userDocs.getTitle();
    }

    @Override
    public int getItemCount() {
        return mDocsArrayList.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        String uri,titl;
        LinearLayoutCompat layoutCompat;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            layoutCompat = itemView.findViewById(R.id.linear);

            layoutCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewPDF.class);
                    intent.putExtra("URI",uri);
                    intent.putExtra("TITLE",titl);
                    mContext.startActivity(intent);
                }
            });
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
