package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class RegistrationTestsWithInternalMail extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long time = System.currentTimeMillis();
    String username = String.format("test%s", time);
    String password = "password";
    String email = String.format("test%s@localhost.localdomain", time);

    app.users().confirmRegistration(username, email, password);

    assertTrue(app.newSession().login(username, password));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
