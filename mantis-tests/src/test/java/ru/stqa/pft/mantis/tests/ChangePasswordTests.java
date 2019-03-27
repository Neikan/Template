package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {

    String newPassword = "newpassword";
    Users listusers = app.db().users(); // Получаем список пользователей
    UserData userChange = listusers.iterator().next(); // Берем случайного пользователя

    if (userChange.getId() == 1) { // Проверяем, что случайно не взяли администратора в качестве подопытного
      listusers = listusers.without(userChange);
      userChange = listusers.iterator().next();
    }

    app.session().loginAdmin(); // Выполняем вход под администратором
    app.goTo().manageUsers(); // Переходим на страницу управления пользователями
    app.users().modification(userChange.getId()); // Открываем страницу изменения сведений о пользователе
    app.users().resetPassword(); // Нажимаем кнопку сброса пароля
    app.users().confirmChange(userChange.getUsername(), userChange.getEmail(), newPassword); // Открываем ссылку из письма и вводим новый пароль

    assertTrue(app.newSession().login(userChange.getUsername(), newPassword)); // Проверяем вход с новым паролем
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
