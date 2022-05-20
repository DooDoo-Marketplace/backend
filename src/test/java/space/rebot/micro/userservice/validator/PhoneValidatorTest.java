package space.rebot.micro.userservice.validator;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneValidatorTest {

    private final PhoneValidator phoneValidator = new PhoneValidator();

    @Test
    @DisplayName("valid phone number")
    public void isValid_shouldReturnTrue() {
        String phone = "+79214153153";
        assertTrue(phoneValidator.isValid(phone));
    }

    @Test
    @DisplayName("phone doesn't start with 7 or 8 ")
    public void isValid_shouldReturnFalse_whenFirstNumberInvalid() {
        String phone = "+99214153153";
        assertFalse(phoneValidator.isValid(phone));
    }

    @Test
    @DisplayName("phone length isn't correct (too small) ")
    public void isValid_shouldReturnFalse_whenLengthIsLess() {
        String phone = "+7921";
        assertFalse(phoneValidator.isValid(phone));
    }

    @Test
    @DisplayName("phone length isn't correct (too big) ")
    public void isValid_shouldReturnFalse_whenLengthIsOver() {
        String phone = "+79215478962145";
        assertFalse(phoneValidator.isValid(phone));
    }
}
