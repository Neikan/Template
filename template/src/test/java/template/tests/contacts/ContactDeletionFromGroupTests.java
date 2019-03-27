package ru.stqa.pft.addressbook.tests.contacts;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) { // Проверяем, что есть существующий контакт
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstname("Евгений").withMiddlename("Михайлович").withLastname("Сладков").withAddress("Ростов-на-Дону, бул. Комарова, 1М").withPhoneMobile("+79188900075").withEmailOne("sladkov.e.m.@outlook.com").withBday("12").withBmonth("Ноябрь").withByear("1987").withAddress2("Ростов-на-Дону").inGroup(app.db().groups().iterator().next()), true);
    }
    if (app.db().groups().size() == 0) { // Проверяем, что есть группа
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("GroupForDeletionContactFrom"));
    }
    if (app.db().contacts().iterator().next().getGroups().size() == 0) { // Проверяем, что выбираемый контакт состоит в какой-либо группе
      app.contact().addToGroup(app.db().contacts().iterator().next(), app.db().groups().iterator().next().getId());
    }
  }

  @Test
  public void testContactDeletionFromGroup() throws Exception {

    Contacts contactsDb = app.db().contacts();
    ContactData removedContact = contactsDb.iterator().next();
    GroupData groupsContact = removedContact.getGroups().iterator().next();

    app.goTo().homePage();
    app.contact().deleteFromGroup(removedContact, groupsContact.getId()); // Удаляем выбранный контакт из группы

    Integer idContact = removedContact.getId(); // Заново получаем контакт из БД
    ContactData checkContact = app.db().contacts().stream()
            .filter(c -> (idContact == c.getId()))
            .findAny()
            .orElse(null);

    assertThat((removedContact.getGroups().size()-1), equalTo(checkContact.getGroups().size())); // Проверяем, что контакт удален из группы
  }

}
