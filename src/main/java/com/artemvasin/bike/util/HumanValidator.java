package com.artemvasin.bike.util;

import com.artemvasin.bike.model.Human;
import com.artemvasin.bike.services.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HumanValidator implements Validator {

    private HumanService humanService;

    @Autowired
    public HumanValidator(HumanService humanService) {
        this.humanService = humanService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Human.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Human human = (Human) target;
        if (humanService.getHumanByFullName(human.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
    }
}
