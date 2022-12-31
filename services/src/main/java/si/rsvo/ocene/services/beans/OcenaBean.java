package si.rsvo.ocene.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.rsvo.ocene.models.converters.OcenaConverter;
import si.rsvo.ocene.models.entities.OcenaEntity;
import si.rsvo.uporabniki.lib.Ocena;



@RequestScoped
public class OcenaBean {

    private Logger log = Logger.getLogger(OcenaBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Ocena> getOcene() {

        TypedQuery<OcenaEntity> query = em.createNamedQuery(
                "OcenaEntity.getAll", OcenaEntity.class);

        List<OcenaEntity> resultList = query.getResultList();

        return resultList.stream().map(OcenaConverter::toDto).collect(Collectors.toList());

    }

    public Ocena getOcenaById(Integer id) {

        OcenaEntity ocenaEntity = em.find(OcenaEntity.class, id);

        if (ocenaEntity == null) {
            throw new NotFoundException();
        }

        Ocena ocena = OcenaConverter.toDto(ocenaEntity);

        return ocena;
    }


    @Timed
    public List<Ocena> getOceneByUsername(String username) {

        TypedQuery<OcenaEntity> query = em.createNamedQuery(
                "OcenaEntity.getByUsername", OcenaEntity.class);

        query.setParameter("uporabnikUsername", username);

        List<OcenaEntity> resultList = query.getResultList();

        /*
        System.out.println("resultList: ");
        for(UporabnikEntity up : resultList) {
            System.out.println(up.getId());
            System.out.println(up.getIme());
            System.out.println(up.getPriimek());
            System.out.println(up.getUsername());
            System.out.println(up.getEmail());
        }
        */

        return resultList.stream().map(OcenaConverter::toDto).collect(Collectors.toList());

    }

    @Timed
    public Ocena createOcena(Ocena ocena) {

        OcenaEntity ocenaEntity = OcenaConverter.toEntity(ocena);

        try {
            beginTx();
            em.persist(ocenaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return OcenaConverter.toDto(ocenaEntity);
    }

    /*
    @Timed
    public List<UporabnikoviIzdelkiMetadata> getUporabnikoviIzdelkiMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, UporabnikoviIzdelkiMetadataEntity.class, queryParameters).stream()
                .map(UporabnikoviIzdelkiMetadataConverter::toDto).collect(Collectors.toList());
    }

    public UporabnikoviIzdelkiMetadata getUporabnikoviIzdelkiMetadata(Integer id) {

        UporabnikoviIzdelkiMetadataEntity uporabnikoviIzdelkiMetadataEntity = em.find(UporabnikoviIzdelkiMetadataEntity.class, id);

        if (uporabnikoviIzdelkiMetadataEntity == null) {
            throw new NotFoundException();
        }

        UporabnikoviIzdelkiMetadata uporabnikoviIzdelkiMetadata = UporabnikoviIzdelkiMetadataConverter.toDto(uporabnikoviIzdelkiMetadataEntity);

        return uporabnikoviIzdelkiMetadata;
    }

    public UporabnikoviIzdelkiMetadata createUporabnikoviIzdelkiMetadata(UporabnikoviIzdelkiMetadata uporabnikoviIzdelkiMetadata) {

        UporabnikoviIzdelkiMetadataEntity uporabnikoviIzdelkiMetadataEntity = UporabnikoviIzdelkiMetadataConverter.toEntity(uporabnikoviIzdelkiMetadata);

        try {
            beginTx();
            em.persist(uporabnikoviIzdelkiMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (uporabnikoviIzdelkiMetadataEntity.getIzdelekId() == null || uporabnikoviIzdelkiMetadataEntity.getUporabnikId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return UporabnikoviIzdelkiMetadataConverter.toDto(uporabnikoviIzdelkiMetadataEntity);
    }

    public UporabnikoviIzdelkiMetadata putUporabnikoviIzdelkiMetadata(Integer id, UporabnikoviIzdelkiMetadata uporabnikoviIzdelkiMetadata) {
uporabniki
        UporabnikoviIzdelkiMetadataEntity c = em.find(UporabnikoviIzdelkiMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        UporabnikoviIzdelkiMetadataEntity updatedUporabnikoviIzdelkiMetadataEntity = UporabnikoviIzdelkiMetadataConverter.toEntity(uporabnikoviIzdelkiMetadata);

        try {
            beginTx();
            updatedUporabnikoviIzdelkiMetadataEntity.setIzdelekId(c.getIzdelekId());
            updatedUporabnikoviIzdelkiMetadataEntity.setUporabnikId(c.getUporabnikId());
            updatedUporabnikoviIzdelkiMetadataEntity = em.merge(updatedUporabnikoviIzdelkiMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return UporabnikoviIzdelkiMetadataConverter.toDto(updatedUporabnikoviIzdelkiMetadataEntity);
    }

    public boolean deleteUporabnikoviIzdelkiMetadata(Integer id) {

        UporabnikoviIzdelkiMetadataEntity uporabnikoviIzdelkiMetadata = em.find(UporabnikoviIzdelkiMetadataEntity.class, id);

        if (uporabnikoviIzdelkiMetadata != null) {
            try {
                beginTx();
                em.remove(uporabnikoviIzdelkiMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

     */

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
