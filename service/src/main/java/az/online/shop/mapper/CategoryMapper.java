package az.online.shop.mapper;

import az.online.shop.dto.CategoryDTO;
import az.online.shop.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDTO> {

    @Override
    public CategoryDTO map(Category object) {
        return CategoryDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public Category mapDtoToEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}