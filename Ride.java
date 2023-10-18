/**
 * Abstract class representing an amusement park ride.
 * @author jvogt33
 * @version 1.00
 */
public abstract class Ride {
    protected final String id;
    protected double earnings;
    protected int runsSinceInspection;
    protected String[] passengers;

    /**
     * Constructor of new Ride with specified params.
     * @param id the name of this Ride
     * @param runsSinceInspection Runs since Ride's last inspectRide() invocation
     * @param passengers list of passengers on the Ride
     */
    public Ride(String id, int runsSinceInspection, String[] passengers) {
        this.id = id;
        this.earnings = 0.0;
        this.runsSinceInspection = runsSinceInspection;
        this.passengers = new String[passengers.length];
        for (int i = 0; i < passengers.length; i++) {
            this.passengers[i] = passengers[i];
        }
    }

    /**
     * Constructor of new Ride with default runsSinceInspection of 0.
     * @param id the name of this Ride
     * @param passengers list of passengers on this Ride
     */
    public Ride(String id, String[] passengers) {
        this(id, 0, passengers);
    }

    /**
     * Virtual method to test whether Ride can run. Overridden in subclasses.
     * @param stops Number of stops to run the ride
     * @return boolean whether the ride can run number of stops given
     */
    public abstract boolean canRun(int stops);

    /**
     * Virtual method to inspect current Ride. Overridden in subclasses.
     * runsSinceInspection should also be reset if inspection passes.
     * @param components Components to inspect on this ride
     * @return boolean whether inspection was successful.
     */
    public abstract boolean inspectRide(String[] components);

    /**
     * Virtual method to calculate cost per passenger. Overridden in subclasses.
     * @param stops number of stops a passenger has requested to run.
     * @return double-precision floating point of ride cost.
     */
    public abstract double costPerPassenger(int stops);

    /**
     * Virtual method to add passengers to ride. Overridden in subclasses.
     * @param stops Number of stops passengers will ride
     * @param passToAdd List of passengers to add
     * @return Boolean whether adding passengers was successful
     */
    public abstract boolean addPassengers(int stops, String[] passToAdd);

    /**
     * String representation of passengers[].
     * @return String representing list of passengers
     */
    public String getPassengerList() {
        String o = "";
        o += String.format("Passenger List for %s:", id);
        for (int i = 0; i < passengers.length; i++) {
            if (passengers[i] != null) {
                o += String.format("%n%s", passengers[i]);
            }
        }
        return o;
    }

    /**
     * Charges passengers for a specified number of stops.
     * This method is final and should not be overridden.
     * @param stops Number of stops to charge passenger for
     */
    public final void chargePassenger(int stops) {
        this.earnings += costPerPassenger(stops);
    }

    /**
     * Removes passenger from the ride. Removes first occurrence of passenger.
     * @param name The name of the passenger to remove
     * @return boolean whether the passenger was successfully removed
     */
    public boolean removePassenger(String name) {

        for (int i = 0; i < this.passengers.length; i++) {
            if (this.passengers[i] != null) {
                if (this.passengers[i].toLowerCase().equals(name.toLowerCase())) {
                    this.passengers[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Overrides Object.equals(). Checks whether two Rides are equal.
     * @param other the object to test against
     * @return boolean representing equality of the objects
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() == other.getClass()) {
            return (this.id == ((Ride) other).id
                && this.runsSinceInspection == ((Ride) other).runsSinceInspection);
        }
        return false;
    }

    /**
     * String representation of this Ride.
     * @return String representing this Ride
     */
    @Override
    public String toString() {
        String o = String.format("%s has run %d times and has earned $%.2f.",
            this.id, this.runsSinceInspection, this.earnings);
        return o;
    }
}