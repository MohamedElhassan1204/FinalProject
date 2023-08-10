package algonquin.cst2335.finalproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {QuizContestant.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {
    public abstract QuizContestantDAO qcDAO();
}
