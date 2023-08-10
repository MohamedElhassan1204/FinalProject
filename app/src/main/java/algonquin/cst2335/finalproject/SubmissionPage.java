package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.SubmissionPageBinding;

public class SubmissionPage extends AppCompatActivity {

    int questionNumber;
    int score;
    int totalScore;
    RecyclerView recyclerView;
    Adapter adapter;

    ArrayList<String> contestant;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubmissionPageBinding binding = SubmissionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contestant = new ArrayList<>();
        contestant.add(1,binding.nameText.getText().toString());
        recyclerView = binding.recyclerScoreView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,contestant);
        recyclerView.setAdapter(adapter);

    }
    private void calculateScore(){
        Intent fromPrevious = getIntent();
        questionNumber = fromPrevious.getIntExtra("questionNumber", 0);
        score = fromPrevious.getIntExtra("score",0);
        totalScore = score/questionNumber;

    }
}
