package algonquin.cst2335.finalproject;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FlightFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flight_view_details, container, false);

        // Extract the flight details using getArguments()
        Flight flight = (Flight) getArguments().getSerializable("selectedFlight");

        // Assign the flight details to the respective views
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView terminalID = view.findViewById(R.id.terminalID);
        TextView terminalContent = view.findViewById(R.id.terminalContent);
        TextView gateID = view.findViewById(R.id.gateID);
        TextView gateContent = view.findViewById(R.id.gateContent);
        TextView delayID = view.findViewById(R.id.delayID);
        TextView delayContent = view.findViewById(R.id.delayContent);
        Button saveFlightButton = view.findViewById(R.id.saveFlightButton);

        terminalContent.setText(flight.getTerminal());
        gateContent.setText(flight.getGate());
        delayContent.setText(flight.getDelay());

        saveFlightButton.setOnClickListener(v -> {
            // Logic to save flight to DB goes here
        });

        return view;
    }

}
