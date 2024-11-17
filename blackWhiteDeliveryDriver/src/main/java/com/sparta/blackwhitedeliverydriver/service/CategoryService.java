package com.sparta.blackwhitedeliverydriver.service;

import com.sparta.blackwhitedeliverydriver.dto.CategoryIdResponseDto;
import com.sparta.blackwhitedeliverydriver.dto.CategoryRequestDto;
import com.sparta.blackwhitedeliverydriver.dto.CategoryResponseDto;
import com.sparta.blackwhitedeliverydriver.entity.Category;
import com.sparta.blackwhitedeliverydriver.entity.User;
import com.sparta.blackwhitedeliverydriver.exception.CategoryExceptionMessage;
import com.sparta.blackwhitedeliverydriver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getOrCreateCategory(String categoryNames, User user) {
        Set<String> categorySet = new HashSet<>();
        Arrays.stream(categoryNames.split(","))
                .map(String::trim)
                .forEach(categorySet::add);

        List<Category> categoryList = new ArrayList<>();
        for(String categoryName : categorySet) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        // 없으면 새로 생성하고 저장
                        Category newCategory = Category.from(categoryName);
                        categoryList.add(newCategory);
                        return categoryRepository.save(newCategory);
                    });
            categoryList.add(category);
        }

        return categoryList;
    }

    public Page<CategoryResponseDto> getAllCategories(int page, int size, String sortBy, boolean isAsc) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(CategoryResponseDto::from);
    }
}
