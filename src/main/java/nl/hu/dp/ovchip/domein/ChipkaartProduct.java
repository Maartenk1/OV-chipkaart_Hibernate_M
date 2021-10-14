package nl.hu.dp.ovchip.domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "ov_chipkaart_product")
public class ChipkaartProduct {
    @Id
    private int kaart_nummer;
    private int product_nummer;
    private String status;
    private Date last_update;

    public ChipkaartProduct(){}

    public ChipkaartProduct(int kaart_nummer, int product_nummer, String status, Date last_update) {
        this.kaart_nummer = kaart_nummer;
        this.product_nummer = product_nummer;
        this.status = status;
        this.last_update = last_update;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public String toString() {
        String chipkaartproduct = "#" + kaart_nummer + " #";
        chipkaartproduct += product_nummer + " ";
        chipkaartproduct += status + " ";
        chipkaartproduct += last_update + " ";
        return chipkaartproduct;
    }
}
