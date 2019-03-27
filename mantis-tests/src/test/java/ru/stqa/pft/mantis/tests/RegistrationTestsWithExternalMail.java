package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTestsWithExternalMail extends TestBase {

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long time = System.currentTimeMillis();
    String username = String.format("test%s", time);
    String password = "password";
    String email = String.format("test%s@localhost", time);
    app.james().createUser(username, password); // Создаем пользователя на внешнем почтовом сервере
    app.registration().start(username, email);
    List<MailMessage> mailMessages = app.james().waitForMail(username, password, 60000); // Получаем почту с внешнего почтового сервера
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(username, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

}
