/**
 * @author jvogt33
 * @version 1.00
 */
public abstract class Ride {
    protected final String id;
    protected double earnings;
    protected int ridesSinceInspection;
    protected String[] passengers;

    public Ride(String id, int ridesSinceInspection, String[] passengers) {
        this.id = id;
        this.earnings = 0.0;
        this.ridesSinceInspection = ridesSinceInspection;
        this.passengers = new String[passengers.length];
        for(int i = 0; i < passengers.length; i++) {
            this.passengers[i] = passengers[i];
        }
    }

    public Ride(String id, String[] passengers) {
        this(id, 0, passengers);
    }

    public abstract boolean canRun(int numRuns);

    public abstract boolean inspectRide(String[] components);

    public abstract double costPerPassenger(int stops);

    public abstract boolean addPassengers(int stops , String[] passengers);
    
    public String getPassengerList() {
        String o = "";
        o += String.format("Passenger list for %d:%n", id);
        for (String i : this.passengers) {
            if (i != null) {
                o += String.format("%s%n", i);
            }
        }
        return o;
    }

    public final void chargePassenger(int stops) {
        this.earnings += costPerPassenger(stops);
    }

    public boolean removePassenger(String name) {
        
        for(int i = 0; i < this.passengers.length; i++) {
            if (this.passengers[i] != null) {
                if (this.passengers[i].toLowerCase().equals(name.toLowerCase())) {
                    this.passengers[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Ride) {
            Ride r = (Ride) other;
            if(this.id == r.id
                && this.ridesSinceInspection == r.ridesSinceInspection) {
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String o = String.format("%d has run %d times and has earned $%.2f.",
            this.id, this.ridesSinceInspection, this.earnings);
        return o;
    }
}