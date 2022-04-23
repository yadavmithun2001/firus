package com.example.firus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firus.R;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.viewholder> {
    Context context;
    File[] filesandfolder;

    public DownloadAdapter(Context context, File[] filesandfolder) {
        this.context = context;
        this.filesandfolder = filesandfolder;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_download_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        File selectedfiles = filesandfolder[position];
        holder.filename.setText(selectedfiles.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(selectedfiles.getAbsolutePath()),"image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return filesandfolder.length;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView filename;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            filename = itemView.findViewById(R.id.filename);
        }
    }
}
