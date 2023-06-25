package com.saveforyou.savinggoalsservice.controller.category.mapper;

import com.saveforyou.savinggoalsservice.domain.category.model.Category;
import io.swagger.model.CategoryApiModel;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryApiMapper {

    CategoryApiModel toApiModel(Category category);

    @InheritConfiguration
    List<CategoryApiModel> toApiModel(List<Category> categories);
}