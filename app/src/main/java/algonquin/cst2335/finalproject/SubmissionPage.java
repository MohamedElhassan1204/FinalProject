package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.finalproject.databinding.SubmissionPageBinding;

public class SubmissionPage extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubmissionPageBinding binding = SubmissionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
