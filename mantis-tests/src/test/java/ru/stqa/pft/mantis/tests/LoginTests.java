package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.SessionHttp;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException {
    SessionHttp session = app.newSession();
    assertTrue(session.login("administrator","root2"));
    assertTrue(session.isLoggedInAs("administrator"));
  }
}
