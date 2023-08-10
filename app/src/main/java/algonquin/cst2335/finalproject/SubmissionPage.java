package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.SubmissionPageBinding;

public class SubmissionPage extends AppCompatActivity {

    int questionNumber;
    int score;
    int totalScore;
    RecyclerView recyclerView;
    ScoreAdapter scoreAdapter; // Use the ScoreAdapter here
    boolean hasSubmitted = false;
    ArrayList<String> contestant;
    int scoreToPass ;
    QuizContestantDAO qDAO;
    QuizContestantViewModel contestantModel;
    ArrayList<QuizContestant> quizContestant;
    QuizContestant contestantObj;
    QuizContestantDAO contestantDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubmissionPageBinding binding = SubmissionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contestantModel = new ViewModelProvider(this).get(QuizContestantViewModel.class);
        quizContestant = contestantModel.quizContestant.getValue();

        contestant = new ArrayList<>();
        recyclerView = binding.recyclerScoreView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreAdapter = new ScoreAdapter(this, contestant, scoreToPass); // Use ScoreAdapter
        recyclerView.setAdapter(scoreAdapter); // Set the scoreAdapter

        QuestionDatabase db = Room.databaseBuilder(getApplicationContext(), QuestionDatabase.class, "database-name").build();
        qDAO = db.qcDAO();

        binding.scoreButton.setOnClickListener(view -> {

            // Create a QuizContestant object and set its attributes
            String name = binding.nameText.getText().toString();
            String contestantScore = String.valueOf(scoreToPass); // You need to set the score here based on your logic

            if (!name.isEmpty()) {


                // Pass the contestant object to the ContestantDetailsFragment


                contestant.add(name);
                contestant.add(String.valueOf(score));
                hasSubmitted = true;
            } else {
                Toast.makeText(this, "Please enter a name before submitting.", Toast.LENGTH_SHORT).show();
            }
        });

//        private void calculateScore() {
//            Intent fromPrevious = getIntent();
//            questionNumber = fromPrevious.getIntExtra("questionNumber", 0);
//            score = fromPrevious.getIntExtra("score", 0);
//            if (score == 0) {
//                totalScore = 0;
//            } else {
//                totalScore = score / questionNumber;
//            }
//            scoreToPass = totalScore; // Assign the calculated totalScore to scoreToPass
//        }
    }
}
