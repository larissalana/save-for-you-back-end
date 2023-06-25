package com.saveforyou.savinggoalsservice.domain.category.mapper;

import com.saveforyou.savinggoalsservice.domain.category.model.Category;
import com.saveforyou.savinggoalsservice.infrastructure.mongo.document.CategoryDocument;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toDomainModel(CategoryDocument categoryDocument);

    @InheritConfiguration
    List<Category> toDomainModel(List<CategoryDocument> categoriesDocument);
}