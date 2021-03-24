package com.openclassrooms.realestatemanager.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.MapStateManager;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.MapFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MapFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "MapFragment";

    private final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private FloatingActionButton mFab;

    private MapFragmentViewModel mViewModel;
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private LocationCallback mLocationCallback;
    private LatLng mLatLng;
    private Boolean mIsCenter = false;

    List<RealEstate> mListRealEstate = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = view.findViewById(R.id.mapView);
        mFab = view.findViewById(R.id.fab);

        //Create the map
        mMapView.onCreate(savedInstanceState);

        //Display the map immediately
        mMapView.onResume();

        //Permissions
        askPermissions();

        //FAB on click listener
        setOnClickFAB();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MapFragmentViewModel.class);
    }

    //Create callback to get the location
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLatLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                Log.d(TAG, "onLocationResult: Lat Lng = " + mLatLng);

                if (!mIsCenter) {
                    Log.d(TAG, "onLocationResult: center camera");
                    setCameraPosition();
                    //Set this boolean to true in the order not to center the map on the user's position a second time
                    mIsCenter = true;

                    //When the user click on marker
                    setMarkerOnCLick();
                }
            }
        };
    }

    private void addRealEstateMarker() {
        mViewModel.getAllRealEstate().observe(this, new Observer<List<RealEstate>>() {
            @Override
            public void onChanged(List<RealEstate> realEstate) {
                if (mListRealEstate.isEmpty()) {
                    Log.d(TAG, "onChanged: empty");
                    mListRealEstate = realEstate;
                    for (RealEstate realEstate2 : mListRealEstate) {
                        Log.d(TAG, "onChanged: RealEstate " + realEstate2.getId());
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(realEstate2.getAddress().getLat()), Double.parseDouble(realEstate2.getAddress().getLng()))).
                                title(realEstate2.getType())).setTag(realEstate2.getId());
                    }
                } else {
                    Log.d(TAG, "onChanged: not empty");
                    for (RealEstate r1 : realEstate) {
                        if (!mListRealEstate.contains(r1)) {
                            mListRealEstate.add(r1);
                            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(r1.getAddress().getLat()), Double.parseDouble(r1.getAddress().getLng())))
                                    .title(r1.getType())).setTag(r1.getId());
                        } else {
                            Log.d(TAG, "onChanged: RealEstate already displayed on map");
                        }
                    }
                }
            }
        });
    }


    //Set the camera to the user's position
    private void setCameraPosition() {
        if (mLatLng != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(mLatLng).zoom(16).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Log.d(TAG, "setCameraPosition: done");
        }
    }

    //Center on latlng on the map when the user click on the FAB
    private void setOnClickFAB() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean locationGranted = EasyPermissions.hasPermissions(requireContext(), permissions);
                if (!locationGranted) {
                    Log.d(TAG, "onClick: location denied : " + locationGranted);
                    askPermissions();
                } else {
                    Log.d(TAG, "onClick: location granted");
                    setCameraPosition();
                }
            }
        });
    }

    //When the user click on icon that will start the description of the restaurant
    private void setMarkerOnCLick() {
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                Intent intent = new Intent(requireContext(), DescriptionRestaurantActivity.class);
//                intent.putExtra("id", (String) marker.getTag());
//                startActivityForResult(intent, 10); //TODO Add result activity to set again the request location
                return false;
            }
        });
    }

    //If map was saved before, load it
    private void setupMapIfNeeded() {
        Log.d(TAG, "setupMapIfNeeded: start");
        try {
            MapsInitializer.initialize(requireContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = mGoogleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    requireContext(), R.raw.style_map));

                    if (!success) {
                        Log.e("TAG", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("TAG", "Can't find style. Error: ", e);
                }

                MapStateManager mMapStateManager = new MapStateManager(requireContext());
                CameraPosition mPosition = mMapStateManager.getSavedCameraPosition();

                //Add marker on map
                addRealEstateMarker();
                Log.d(TAG, "onMapReady: map saved");
                if (mPosition != null) {
                    Log.d(TAG, "onMapReady: in if : set camera position");
                    CameraUpdate update = CameraUpdateFactory.newCameraPosition(mPosition);
                    mGoogleMap.moveCamera(update);
                    mGoogleMap.setMapType(mMapStateManager.getSavedMapType());
                }
            }
        });
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        mMapView.onResume();

        if (EasyPermissions.hasPermissions(requireContext(), permissions)) {
            //Check if a map is saved or not to load it
            setupMapIfNeeded();

            //Start location update
            createLocationCallback();
            mViewModel.startLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        if (EasyPermissions.hasPermissions(requireContext(), permissions)) {
            MapStateManager mMapStateManager = new MapStateManager(requireContext());
            mMapStateManager.saveMapState(mGoogleMap);
            mViewModel.stopLocationUpdates(mLocationCallback);
        }
        mMapView.onPause();
        mIsCenter = false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @AfterPermissionGranted(123)
    private void askPermissions() {
        if (!EasyPermissions.hasPermissions(requireContext(), permissions)) {
            Log.d(TAG, "askPermissions: hasn't permissions");
            EasyPermissions.requestPermissions(this, requireContext().getResources().getString(R.string.permissions_denied),
                    123, permissions);
        } else {
            Log.d(TAG, "askPermissions: has permissions");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: start");
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.hasPermissions(requireContext(), permissions)) {
            Log.d(TAG, "onPermissionsGranted: has permissions");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        boolean toast = false;
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Log.d(TAG, "onPermissionsDenied: permanently denied");
            new AppSettingsDialog.Builder(this).build().show();
            toast = true;
        }
        if (!toast) {
            Log.d(TAG, "onPermissionsDenied: denied");
            displayToastIfPermsDenied();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Log.d(TAG, "onActivityResult: request code is the same");
            displayToastIfPermsDenied();
        }
    }

    //Toast if permissions denied
    private void displayToastIfPermsDenied() {
        if (EasyPermissions.somePermissionDenied(this, permissions)) {
            Utils.getEmojiByUnicode(0x26A0);
            Toast.makeText(requireContext(), (Utils.getEmojiByUnicode(0x26A0)) + requireContext().getResources().getString(R.string.permissions_toast_denied) +
                    (Utils.getEmojiByUnicode(0x26A0)), Toast.LENGTH_LONG).show();
        }
    }
}