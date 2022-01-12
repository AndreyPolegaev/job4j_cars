package entitiessecond;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

/** - показать объявления за последний день;
 - показать объявления с фото;
 - показать объявления определенной марки */

public class AdRepository implements AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public void initObjects() {
        Session session = sf.openSession();
        session.getTransaction().begin();

        Users u1 = new Users("Andrey");
        Mark bmw = new Mark("BMW");
        Body body1 = new Body("Sedan");
        Advertisement ads1 = new Advertisement("some advertisement", false, bmw, body1, u1);
        session.persist(ads1);

        session.getTransaction().commit();
        session.close();
    }

    /** ???????????  */
    public List<Advertisement> showAdsLastDay() {
        List<Advertisement> ads = null;
        Session session = sf.openSession();
        session.getTransaction().begin();

        ads = session.createQuery("select a from Advertisement a where a.created between current_timestamp - 1 and current_timestamp").list();

        session.getTransaction().commit();
        session.close();
        return ads;
    }

    /** обьявления с фото */
    public List<Advertisement> withPhoto() {
        List<Advertisement> ads = null;
        Session session = sf.openSession();
        session.getTransaction().begin();
        ads = session.createQuery("select a from Advertisement a where a.photoList.size > 0").list();
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    /** выбрать обьявления по марке */
    public List<Advertisement> differentMark(String mark) {
        List<Advertisement> ads = null;
        Session session = sf.openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("select a from Advertisement a where a.mark = :param");
        query.setParameter("param", mark);
        ads = query.list();
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    @Override
    public void close() throws Exception {
    }
}
