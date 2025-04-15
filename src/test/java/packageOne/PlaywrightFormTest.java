package packageOne;

import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.*;

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

        @DisplayName("Input Fields")
        @Test
        void fieldValues(){
            var firstNameField = page.getByLabel("First name");
        firstNameField.fill("aleksandra");

        assertThat(firstNameField).hasValue("aleksandra");
        }

    }
}
