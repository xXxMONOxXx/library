package by.mishastoma.libraryweb.validator.impl;

import by.mishastoma.libraryweb.validator.BookValidator;
import jakarta.servlet.http.Part;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BookValidatorImplTest {
    private final BookValidator validator = BookValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectName() {
        String correctFirstName = "Peace and War";
        assertTrue(validator.isValidName(correctFirstName));
    }

    @Test
    public void testValidateIncorrectName() {
        String correctFirstName = "*dead*";
        assertFalse(validator.isValidName(correctFirstName));
    }

    @Test
    public void testValidateCorrectReleaseDate() {
        String correctReleaseDate = "2000-10-10";
        assertTrue(validator.isValidReleaseDate(correctReleaseDate));
    }

    @Test
    public void testValidateIncorrectReleaseDate() {
        String incorrectReleaseDate = "2000-30-30";
        assertFalse(validator.isValidReleaseDate(incorrectReleaseDate));
    }

    @Test
    public void testValidateCorrectAbout() {
        String correctAbout = "It is a long established fact that a reader will be distracted" +
                " by the readable content of a page when looking at its layout." +
                " The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters," +
                " as opposed to using 'Content here, content here', making it look like readable English." +
                " Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text," +
                " and a search for 'lorem ipsum' will uncover many web sites still in their infancy. " +
                "Various versions have evolved over the years," +
                " sometimes by accident, sometimes on purpose (injected humour and the like).";
        assertTrue(validator.isValidAbout(correctAbout));
    }

    @Test
    public void testValidateCorrectAboutEmpty() {
        String correctAbout = "";
        assertTrue(validator.isValidAbout(correctAbout));
    }

    @Test
    public void testValidateIncorrectAbout() {
        String incorrectAbout = "It is a long established fact that a reader will be distracted" +
                " by the readable content of a page when looking at its layout." +
                " The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters," +
                " as opposed to using 'Content here, content here', making it look like readable English." +
                " Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text," +
                " and a search for 'lorem ipsum' will uncover many web sites still in their infancy. " +
                "Various versions have evolved over the years," +
                " sometimes by accident, sometimes on purpose (injected humour and the like)." +
                "There are many variations of passages of Lorem Ipsum available," +
                " but the majority have suffered alteration in some form, by injected humour, " +
                "or randomised words which don't look even slightly believable. " +
                "If you are going to use a passage of Lorem Ipsum, " +
                "you need to be sure there isn't anything embarrassing hidden in the middle of text. " +
                "All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary," +
                " making this the first true generator on the Internet." +
                " It uses a dictionary of over 200 Latin words, " +
                "combined with a handful of model sentence structures, " +
                "to generate Lorem Ipsum which looks reasonable." +
                "The generated Lorem Ipsum is therefore always free from repetition, " +
                "injected humour, or non-characteristic words etc.";
        assertFalse(validator.isValidAbout(incorrectAbout));
    }

    @Test
    public void testValidateCorrectQuantity() {
        String correctQuantity = "10";
        assertTrue(validator.isValidQuantity(correctQuantity));
    }

    @Test
    public void testValidateIncorrectQuantityInvalidSymbol() {
        String incorrectQuantity = "1*0";
        assertFalse(validator.isValidQuantity(incorrectQuantity));
    }

    @Test
    public void testValidateIncorrectQuantityTooBig() {
        String incorrectQuantity = "100000";
        assertFalse(validator.isValidQuantity(incorrectQuantity));
    }

    @Test
    public void testValidateCorrectAgeLimitations() {
        String correctAgeLimitations = "6";
        assertTrue(validator.isValidAgeLimitations(correctAgeLimitations));
    }

    @Test
    public void testValidateIncorrectAgeLimitationsInvalidSymbol() {
        String incorrectAgeLimitations = "1^2";
        assertFalse(validator.isValidAgeLimitations(incorrectAgeLimitations));
    }

    @Test
    public void testValidateIncorrectAgeLimitationsTooBig() {
        String incorrectAgeLimitations = "1000";
        assertFalse(validator.isValidAgeLimitations(incorrectAgeLimitations));
    }

    @Test
    public void testValidateCorrectIdArray() {
        String[] correctArray = {"1", "2", "3", "33"};
        assertTrue(validator.isValidIdsArray(correctArray));
    }

    @Test
    public void testValidateIncorrectArray() {
        String[] incorrectArray = {"i", "2", "3", "h3"};
        assertFalse(validator.isValidIdsArray(incorrectArray));
    }

    @Test
    public void testValidateCorrectPicture() {
        Part correctFile = Mockito.mock(Part.class);
        Mockito.when(correctFile.getSize()).thenReturn(600L);
        Mockito.when(correctFile.getContentType()).thenReturn("image/png");
        assertTrue(validator.isValidPicture(correctFile));
    }

    @Test
    public void testValidateIncorrectPictureInvalidType() {
        Part incorrectFile = Mockito.mock(Part.class);
        Mockito.when(incorrectFile.getSize()).thenReturn(600L);
        Mockito.when(incorrectFile.getContentType()).thenReturn("image/jpg");
        assertFalse(validator.isValidPicture(incorrectFile));
    }

    @Test
    public void testValidateIncorrectPictureTooBig() {
        Part incorrectFile = Mockito.mock(Part.class);
        Mockito.when(incorrectFile.getSize()).thenReturn(17777216L);
        Mockito.when(incorrectFile.getContentType()).thenReturn("image/png");
        assertFalse(validator.isValidPicture(incorrectFile));
    }
}
