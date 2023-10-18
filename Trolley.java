/**
 * Represents a Trolley, extension on Ride.
 * @author jvogt33
 * @version 1.00
 */
public class Trolley extends Ride {
    private String[] stations;
    private int currentStation;

    /**
     * Creates new Trolley with specified parameters.
     * @param id The name of this Trolley
     * @param runsSinceInspection The <b>full loops</b> run since last inspection
     * @param stations Array containing all stops along a loop
     * @param currentStation The station where the Trolley currently resides
     */
    public Trolley(String id, int runsSinceInspection, String[] stations, int currentStation) {
        super(id, runsSinceInspection, new String[20]);
        this.currentStation = currentStation;
        this.stations = new String[stations.length];
        for (int i = 0; i < stations.length; i++) {
            this.stations[i] = stations[i];
        }
    }

    /**
     * Trolley constructor with default runsSinceInspection of 0.
     * @param id The name of this Trolley
     * @param stations Array containing all stops along a loop
     * @param currentStation The station where the Trolley currently resides
     */
    public Trolley(String id, String[] stations, int currentStation) {
        this(id, 0, stations, currentStation);
    }

    /**
     * Returns boolean whether or not the Trolley can run the number of runs provided.
     * @param numRuns the number of runs the Trolley will travel.
     * @return boolean whether this Trolley can run
     */
    @Override
    public boolean canRun(int numRuns) {
        if (numRuns >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Inspects ride and sets runsSinceInspection to 0 if it passes.
     * @param components pass in components to test for
     * @return boolean whether this ride passed inspection
     */
    @Override
    public boolean inspectRide(String[] components) {
        String gas = "Gas Tank Not Empty";
        String brakes = "Brakes OK";
        if (contains(components, gas) && contains(components, brakes)) {
            this.runsSinceInspection = 0;
            return true;
        }
        return false;
    }

    /**
     * Returns the cost for each passenger for the number of stops traveled.
     * @param stops int for number of stops to travel
     * @return double representation of passenger cost
     */
    @Override
    public double costPerPassenger(int stops) {
        double loopSize = (double) stations.length;
        return ((double) stops * 3.0) / loopSize;
    }

    /**
     * Adds list of passengers for a specified number of stops.
     * Returns true if the trolley can run regardless of number of passengers
     * added.
     * @param stops the number of Stops to travel
     * @param passengers a list of passengers to add.
     * @return boolean whether the Trolley can run
     */
    @Override
    public boolean addPassengers(int stops, String[] passengers) {
        if (!canRun(stops)) {
            return false;
        }
        for (String p : passengers) {
            for (int i = 0; i < this.passengers.length; i++) {
                if (this.passengers[i] == null) {
                    this.passengers[i] = p;
                    chargePassenger(stops);
                    break;
                }
            }
        }
        moveTrolley(stops);
        return true;
    }

    /**
     * Moves trolley a specified number of stops.
     * @param stops The number of stops to move
     */
    public void moveTrolley(int stops) {
        if (!this.canRun(stops)) {
            return;
        }
        int runsCompleted = 0;
        if ((currentStation + stops) >= stations.length) {
            runsCompleted += ((currentStation + stops) / stations.length);
            runsSinceInspection += runsCompleted;
        }
        for (int i = 0; i < stops; i++) {
            currentStation++;
            if (currentStation >= stations.length) {
                currentStation = 0;
            }
        }
    }

    /**
     * Overrides Ride.equals. Tests if two Trolleys equal.
     * @param other the Object to test against
     * @return boolean representation of equality
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() == other.getClass()) {
            Trolley o = (Trolley) other;
            if (this.stations.length != o.stations.length) {
                return false;
            }
            for (int i = 0; i < this.stations.length; i++) {
                if (!(this.stations[i].toLowerCase().equals(
                    o.stations[i].toLowerCase()))) {
                    return false;
                }
            }
            return (super.equals(other)
                && this.currentStation == o.currentStation);
        }
        return false;
    }

    /**
     * Overrides Object.toString.
     * @return String representation of this trolley.
     */
    @Override
    public String toString() {
        String nextStation = (this.currentStation < this.stations.length - 1
            ? this.stations[currentStation + 1] : this.stations[0]);
        String o = String.format("Trolley %s has driven %d loops and has earned $%.2f. ",
            this.id, this.runsSinceInspection, this.earnings);
        o += String.format("This trolley is at %s. Next up is %s.",
            this.stations[currentStation], nextStation);
        return o;
    }

    ///Helper methods
    private boolean contains(String[] s, String element) {
        for (String e : s) {
            if (e.toLowerCase().equals(element.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
