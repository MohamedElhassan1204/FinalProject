package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.SubmissionPage;
import algonquin.cst2335.finalproject.databinding.QuestionPageBinding;

public class QuestionPage extends AppCompatActivity {

    @NonNull
    QuestionPageBinding binding;
    int index = 0;
    int score = 0;
    ArrayList<Button> answers = new ArrayList<>();
    int questionNumber;
    String correctAnswer;
    boolean isSelected;
    private Button selectedButton = null;
    RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = QuestionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadQuestionData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
                    + "&category=" + URLEncoder.encode("", "UTF-8") + "&type=multiple";
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

    private String formatHtml(String JsonText) {
        JsonText = HtmlCompat.fromHtml(JsonText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
        return JsonText;
    }

    private void loadQuestionData(JSONArray triviaQuiz) throws JSONException {
        initializeUIElements();
        answers.clear(); // Clear the previous answers
        answers.add(binding.answerA);
        answers.add(binding.answerB);
        answers.add(binding.answerC);
        answers.add(binding.answerD);
        JSONObject questionObject = triviaQuiz.getJSONObject(index);
        String question = questionObject.getString("question");
        correctAnswer = questionObject.getString("correct_answer");
        JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");
        runOnUiThread(() -> {
            binding.questionText.setText(formatHtml(question));
            binding.questionNumber.setText((index + 1) + ":");
            Random rand = new Random();
            int answerRandomizer = rand.nextInt(4);
            answers.get(answerRandomizer).setText(formatHtml(correctAnswer));
            answers.remove(answerRandomizer);
            try {
                answers.get(0).setText(formatHtml(incorrectAnswers.getString(0)));
                answers.get(1).setText(formatHtml(incorrectAnswers.getString(1)));
                answers.get(2).setText(formatHtml(incorrectAnswers.getString(2)));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setAnswerButtonListeners() {
        for (Button answer : answers) {
            answer.setOnClickListener(click -> {
                if (selectedButton != null) {
                    selectedButton.setBackgroundColor(getColor(R.color.purple_500));
                }
                answer.setBackgroundColor(getColor(R.color.teal_200));
                selectedButton = answer;
                checkAnswer(answer.getText().toString());
            });
        }
    }

    private void resetButton() {
        for (Button btn : answers) {
            btn.setBackgroundColor(getColor(R.color.purple_500));
        }
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }
    }

    private void loadNextQuestion(View view) {
        initializeUIElements();
        isSelected = false;
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
        resetButton();
    }

    private int calculateScore() {
        int calculatedScore = 0;

        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getText().toString().equals(correctAnswer)) {
                calculatedScore++;
            }
        }
        return calculatedScore;
    }

    private void saveScore(int calculatedScore) {
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", calculatedScore);
        editor.apply();
    }

    private void submitAnswers(View view) {
        Intent nextPage = new Intent(QuestionPage.this, SubmissionPage.class);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("questionNumber", questionNumber);
        editor.apply();

        int calculatedScore = calculateScore();
        saveScore(calculatedScore);

        nextPage.putExtra("score", score);
        startActivity(nextPage);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.Help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instructions");
            builder.setMessage(R.string.helpmessage);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (item.getItemId() == R.id.Home) {
            Intent HomeIntent = new Intent(QuestionPage.this, MainActivity.class);
            startActivity(HomeIntent);
        } else if (item.getItemId() == R.id.Delete) {
            Snackbar.make(binding.getRoot(), "Item Deleted", Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }
}
