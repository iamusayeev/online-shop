package az.online.shop.mapper;

import java.util.List;

public interface Mapper<E, D> {

    D map(E object);

    default D map(E entity, D dto) {
        return null;
    }

    default E mapDtoToEntity(D dto) {
        return null;
    }

    default List<D> mapEntitiesToDtos(List<E> entities) {
        return null;
    }
}