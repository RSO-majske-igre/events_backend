package team.marela.backend.core.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class DtoMapper<Entity, Dto> {

    private final ModelMapper modelMapper = new ModelMapper();
    private final Class<Entity> entityClassType;
    private final Class<Dto> dtoClassType;

    public DtoMapper(Class<Entity> entityClassType, Class<Dto> dtoClassType) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        this.entityClassType = entityClassType;
        this.dtoClassType = dtoClassType;
    }

    public Dto toDto(Entity entity) {
        return modelMapper.map(entity, dtoClassType);
    }

    public Entity toEntity(Dto dto) {
        return modelMapper.map(dto, entityClassType);
    }
}
