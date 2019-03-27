package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class UserHelper extends HelperBase {

  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void modification(int id) {
    wd.findElement(By.xpath("//a[@href=\"manage_user_edit_page.php?user_id=" + id + "\"]")).click();
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public void confirmRegistration(String username, String email, String password) throws MessagingException, IOException {
    app.registration().start(username, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 20000);  // Получаем почту с внутреннего почтового сервера
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
  }

  public void confirmChange(String username, String email, String password) throws MessagingException, IOException {
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 20000);  // Получаем почту с внутреннего почтового сервера
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

}
