package nl.hu.dp.ovchip.implement;

import nl.hu.dp.ovchip.domein.ChipkaartProduct;
import nl.hu.dp.ovchip.domein.OVchipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.interfaces.ProductDAO;
import org.hibernate.Session;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {

    private Session manager;

    public ProductDAOHibernate(Session manager) {
        this.manager = manager;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        int id = product.getProductnummer();
        if (manager.find(Product.class, id) == null) {
            manager.beginTransaction();
            manager.save(product);
            manager.getTransaction().commit();

            for (OVchipkaart chipkaart : product.getChipkaart()) {
                try{
                String Update = "UPDATE ChipkaartProduct SET kaart_nummer = :kaart_nummer, product_nummer =  :product_nummer, status = :status, last_update = :last_update  WHERE product_nummer = :nummer";
                Query query = manager.createQuery(Update);
                query.setParameter("kaart_nummer", chipkaart.getKaart_nummer());
                query.setParameter("product_nummer", product.getProductnummer());
                query.setParameter("status", "actief");
                query.setParameter("last_update" ,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                query.setParameter("nummer", product.getProductnummer());
                } catch (Exception e) {
                    System.out.println("something  went wrong");
                }

            }
        } else {
            update(product);
        }
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        for (OVchipkaart chipkaart : product.getChipkaart()) {
            String Update = "UPDATE ChipkaartProduct SET kaart_nummer = :kaart_nummer, product_nummer =  :product_nummer, status = :status, last_update = :last_update  WHERE product_nummer = :nummer";
            Query query = manager.createQuery(Update);
            query.setParameter("status", "actief");
            query.setParameter("last_update" ,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            query.setParameter("nummer", product.getProductnummer());
        }

        manager.beginTransaction();
        manager.merge(product);
        manager.getTransaction().commit();
        return false;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        String deletefromchipkaartproduct = "DELETE FROM ChipkaartProduct WHERE product_nummer = :nummer";
        Query query = manager.createQuery(deletefromchipkaartproduct);
        query.setParameter("nummer", product.getProductnummer());

        manager.beginTransaction();
        manager.delete(product);
        manager.getTransaction().commit();
        return false;
    }

    @Override
    public Product findById(int id) throws SQLException {
        Product product = manager.find(Product.class, id);
        if (product == null) {
            throw new EntityNotFoundException("Can't find product for ID "
                    + id);
        }
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return manager.createQuery("FROM Product").list();
    }

    @Override
    public List<Product> findByOVChipkaart(OVchipkaart chipkaart) throws SQLException {
        int chipkaartnummer = chipkaart.getKaart_nummer();
        Query query = manager.createQuery("FROM Product where productnummer in (SELECT p.productnummer FROM OVchipkaart AS ovc, ChipkaartProduct AS ovcp, Product AS p WHERE ovcp.kaart_nummer = ovc.kaart_nummer AND ovcp.product_nummer = p.productnummer AND ovc.kaart_nummer = :id)");
        query.setParameter("id", chipkaartnummer);
        return query.getResultList();
    }

    @Override
    public List<OVchipkaart> findByProduct(Product product) {
        String hql = "FROM OVchipkaart WHERE kaart_nummer IN (SELECT ov.kaart_nummer FROM OVchipkaart AS ov, Product AS prod, ChipkaartProduct AS ovP WHERE ovP.kaart_nummer = ov.kaart_nummer AND ovP.product_nummer = prod.productnummer AND ovP.product_nummer = :product_nummer)";
        Query query = manager.createQuery(hql);
        query.setParameter("product_nummer", product.getProductnummer());
        return query.getResultList();
    }
}