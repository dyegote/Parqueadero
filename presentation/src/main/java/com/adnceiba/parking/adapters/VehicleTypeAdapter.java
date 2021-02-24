package com.adnceiba.parking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.parking.R;

import java.util.ArrayList;
import java.util.List;

public class  VehicleTypeAdapter extends ArrayAdapter<VehicleType> {

    List<VehicleType> items;
    Context context;

    public VehicleTypeAdapter(@NonNull Context context, int resource, int textViewResourceId, List<VehicleType> items) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.items = items;
    }

    @Override
    public VehicleType getItem(int position) {

        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        VehicleType type = items.get(position);
        if (type != null) {
            TextView lblName = (TextView) view;
            if (lblName != null)
                lblName.setText(type.getName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((VehicleType) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                List<VehicleType> suggestions = new ArrayList<VehicleType>();
                suggestions.clear();
                List<VehicleType> tempItems = new ArrayList<VehicleType>(items);
                for (VehicleType type : tempItems) {
                    if (type.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(type);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<VehicleType> filterList = (ArrayList<VehicleType>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (VehicleType type : filterList) {
                    add(type);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
