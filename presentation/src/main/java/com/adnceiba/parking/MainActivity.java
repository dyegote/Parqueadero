package com.adnceiba.parking;

import android.os.Bundle;

import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.service.VehicleTypeService;
import com.adnceiba.parking.ViewModel.VehicleTypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    VehicleTypeViewModel vehicleTypeViewModel;

    private Spinner vehicleTypeSpinner;
    private Button enterVehicleButton;
    private EditText licensePlateEdiText;

    @Inject
    VehicleTypeService vehicleTypeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureView();
        this.loadObservers();

        vehicleTypeViewModel.getVehicleTypes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.vehicleTypeSpinner = (Spinner) findViewById(R.id.vehicleTypeSpinner);
        this.enterVehicleButton = (Button) findViewById(R.id.enterButton);
        this.licensePlateEdiText = (EditText) findViewById(R.id.licensePlateEditText);
        vehicleTypeViewModel = new ViewModelProvider(this).get(VehicleTypeViewModel.class);
    }

    private void loadObservers(){
        vehicleTypeViewModel.vehicleTypeLiveData.observe(this, vehicleTypeList -> {
            List<CharSequence> items = new ArrayList<>();
            for(VehicleType typeItem : vehicleTypeList)
                items.add(typeItem.getId());
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vehicleTypeSpinner.setAdapter(adapter);
        });
    }


}