package proiect_final.WalkMyPet.service.helper.customAnnotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EndStartTimeValidator.class)
public @interface CompareTime {

    public String message() default "";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
    String start();
    String end();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CompareTime[] value();
    }

}
