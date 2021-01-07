package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ViewPDF extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {
    private static final int PERMISSION_STORAGE_CODE = 1000;
    PDFView pdfView;
    String pdf,name;
    Uri uri;
    SeekBar seekBar;
    TextView percent;
    ProgressBar progressBar;
    DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdfView = findViewById(R.id.pdfviewer);
        progressBar = findViewById(R.id.progress);
        seekBar = findViewById(R.id.seekbar);
        percent = findViewById(R.id.textView);
        pdf = getIntent().getExtras().getString("URI");
        name = getIntent().getExtras().getString("TITLE");
        setTitle(name);
        progressBar.setVisibility(View.VISIBLE);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        uri = Uri.parse(pdf);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.download) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                }
                else {
                    startDownloading();
                    //new DownloadTask(ViewPDF.this, "http://www.codeplayon.com/samples/resume.pdf");
                }
            }
            else {
                startDownloading();
               // new DownloadTask(ViewPDF.this, "http://www.codeplayon.com/samples/resume.pdf");
            }

//            DownloadManager.Request request = new DownloadManager.Request(uri);
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            Long reference = downloadManager.enqueue(request);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startDownloading();
                }
                else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startDownloading() {
        String pp = "https://bobcarp.files.wordpress.com/2014/07/code-charles-petzold.pdf".trim();
       // String pp = pdf.trim();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pp));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(name);
        request.setDescription("Downloading file");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+name);
        DownloadManager manager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
