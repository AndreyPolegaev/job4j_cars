package entities;

public class ClientClass {

    public static void main(String[] args) {
        Store db = Db.instOf();
        Engine b52 = new Engine("b52");
        Driver andrey = new Driver("Andrey");
        Car bmw = new Car("BMW", b52);
        bmw.addDriver(andrey);
        db.saveCar(bmw);
    }
}
