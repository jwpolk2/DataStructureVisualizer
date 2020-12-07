package com.example.datastructurevisualizer.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datastructurevisualizer.R;
import com.example.datastructurevisualizer.File;
import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileNote> {
    ArrayList<File> mFile;
    private onItemClickListener mListener;

    FileAdapter(ArrayList<File> mFile) {
        this.mFile = mFile;
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }



    public static class FileNote extends RecyclerView.ViewHolder {
        private TextView fileName;
        private TextView structureType;
        private TextView dateModified;

        public FileNote(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileViewName);
            structureType = itemView.findViewById(R.id.fileViewType);
            dateModified = itemView.findViewById(R.id.fileViewDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public FileAdapter.FileNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_view, parent, false);
        return new FileNote(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.FileNote holder, int position) {
        File holderA = mFile.get(position);

        holder.fileName.setText(holderA.getFileName());
        holder.structureType.setText(holderA.getStructureType());
        holder.dateModified.setText(holderA.getDateModified());
    }

    // convenience method for getting data at click position
    public File getItem(int id) {
        return mFile.get(id);
    }

    public int getItemCount() {
        return mFile.size();
    }
}

