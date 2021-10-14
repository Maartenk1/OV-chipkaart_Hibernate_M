package nl.hu.dp.ovchip.implement;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVchipkaart;
import nl.hu.dp.ovchip.domein.Reiziger;
import nl.hu.dp.ovchip.interfaces.OVChipkaartDAO;
import org.hibernate.Session;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVchipkaartDAOHibernate implements OVChipkaartDAO {

    private Session manager;

    public OVchipkaartDAOHibernate(Session manager) {
        this.manager = manager;
    }

    @Override
    public boolean save(OVchipkaart chipkaart) throws SQLException {
        manager.beginTransaction();
        manager.save(chipkaart);
        manager.getTransaction().commit();
        return true;
    }
    @Override
    public List<OVchipkaart> update(Reiziger reiziger) throws SQLException {
        List<OVchipkaart> ovChipkaarten = reiziger.getChipkaart();
        int reizigerId = reiziger.getId();
        String deleteWhereId = "DELETE FROM OVchipkaart WHERE reiziger.id = :id";
        Query query = manager.createQuery(deleteWhereId);
        query.setParameter("id", reizigerId);
        for (OVchipkaart ovChipkaart : ovChipkaarten) {
            save(ovChipkaart);
        }
        return null;
    }

    @Override
    public boolean delete(OVchipkaart chipkaart) throws SQLException {
        manager.beginTransaction();
        manager.delete(chipkaart);
        manager.getTransaction().commit();
        return true;
    }

    @Override
    public OVchipkaart findById(int id) throws SQLException {
        OVchipkaart chipkaart = manager.find(OVchipkaart.class, id);
        if (chipkaart == null) {
            throw new EntityNotFoundException("Can't find OVchipkaart for ID "
                    + id);
        }
        return chipkaart;
    }

    @Override
    public List<OVchipkaart> findAll() throws SQLException {
        return manager.createQuery("FROM OVchipkaart").list();
    }

    @Override
    public List<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        int reizigerId = reiziger.getId();
        Query query = manager.createQuery("FROM OVchipkaart Where reiziger.id = :id");
        query.setParameter("id", reizigerId);
        return query.getResultList();
    }
}
