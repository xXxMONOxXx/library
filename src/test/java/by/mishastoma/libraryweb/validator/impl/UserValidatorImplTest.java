package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.UserValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorImplTest {
    private final UserValidator validator = UserValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectLogin() {
        String correctLogin = "login";
        assertTrue(validator.isValidLogin(correctLogin));
    }

    @Test
    public void testValidateIncorrectLogin() {
        String incorrectLogin = "j_j";
        assertFalse(validator.isValidLogin(incorrectLogin));
    }

    @Test
    public void testValidateCorrectFirstName() {
        String correctFirstName = "misha";
        assertTrue(validator.isValidFirstName(correctFirstName));
    }

    @Test
    public void testValidateIncorrectFirstName() {
        String incorrectFirstName = "mak6*s";
        assertFalse(validator.isValidFirstName(incorrectFirstName));
    }

    @Test
    public void testValidateCorrectLastName() {
        String correctLastName = "Stoma";
        assertTrue(validator.isValidLastName(correctLastName));
    }

    @Test
    public void testValidateIncorrectLastName() {
        String incorrectLastName = "&lrR";
        assertFalse(validator.isValidLastName(incorrectLastName));
    }

    @Test
    public void testValidateCorrectPassword() {
        String correctPassword = "qwerty123A@";
        assertTrue(validator.isValidPassword(correctPassword));
    }

    @Test
    public void testValidateIncorrectPassword() {
        String incorrectPassword = "12345";
        assertFalse(validator.isValidPassword(incorrectPassword));
    }

    @Test
    public void testValidateCorrectEmail() {
        String correctEmail = "misha@mail.com";
        assertTrue(validator.isValidEmail(correctEmail));
    }

    @Test
    public void testValidateIncorrectEmail() {
        String incorrectEmail = "mi@@@*mail.ru";
        assertFalse(validator.isValidEmail(incorrectEmail));
    }

    @Test
    public void testValidateCorrectBirthdate() {
        String correctBirthdate = "2002-06-28";
        assertTrue(validator.isValidBirthDate(correctBirthdate));
    }

    @Test
    public void testValidateIncorrectBirthdateInvalidSymbol() {
        String incorrectBirthdate = "2000?-?07-18";
        assertFalse(validator.isValidBirthDate(incorrectBirthdate));
    }

    @Test
    public void testValidateIncorrectBirthDateDidntBorn() {
        String incorrectBirthdate = "3000-01-01";
        assertFalse(validator.isValidBirthDate(incorrectBirthdate));
    }

    @Test
    public void testValidateCorrectBalance(){
        String correctBalance = "100";
        assertTrue(validator.isValidBalance(correctBalance));
    }

    @Test
    public void testValidateIncorrectBalance(){
        String incorrectBalance = "*10";
        assertFalse(validator.isValidBalance(incorrectBalance));
    }
}
