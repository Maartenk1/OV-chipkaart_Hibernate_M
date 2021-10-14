package nl.hu.dp.ovchip.implement;

import nl.hu.dp.ovchip.domein.Reiziger;
import nl.hu.dp.ovchip.interfaces.ReizigerDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.postgresql.util.PSQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReizigerDAOHibernate  implements ReizigerDAO {

    private Session manager;

    public ReizigerDAOHibernate(Session manager) {
        this.manager = manager;
    }

    @Override
    public boolean save(Reiziger reiziger){
        manager.beginTransaction();
        manager.save(reiziger);
        manager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger){
            manager.beginTransaction();
            manager.merge(reiziger);
            manager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger){
            manager.beginTransaction();
            manager.delete(reiziger);
            manager.getTransaction().commit();
        return true;
    }

    @Override
    public Reiziger findById(int reiziger_id){
        Reiziger reiziger = manager.find(Reiziger.class, reiziger_id);
        if (reiziger == null) {
            throw new EntityNotFoundException("Can't find reiziger for ID "
                    + reiziger_id);
        }
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum){
        Date date = Date.valueOf(datum);
        String selBirthDateHQL = "FROM Reiziger AS r WHERE r.geboortedatum = :birthdate";
        Query query = manager.createQuery(selBirthDateHQL);
        query.setParameter("birthdate", date);
        return query.getResultList();
    }

    @Override
    public List<Reiziger> findAll(){
        return manager.createQuery("FROM Reiziger").list();
    }
}
