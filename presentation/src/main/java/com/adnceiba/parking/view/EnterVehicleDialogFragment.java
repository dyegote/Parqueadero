package com.adnceiba.parking.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.adnceiba.parking.Utils;
import com.adnceiba.parking.viewModel.VehicleTypeViewModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EnterVehicleDialogFragment extends DialogFragment {

    private VehicleTypeViewModel vehicleTypeViewModel;
    private Spinner vehicleTypeSpinner;
    private EditText licensePlateEdiText;
    private EditText cylinderEditText;
    private TextView cylinderTextView;
    private Tariff tariff = null;

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

        this.vehicleTypeSpinner = (Spinner) view.findViewById(R.id.vehicleTypeSpinner);
        this.licensePlateEdiText = (EditText) view.findViewById(R.id.licensePlateEditText);
        this.cylinderEditText = (EditText) view.findViewById(R.id.cylinderTextViewEditText);
        this.cylinderTextView = (TextView) view.findViewById(R.id.cylinderTextView);
        this.licensePlateEdiText = (EditText) view.findViewById(R.id.licensePlateEditText);
        this.vehicleTypeViewModel = new ViewModelProvider(this).get(VehicleTypeViewModel.class);

        this.vehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        tariff = Tariff.MOTO;
                        cylinderEditText.setVisibility(View.VISIBLE);
                        cylinderTextView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tariff= Tariff.CAR;
                        cylinderEditText.setVisibility(View.GONE);
                        cylinderTextView.setVisibility(View.GONE);
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
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vehicleTypeSpinner.setAdapter(adapter);
        });
    }

    private boolean enterVehicle(){
        boolean isEnter = false;
        try {
            String licensePlate = licensePlateEdiText.getText().toString();
            if(!TextUtils.isEmpty(licensePlate)){
                if(tariff == Tariff.MOTO)
                    this.enterMoto(licensePlate);
                else
                    this.enterCar(licensePlate);
                isEnter = true;
            }
            else
                Utils.showMessage(getActivity(),"Typed the license plate.").show();
        }
        catch (Exception ex){
            ex.printStackTrace();
            Utils.showMessage(getActivity(),ex.getMessage()).show();
        }

        return isEnter;
    }

    private void enterCar(String licensePlate) throws Exception {
        Vehicle car = new Car(licensePlateEdiText.getText().toString());
        Parking parking = new Parking(Calendar.getInstance().getTime().getTime(),Calendar.getInstance().getTime().getTime(),car,tariff);
        carParkingService.enterVehicle(parking);
        Toast.makeText(getContext(),"INGRESO CARRO: ", Toast.LENGTH_LONG).show();
    }

    private  void enterMoto(String licensePlate) throws Exception {
        Vehicle moto = new Moto(licensePlateEdiText.getText().toString(),Integer.parseInt(cylinderEditText.getText().toString()));
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