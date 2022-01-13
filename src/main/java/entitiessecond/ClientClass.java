package entitiessecond;

public class ClientClass {
    public static void main(String[] args) {
        Store store = AdRepository.instOf();
        Users u1 = new Users("Petr");
        Mark volvo = new Mark("Volvo");
        Body body1 = new Body("Sedan");
        Advertisement ads1 = new Advertisement("some advertisement2", false, volvo, body1, u1);
        store.saveADS(ads1);
    }
}
