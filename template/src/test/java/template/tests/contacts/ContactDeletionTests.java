package template.tests.contacts;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import template.model.ContactData;
import template.model.Contacts;
import template.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstname("Евгений").withMiddlename("Михайлович").withLastname("Сладков").withAddress("Ростов-на-Дону, бул. Комарова, 1М").withPhoneMobile("+79188900075").withEmailOne("sladkov.e.m.@outlook.com").withBday("12").withBmonth("Ноябрь").withByear("1987").withAddress2("Ростов-на-Дону").inGroup(app.db().groups().iterator().next()), true);
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() - 1));//Проверка на основе хеширования
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListInUI();
  }
}