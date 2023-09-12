package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.Category;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.CategoryRepository;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.exception.CategoryNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category categoryToSave = CategoryMapper.toCategory(newCategoryDto);
        Category savedCategory = categoryRepository.save(categoryToSave);
        log.info("Добавлена категория: " + savedCategory.toString());

        return CategoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException("Категория не найдена"));

        categoryRepository.deleteById(catId);
        log.info("Удалена категория с id: " + catId);
    }

    @Override
    @Transactional(rollbackFor = DataIntegrityViolationException.class)
    public CategoryDto updateCategory(long catId, CategoryDto categoryDto) {
        categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException("Категория не найдена"));

        Category categoryToUpdate = CategoryMapper.toCategory(categoryDto);
        categoryToUpdate.setId(catId);
        Category savedCategory = categoryRepository.save(categoryToUpdate);
        log.info("Обновлена категория: " + savedCategory.toString());

        return CategoryMapper.toCategoryDto(savedCategory);

    }
}

