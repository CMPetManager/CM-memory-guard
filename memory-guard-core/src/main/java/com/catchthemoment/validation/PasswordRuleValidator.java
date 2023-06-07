package com.catchthemoment.validation;

import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.Rule;
import org.passay.RuleResult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PasswordRuleValidator implements ConstraintValidator<Password, String> {
	private static final int MIN_COMPLEX_RULES = 2;
	private static final int MAX_REPETITIVE_CHARS = 3;
	private static final int MIN_LOWERCASE_CHARS = 1;
	private static final int MIN_DIGIT_CASE_CHARS = 1;
	private static final int MIN_SPECIAL = 1;

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		PasswordValidator passwordValidator = getPasswordValidator();
		RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
		return ruleResult.isValid();
	}

	private PasswordValidator getPasswordValidator() {
		List<Rule> passwordRules = prepareValidationRules();
		return new PasswordValidator(passwordRules);
	}

	private List<Rule> prepareValidationRules() {
		List<Rule> passwordRules = new ArrayList<>();
		passwordRules.add(new LengthRule(8, 30));
		CharacterCharacteristicsRule characteristicsRule = new CharacterCharacteristicsRule(MIN_COMPLEX_RULES,
				new CharacterRule(EnglishCharacterData.Special, MIN_SPECIAL),
				new CharacterRule(EnglishCharacterData.LowerCase, MIN_LOWERCASE_CHARS),
				new CharacterRule(EnglishCharacterData.Digit, MIN_DIGIT_CASE_CHARS));
		passwordRules.add(characteristicsRule);
		passwordRules.add(new RepeatCharacterRegexRule(MAX_REPETITIVE_CHARS));
		return passwordRules;
	}
}
