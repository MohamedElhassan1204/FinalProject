package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.databinding.AviationSfTrackerBinding;


public class AviationActivity extends AppCompatActivity {

    AviationSfTrackerBinding binding;
    protected String airportCode;
    protected RequestQueue queue = null;
    private RecyclerView.Adapter myAdapter;
    ArrayList<Flight> flightsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        AviationSfTrackerBinding binding = AviationSfTrackerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedAirportCode = sharedPreferences.getString("savedAirportCode", "");
        binding.enterAirportCode.setText(savedAirportCode);
        binding.flightList.setLayoutManager(new LinearLayoutManager(this));

        flightsList = new ArrayList<>();

        binding.searchFlight.setOnClickListener(click -> {
            airportCode = binding.enterAirportCode.getText().toString();

            if (airportCode.isEmpty()) {
                Toast.makeText(this, "Please enter an airport code.", Toast.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.getRoot(), "Searching flights...", Snackbar.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("savedAirportCode", airportCode);
                editor.apply();

                String stringURL = null;
                try {
                    stringURL = "http://api.aviationstack.com/v1/flights?access_key=18c69d26a38b6a41b9c40dbefb83398c&dep_iata="
                            + URLEncoder.encode(airportCode, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> {
                            try {
                                JSONArray data = response.getJSONArray("data");
                                flightsList = new ArrayList<>();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject position = data.getJSONObject(i);
                                    JSONObject departureObject = position.getJSONObject("departure");
                                    String terminal = departureObject.getString("terminal");
                                    String gate = departureObject.getString("gate");
                                    String delay = departureObject.getString("delay");

                                    JSONObject arrivalObject = position.getJSONObject("arrival");
                                    String destination = arrivalObject.getString("iata");

                                    JSONObject flightObject = position.getJSONObject("flight");
                                    String flightCode = flightObject.getString("iata");

                                    Flight flight = new Flight(flightCode, destination, terminal, gate, delay);
                                    flightsList.add(flight);
                                }
                                myAdapter = new FlightAdapter(this, flightsList);
                                myAdapter.notifyDataSetChanged();
                                binding.flightList.setAdapter(myAdapter);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        (error) -> {
                            Toast.makeText(this, "Failed to retrieve data. " + error.toString(), Toast.LENGTH_LONG).show();
                        });

                queue.add(request);
            }
        });

        binding.information.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviation Tracker: How-to-use");
            builder.setMessage(
                    "- Enter an airport code and search.\n" +
                            "- Scroll to view all available flights for the specified airport.\n" +
                            "- Click on a flight to view more details (Terminal, Gate, & Delay).\n" +
                            "- Add a flight to 'My Flights' to save it.\n" +
                            "- Open 'My Flights' to view all your saved flights or unsave a flight.\n"
            );
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        binding.viewSavedFlights.setOnClickListener(click -> {
//            SavedFlightsFragment savedFlightsFragment = new SavedFlightsFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragmentContainerView, savedFlightsFragment)
//                    .addToBackStack(null)
//                    .commit();
        });

//        deleteButton.setOnClickListener(v -> {
//            Flight selectedFlight = savedFlightList.get(getAdapterPosition());
//            FlightDatabase db = Room.databaseBuilder(getContext(), FlightDatabase.class, "flightsDB").build();
//            FlightDAO flightDAO = db.fDAO();
//            new Thread(() -> {
//                flightDAO.deleteFlight(selectedFlight);
//                getActivity().runOnUiThread(() -> {
//                    // Refresh the list or notify the adapter of item removed
//                    Toast.makeText(getContext(), "Flight deleted!", Toast.LENGTH_SHORT).show();
//                });
//            }).start();
//        });
    }
}