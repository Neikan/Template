package template.tests.contacts;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import template.model.ContactData;
import template.model.Contacts;
import template.model.Groups;
import template.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
                .withFirstname("Евгений").withMiddlename("Михайлович").withLastname("Сладков").withAddress("Ростов-на-Дону, бул. Комарова, 1М").withPhoneMobile("+79188900075").withEmailOne("sladkov.e.m.@outlook.com").withBday("12").withBmonth("Ноябрь").withByear("1987").withAddress2("Ростов-на-Дону").inGroup(app.db().groups().iterator().next()), true);
    }
  }

  @Test
  public void testContactModification() throws Exception {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Евгений").withMiddlename("Михайлович").withLastname("Сладков").withAddress("Ростов-на-Дону, бул. Комарова, 1М").withPhoneMobile("+79188900075").withEmailOne("sladkov.e.m.@outlook.com").withBday("12").withBmonth("Ноябрь").withByear("1987").withAddress2("Ростов-на-Дону").inGroup(groups.iterator().next());
    app.goTo().homePage();
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));//Проверка на основе хеширования
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }
}
