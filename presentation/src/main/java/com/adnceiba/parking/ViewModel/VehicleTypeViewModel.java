package com.adnceiba.parking.ViewModel;

import java.util.List;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.service.VehicleTypeService;

public class VehicleTypeViewModel extends ViewModel {

    private VehicleTypeService vehicleTypeService;
    public MutableLiveData<List<VehicleType>> vehicleTypeLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    @ViewModelInject
    public VehicleTypeViewModel(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    public LiveData<List<VehicleType>> getVehicleTypes() {
        vehicleTypeLiveData.setValue(vehicleTypeService.loadAll());
        return  vehicleTypeLiveData;
    }
}
