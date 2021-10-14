package nl.hu.dp.ovchip.implement;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import nl.hu.dp.ovchip.interfaces.AdresDAO;
import org.hibernate.Session;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {

    private Session manager;

    public AdresDAOHibernate(Session manager) {
        this.manager = manager;
    }

    @Override
    public boolean save(Adres adres){
        manager.beginTransaction();
        manager.save(adres);
        manager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        manager.beginTransaction();
        manager.merge(adres);
        manager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        manager.beginTransaction();
        manager.delete(adres);
        manager.getTransaction().commit();
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Adres adres = manager.find(Adres.class, id);
        if (adres == null) {
            throw new EntityNotFoundException("Can't find adres for ID "
                    + id);
        }
        return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Adres adres = manager.find(Adres.class, reiziger.getId());
        if (adres == null) {
            throw new EntityNotFoundException("Can't find adres for ID "
                    + reiziger.getId());
        }
        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        return manager.createQuery("FROM Adres").list();
    }
}
