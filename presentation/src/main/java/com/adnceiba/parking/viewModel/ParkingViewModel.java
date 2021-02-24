package com.adnceiba.parking.viewModel;

import android.text.TextUtils;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.service.ParkingService;
import com.adnceiba.domain.service.VehicleTypeService;

import java.util.List;

public class ParkingViewModel extends ViewModel {

    ParkingService parkingService;
    public MutableLiveData<List<Parking>> parkingLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<String> filterName = new MutableLiveData<>();

    @ViewModelInject
    public ParkingViewModel(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public LiveData<List<Parking>> getParkings(String licensePlate) {

        if(TextUtils.isEmpty(licensePlate))
            parkingLiveData.setValue(parkingService.loadAllActive());
        else
            parkingLiveData.setValue(parkingService.loadByLicensePlate(licensePlate));

        return  parkingLiveData;
    }
}
