package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import algonquin.cst2335.finalproject.Flight;
import java.util.List;

@Dao
public interface FlightDAO {

    @Query("SELECT * FROM Flight")
    List<Flight> getAllFlights();

    @Insert
    void insertFlight(Flight flightCode);

    @Delete
    void deleteFlight(Flight flightCode);
}
