/**
 * Class representing Roller Coaster. Extends Ride.
 * @author jvogt33
 * @version 1.00
 */
public class RollerCoaster extends Ride {
    private double rate;
    private double photoFees;
    private int maxNumRuns;

    /**
     * Constructor for new RollerCoaster with specified parameters.
     * @param id the name of this RollerCoaster
     * @param runsSinceInspection Runs since last inspectRide() invocation
     * @param passengers List of passengers on this RollerCoaster
     * @param rate double-precision float representing cost per ride
     * @param photoFees double-precision float representing photo fee
     * @param maxNumRuns maximum runs between inspectRide() invocations
     */
    public RollerCoaster(String id, int runsSinceInspection, String[] passengers,
        double rate, double photoFees, int maxNumRuns) {
        super(id, runsSinceInspection, passengers);
        this.rate = rate;
        this.photoFees = photoFees;
        this.maxNumRuns = maxNumRuns;
    }

    /**
     * Constructor for RollerCoaster with passenger list of size 4, rate 10, photoFees 15.
     * @param id the name of this RollerCoaster
     * @param runsSinceInspection Runs since last inspectRide() invocation
     * @param maxNumRuns maximum runs between inspectRide() invocations
     */
    public RollerCoaster(String id, int runsSinceInspection, int maxNumRuns) {
        this(id, runsSinceInspection, new String[4], 10, 15, maxNumRuns);
    }

    /**
     * Constructor for RollerCoaster with maxNumRuns 200 and other defaults.
     * @param id the name of this RollerCoaster
     */
    public RollerCoaster(String id) {
        this(id, 0, new String[4], 10, 15, 200);
    }

    /**
     * Checks whether this RollerCoaster can run specified number of stops.
     * @param stops int representing number of stops to run
     * @return boolean whether the RollerCoaster can run the number of stops.
     */
    @Override
    public boolean canRun(int stops) {
        if (stops < 0
            || (this.runsSinceInspection + stops) > this.maxNumRuns) {
            return false;
        }
        return true;
    }

    /**
     * Represents a ride inspection.
     * Sets runsSinceInspection to 0 if passed inspection.
     * @param components the components of the ride to check for
     * @return boolean whether the RollerCoaster passed inspection
     */
    @Override
    public boolean inspectRide(String[] components) {
        String comp1 = "Tracks Clear";
        String comp2 = "Brakes OK";
        if (contains(components, comp1) && contains(components, comp2)) {
            this.runsSinceInspection = 0;
            return true;
        }
        return false;
    }

    /**
     * Calculates cost for number of stops given.
     * @param stops number of stops to travel
     * @return double-precision float representing cost for a passenger
     */
    @Override
    public double costPerPassenger(int stops) {
        return ((double) stops * (double) this.rate) + (double) this.photoFees;
    }

    /**
     * Adds passengers if a) there is space and b) canRun(stops) is true.
     * @param stops number of stops to travel
     * @param passengers list of passengers to add
     * @return boolean whether adding passengers was successful
     */
    @Override
    public boolean addPassengers(int stops, String[] passengers) {
        if (!canRun(stops) || numEmptySeats() < passengers.length) {
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
        runsSinceInspection += stops;
        return true;
    }

    /**
     * Tests for equality between RollerCoasters. Overrides Ride.equals.
     * @param other object to check against
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
            RollerCoaster o = (RollerCoaster) other;
            return (this.id.equals(o.id)
                && this.runsSinceInspection == o.runsSinceInspection
                && this.rate == o.rate
                && this.photoFees == o.photoFees
                && this.maxNumRuns == o.maxNumRuns);
        }
        return false;
    }

    /**
     * String representation of this RollerCoaster. Overrides Ride.toString.
     * @return string representing this RollerCoaster.
     */
    @Override
    public String toString() {
        String o = String.format("Roller Coaster %s ", super.toString());
        o += String.format("It can only run %d more times. ",
            maxNumRuns - runsSinceInspection);
        o += String.format("It costs $%.2f per ride and there is a one-time photo fee of $%.2f.",
            this.rate, this.photoFees);
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

    private int numEmptySeats() {
        int count = 0;
        for (String i : passengers) {
            if (i == null) {
                count++;
            }
        }
        return count;
    }
}
