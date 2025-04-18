package packageOne;

import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightFormTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;
    @BeforeAll

    static void setUpBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false));
    }

    @BeforeEach
    void setUp(){
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterEach
    void closeContext(){
        browserContext.close();
    }

    @AfterAll
    static void tearDown(){
        browser.close();
        playwright.close();
    }

    @DisplayName("Interacting with text field")
    @Nested
    class WhenInteractingWithTextFields {
        @BeforeEach
        void openContactPage(){ page.navigate("https://practicesoftwaretesting.com/contact");}

        @DisplayName("Complete the form")
        @Test
        void completeForm() throws URISyntaxException {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");
            var uploadField = page.getByLabel("Attachment");

            firstNameField.fill("aleksandra");
            lastNameField.fill("karic");
            emailField.fill("email@ema.il");
            messageField.fill("Hello, world!");
            subjectField.selectOption("Warranty");
            // subjectField.selectOption(new SelectOption().setIndex(2));  ovde test pada/ ovo je fleksibilnije

            Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/sample-data.txt").toURI());
            page.setInputFiles("#attachment", fileToUpload);

        assertThat(firstNameField).hasValue("aleksandra");
        assertThat(lastNameField).hasValue("karic");
        assertThat(emailField).hasValue("email@ema.il");
        assertThat(messageField).hasValue("Hello, world!");
        assertThat(subjectField).hasValue("warranty");

        String uploadedValue = uploadField.inputValue();
            Assertions.assertThat(uploadedValue).endsWith("sample-data.txt");
        }

        @DisplayName("Mandatory fields")
        @ParameterizedTest
        @ValueSource(strings = {"First name","Last name","Email","Message"})
        void mandatoryFields(String fieldName) {
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");
            var sendButton = page.getByText("Send");

            //Fill in the fields values
            firstNameField.fill("aleksandra");
            lastNameField.fill("karic");
            emailField.fill("email@ema.il");
            messageField.fill("Hello, world!");
            subjectField.selectOption("Warranty");

            // Clear one of the field
            page.getByLabel(fieldName).clear();

            // Check the error message for that field
            var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");
        }

        /*@Test
        void mandatoryFields1(){
            var firstNameField = page.getByLabel("First name");
            var lastNameField = page.getByLabel("Last name");
            var emailField = page.getByLabel("Email");
            var messageField = page.getByLabel("Message");
            var subjectField = page.getByLabel("Subject");
            var sendButton = page.getByText("Send");

            sendButton.click();

            var errorMessage = page.getByRole(AriaRole.ALERT).getByText("First name is required");

            assertThat(errorMessage).isVisible();

        }*/


    }
}
