package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import algonquin.cst2335.finalproject.databinding.QuestionPageBinding;
import algonquin.cst2335.finalproject.databinding.QuizSelectionPageBinding;

public class QuestionPage extends AppCompatActivity {

    @NonNull QuestionPageBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);
        binding = QuestionPageBinding.inflate(getLayoutInflater());

        Intent fromPrevious = getIntent();
        String questionNumber = fromPrevious.getStringExtra("questionNumber");
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("questionNumber", questionNumber);
        editor.apply();

        String url = null;
        /**
         * try catch block to encode question number attribute into
         * json object URL
         */
        try {
            url = "https://opentdb.com/api.php?amount=" + URLEncoder.encode(questionNumber,"UTF-8")
                + "&category=22&type=multiple";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            (response) -> {
                try {
                    JSONArray triviaQuiz = response.getJSONArray("results");
                    JSONObject questionObject = triviaQuiz.getJSONObject(0);
                    JSONObject mainObject = response.getJSONObject("main");
                    String question = mainObject.getString("question");
                    String correctAnswer = mainObject.getString("correct_answer");
                    JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");

                    binding.questionText.setText(question);

                    ArrayList<Button> answers = new ArrayList<>();
                    answers.add(binding.answerA);
                    answers.add(binding.answerB);
                    answers.add(binding.answerC);
                    answers.add(binding.answerD);

                    Random rand = new Random();
                    int answerRandomizer = rand.nextInt(4);
                    answers.get(answerRandomizer).setText(correctAnswer);
                    for (int i = 0 ; i > 2 ; i++){
                        answers.get(answerRandomizer).setText(incorrectAnswers.getString(i));
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            },(error) -> {

        });
    }
}