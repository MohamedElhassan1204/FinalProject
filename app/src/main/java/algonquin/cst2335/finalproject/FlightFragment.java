package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FlightFragment extends Fragment {

    private Flight flight;

    public FlightFragment(Flight flight) {
        this.flight = flight;
    }

    public static FlightFragment newInstance(Flight flight) {
        FlightFragment fragment = new FlightFragment(flight);
        Bundle args = new Bundle();
        args.putParcelable("flight_key", (Parcelable) flight);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flight_view_details, container, false);

        TextView terminalContent = view.findViewById(R.id.terminalContent);
        TextView gateContent = view.findViewById(R.id.gateContent);
        TextView delayContent = view.findViewById(R.id.delayContent);

        terminalContent.setText(flight.getTerminal());
        gateContent.setText(flight.getGate());
        delayContent.setText(flight.getDelay());

        return view;
    }
}