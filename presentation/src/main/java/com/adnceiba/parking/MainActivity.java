package com.adnceiba.parking;

import android.os.Bundle;

import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.service.CarParkingService;
import com.adnceiba.domain.service.MotoParkingService;
import com.adnceiba.domain.service.VehicleTypeService;
import com.adnceiba.domain.valueobject.Tariff;
import com.adnceiba.parking.ViewModel.VehicleTypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private VehicleTypeViewModel vehicleTypeViewModel;
    private Spinner vehicleTypeSpinner;
    private EditText licensePlateEdiText;
    private EditText cylinderEditText;
    private TextView cylinderTextview;
    private Tariff tariff = null;

    @Inject VehicleTypeService vehicleTypeService;
    @Inject CarParkingService carParkingService;
    @Inject MotoParkingService motoParkingService;

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
        this.licensePlateEdiText = (EditText) findViewById(R.id.licensePlateEditText);
        this.cylinderEditText = (EditText) findViewById(R.id.cylinderTextViewEditText);
        this.cylinderTextview = (TextView) findViewById(R.id.cylinderTextView);
        this.licensePlateEdiText = (EditText) findViewById(R.id.licensePlateEditText);
        this.vehicleTypeViewModel = new ViewModelProvider(this).get(VehicleTypeViewModel.class);

        this.vehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        tariff = Tariff.MOTO;
                        cylinderEditText.setVisibility(View.VISIBLE);
                        cylinderTextview.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tariff= Tariff.CAR;
                        cylinderEditText.setVisibility(View.GONE);
                        cylinderTextview.setVisibility(View.GONE);
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
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

    public void onClickEnterVehicle(View v){
        licensePlateEdiText.onEditorAction(EditorInfo.IME_ACTION_DONE);

        try {

            String licensePlate = licensePlateEdiText.getText().toString();
            if(TextUtils.isEmpty(licensePlate))
                throw new Exception("Digite la placa.");

            switch (this.tariff){
                case CAR:
                    this.enterCar(v, licensePlate);
                    break;
                case MOTO:
                    this.enterMoto(v, licensePlate);
                    break;
            }
        }
        catch (Exception ex){
            Snackbar.make(v, ex.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    public void onClickLeaveVehicle(View v) {

    }

    private void enterCar(View v, String licensePlate) throws Exception {

        Vehicle car = new Car(licensePlateEdiText.getText().toString());
        Parking parking = new Parking(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),car,tariff);
        carParkingService.enterVehicle(parking);
        Snackbar.make(v, "INGRESO CARRO: " +licensePlateEdiText.getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private  void enterMoto(View v, String licensePlate) throws Exception {
        Vehicle moto = new Moto(licensePlateEdiText.getText().toString(),Integer.parseInt(cylinderEditText.getText().toString()));
        Parking parking = new Parking(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),moto,tariff);
        motoParkingService.enterVehicle(parking);
        Snackbar.make(v, "INGRESO MOTO: " +licensePlateEdiText.getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


}