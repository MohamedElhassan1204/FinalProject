package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuizContestantDAO {

    @Insert
    public long insertContestant(QuizContestant c);

    @Query("Select * from QuizContestant ORDER BY score DESC")
    public List<QuizContestant> getContestants();

    @Delete
    void deleteContestant(QuizContestant c);
}
