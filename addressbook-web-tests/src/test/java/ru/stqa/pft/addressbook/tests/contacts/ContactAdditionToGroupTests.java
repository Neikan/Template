package ru.stqa.pft.addressbook.tests.contacts;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTests extends TestBase {

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
  }

  @Test
  public void testContactAdditionToGroup() throws Exception {

    Groups groupsDb = app.db().groups(); // Получаем список групп из БД
    Contacts contactsDb = app.db().contacts(); // Получаем список контатов из БД
    ContactData addedContact = contactsDb.iterator().next(); // Получаем контакт

    while (contactsDb.size() != 0) { // Ищем контакт, который не входит во все группы
      if (addedContact.getGroups().size() == groupsDb.size()) {
        contactsDb.remove(addedContact); // Убираем текущий контакт из рассмотрения как неподходящий
        if (contactsDb.size() == 0) { // Если все контакты состоят во всех текущих группах, то создаем новую группу
          app.goTo().groupPage();
          app.group().create(new GroupData().withName(String.format("GroupForContact %s", addedContact.getLastname()))); // Создаем новую группу, если все контакты состоят во всех группах
          groupsDb = app.db().groups();
        } else
          addedContact = contactsDb.iterator().next(); // Получаем следующий контакт
      } else break; // Прерываем цикл если нашелся контакт, количество групп которого меньше всех групп системы
    }

    List<GroupData> listGroupWithoutAddedContact = new ArrayList<GroupData>(groupsDb); // Получаем список групп, в которых контакт не состоит
    for (GroupData groupContact : addedContact.getGroups()) {
      for (GroupData groupDb : groupsDb) {
        if (groupContact.getId() == groupDb.getId()) {
          listGroupWithoutAddedContact.remove(groupDb);
        }
      }
    }

    for (GroupData li : listGroupWithoutAddedContact) { // Выполняем добавление контакта в найденные группы
      app.goTo().homePage();
      app.contact().addToGroup(addedContact, li.getId());
    }

    assertThat((addedContact.getGroups().size()+listGroupWithoutAddedContact.size()), equalTo(groupsDb.size())); // Проверяем через количество добавлений, что контакт добавлен во все группы

    Integer idContact = addedContact.getId(); // Заново получаем контакт из БД
    ContactData checkContact = app.db().contacts().stream()
            .filter(c -> (idContact == c.getId()))
            .findAny()
            .orElse(null);

    assertThat(checkContact.getGroups().size(), equalTo(groupsDb.size())); // Проверяем через БД, что контакт добавлен во все группы

  }
}