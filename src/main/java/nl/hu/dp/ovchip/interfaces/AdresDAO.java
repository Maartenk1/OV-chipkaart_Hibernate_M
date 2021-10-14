package nl.hu.dp.ovchip.interfaces;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    public boolean save(Adres adres) throws SQLException;

    public boolean update(Adres adres) throws SQLException;

    public boolean delete(Adres adres) throws SQLException;

    public Adres findById(int id) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger) throws SQLException;
    public List<Adres> findAll() throws SQLException;
}

