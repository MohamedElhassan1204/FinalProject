package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Flight {
    @PrimaryKey @NonNull
    @ColumnInfo (name = "FlightCode")
    private String flightCode;

    @ColumnInfo (name = "destination")
    private String destination;

    @ColumnInfo (name = "terminal")
    private String terminal;

    @ColumnInfo (name = "gate")
    private String gate;

    @ColumnInfo (name = "delay")
    private String delay;

    // Constructor
    public Flight(String flightCode, String destination, String terminal, String gate, String delay) {
        this.flightCode = flightCode;
        this.destination = destination;
        this.terminal = terminal;
        this.gate = gate;
        this.delay = delay;
    }

    public Flight() {
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

}
