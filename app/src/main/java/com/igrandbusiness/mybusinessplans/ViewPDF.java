package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class ViewPDF extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {

    PDFView pdfView;
    String pdf;
    SeekBar seekBar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdfView = findViewById(R.id.pdfviewer);
        progressBar = findViewById(R.id.progress);
        seekBar = findViewById(R.id.seekbar);
        pdf = getIntent().getExtras().getString("URI");
        progressBar.setVisibility(View.VISIBLE);

        FileLoader.with(this).load(pdf,false)
                .fromDirectory("My_Pdfs",FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progressBar.setVisibility(View.GONE);
                        File file = response.getBody();
                        try {
                            pdfView.fromFile(file)
                                    .defaultPage(0)
                                    .enableAnnotationRendering(true)
                                    .onLoad(ViewPDF.this)
                                    .scrollHandle(new DefaultScrollHandle(ViewPDF.this))
                                    .spacing(2)
                                    .onPageError(ViewPDF.this)
                                    .load();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ViewPDF.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void loadComplete(int nbPages) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int page, Throwable t) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(ViewPDF.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
