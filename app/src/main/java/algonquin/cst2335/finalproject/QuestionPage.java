package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import algonquin.cst2335.finalproject.databinding.QuestionPageBinding;

public class QuestionPage extends AppCompatActivity {

    @NonNull QuestionPageBinding binding;
    int index = 0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        binding = QuestionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent fromPrevious = getIntent();
        int questionNumber = fromPrevious.getIntExtra("questionNumber",0);

        String url = null;
        /**
         * try catch block to encode question number attribute into
         * json object URL
         */
        try {
            url = "https://opentdb.com/api.php?amount=" + URLEncoder.encode(String.valueOf(questionNumber),"UTF-8")
                    + "&category=22&type=multiple";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ArrayList<Button> answers = new ArrayList<>();
        answers.add(binding.answerA);
        answers.add(binding.answerB);
        answers.add(binding.answerC);
        answers.add(binding.answerD);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            (response) -> {
                try {
                    JSONArray triviaQuiz = response.getJSONArray("results");
                    JSONObject questionObject = triviaQuiz.getJSONObject(index);
                    String question = questionObject.getString("question");
                    String correctAnswer = questionObject.getString("correct_answer");
                    JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");
                    runOnUiThread(()->{
                    index++;
                    binding.questionText.setText(question);
                    Random rand = new Random();
                    int answerRandomizer = rand.nextInt(4);
                    answers.get(answerRandomizer).setText(correctAnswer);
                    answers.remove(answerRandomizer);
                    for (int i = 0 ; i < 2 ; i++){
                        try {
                            answers.get(i).setText(incorrectAnswers.getString(i));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Log.w("TAG", answers.get(0).getText().toString());
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            },(error) -> {}
        );
        queue.add(request);
        binding.nextQuestionButton.setOnClickListener(click->{
            index++;
            binding.backButton.setVisibility(View.VISIBLE);

        });
    }

}