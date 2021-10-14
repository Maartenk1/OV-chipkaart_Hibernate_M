package nl.hu.dp.ovchip.domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ov_chipkaart")
public class OVchipkaart {

    @Id
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private float saldo;
    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(name="ov_chipkaart_product", joinColumns={@JoinColumn(name ="kaart_nummer")}
            , inverseJoinColumns={@JoinColumn(name ="product_nummer")})
    private List<Product> producten = new ArrayList<>();

    public OVchipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }


    public OVchipkaart(){}

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public List<Product> getProduct() {
        return producten;
    }

    public void addProduct(List<Product> product) {
        this.producten = product;
    }

    public void removeProduct(Product product){
        producten.remove(product);
    }

    public void addReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString(){
        String chipkaart = "#" + kaart_nummer + ", ";
        chipkaart += "datum " + Date.valueOf(String.valueOf(geldig_tot))+ ", ";
        chipkaart += "klasse " + klasse + ", ";
        chipkaart += "saldo €" + saldo;

        return chipkaart;
    }
}
