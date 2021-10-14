package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVchipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import nl.hu.dp.ovchip.implement.AdresDAOHibernate;
import nl.hu.dp.ovchip.implement.OVchipkaartDAOHibernate;
import nl.hu.dp.ovchip.implement.ProductDAOHibernate;
import nl.hu.dp.ovchip.implement.ReizigerDAOHibernate;
import nl.hu.dp.ovchip.interfaces.AdresDAO;
import nl.hu.dp.ovchip.interfaces.OVChipkaartDAO;
import nl.hu.dp.ovchip.interfaces.ProductDAO;
import nl.hu.dp.ovchip.interfaces.ReizigerDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;
//    private static final String PERSISTENCE_UNIT_NAME = "reiziger_id";

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        Session session = getSession();
        ReizigerDAO rdao = new ReizigerDAOHibernate(session);
        AdresDAO adao = new AdresDAOHibernate(session);
        OVChipkaartDAO cdao = new OVchipkaartDAOHibernate(session);
        ProductDAO pdao = new ProductDAOHibernate(session);

        testFetchAll();
        testDAOHibernate(rdao, adao, cdao, pdao);
        testProductDAOHibernate(rdao, adao, cdao, pdao);

        session.close();

    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testDAOHibernate(ReizigerDAO rdao, AdresDAO adao, OVChipkaartDAO cdao, ProductDAO pdao) throws SQLException {

        System.out.println("---------------------------Reiziger-------------------------------");

        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(17, "S", "van", "Boers", Date.valueOf(gbdatum));
        rdao.save(sietske);

        // Verander reiziger
        sietske.setAchternaam("Kattevlinder");
        rdao.update(sietske);

        //  delete reiziger
        rdao.delete(sietske);

        //Vind reiziger met id
        System.out.println("\n[Test] Vind reiziger met ID");
        System.out.println(rdao.findById(2));

        //Vind reiziger doormiddel van geboortedatum
        System.out.println("\n[Test] Vind reiziger doormiddel van geboortedatum");
        System.out.println(rdao.findByGbdatum("1981-03-14"));

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        System.out.println("---------------------------Adres-------------------------------");

        //         Maak een nieuw adres aan en persisteer deze in de database
        rdao.save(sietske);
        Adres Utrecht = new Adres(8, "5555 AK", "22", "Havenlaan", "Utrecht");
        sietske.setAdres(Utrecht);
        adao.save(Utrecht);

        // Verander adres
        Utrecht.setStraat("Leegwaterstraat");
        adao.update(Utrecht);

        //delete adres
        sietske.removeAdres();
        adao.delete(Utrecht);
        rdao.delete(sietske);

        //Vind adres bij id
        System.out.println("[Test] Vind adres met ID");
        System.out.println(adao.findById(1));

        //Vind adres doormiddel van reiziger
        System.out.println("\n[Test] Vind adres doormiddel van reiziger");
        Reiziger re = rdao.findById(1);
        System.out.println(adao.findByReiziger(re));

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("\n[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres adres : adressen) {
            System.out.println(adres);
        }

        System.out.println("---------------------------OVchipkaart-------------------------------");

        // Maak een nieuwe chipkaart aan en persisteer deze in de database
        Reiziger kees = new Reiziger(16, "S", "", "Boers", Date.valueOf("1981-03-14"));
        rdao.save(kees);
        OVchipkaart chipkaart = new OVchipkaart(84533, Date.valueOf("2020-03-14"), 1, 25);
        kees.addChipkaart(chipkaart);
        cdao.update(kees);

        // Verander chipkaart
        chipkaart.setSaldo(500);
        kees.getChipkaart().remove(0);
        kees.addChipkaart(chipkaart);
        cdao.update(kees);

        //delete chipkaart
        kees.getChipkaart().remove(0);
        cdao.update(kees);
        rdao.delete(kees);

        //Vind chipkaart bij id
        System.out.println("\n[Test] Vind chipkaart met ID");
        System.out.println(cdao.findById(35283));

        //Vind chipkaart doormiddel van reiziger
        System.out.println("\n[Test] Vind chipkaart doormiddel van reiziger");
        Reiziger rei = rdao.findById(2);
        List<OVchipkaart> chipkaarten = cdao.findByReiziger(rei);
        for (OVchipkaart chipkaart2 : chipkaarten) {
            System.out.println(chipkaart2);
        }

        //Vind alle chipkaarten
        List<OVchipkaart> chipkaarten2 = cdao.findAll();
        System.out.println("\n[Test] OVChipkaartDAO.findAll() geeft de volgende chipkaarten:");
        for (OVchipkaart chipkaart3 : chipkaarten2) {
            System.out.println(chipkaart3);
        }
    }

        private static void testProductDAOHibernate(ReizigerDAO rdao, AdresDAO adao, OVChipkaartDAO cdao, ProductDAO pdao) throws SQLException {

            System.out.println("---------------------------Product-------------------------------");

            // Maak een nieuw product aan en persisteer deze in de database
            // 2 lijsten aanmaken voor chipkaarten en producten
            List<OVchipkaart> chipkaarten = new ArrayList<>();
            List<Product> producten = new ArrayList<>();
            // Nieuwe reiziger aanmaken en toevoegen aan de database
            Reiziger kees = new Reiziger(17, "S", "", "Boers", Date.valueOf("1981-03-14"));
            rdao.save(kees);
            // Nieuwe OVchipkaart aanmaken en toevoegen aan lijst en reiziger + updaten van database
            OVchipkaart chipkaart = new OVchipkaart(84533, Date.valueOf("2020-03-14"), 1, 25);
            kees.addChipkaart(chipkaart);
            chipkaarten.add(chipkaart);
            cdao.update(kees);
            // Nieuw product aanmaken lijst chipkaarten toevoegen aan product
            Product product1 = new Product(7, "40% Korting", "Korting is 40%", 60);
            product1.addChipkaart(chipkaarten);
            // Product toevoegen aan chipkaart en opslaan in de database
            producten.add(product1);
            chipkaart.addProduct(producten);
            pdao.save(product1);

            // Verander product
            product1.setBeschrijving("Korting is 80%");
            pdao.update(product1);

            //delete product
            // Product verwijderen uit lijsten van chipkaarten
            product1.removeFromChipkaarten();
            // Product verwijderen uit database samen met connectie van chipkaart
            pdao.delete(product1);
            // chipkaart verwijderen uit lijst van reiziger en beide verwijderen uit de database
            kees.getChipkaart().remove(0);
            cdao.update(kees);
            rdao.delete(kees);

            //Vind product bij id
            System.out.println("\n[Test] Vind product met ID");
            System.out.println(pdao.findById(1));

            //Vind alle producten
            List<Product> producten2 = pdao.findAll();
            System.out.println("\n[Test] ProductDAO.findAll() geeft de volgende producten:");
            for (Product product2 : producten2) {
                System.out.println(product2);
            }

            //Vind producten doormiddel van een chipkaart
            System.out.println("\n[Test] Vind producten doormiddel van een chipkaart");
            List<Product> producten3 = pdao.findByOVChipkaart(cdao.findById(68514));
            for (Product product4 : producten3) {
                System.out.println(product4);
            }

            //Vind chipkaarten doormiddel van een product
            System.out.println("\n[Test] Vind chipkaarten doormiddel van een product");
            List<OVchipkaart> chipkaarten2 = pdao.findByProduct(pdao.findById(1));
            for (OVchipkaart chipkaart3 : chipkaarten2) {
                System.out.println(chipkaart3);
            }
    }
}

