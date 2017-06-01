package io.github.projektmedinf.wifisdcryptolocker.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import io.github.projektmedinf.wifisdcryptolocker.R;
import io.github.projektmedinf.wifisdcryptolocker.activities.ImageDetailsActivity;
import io.github.projektmedinf.wifisdcryptolocker.model.Image;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;
import io.github.projektmedinf.wifisdcryptolocker.service.impl.SessionServiceImpl;
import io.github.projektmedinf.wifisdcryptolocker.utils.GridViewAdapter;

import java.util.ArrayList;

public class GalleryFragment extends android.support.v4.app.Fragment {

    //Todo Remove
    public static int imageStartId = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) view.findViewById(R.id.autocompleteView);
        final GridView imageGridView = (GridView) view.findViewById(R.id.gridView);

        ArrayAdapter<Session> sessionListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new SessionServiceImpl(getActivity()).getAllSessions());
        autocompleteView.setAdapter(sessionListAdapter);
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Session session = (Session) arg0.getItemAtPosition(arg2);
                Log.d("selected", session.toString());
                imageStartId = (int) (session.getId() % 3);
                //Todo replace getData() with getImagesBySession
                imageGridView.setAdapter(new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData()));
            }
        });

        imageGridView.setAdapter(new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData()));
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Image image = (Image) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ImageDetailsActivity.class);
                intent.putExtra("title", image.getTitle());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // Prepare some dummy data for gridview
    private ArrayList<Image> getData() {
        final ArrayList<Image> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = imageStartId; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            Image image = new Image(0l, null, null, 0, null, null);
            image.setTitle(Integer.toString(i));
            image.setImage(bitmap);
            imageItems.add(image);
        }
        return imageItems;
    }
}
