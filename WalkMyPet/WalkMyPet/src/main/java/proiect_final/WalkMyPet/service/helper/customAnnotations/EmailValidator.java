package proiect_final.WalkMyPet.service.helper.customAnnotations;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import proiect_final.WalkMyPet.repository.ProfileARepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class EmailValidator implements ConstraintValidator<EmailExists, Object> {
    private String email;
    private String message;

    @Autowired
    ProfileARepository profileARepository;

    @Override
    public void initialize(final EmailExists constraintAnnotation) {
        email = constraintAnnotation.email();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final String newEmail = BeanUtils.getProperty(value, email);
            valid = profileARepository.findByEmail(newEmail) == null;
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(email)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}