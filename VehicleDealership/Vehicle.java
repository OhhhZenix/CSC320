public class Vehicle {
    private String vin;
    private String make;
    private String model;
    private String color;
    private int year;
    private int mileage;

    public Vehicle() {
        this.vin = "??? VIN";
        this.make = "??? Model";
        this.model = "??? Make";
        this.color = "??? Color";
        this.year = 0;
        this.mileage = 0;
    }

    public Vehicle(String vin, String make, String model, String color, int year, int mileage) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.mileage = mileage;
    }

    public String getVIN() {
        return this.vin;
    }

    public void setVIN(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return this.mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String summary() {
        return String.format(
                "%s %s %s %s with %s miles (VIN: %s).",
                this.year, this.color, this.make, this.model, this.mileage, this.vin);
    }

    public static Vehicle deserialize(String data) {
        String[] splitData = data.split(";");
        try {
            String vin = splitData[0];
            String make = splitData[1];
            String model = splitData[2];
            String color = splitData[3];
            int year = Integer.parseInt(splitData[4]);
            int mileage = Integer.parseInt(splitData[5]);
            return new Vehicle(vin, make, model, color, year, mileage);
        } catch (Exception e) {
            System.out.println("Failed to read data.");
            e.printStackTrace();
        }
        return new Vehicle();
    }

    public String serialize() {
        return String.format(
                "%s;%s;%s;%s;%s;%s",
                this.vin, this.make, this.model, this.color, this.year, this.mileage);
    }
}
