package si.rsvo.ocene.models.converters;
import si.rsvo.ocene.models.entities.OcenaEntity;
import si.rsvo.uporabniki.lib.Ocena;

public class OcenaConverter {

    public static Ocena toDto(OcenaEntity entity) {

        Ocena dto = new Ocena();
        dto.setId(entity.getId());
        dto.setIzdelekId(entity.getIzdelekId());
        dto.setUsername(entity.getUsername());
        dto.setKomentar(entity.getKomentar());
        dto.setOcena(entity.getOcena());

        return dto;

    }

    public static OcenaEntity toEntity(Ocena dto) {

        OcenaEntity entity = new OcenaEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setIzdelekId(dto.getIzdelekId());
        entity.setKomentar(dto.getKomentar());
        entity.setOcena(dto.getOcena());

        return entity;

    }

}
