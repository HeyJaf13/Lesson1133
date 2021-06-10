package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session ;
    private Transaction transaction ;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();    // открыл .спросить ментра нужно ли авто импорт
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE testtable2 " +
                    "(id INT not NULL AUTO_INCREMENT, " +
                    " PRIMARY KEY (id))" +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " Age VARCHAR (3), ");
            transaction.commit();
            System.out.println("естть");
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age)); // или save побуй и спроси  метод сеанса persist
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            list = session.createSQLQuery("SELECT * FROM users").addEntity("name", User.class).list();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            SQLQuery query;
            query = session.createSQLQuery("TRUNCATE Table users");
            query.executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
