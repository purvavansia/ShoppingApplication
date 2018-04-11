package com.example.purva.shoppingapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by purva on 4/10/18.
 */

public class NecklaceFragment extends Fragment {

    ArrayList NecklaceNames = new ArrayList<>(Arrays.asList("Figaro chain", "Curb chain", "Omega chain", "Ball chain", "Diamond chain", "Gold chain"));
    ArrayList NecklacePrices = new ArrayList<>(Arrays.asList("$10.00", "$15.00", "$7.00", "$12.00", "$25.00", "$23.00"));
    ArrayList NecklaceImages = new ArrayList<>(Arrays.asList(R.drawable.necklace1, R.drawable.necklace2, R.drawable.necklace3,
            R.drawable.necklace4, R.drawable.necklace5, R.drawable.necklace6));
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_necklaces,container,false);

        recyclerView = view.findViewById(R.id.recyclerViewNecklace);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        CustomAdapterGrid customAdapterGrid = new CustomAdapterGrid(getActivity(),NecklaceNames,NecklaceImages,NecklacePrices);
        recyclerView.setAdapter(customAdapterGrid);
        return view;
    }
}
