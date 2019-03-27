package ru.stqa.pft.addressbook.tests.contacts;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTestsV2 extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstname("Евгений").withMiddlename("Михайлович").withLastname("Сладков").withAddress("Ростов-на-Дону, бул. Комарова, 1М").withPhoneMobile("+79188900075").withEmailOne("sladkov.e.m.@outlook.com").withBday("12").withBmonth("Ноябрь").withByear("1987").withAddress2("Ростов-на-Дону").inGroup(app.db().groups().iterator().next()), true);
    }
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test4"));
    }
    if (app.db().contacts().iterator().next().getGroups().size() == app.db().groups().size() // Проверяем, что выбираемый контакт не состоит в какой-либо группе
            && app.db().contacts().iterator().next().getGroups().size() != 0) {
      app.contact().deleteFromGroup(app.db().contacts().iterator().next(), app.db().contacts().iterator().next().getGroups().iterator().next().getId());
    }
  }

  @Test
  public void testContactAdditionToGroup() throws Exception {

    Groups groupsDb = app.db().groups(); // Получаем список групп из БД
    GroupData groupDb = groupsDb.iterator().next(); // Получаем группу
    Contacts contactsDb = app.db().contacts(); // Получаем список контактов из БД
    ContactData addedContact = contactsDb.iterator().next(); // Получаем контакт

    app.goTo().homePage();
    app.contact().addToGroup(addedContact, groupDb.getId()); // Добавляем выбранный контакт в группу

    Integer idContact = addedContact.getId(); // Заново получаем контакт из БД
    ContactData checkContact = app.db().contacts().stream()
            .filter(c -> (idContact == c.getId()))
            .findAny()
            .orElse(null);

    assertThat((addedContact.getGroups().size() + 1), equalTo(checkContact.getGroups().size())); // Проверяем через БД, что контакт добавлен в группу
  }
}