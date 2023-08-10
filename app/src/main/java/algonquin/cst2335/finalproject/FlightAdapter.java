package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private List<Flight> flightList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Flight flight);
    }

    public FlightAdapter(Context context, List<Flight> flightList, OnItemClickListener listener) {
        this.context = context;
        this.flightList = flightList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_info_row, parent, false);
        return new FlightViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.destinationAirportValue.setText(flight.getDestination());
        holder.flightNumber.setText(flight.getFlightCode());
    }
    @Override
    public int getItemCount() {
        return flightList.size();
    }

    class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView destinationLabel, destinationAirportValue, flightNumberLabel, flightNumber;
        ImageView planeImage;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            planeImage = itemView.findViewById(R.id.planeImage);
            destinationLabel = itemView.findViewById(R.id.destinationLabel);
            destinationAirportValue = itemView.findViewById(R.id.destinationAirportValue);
            flightNumberLabel = itemView.findViewById(R.id.flightNumberLabel);
            flightNumber = itemView.findViewById(R.id.flightNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(flightList.get(getAdapterPosition()));
                }
            });
        }
    }
}