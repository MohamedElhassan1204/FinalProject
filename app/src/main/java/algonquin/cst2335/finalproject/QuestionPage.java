package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URLEncoder;

public class QuestionPage extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

        Intent fromPrevious = getIntent();
        String questionNumber = fromPrevious.getStringExtra("questionNumber");

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Email Address",questionNumber);
        editor.apply();

        String url = null;
        /**
         * try catch block to encode question number attribute into
         * json object URL
         */
        try {
            url = "https://opentdb.com/api.php?amount="
                    + URLEncoder.encode(String.valueOf(questionNumber),"UTF-8")
                    +"&category=22&type=multiple";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (response) -> {
                    try {
                        JSONArray triviaQuiz = response.getJSONArray("api.php");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },(error) -> {

        });
    }
}