package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    wd.findElement(By.linkText("home page")).click();
  }

  public void submitContactCreation() {
    wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getPhoneMobile());
    type(By.name("home"), contactData.getPhoneHome());
    type(By.name("work"), contactData.getPhoneWork());
    type(By.name("email"), contactData.getEmailOne());
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());
    type(By.name("byear"), contactData.getByear());
    type(By.name("address2"), contactData.getAddress2());

    if (creation) {//добавлена проверка наличия/отсутствия элементов на форме
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void gotoNewContactPage() {
    click(By.linkText("Добавить контакт"));
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='"+ id + "']")).click();
  }

  private void selectToAddToGroup(Integer idGroup) { // Выбираем группу по id, т.к. он уникален
    new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(idGroup));
  }

  private void selectToDeletionToGroup(Integer idGroup) { // Выбираем группу по id, т.к. он уникален
    new Select(wd.findElement(By.name("group"))).selectByValue(String.valueOf(idGroup));
  }

  private void copyToGroup() {
    wd.findElement(By.name("add")).click();
  }

  private void removeFromGroup() {
    wd.findElement(By.name("remove")).click();
  }

  public void messageCompleteAddedContactToGroup() {
    wd.findElement(By.xpath("//*[contains(text(), 'Users added')]"));
  }

  public void messageCompleteRemovedContactFromGroup() {
    wd.findElement(By.xpath("//*[contains(text(), 'Users removed')]"));
  }


  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Удалить']"));
  }

  public void acceptDeletionContacts() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification(int id) {
    //wd.findElement(By.cssSelector("input[value='"+ id + "']")).findElement(By.xpath("//a[@href='edit.php?id="+ id +"']")).click();  //Способ 1. Рожден через боль и страдание
    wd.findElement(By.xpath("//a[@href='edit.php?id="+ id +"']")).click();  //Способ 2. Более простой способ перехода на страницу редактирования контакта - удалено лишнее
    //wd.findElement(By.cssSelector((String.format("a[@href='edit.php?id=%s']", id)))); //Способ 3. Другая запись "Способа 2"
    //wd.findElement((By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id)))); //Способ 4
    //wd.findElement((By.cssSelector(String.format("a[href='edit.php?id=%s']", id))));  //Способ 5

    //Способ 6
    //WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    //WebElement row = checkbox.findElement(By.xpath("./../.."));
    //List<WebElement> cells = row.findElements(By.tagName("td"));
    //cells.get(7).findElement(By.tagName("a")).click();
  }

  public void submitContactModification() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void messageCompleteDeletionContacts() {
    wd.findElement(By.xpath("//*[contains(text(), 'Record successful deleted')]"));
  }

  public void create(ContactData contactData, boolean creation) {
    gotoNewContactPage();
    fillContactForm(contactData, creation);
    submitContactCreation();
    contactCashe = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModification(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCashe = null;
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    acceptDeletionContacts();
    contactCashe = null;
    messageCompleteDeletionContacts();
  }

  public void addToGroup(ContactData contact, Integer idGroup) { // Добавление контакта в группу
    selectContactById(contact.getId());
    selectToAddToGroup(idGroup);
    copyToGroup();
    messageCompleteAddedContactToGroup();
  }

  public void deleteFromGroup(ContactData contact, Integer idGroup) { // Добавление контакта в группу
    selectToDeletionToGroup(idGroup);
    selectContactById(contact.getId());
    removeFromGroup();
    messageCompleteRemovedContactFromGroup();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCashe = null;

  public Contacts all() {
    if (contactCashe != null) {
      return new Contacts(contactCashe);
    }

    contactCashe = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String emailsAll = cells.get(4).getText();
      String phonesAll = cells.get(5).getText();
      String[] phones = cells.get(5).getText().split("\n");
      contactCashe.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).withPhonesAll(phonesAll).withEmailsAll(emailsAll).withAddress(address));
    }
    return new Contacts(contactCashe);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String phoneHome = wd.findElement(By.name("home")).getAttribute("value");
    String phoneMobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String phoneWork = wd.findElement(By.name("work")).getAttribute("value");
    String emailOne = wd.findElement(By.name("email")).getAttribute("value");
    String emailTwo = wd.findElement(By.name("email2")).getAttribute("value");
    String emailThree = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withPhoneMobile(phoneMobile).withPhoneHome(phoneHome).withPhoneWork(phoneWork)
            .withEmailOne(emailOne).withEmailTwo(emailTwo).withEmailThree(emailThree)
            .withAddress(address);
  }
}