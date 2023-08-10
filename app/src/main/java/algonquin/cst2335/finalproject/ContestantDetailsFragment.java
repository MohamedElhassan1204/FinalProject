package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.databinding.ScoreLayoutBinding;

    public class ContestantDetailsFragment extends Fragment {

        QuizContestant selected;

        public ContestantDetailsFragment(QuizContestant m){
            selected = m;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            ScoreLayoutBinding binding = ScoreLayoutBinding.inflate(inflater);

            binding.nameText.setText(selected.getName());
            binding.score.setText(selected.getScore());
            binding.rank.setText(selected.getId());

            return binding.getRoot();
        }
    }


