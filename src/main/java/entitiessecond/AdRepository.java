package entitiessecond;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

/**
 * - добавить обьявление
 * - показать объявления за последний день;
 * - показать объявления с фото;
 * - показать объявления определенной марки
 */

public class AdRepository implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new AdRepository();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /** сохранить обьявление */
    @Override
    public Advertisement saveADS(Advertisement ads) {
        return tx(session -> {
            session.persist(ads);
            return ads;
        });
    }

    /** Вывод обьявлений за последние 24 часа */
    @Override
    public List<Advertisement> showAdsLastDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String start = formatter.format(LocalDateTime.now().minusHours(24));
        String end = formatter.format(LocalDateTime.now());
        return tx(session -> {
            List<Advertisement> rsl = session.createQuery("select a from Advertisement a where a.created between :param1 and :param2")
                    .setParameter("param1", Timestamp.valueOf(start))
                    .setParameter("param2", Timestamp.valueOf(end))
                    .getResultList();
            return rsl;
        });
    }

    /** обьявления с фото */
    @Override
    public List<Advertisement> withPhoto() {
        return tx(session -> session.createQuery("select a from Advertisement a where a.photoList.size > 0").list());
    }

    /** выбрать обьявления по марке */
    @Override
    public List<Advertisement> differentMark(String mark) {
        return tx(session -> {
            List<Advertisement> rsl = session.createQuery("select a from Advertisement a where a.mark.name = :param")
                    .setParameter("param", mark)
                    .list();
            return rsl;
        });
    }

    @Override
    public void close() throws Exception {
    }
}
