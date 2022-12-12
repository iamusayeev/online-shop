package az.online.shop.service;

import az.online.shop.dto.CategoryDTO;
import az.online.shop.entity.Category;
import az.online.shop.mapper.CategoryMapper;
import az.online.shop.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void create(CategoryDTO dto) {
        Category category = categoryMapper.mapDtoToEntity(dto);
        categoryRepository.save(category);
    }
}