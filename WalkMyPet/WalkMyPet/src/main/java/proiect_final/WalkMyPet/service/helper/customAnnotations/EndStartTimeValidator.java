package proiect_final.WalkMyPet.service.helper.customAnnotations;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class EndStartTimeValidator implements ConstraintValidator<CompareTime, Object> {
    private String starTime;
    private String endTime;
    private String message;

    @Override
    public void initialize(final CompareTime constraintAnnotation) {
        starTime = constraintAnnotation.start();
        endTime = constraintAnnotation.end();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        boolean valid = true;
        try {
            final String sTime = BeanUtils.getProperty(value, starTime);
            final String eTime = BeanUtils.getProperty(value, endTime);
            LocalTime start = LocalTime.parse(sTime, formatter);
            LocalTime end = LocalTime.parse(eTime, formatter);

            valid = start.isBefore(end) && !start.equals(end);
        } catch (final Exception ignore) {
            // ignore
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(endTime)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}