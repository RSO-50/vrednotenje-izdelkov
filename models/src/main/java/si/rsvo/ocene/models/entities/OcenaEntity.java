package si.rsvo.ocene.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "ocene")
@NamedQueries(value =
        {
                @NamedQuery(name = "OcenaEntity.getAll",
                        query = "SELECT oc FROM OcenaEntity oc"),
                @NamedQuery(name = "OcenaEntity.getByUsername",
                        query = "SELECT oc FROM OcenaEntity oc WHERE oc.username = :uporabnikUsername")
        })

public class OcenaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ocena")
    private Integer ocena;

    @Column(name = "komentar")
    private String komentar;

    @Column(name = "username")
    private String username;

    @Column(name = "izdelekId")
    private Integer izdelekId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIzdelekId() {
        return izdelekId;
    }

    public void setIzdelekId(Integer izdelekId) {
        this.izdelekId = izdelekId;
    }
}