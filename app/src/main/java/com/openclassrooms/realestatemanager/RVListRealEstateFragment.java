package com.openclassrooms.realestatemanager;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RVListRealEstateFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<RealEstate> mItemRealEstate;
    private Drawable drawable1, drawable2, drawable3, drawable4, drawable5, drawable6;
    private RVListRealEstateViewModel mViewModel;

    public static RVListRealEstateFragment newInstance() {
        return new RVListRealEstateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rv_real_estate, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, new ViewModelProvider.NewInstanceFactory()).get(RVListRealEstateViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_real_estate);
        setRV();
    }

    private void setRV() {
        setDatasRV();
        ListRealEstateRVAdapter mAdapter = new ListRealEstateRVAdapter(mItemRealEstate, requireContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setDatasRV() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable1 = ContextCompat.getDrawable(requireContext(), R.drawable.house_one);
            drawable2 = ContextCompat.getDrawable(requireContext(), R.drawable.house_two);
            drawable3 = ContextCompat.getDrawable(requireContext(), R.drawable.house_three);
            drawable4 = ContextCompat.getDrawable(requireContext(), R.drawable.house_four);
            drawable5 = ContextCompat.getDrawable(requireContext(), R.drawable.house_five);
            drawable6 = ContextCompat.getDrawable(requireContext(), R.drawable.house_six);
        } else {
            ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.house_one, null);
            ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.house_two, null);
            ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.house_three, null);
            ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.house_four, null);
            ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.house_five, null);
            ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.house_six, null);
        }


        mItemRealEstate = Arrays.asList(
                new RealEstate("Maison1", 120000, 520.25f, 16, "Description1",
                        "adresse1", "Rien", drawable1, false, "12/2/2019", "0", "Jean"),
                new RealEstate("Maison2", 210000, 520.25f, 16, "Description2",
                        "adresse2", "Rien", drawable2, false, "12/2/2019", "0", "Jean"),
                new RealEstate("Maison3", 170000, 520.25f, 16, "Description3",
                        "adresse3", "Rien", drawable3, false, "12/2/2019", "0", "Jean"),
                new RealEstate("Maison4", 260000, 520.25f, 16, "Description4",
                        "adresse4", "Rien", drawable4, false, "12/2/2019", "0", "Jean"),
                new RealEstate("Maison5", 190000, 520.25f, 16, "Description5",
                        "adresse5", "Rien", drawable5, false, "12/2/2019", "0", "Jean"),
                new RealEstate("Maison6", 230000, 520.25f, 16, "Description6",
                        "adresse6", "Rien", drawable6, false, "12/2/2019", "0", "Jean"));
    }
}