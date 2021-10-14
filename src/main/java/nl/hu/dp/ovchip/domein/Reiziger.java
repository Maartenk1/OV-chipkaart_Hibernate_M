package nl.hu.dp.ovchip.domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
public class Reiziger {

    @Id
    @Column(name = "reiziger_id")
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    @OneToOne(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Adres adres;

    @OneToMany(mappedBy = "reiziger", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OVchipkaart> chipkaarten = new ArrayList<>();

    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this(voorletters, tussenvoegsel, achternaam, geboortedatum);
        this.id = id;
    }

    public Reiziger(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public List<OVchipkaart> getChipkaart() {
        return chipkaarten;
    }

    public void addChipkaart(OVchipkaart chipkaart){
        chipkaarten.add(chipkaart);
        chipkaart.addReiziger(this);
    }

    public void removeChipkaart(OVchipkaart chipkaart){
        chipkaarten.remove(chipkaart);
    }

    public void removeAdres(){
        this.adres = null;
    }

    public String toString(){
        String reiziger = "Reiziger: #" + id + " ";
        reiziger += voorletters + " ";
        reiziger += tussenvoegsel + " ";
        reiziger += achternaam + " ";
        reiziger  += Date.valueOf(String.valueOf(geboortedatum));
        if(adres != null){
        reiziger += "\n     Adres: " + adres.toString();}
        for(OVchipkaart chipkaart: chipkaarten){
            reiziger +="\n          OVchipkaart: " + chipkaart;
        }
        reiziger += "\n";
        return reiziger;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
        this.adres.setReiziger(this);
    }

    public Adres getAdres(Adres adres) {
        return adres;
    }
}
