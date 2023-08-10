package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.AviationSfTrackerBinding;

public class AviationActivity extends AppCompatActivity {
    protected String airportCode;
    protected RequestQueue queue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        AviationSfTrackerBinding binding = AviationSfTrackerBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String savedAirportCode = sharedPreferences.getString("savedAirportCode", "");
        binding.enterAirportCode.setText(savedAirportCode);

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
                    stringURL = "http://api.aviationstack.com/v1/flights?access_key=18c69d26a38b6a41b9c40dbefb83398c?dep_iata="
                            + URLEncoder.encode(airportCode, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> {
                            try {
                                JSONArray data = response.getJSONArray("data");
                                JSONObject position = data.getJSONObject(0);

                                JSONObject departureObject = response.getJSONObject("departure");
                                String terminal = departureObject.getString("terminal");
                                String gate = departureObject.getString("gate");
                                String delay = departureObject.getString("delay");

                                JSONObject arrivalObject = response.getJSONObject("arrival");
                                String airportArr = arrivalObject.getString("iata");

                                JSONObject flightObject = response.getJSONObject("flight");
                                String flightNum = flightObject.getString("iata");

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        (error) -> {
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
        });
    }
}