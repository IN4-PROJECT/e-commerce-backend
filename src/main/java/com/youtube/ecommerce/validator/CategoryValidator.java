package com.youtube.ecommerce.validator;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

  public static <CategoryDto> List<String> validate(CategoryDto categoryDto) {
    List<String> errors = new ArrayList<>();

    if (categoryDto == null || !StringUtils.hasLength(categoryDto.toString())) {
      errors.add("Veuillez renseigner le code de la categorie");
    }
    return errors;
  }

}
