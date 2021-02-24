package com.adnceiba.parking.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.service.CarParkingService;
import com.adnceiba.domain.service.MotoParkingService;
import com.adnceiba.domain.service.ParkingService;
import com.adnceiba.domain.service.ParkingTimeCalculatorService;
import com.adnceiba.domain.valueobject.Tariff;
import com.adnceiba.parking.R;
import com.adnceiba.parking.adapters.VehicleTypeAdapter;
import com.adnceiba.parking.viewModel.VehicleTypeViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeaveVehicleDialogFragment extends DialogFragment {

    private VehicleTypeViewModel vehicleTypeViewModel;
    public final static String KEY_ARRIVING_TIME = "arrivingTime";
    public final static String KEY_LICENSE_PLATE = "licensePlate";
    public final static String KEY_ID_TARIFF = "idTariff";
    public final static String KEY_CYLINDER = "cylinder";
    private TextView licensePlateEditText;
    private TextView arrivingTimeTextView;
    private TextView leavingTimeTextView;
    private TextView vehicleTypeTextView;
    private TextView cylinderTextView;
    private TextView ccTextView;
    private TextView daysTextView;
    private TextView totalDaysTextView;
    private TextView hoursTextView;
    private TextView totalHoursTextView;
    private TextView totalPriceTextView;
    private ImageView timeImageView;
    private ImageView vehicleImageView;
    private float totalHours = 0;
    private float parkingHours = 0;
    private float totalDays = 0;
    private float totalPrice = 0;
    private Parking parking;
    private  Tariff tariff;

    @Inject MotoParkingService motoParkingService;
    @Inject CarParkingService carParkingService;
    @Inject ParkingService parkingService;


    LeaveVehicleDialogListener listener;

    public LeaveVehicleDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.leave_vehicle_dialog, null);

        builder.setCancelable(false).setView(view)
                .setTitle(R.string.leave_vehicle_tittle)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LeaveVehicleDialogFragment.this.getDialog().cancel();
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
                        if(leaveVehicle()){
                            dismiss();
                            listener.onDialogLeaveVehicleClick(LeaveVehicleDialogFragment.this);
                        }

                    }
                });
            }
        });

        Bundle args = getArguments();
        tariff = Tariff.valueOf(args.getString(KEY_ID_TARIFF));
        Vehicle vehicle = null;
        if(tariff == Tariff.MOTO)
            vehicle = new Moto(args.getString(KEY_LICENSE_PLATE),args.getInt(KEY_CYLINDER ));
        else
            vehicle = new Car(args.getString(KEY_LICENSE_PLATE));

        parking = new Parking(args.getLong(KEY_ARRIVING_TIME),Calendar.getInstance().getTime().getTime(),vehicle,tariff);

        this.loadTotal();
        this.configureView(view);
        this.loadObservers();
        this.vehicleTypeViewModel.getVehicleType(parking.getTariff().toString());

        return alertDialog;
    }

    private void configureView(View view){
        licensePlateEditText = view.findViewById(R.id.licensePlateTextView);
        arrivingTimeTextView = view.findViewById(R.id.arrivingTimeTextView);
        leavingTimeTextView = view.findViewById(R.id.leavingTimeTextView);
        vehicleTypeTextView =  view.findViewById(R.id.vehicleTypeTextView);
        cylinderTextView = view.findViewById(R.id.cylinderTextView);
        ccTextView = view.findViewById(R.id.ccTextView);
        daysTextView = view.findViewById(R.id.daysTextView);
        totalDaysTextView = view.findViewById(R.id.totalDaysTextView);
        hoursTextView = view.findViewById(R.id.hoursTextView);
        totalHoursTextView = view.findViewById(R.id.totalHoursTextView);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        timeImageView = view.findViewById(R.id.timeImageView);
        vehicleImageView = view.findViewById(R.id.vehicleImageView);
        this.vehicleTypeViewModel = new ViewModelProvider(this).get(VehicleTypeViewModel.class);

        timeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showMessage(getActivity(),"Total Horas: " + String.format("%.1f",totalHours)).show();
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        DecimalFormat hourFormat = new DecimalFormat("##.00");
        this.cylinderTextView.setVisibility(View.GONE);
        this.ccTextView.setVisibility(View.GONE);
        this.licensePlateEditText.setText(parking.getVehicle().getLicensePlate());
        this.arrivingTimeTextView.setText(simpleDateFormat.format(parking.getArrivingTime()));
        this.leavingTimeTextView.setText(simpleDateFormat.format(parking.getLeavingTime()));
        this.totalDaysTextView.setText(String.format("%.0f",totalDays));
        this.totalHoursTextView.setText(String.format("%.1f",parkingHours));
        this.totalPriceTextView.setText(String.format("%,.2f",Math.floor(totalPrice)));

        if(parking.getTariff() == Tariff.MOTO){
            Moto moto = (Moto)parking.getVehicle();
            this.cylinderTextView.setVisibility(View.VISIBLE);
            this.ccTextView.setVisibility(View.VISIBLE);
            this.cylinderTextView.setText(String.valueOf(moto.getCylinder()));
            this.vehicleImageView.setImageResource(R.drawable.ic_moto_24);
        }
    }

    private void loadTotal(){
        this.parkingHours = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime()).getParkingHours();
        this.totalHours = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime()).getTotalHours();
        this.totalDays = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime()).getParkingDays();
        if(parking.getTariff() == Tariff.MOTO)
            this.totalPrice = motoParkingService.calculatePrice(parking);
        else
            this.totalPrice = carParkingService.calculatePrice(parking);

    }

    private boolean leaveVehicle(){
        boolean isLeave = true;
        try{
            parkingService.inactivate(parking);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Utils.showMessage(getActivity(),ex.getMessage()).show();
        }
        return isLeave;
    }

    private void loadObservers(){
        vehicleTypeViewModel.vehicleTypeLiveData.observe(this, vehicleType -> {
            if(vehicleType != null)
                this.vehicleTypeTextView.setText(vehicleType.getName());
        });
    }

    public interface LeaveVehicleDialogListener {
        public void onDialogLeaveVehicleClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (LeaveVehicleDialogFragment.LeaveVehicleDialogListener) context;
    }
}
