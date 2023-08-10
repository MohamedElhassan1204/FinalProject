package algonquin.cst2335.finalproject;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.QuizSelectionPageBinding;

public class QuizSelectionPage extends AppCompatActivity {
    QuizSelectionPageBinding binding;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_selection_page);
        binding = QuizSelectionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items = new ArrayList<>();
        items.add(0,"Math");
        items.add(1,"Geography");
        items.add(2,"Science & Nature");
        items.add(3,"Entertainment:Music");
        items.add(4,"Entertainment:Television");
        items.add(5,"Entertainment:Games");
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,items);
        recyclerView.setAdapter(adapter);

            binding.startTest.setOnClickListener(click -> {
                if (!binding.questionNumber.getText().toString().isEmpty()) {

                    int questionNumber = Integer.parseInt(binding.questionNumber.getText().toString());
                    String url = null;

                    if (questionNumber > 50 || questionNumber == 0) {
                        Toast.makeText(this, "cannot select over 50 questions:", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("questionNumber", questionNumber);
                        editor.apply();

                        Intent nextPage = new Intent(QuizSelectionPage.this, QuestionPage.class);
                        nextPage.putExtra("questionNumber", questionNumber);

                        startActivity(nextPage);
                        Log.w("MainActivity", "In onCreate() - Loading Widgets");
                        //binding..setOnClickListener(click -> {});
                    }
                }
            });
        }
    }