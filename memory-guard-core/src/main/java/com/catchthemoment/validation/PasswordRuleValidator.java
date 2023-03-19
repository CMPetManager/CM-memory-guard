package com.catchthemoment.validation;

import org.passay.*;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class PasswordRuleValidator implements ConstraintValidator<Password, String> {
    private static final int MIN_COMPLEX_RULES = 2;
    private static final int MAX_REPETITIVE_CHARS = 3;
    private static final int MIN_LOWERCASE_CHARS = 1;
    private static final int MIN_DIGIT_CASE_CHARS = 1;
    private static final int MIN_SPECIAL = 1;


    @Override
    public void initialize(final Password arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        List<Rule> passwordRules = new ArrayList<>();
        passwordRules.add(new LengthRule(8, 30));
        CharacterCharacteristicsRule characteristicsRule = new CharacterCharacteristicsRule(MIN_COMPLEX_RULES,
                new CharacterRule(
                        EnglishCharacterData.Special, MIN_SPECIAL
                ), new CharacterRule(EnglishCharacterData.LowerCase, MIN_LOWERCASE_CHARS),
                new CharacterRule(EnglishCharacterData.Digit, MIN_DIGIT_CASE_CHARS));
        passwordRules.add(characteristicsRule);
        passwordRules.add(new RepeatCharacterRegexRule(MAX_REPETITIVE_CHARS));
        PasswordValidator passwordValidator = new PasswordValidator(passwordRules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult ruleResult = passwordValidator.validate(passwordData);
        return ruleResult.isValid();
    }
}
