package facebookdatamining.Domain.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Thiago N. Oliveira
 */
public abstract class BaseRepository {

    private static Configuration configuration = new Configuration();
    private static SessionFactory factory = CreateSessionFactory();
    private static Session _session;
    private static Session session;
    private static Object SyncObj = 1;

    public static Session getSession() {
        if (_session != null) {
            return _session;
        } else {
            return _session = GetCurrentSession();
        }
    }

    public static void setSession(Session session) {
        _session = session;
    }

    public BaseRepository() {
    }

    public BaseRepository(Session session) {
        BaseRepository.session = session;
    }

    public static void CloseTransaction(Transaction transaction) {
        //transaction.
    }

    public static Session GetCurrentSession() {
        Session currentSession;
        currentSession = factory.openSession();
        return currentSession;
    }

    public static SessionFactory CreateSessionFactory() {
        return configuration.buildSessionFactory();
    }
}
