package com.igrandbusiness.mybusinessplans;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long reference = downloadManager.enqueue(request);
        }
        return super.onOptionsItemSelected(item);
    }
//    private void downloadPdf(final String fileName) {
//        new AsyncTask<Void, Integer, Boolean>() {
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                return downloadPdf();
//            }
//
//            @Nullable
//            private boolean downloadPdf() {
//                try {
//                    File file = getFileStreamPath(fileName);
//                    if (file.exists())
//                        return true;
//                    try {
//                        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//                        URL u = new URL(pdf);
//                        URLConnection urlConnection = u.openConnection();
//                        int contentlength = urlConnection.getContentLength();
//                        InputStream input = new BufferedInputStream(u.openStream());
//                        byte data[] = new byte[contentlength];
//                        long total = 0;
//                        int count;
//                        while ((count = input.read(data)) != -1) {
//                            total += count;
//                            publishProgress((int) ((total * 100) / contentlength));
//                            fileOutputStream.write(data, 0, count);
//                        }
//                        fileOutputStream.flush();
//                        fileOutputStream.close();
//                        input.close();
//                        return true;
//                    } catch (final Exception e) {
//                        e.printStackTrace();
//                        return false; //swallow a 404
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                super.onProgressUpdate(values);
//                seekBar.setProgress(values[0]);
//            }
//
//            @Override
//            protected void onPostExecute(Boolean aBoolean) {
//                super.onPostExecute(aBoolean);
//                if (aBoolean) {
//                    seekBar.setVisibility(View.GONE);
//                    percent.setVisibility(View.GONE);
//                    Toast.makeText(ViewPDF.this, "Download complete", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ViewPDF.this, "Unable to download PDF", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }.execute();
//
//    }
//        private void initSeekBar () {
//        seekBar.setVisibility(View.VISIBLE);
//        percent.setVisibility(View.VISIBLE);
//            seekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//            seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                                                   @Override
//                                                   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                                                       int val = (progress * (seekBar.getWidth() - 3 * seekBar.getThumbOffset())) / seekBar.getMax();
//                                                       percent.setText("" + progress);
//                                                       percent.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
//                                                   }
//
//                                                   @Override
//                                                   public void onStartTrackingTouch(SeekBar seekBar) {
//
//                                                   }
//
//                                                   @Override
//                                                   public void onStopTrackingTouch(SeekBar seekBar) {
//
//                                                   }
//                                               }
//            );
//    }

}
