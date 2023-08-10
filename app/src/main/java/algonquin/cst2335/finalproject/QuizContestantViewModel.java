package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class QuizContestantViewModel extends ViewModel {

    public MutableLiveData<ArrayList<QuizContestant>> quizContestant = new MutableLiveData<>();
    public MutableLiveData<QuizContestant> scoreBoard= new MutableLiveData<>();
}
