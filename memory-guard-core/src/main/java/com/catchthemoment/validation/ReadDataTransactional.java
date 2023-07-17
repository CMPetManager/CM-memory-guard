package com.catchthemoment.validation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = true , noRollbackFor = Exception.class)
@Documented
public @interface ReadDataTransactional {

    @AliasFor(annotation = Transactional.class,attribute = "value")
    String value() default "";

    @AliasFor(annotation = Transactional.class, attribute = "transactionManager")
    String transactionManager() default "";

    @AliasFor(annotation = Transactional.class, attribute = "label")
    String[] label() default {};

    @AliasFor(annotation = Transactional.class, attribute = "propagation")
    Propagation propagation() default Propagation.REQUIRED;

    @AliasFor(annotation = Transactional.class, attribute = "isolation")
    Isolation isolation() default Isolation.DEFAULT;

    @AliasFor(annotation = Transactional.class, attribute = "timeout")
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

    @AliasFor(annotation = Transactional.class, attribute = "timeoutString")
    String timeoutString() default "";
}
