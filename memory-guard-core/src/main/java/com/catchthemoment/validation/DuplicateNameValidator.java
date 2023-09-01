package com.catchthemoment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class DuplicateNameValidator implements ConstraintValidator<DuplicateNameCharsValid, String> {

    public static final int NUMBER_COUNTS_CHARS_ALLOW = 4;

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        var countChars = userName.chars()
                .boxed()
                .collect(groupingBy(c -> c, counting()));
        int sizeChars = countChars.keySet().stream().toList().size();
        return NUMBER_COUNTS_CHARS_ALLOW <= sizeChars;
    }

}
