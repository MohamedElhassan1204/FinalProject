package algonquin.cst2335.finalproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.AviationSfTrackerBinding;

public class AviationActivity extends AppCompatActivity {
    protected String airportCode;
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviation_sf_tracker);

        queue = Volley.newRequestQueue(this);
        AviationSfTrackerBinding binding = AviationSfTrackerBinding.inflate( getLayoutInflater() );

        binding.searchFlight.setOnClickListener(click -> {
            airportCode = binding.enterAirportCode.getText().toString();
            String stringURL = null;
            try {
                stringURL = "http://api.aviationstack.com/v1/flights?access_key=18c69d26a38b6a41b9c40dbefb83398c?dep_iata="
                        + URLEncoder.encode(airportCode, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {},
                    (error) -> {} );
            queue.add(request);
        });


    }
}