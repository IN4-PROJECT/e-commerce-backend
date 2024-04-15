package com.youtube.ecommerce.validator;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

  public static <ClientDto> List<String> validate(ClientDto dto) {
    List<String> errors = new ArrayList<>();

    if (dto == null) {
      errors.add("Veuillez renseigner le nom du client");
      errors.add("Veuillez renseigner le prenom du client");
      errors.add("Veuillez renseigner le Mail du client");
      errors.add("Veuillez renseigner le numero de telephone du client");
      errors.addAll(AdresseValidator.validate(null));
      return errors;
    }

    if (!StringUtils.hasLength(dto.toString())) {
      errors.add("Veuillez renseigner le nom du client");
    }
    if (!StringUtils.hasLength(dto.toString())) {
      errors.add("Veuillez renseigner le prenom du client");
    }
    if (!StringUtils.hasLength(dto.toString())) {
      errors.add("Veuillez renseigner le Mail du client");
    }
    if (!StringUtils.hasLength(dto.toString())) {
      errors.add("Veuillez renseigner le numero de telephone du client");
    }
    errors.addAll(AdresseValidator.validate(dto.toString()));
    return errors;
  }

}
