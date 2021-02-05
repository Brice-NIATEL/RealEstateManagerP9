package com.openclassrooms.realestatemanager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Constants;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.viewmodel.DescriptionRealEstateActivityViewModel;

public class DescriptionRealEstateFragment extends Fragment {
    private static final String TAG = "DescRealEstateFragment";

    private DescriptionRealEstateActivityViewModel mViewModel;

    private ImageView mImage;
    private TextView mDescription;
    private TextView mLocation1, mLocation2, mLocation3;
    private TextView mSurface, mNumberRoom, mNumberBedroom, mNumberBathroom;

    public static DescriptionRealEstateFragment newInstance() {
        return new DescriptionRealEstateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description_fragment, container, false);

        mImage = view.findViewById(R.id.iv_description);

        mDescription = view.findViewById(R.id.tv_description);

        mLocation1 = view.findViewById(R.id.tv_location1);
        mLocation2 = view.findViewById(R.id.tv_location2);
        mLocation3 = view.findViewById(R.id.tv_location3);

        mSurface = view.findViewById(R.id.tv_surface);
        mNumberRoom = view.findViewById(R.id.tv_room);
        mNumberBedroom = view.findViewById(R.id.tv_bedroom);
        mNumberBathroom = view.findViewById(R.id.tv_bathroom);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DescriptionRealEstateActivityViewModel.class);

        if (getArguments() != null) {
            long id = getArguments().getLong(Constants.BUNDLE_ID);
            Log.d(TAG, "onActivityCreated: id = " + id);

            mViewModel.getRealestateById(id).observe((LifecycleOwner) requireContext(), new Observer<RealEstate>() {
                @Override
                public void onChanged(RealEstate realEstate) {
                    //TODO Point d'intéret à faire
                    mImage.setImageBitmap(mViewModel.getBitmap(realEstate.getImage()));

                    mDescription.setText(realEstate.getDescription());

                    mLocation1.setText(new StringBuilder(realEstate.getAddress().getNumber() + " " + realEstate.getAddress().getStreet()));
                    mLocation2.setText(realEstate.getAddress().getPostalCode());
                    mLocation3.setText(realEstate.getAddress().getTown());

                    mSurface.setText(new StringBuilder(realEstate.getSurface() + " m2"));
                    mNumberRoom.setText(String.valueOf(realEstate.getPieceNumber()));
                    mNumberBedroom.setText(String.valueOf(realEstate.getBedroomNumber()));
                    mNumberBathroom.setText(String.valueOf(realEstate.getBathroomNumber()));
                }
            });
        } else {
            Log.d(TAG, "onActivityCreated: getArguments() = null");
        }
    }

}