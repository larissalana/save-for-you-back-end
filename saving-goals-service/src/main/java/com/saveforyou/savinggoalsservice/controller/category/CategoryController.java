package com.saveforyou.savinggoalsservice.controller.category;

import com.saveforyou.savinggoalsservice.controller.category.mapper.CategoryApiMapper;
import com.saveforyou.savinggoalsservice.domain.category.CategoryService;
import io.swagger.api.CategoryApi;
import io.swagger.model.CategoryApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryApiMapper categoryApiMapper;
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryApiModel>> getCategories(){
        return ResponseEntity.ok(categoryApiMapper.toApiModel(categoryService.getAll()));
    }
}