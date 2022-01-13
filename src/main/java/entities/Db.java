package entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.function.Function;

/** БД, шаблон Wrapper */
public class Db implements AutoCloseable, Store{

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Db INST = new Db();
    }

    public static Db instOf() {
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

    @Override
    public void saveCar(Car car) {
        tx(session -> {
            session.persist(car);
            return car;
        });
    }

    @Override
    public void saveEngine(Engine en) {
        tx(session -> {
            session.save(en);
            return en;
        });
    }

    @Override
    public void saveDriver(Driver dr) {
        tx(session -> {
            session.save(dr);
            return dr;
        });
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
