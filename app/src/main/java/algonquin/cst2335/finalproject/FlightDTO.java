package algonquin.cst2335.finalproject;

public class FlightDTO {
    private String flightCode;
    private String destination;
    private String terminal;
    private String gate;
    private String delay;

    // Constructor
    public FlightDTO(String flightCode, String destination, String terminal, String gate, String delay) {
        this.flightCode = flightCode;
        this.destination = destination;
        this.terminal = terminal;
        this.gate = gate;
        this.delay = delay;
    }

    public FlightDTO() {
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
