package com.example.firus.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.firus.R;

public class Shorts extends Fragment {
    VideoView videoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shorts, container, false);
        videoView = view.findViewById(R.id.videoView);

        Uri uri = Uri.parse("https://www.youtube.com/watch?v=284Ov7ysmfA");
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(getActivity());
        videoView.start();
        videoView.setMediaController(mediaController);
        return view;
    }
}