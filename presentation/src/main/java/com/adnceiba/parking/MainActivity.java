package com.adnceiba.parking;

import android.os.Bundle;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.service.CarParkingService;
import com.adnceiba.domain.service.MotoParkingService;
import com.adnceiba.domain.service.VehicleTypeService;
import com.adnceiba.parking.adapters.ParkingAdapter;
import com.adnceiba.parking.view.EnterVehicleDialogFragment;
import com.adnceiba.parking.view.LeaveVehicleDialogFragment;
import com.adnceiba.parking.viewModel.ParkingViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements EnterVehicleDialogFragment.EnterVehicleDialogListener, LeaveVehicleDialogFragment.LeaveVehicleDialogListener {

    private ParkingViewModel parkingViewModel;
    private ParkingAdapter parkingAdapter;
    private RecyclerView parkingRecyclerView;
    private EditText searchEditTex;
    private View emptyLayout;

    @Inject VehicleTypeService vehicleTypeService;
    @Inject CarParkingService carParkingService;
    @Inject MotoParkingService motoParkingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureView();
        this.loadObservers();
        this.parkingViewModel.getParkings("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureView(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        emptyLayout = inflater.inflate(R.layout.empty_view, null);
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutparams.addRule(RelativeLayout.CENTER_IN_PARENT);
        emptyLayout.setLayoutParams(layoutparams);

        parkingViewModel = new ViewModelProvider(this).get(ParkingViewModel.class);

        searchEditTex = findViewById(R.id.editTextSearch);
        parkingRecyclerView = findViewById(R.id.parkingRecyclerView);
        parkingRecyclerView.setHasFixedSize(true);
        parkingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        parkingAdapter = new ParkingAdapter(MainActivity.this);

        parkingAdapter.setOnItemClickListenerPostButton(new ParkingAdapter.ClickListenerButton() {
            @Override
            public void onItemClick(int position, View v) {
                Parking parking = parkingAdapter.getParking(position);
                DialogFragment dialog = new LeaveVehicleDialogFragment(parking);
                dialog.show(getSupportFragmentManager(), "LeaveVehicleDialogFragment");
            }
        });

        searchEditTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                parkingViewModel.getParkings(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });
    }

    private void loadObservers(){
        parkingViewModel.parkingLiveData.observe(this, parkingList -> {
            if(parkingList.size() == 0)
            {
                Utils.replaceView(parkingRecyclerView,emptyLayout);
            }
            else
            {
                Utils.replaceView(emptyLayout,parkingRecyclerView);
                parkingAdapter.setParkingList(parkingList);
                parkingRecyclerView.setAdapter(parkingAdapter);
            }
        });
    }

    public void onClickEnterVehicle(View v) {
        DialogFragment dialog = new EnterVehicleDialogFragment();
        dialog.show(getSupportFragmentManager(), "EnterVehicleDialogFragment");
    }

    @Override
    public void onDialogEnterVehicleClick(DialogFragment dialog) {
        Utils.showMessage(this,"Vehiculo ingresado.").show();
        parkingViewModel.getParkings("");
    }

    @Override
    public void onDialogLeaveVehicleClick(DialogFragment dialog) {
        Utils.showMessage(this,"Vehiculo salio.").show();
        parkingViewModel.getParkings("");
    }
}