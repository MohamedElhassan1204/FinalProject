package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TriviaQuiz.class}, version = 1)
public abstract class QuizDatabase extends RoomDatabase {

    public abstract TriviaQuizDAO cmDAO();
}
