package entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** пример работы с обЪектами */

public class Db {
    public static void main(String[] args) {
        SessionFactory sessionfactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        Session session = null;

        try {
            session = sessionfactory.openSession();
            session.getTransaction().begin();

            Engine engine1 = new Engine("123-765-234");
            Car car1 = new Car("BMW", engine1);
            Driver driver1 = new Driver("Andrey");
            car1.addDriver(driver1);
            session.persist(car1);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sessionfactory.close();
        }
    }
}
