package nl.hu.dp.ovchip.interfaces;

import nl.hu.dp.ovchip.domein.OVchipkaart;
import nl.hu.dp.ovchip.domein.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVchipkaart chipkaart) throws SQLException;

    public List<OVchipkaart> update(Reiziger reiziger) throws SQLException;

    public boolean delete(OVchipkaart chipkaart) throws SQLException;

    public OVchipkaart findById(int id) throws SQLException;

    public List<OVchipkaart> findAll() throws SQLException;

    public List<OVchipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
