package com.saveforyou.savinggoalsservice.domain.category;

import com.saveforyou.savinggoalsservice.domain.category.mapper.CategoryMapper;
import com.saveforyou.savinggoalsservice.domain.category.model.Category;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryMapper.toDomainModel(categoryRepository.findAll());
    }
}