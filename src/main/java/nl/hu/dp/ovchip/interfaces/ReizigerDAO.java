package nl.hu.dp.ovchip.interfaces;

import nl.hu.dp.ovchip.domein.Reiziger;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger) throws SQLException;

    public boolean update(Reiziger reiziger) throws SQLException;

    public boolean delete(Reiziger reiziger) throws SQLException, PSQLException;

    public Reiziger findById(int id) throws SQLException;

    public List<Reiziger> findByGbdatum(String datum) throws SQLException;

    public List<Reiziger> findAll() throws SQLException;
}
