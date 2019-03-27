package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase{

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void management() {
    click(By.linkText("управление"));
  }

  public void manageUsers() {
    management();
    click(By.xpath("//a[contains(text(),'Управление пользователями')]"));
  }





}