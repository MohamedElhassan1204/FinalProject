package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Random;

import algonquin.cst2335.finalproject.databinding.QuestionPageBinding;

public class QuestionPage extends AppCompatActivity {

    @NonNull
    QuestionPageBinding binding;
    int index = 0;
    int score = 0;
    ArrayList<Button> answers = new ArrayList<>();
    RequestQueue queue;
    int questionNumber; // Added declaration

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = QuestionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeUIElements();
        loadQuestionData();
    }

    private void initializeUIElements() {
        answers.add(binding.answerA);
        answers.add(binding.answerB);
        answers.add(binding.answerC);
        answers.add(binding.answerD);
        binding.nextQuestionButton.setOnClickListener(this::loadNextQuestion);
        binding.submitButton.setOnClickListener(this::submitAnswers);
        setAnswerButtonListeners();
    }

    private void loadQuestionData() {
        queue = Volley.newRequestQueue(this);

        Intent fromPrevious = getIntent();
        int questionNumber = fromPrevious.getIntExtra("questionNumber", 0);

        String url;
        try {
            url = "https://opentdb.com/api.php?amount=" + URLEncoder.encode(String.valueOf(questionNumber), "UTF-8")
                    + "&category="+ URLEncoder.encode("","UTF-8") +"&type=multiple";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray triviaQuiz = response.getJSONArray("results");
                        loadQuestionData(triviaQuiz);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
        });

        queue.add(request);
    }

    private void loadQuestionData(JSONArray triviaQuiz) throws JSONException {
        JSONObject questionObject = triviaQuiz.getJSONObject(index);
        String question = questionObject.getString("question");
        String correctAnswer = questionObject.getString("correct_answer");
        JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");
        runOnUiThread(() -> {
            binding.questionText.setText(question);
            binding.questionNumber.setText(index + ":");
            Random rand = new Random();
            int answerRandomizer = rand.nextInt(4);
            answers.get(answerRandomizer).setText(correctAnswer);
            answers.remove(answerRandomizer);
            try {
                answers.get(0).setText(incorrectAnswers.getString(0));
                answers.get(1).setText(incorrectAnswers.getString(1));
                answers.get(2).setText(incorrectAnswers.getString(2));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setAnswerButtonListeners() {
        for (Button answer : answers) {
            answer.setOnClickListener(view -> {
                for (Button btn : answers) {
                    btn.setBackgroundColor(getColor(R.color.purple_500));
                }
                view.setBackgroundColor(getColor(R.color.teal_200));
                checkAnswer(answer.getText().toString()); // Corrected line
            });
        }
    }

    private void checkAnswer(String selectedAnswer) {
        // Implement your answer checking logic here
    }

    private void loadNextQuestion(View view) {
        index++;
        int questionNumber = getIntent().getIntExtra("questionNumber", 0);
        if (index < questionNumber) {
            initializeUIElements();
            loadQuestionData();
            binding.submitButton.setVisibility(View.VISIBLE);
            if (index == questionNumber - 1) {
                binding.nextQuestionButton.setVisibility(View.INVISIBLE);
            }
        }
    }


    private void submitAnswers(View view) {
        Intent nextPage = new Intent(QuestionPage.this, SubmissionPage.class);
        startActivity(nextPage);
    }
}
