package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import algonquin.cst2335.finalproject.databinding.QuizPageBinding;

public class QuizPage extends AppCompatActivity {
    QuizPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page);
        binding = QuizPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button submitButton = binding.submitButton;

        binding.math.setOnClickListener(clk -> {
        });

        binding.scienceNature.setOnClickListener(clk -> {
        });

        binding.geography.setOnClickListener(clk -> {
        });

        binding.entMusic.setOnClickListener(clk -> {
        });

        binding.entTelevision.setOnClickListener(clk -> {
        });

        binding.entGames.setOnClickListener(clk -> {
        });

        submitButton.setOnClickListener(clk -> {
        });

    }
}