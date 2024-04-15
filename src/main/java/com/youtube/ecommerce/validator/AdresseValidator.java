package com.youtube.ecommerce.validator;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {

  public static <AdresseDto> List<String> validate(AdresseDto adresseDto) {
    List<String> errors = new ArrayList<>();

    if (adresseDto == null) {
      errors.add("Veuillez renseigner l'adresse 1'");
      errors.add("Veuillez renseigner la ville'");
      errors.add("Veuillez renseigner le pays'");
      errors.add("Veuillez renseigner le code postal'");
      return errors;
    }
    if (!StringUtils.hasLength(String.valueOf(adresseDto.getClass()))) {
      errors.add("Veuillez renseigner l'adresse 1'");
    }
    if (!StringUtils.hasLength(String.valueOf(adresseDto.getClass()))) {
      errors.add("Veuillez renseigner la ville'");
    }
    if (!StringUtils.hasLength(String.valueOf(adresseDto.getClass()))) {
      errors.add("Veuillez renseigner le pays'");
    }
    if (!StringUtils.hasLength(String.valueOf(adresseDto.getClass()))) {
      errors.add("Veuillez renseigner le code postal'");
    }
    return errors;
  }

}
