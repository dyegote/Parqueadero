package com.adnceiba.parking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.valueobject.Tariff;
import com.adnceiba.parking.R;

import java.text.SimpleDateFormat;
import java.util.List;


public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.UserViewHolder>
{

    Context context;
    List<Parking> parkingList;
    private static ClickListenerButton postButtonClickListener;

    public ParkingAdapter(Context context) {
        this.context = context;
    }

    public ParkingAdapter(Context context, List<Parking> userModelList) {
        this.context = context;
        this.parkingList = userModelList;
    }

    public void setParkingList(List<Parking> parkingList) {
        this.parkingList = parkingList;
    }

    public Parking getParking(int position)
    {
        return this.parkingList.get(position);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parking_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Parking parking = parkingList.get(position);
        holder.cylinderTextView.setVisibility(View.GONE);
        holder.ccTextView.setVisibility(View.GONE);
        holder.licensePlateEditText.setText(parking.getVehicle().getLicensePlate());
        holder.arrivingTimeTextView.setText(simpleDateFormat.format(parking.getArrivingTime()));
        holder.leavingTimeTextView.setText(simpleDateFormat.format(parking.getLeavingTime()));
        holder.vehicleTypeTextView.setText(parking.getTariff().toString());

        if(parking.getTariff() == Tariff.MOTO){
            Moto moto = (Moto)parking.getVehicle();
            holder.cylinderTextView.setVisibility(View.VISIBLE);
            holder.ccTextView.setVisibility(View.VISIBLE);
            holder.cylinderTextView.setText(String.valueOf(moto.getCylinder()));
        }
    }

    @Override
    public int getItemCount() {
        return parkingList.size();
    }

    public void setOnItemClickListenerPostButton(ClickListenerButton clickListener) {
        this.postButtonClickListener = clickListener;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView licensePlateEditText;
        TextView arrivingTimeTextView;
        TextView leavingTimeTextView;
        TextView vehicleTypeTextView;
        TextView cylinderTextView;
        TextView ccTextView;
        Button leaveButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            licensePlateEditText = itemView.findViewById(R.id.licensePlateTextView);
            arrivingTimeTextView = itemView.findViewById(R.id.arrivingTimeTextView);
            leavingTimeTextView = itemView.findViewById(R.id.leavingTimeTextView);
            vehicleTypeTextView = itemView.findViewById(R.id.vehicleTypeTextView);
            cylinderTextView = itemView.findViewById(R.id.cylinderTextView);
            ccTextView = itemView.findViewById(R.id.ccTextView);
            leaveButton = itemView.findViewById(R.id.leavingButton);
            leaveButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == leaveButton.getId())
                postButtonClickListener.onItemClick(getAdapterPosition(), v);

        }
    }

    public interface ClickListenerButton {
        public void onItemClick(int position, View v);
    }
}
