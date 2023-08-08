package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import algonquin.cst2335.finalproject.databinding.QuizSelectionPageBinding;

public class QuizSelectionPage extends AppCompatActivity {
    QuizSelectionPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_selection_page);
        binding = QuizSelectionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int questionNumber = Integer.parseInt(binding.questionNumber.getText().toString());
        EditText questionNumberItem = binding.questionNumber;

        binding.startTest.setOnClickListener(click -> {
                String url = null;
                if(questionNumber > 50 || questionNumber == 0) {
                    Toast.makeText(this, "cannot select over 50 questions:", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent nextPage = new Intent( QuizSelectionPage.this, QuestionPage.class);
                    nextPage.putExtra("questionNumber",questionNumber);
                    startActivity(nextPage);
                    Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
                    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    questionNumberItem.setText(prefs.getString("questionNumber",""));
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Email Address", questionNumberItem.getText().toString());
                    editor.apply();
//            binding..setOnClickListener(click -> {});
                }
            });
    }
}