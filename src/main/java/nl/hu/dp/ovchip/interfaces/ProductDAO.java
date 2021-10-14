package nl.hu.dp.ovchip.interfaces;

import nl.hu.dp.ovchip.domein.OVchipkaart;
import nl.hu.dp.ovchip.domein.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;

    public boolean update(Product product) throws SQLException;

    public boolean delete(Product product) throws SQLException;

    public Product findById(int id) throws SQLException;

    public List<Product> findAll() throws SQLException;

    public List<Product> findByOVChipkaart(OVchipkaart chipkaart) throws SQLException;

    public List<OVchipkaart> findByProduct(Product product) throws SQLException;
}
