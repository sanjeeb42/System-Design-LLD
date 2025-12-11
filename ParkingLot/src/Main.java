import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;

enum VehicleType{
    BIKE,
    CAR,
    BUS
};

enum ParkingSpotType{
    BIKE,
    CAR,
    LARGE
};

class Vehicle{
    VehicleType vehicleType;
    String registrationNumber;

    public Vehicle(VehicleType vehicleType, String registrationNumber) {
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}

class ParkingSpot{
    String spotId;
    ParkingSpotType spotType;
    Vehicle vehicle;
    boolean isEmpty;

    public ParkingSpot(String spotId, ParkingSpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.isEmpty = true;
    }

    public ParkingSpotType getSpotType() {
        return spotType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}

class ParkingTicket{
    String ticketId;
    Vehicle vehicle;
    String spotId;
    LocalDateTime entryTime;

    public ParkingTicket(String ticketId, LocalDateTime entryTime, String spotId, Vehicle vehicle) {
        this.ticketId = ticketId;
        this.entryTime = entryTime;
        this.spotId = spotId;
        this.vehicle = vehicle;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getSpotId() {
        return spotId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}

class ParkingSpotManager{
    Map<ParkingSpotType, Queue<ParkingSpot>>freeSpots;
    Map<String,ParkingSpot>parkingSpotMap;

    private ParkingSpotType mapVehicleToSpot(Vehicle vehicle) {
        switch (vehicle.getVehicleType()) {
            case BIKE: return ParkingSpotType.BIKE;
            case CAR: return ParkingSpotType.CAR;
            case BUS: return ParkingSpotType.LARGE;
            default: return null;
        }
    }


    ParkingSpot getParkingSpot(Vehicle vehicle){
        Queue<ParkingSpot> list=freeSpots.get(mapVehicleToSpot(vehicle));
        if(list.isEmpty())return null;
        return list.poll();
    }

    void freeSpot(ParkingSpot parkingSpot){
        parkingSpot.setEmpty(true);
        parkingSpot.setVehicle(null);
        freeSpots.get(parkingSpot.getSpotType()).add(parkingSpot);
    }
}

class EntryGate{
    ParkingSpotManager manager;

    ParkingTicket generateParkingTicket(Vehicle vehicle){
        ParkingSpot parkingSpot=manager.getParkingSpot(vehicle);
        if(parkingSpot==null)return null;
        return new ParkingTicket(java.util.UUID.randomUUID().toString(),LocalDateTime.now(), parkingSpot.getSpotId(), vehicle);
    }

}

class ExitGate{
    ParkingSpotManager manager;
    void processExit(ParkingTicket ticket,ParkingStrategy strategy){
        double amount=strategy.calculateCost(ticket);
        //Pay and simulate Payment using Payment Processing gateway - razorpay, paytm
        manager.freeSpot(manager.parkingSpotMap.get(ticket.spotId));
    }
}

interface ParkingStrategy{
    double calculateCost(ParkingTicket ticket);
}

class BikeParkingStrategy implements ParkingStrategy{

    @Override
    public double calculateCost(ParkingTicket ticket) {
        long hours = java.time.Duration
                .between(ticket.getEntryTime(), LocalDateTime.now())
                .toHours();
        return hours * 20;
    }
}

class CarParkingStrategy implements ParkingStrategy{

    @Override
    public double calculateCost(ParkingTicket ticket) {
        long hours = java.time.Duration
                .between(ticket.getEntryTime(), LocalDateTime.now())
                .toHours();
        return hours * 50;
    }
}


public class Main {
    public static void main(String[] args) {

        Queue<ParkingSpot> bikeSpots = new java.util.LinkedList<>();
        bikeSpots.add(new ParkingSpot("B1", ParkingSpotType.BIKE));
        bikeSpots.add(new ParkingSpot("B2", ParkingSpotType.BIKE));

        Queue<ParkingSpot> carSpots = new java.util.LinkedList<>();
        carSpots.add(new ParkingSpot("C1", ParkingSpotType.CAR));
        carSpots.add(new ParkingSpot("C2", ParkingSpotType.CAR));

        Queue<ParkingSpot> largeSpots = new java.util.LinkedList<>();
        largeSpots.add(new ParkingSpot("L1", ParkingSpotType.LARGE));

        Map<ParkingSpotType, Queue<ParkingSpot>> freeSpots = new java.util.HashMap<>();
        freeSpots.put(ParkingSpotType.BIKE, bikeSpots);
        freeSpots.put(ParkingSpotType.CAR, carSpots);
        freeSpots.put(ParkingSpotType.LARGE, largeSpots);

        Map<String ,ParkingSpot> spotMap = new java.util.HashMap<>();
        for(ParkingSpot s : bikeSpots) spotMap.put(s.getSpotId(), s);
        for(ParkingSpot s : carSpots) spotMap.put(s.getSpotId(), s);
        for(ParkingSpot s : largeSpots) spotMap.put(s.getSpotId(), s);

        ParkingSpotManager manager = new ParkingSpotManager();
        manager.freeSpots = freeSpots;
        manager.parkingSpotMap = spotMap;


        EntryGate entryGate = new EntryGate();
        entryGate.manager = manager;

        ExitGate exitGate = new ExitGate();
        exitGate.manager = manager;

        Vehicle bike = new Vehicle(VehicleType.BIKE, "BIKE-123");
        Vehicle car = new Vehicle(VehicleType.CAR, "CAR-555");


        ParkingTicket bikeTicket = entryGate.generateParkingTicket(bike);
        System.out.println("Bike Ticket → " + bikeTicket.getTicketId() +
                " Spot: " + bikeTicket.getSpotId());

        ParkingTicket carTicket = entryGate.generateParkingTicket(car);
        System.out.println("Car Ticket → " + carTicket.getTicketId() +
                " Spot: " + carTicket.getSpotId());

        try { Thread.sleep(2000); } catch (Exception ignored) {}


        ParkingStrategy bikeStrategy = new BikeParkingStrategy();
        ParkingStrategy carStrategy = new CarParkingStrategy();

        exitGate.processExit(bikeTicket, bikeStrategy);
        System.out.println("Bike EXIT complete!");

        exitGate.processExit(carTicket, carStrategy);
        System.out.println("Car EXIT complete!");
    }
}