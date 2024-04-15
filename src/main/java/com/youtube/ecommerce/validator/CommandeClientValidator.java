package com.youtube.ecommerce.validator;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {


  public static <CommandeClientDto> List<String> validate(CommandeClientDto dto) {
    List<String> errors = new ArrayList<>();
    if (dto == null) {
      errors.add("Veuillez renseigner le code de la commande");
      errors.add("Veuillez renseigner la date de la commande");
      errors.add("Veuillez renseigner l'etat de la commande");
      errors.add("Veuillez renseigner le client");
      return errors;
    }

    if (!StringUtils.hasLength(dto.toString())) {
      errors.add("Veuillez renseigner le code de la commande");
    }
    if (dto.toString() == null) {
      errors.add("Veuillez renseigner la date de la commande");
    }
    if (!StringUtils.hasLength(dto.toString().toString())) {
      errors.add("Veuillez renseigner l'etat de la commande");
    }
    if (dto.toString() == null || dto.toString() == null) {
      errors.add("Veuillez renseigner le client");
    }

    return errors;
  }

}
