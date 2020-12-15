package com.igrandbusiness.mybusinessplans.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.khizar1556.mkvideoplayer.MKPlayerActivity;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoHolder> {
    private final Context mContext;
    private final ArrayList<ReceiveData> mContentArray;
    private final LayoutInflater mLayoutInflator;
    public VideosAdapter(Context context, ArrayList<ReceiveData>videosArray){
        mContext = context;
        mContentArray = videosArray;
        mLayoutInflator = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.list_layouts,null);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        ReceiveData receiveData = mContentArray.get(position);
        holder.title.setText(receiveData.getTitle());
        holder.uri = Uri.parse(Constants.BASE_URL + "videos/" + receiveData.getUrl());
    }

    @Override
    public int getItemCount() {
        return mContentArray.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        TextView title;
        Uri uri;
        File file;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
//            FileLoader.with(mContext).load(uri,false)
//                    .fromDirectory("My_Pdfs",FileLoader.DIR_INTERNAL)
//                    .asFile(new FileRequestListener<File>() {
//                        @Override
//                        public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                            file = response.getBody();
//                            try {
//                                MKPlayerActivity.configPlayer((Activity) mContext).play(Uri.parse(file));
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onError(FileLoadRequest request, Throwable t) {
//                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //MKPlayerActivity.configPlayer((Activity) mContext).play(uri);

                }
            });
        }
    }
}
