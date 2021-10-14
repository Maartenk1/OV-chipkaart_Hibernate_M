package nl.hu.dp.ovchip.domein;

import javax.persistence.*;

@Entity
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int adresid;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    @OneToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;


    public Adres() {
    }

    public Adres(int adresid, String postcode, String huisnummer, String straat, String woonplaats) {
        this.adresid = adresid;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }


    public int getAdresid() {
        return adresid;
    }

    public void setAdresid(int adresid) {
        this.adresid = adresid;
    }

    public Reiziger getReiziger() {return reiziger;}

    public void setReiziger(Reiziger reiziger) {this.reiziger = reiziger;}

    public String toString(){
        String adres = "#" + adresid + " ";
        adres += postcode + " ";
        adres += huisnummer + " ";
        adres += straat + " ";
        adres += woonplaats + " ";
        return adres;
    }
}
