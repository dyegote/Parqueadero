package com.adnceiba.parking.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.service.CarParkingService;
import com.adnceiba.domain.service.MotoParkingService;
import com.adnceiba.domain.service.VehicleTypeService;
import com.adnceiba.domain.valueobject.Tariff;
import com.adnceiba.parking.R;
import com.adnceiba.parking.adapters.VehicleTypeAdapter;
import com.adnceiba.parking.viewModel.VehicleTypeViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EnterVehicleDialogFragment extends DialogFragment {

    private VehicleTypeViewModel vehicleTypeViewModel;
    private TextInputLayout licensePlateTextInputLayout;
    private TextInputLayout cylinderTextInputLayout;
    private TextInputLayout typeTextInputLayout;
    private AutoCompleteTextView typeAutocompleteTextView;
    private Tariff tariff = null;
    private  boolean licensePlateValid = false;
    private  boolean typeVehicleValid = false;
    private  boolean cylinderValid = false;

    @Inject VehicleTypeService vehicleTypeService;
    @Inject CarParkingService carParkingService;
    @Inject MotoParkingService motoParkingService;

    EnterVehicleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.enter_vehicle_dialog, null);

        builder.setCancelable(false).setView(view)
                .setTitle(R.string.enter_vehicle_tittle)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EnterVehicleDialogFragment.this.getDialog().cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(enterVehicle()){
                            dismiss();
                            listener.onDialogEnterVehicleClick(EnterVehicleDialogFragment.this);
                        }

                    }
                });
            }
        });

        this.configureView(view);
        this.loadObservers();
        this.vehicleTypeViewModel.getVehicleTypes();

        return alertDialog;
    }

    private void configureView(View view){

        this.licensePlateTextInputLayout = (TextInputLayout) view.findViewById(R.id.licensePlateTextInputLayout);
        this.cylinderTextInputLayout = (TextInputLayout) view.findViewById(R.id.cylinderTextInputLayout);
        this.typeTextInputLayout = (TextInputLayout) view.findViewById(R.id.typeTextInputLayout);
        this.vehicleTypeViewModel = new ViewModelProvider(this).get(VehicleTypeViewModel.class);
        this.typeAutocompleteTextView = (AutoCompleteTextView)typeTextInputLayout.getEditText();

        typeAutocompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                VehicleType selectedType = (VehicleType) adapterView.getItemAtPosition(pos);
                typeAutocompleteTextView.setError(null);
                switch (Tariff.valueOf(selectedType.getId())){
                    case MOTO:
                        tariff = Tariff.MOTO;
                        cylinderTextInputLayout.setVisibility(View.VISIBLE);
                        break;
                    case CAR:
                        tariff= Tariff.CAR;
                        cylinderTextInputLayout.setVisibility(View.GONE);
                        break;
                }
            }
        });

        licensePlateTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                licensePlateTextInputLayout.setError(null);
                licensePlateValid = true;
                Matcher m = Pattern.compile("[A-Z][A-Z]([A-Z]|\\d)\\d\\d").matcher(s);
                if (!m.find()){
                    licensePlateTextInputLayout.setError("Placa invalida.");
                    licensePlateValid = false;
                }


            }
        });

        cylinderTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cylinderTextInputLayout.setError(null);
                if (TextUtils.isEmpty(s)){
                    cylinderTextInputLayout.setError("Ingrese el cilindraje.");
                }


            }
        });
    }

    private void loadObservers(){
        vehicleTypeViewModel.vehicleTypeListLiveData.observe(this, vehicleTypeList -> {
            VehicleTypeAdapter vadapter = new VehicleTypeAdapter(getActivity(), R.layout.activity_main, R.layout.list_item, vehicleTypeList);
            typeAutocompleteTextView.setAdapter(vadapter);
        });
    }

    private boolean isValid(){
        typeVehicleValid = true;
        licensePlateValid = true;
        cylinderValid = true;

        if(tariff == null){
            typeAutocompleteTextView.setError("Seleccione tipo de vehiculo.");
            typeVehicleValid = false;
        }

        if(TextUtils.isEmpty(licensePlateTextInputLayout.getEditText().getText().toString())){
            licensePlateTextInputLayout.setError("Digite la placa.");
            licensePlateValid = false;
        }

        if(typeVehicleValid){
            if(tariff == Tariff.MOTO){
                if(TextUtils.isEmpty(cylinderTextInputLayout.getEditText().getText())) {
                    cylinderTextInputLayout.setError("Ingrese el cilindraje.");
                    cylinderValid = false;
                }
                return cylinderValid && licensePlateValid;
            }
            else
                return licensePlateValid;
        }
        else
            return false;
    }

    private boolean enterVehicle(){
        boolean isEnter = false;
        try {
            String licensePlate = licensePlateTextInputLayout.getEditText().getText().toString();

            if(isValid()){
                if(tariff == Tariff.MOTO)
                    this.enterMoto(licensePlate);
                else
                    this.enterCar(licensePlate);
                isEnter = true;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            Utils.showMessage(getActivity(),ex.getMessage()).show();
        }

        return isEnter;
    }

    private void enterCar(String licensePlate) throws Exception {
        Vehicle car = new Car(licensePlateTextInputLayout.getEditText().getText().toString());
        Parking parking = new Parking(Calendar.getInstance().getTime().getTime(),Calendar.getInstance().getTime().getTime(),car,tariff);
        carParkingService.enterVehicle(parking);
        Toast.makeText(getContext(),"INGRESO CARRO: ", Toast.LENGTH_LONG).show();
    }

    private  void enterMoto(String licensePlate) throws Exception {

        Vehicle moto = new Moto(licensePlateTextInputLayout.getEditText().getText().toString(),Integer.parseInt(cylinderTextInputLayout.getEditText() .getText().toString()));
        Parking parking = new Parking(Calendar.getInstance().getTime().getTime(),Calendar.getInstance().getTime().getTime(),moto,tariff);
        motoParkingService.enterVehicle(parking);
        Toast.makeText(getContext(),"INGRESO MOTO: ", Toast.LENGTH_LONG).show();
    }

    public interface EnterVehicleDialogListener {
        public void onDialogEnterVehicleClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EnterVehicleDialogListener) context;
    }
}