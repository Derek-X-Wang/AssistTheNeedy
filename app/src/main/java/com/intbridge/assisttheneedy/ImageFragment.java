package com.intbridge.assisttheneedy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Derek on 9/11/2015.
 */
public class ImageFragment extends Fragment {

    ImageView imageView;

    private int mPosition = 0;

    public static ImageFragment newInstance(int param1) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("POSITION");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageview);
        String resourceName =  "frame"+(mPosition+1);
        int id = getResources().getIdentifier(resourceName, "mipmap", "com.intbridge.assisttheneedy");
        imageView.setImageResource(id);
        return view;
    }

}
