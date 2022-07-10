package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.GenreValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class GenreValidatorImplTest {
    private final GenreValidator validator = GenreValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectName(){
        String correctName = "Romance";
        assertTrue(validator.isValidName(correctName));
    }

    @Test
    public void testValidateIncorrectName(){
        String incorrectName = "Sci55*fy";
        assertFalse(validator.isValidName(incorrectName));
    }
}
