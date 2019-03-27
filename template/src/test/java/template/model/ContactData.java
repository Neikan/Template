package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contact")
@Entity // Сделаны привязки к базе данных
@Table(name = "addressbook")
public class ContactData {
  @XStreamOmitField
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "firstname")
  private String firstname;

  @Expose
  @Column(name = "middlename")
  private String middlename;

  @Expose
  @Column(name = "lastname")
  private String lastname;

  @Expose
  @Column(name = "address")
  @Type(type = "text")
  private String address;

  @Expose
  @Column(name = "mobile")
  @Type(type = "text")
  private String phoneMobile;

  @Expose
  @Column(name = "work")
  @Type(type = "text")
  private String phoneWork;

  @Expose
  @Column(name = "home")
  @Type(type = "text")
  private String phoneHome;

  @Expose
  @Transient
  private String phonesAll;

  @Expose
  @Column(name = "email")
  @Type(type = "text")
  private String emailOne;

  @Expose
  @Column(name = "email2")
  @Type(type = "text")
  private String emailTwo;

  @Expose
  @Column(name = "email3")
  @Type(type = "text")
  private String emailThree;

  @Expose
  @Transient
  private String emailsAll;

  @Expose
  @Transient
  private String bday;

  @Expose
  @Transient
  private String bmonth;

  @Expose
  @Transient
  private String byear;

  @Expose
  @Column(name = "address2")
  @Type(type = "text")
  private String address2;

  @Expose
  @Column(name = "photo")
  @Type(type = "text")
  private String photo;

  @Expose
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "address_in_groups",
          joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneMobile() {
    return phoneMobile;
  }

  public String getPhoneHome() {
    return phoneHome;
  }

  public String getPhoneWork() {
    return phoneWork;
  }

  public String getPhonesAll() {
    return phonesAll;
  }

  public String getEmailOne() {
    return emailOne;
  }

  public String getEmailTwo() {
    return emailTwo;
  }

  public String getEmailThree() {
    return emailThree;
  }

  public String getEmailsAll() {
    return emailsAll;
  }

  public String getBday() {
    return bday;
  }

  public String getBmonth() {
    return bmonth;
  }

  public String getByear() {
    return byear;
  }

  public String getAddress2() {
    return address2;
  }

  public Groups getGroups() {
    return new Groups(groups);
  }

  public File getPhoto() {
    if (photo == null) {
      return null;
    }
    return new File(photo);
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withMiddlename(String middlename) {
    this.middlename = middlename;
    return this;
  }

  public ContactData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withPhoneMobile(String phoneMobile) {
    this.phoneMobile = phoneMobile;
    return this;
  }

  public ContactData withPhoneHome(String phoneHome) {
    this.phoneHome = phoneHome;
    return this;
  }

  public ContactData withPhoneWork(String phoneWork) {
    this.phoneWork = phoneWork;
    return this;
  }

  public ContactData withPhonesAll(String phonesAll) {
    this.phonesAll = phonesAll;
    return this;
  }

  public ContactData withEmailOne(String emailOne) {
    this.emailOne = emailOne;
    return this;
  }

  public ContactData withEmailTwo(String emailTwo) {
    this.emailTwo = emailTwo;
    return this;
  }

  public ContactData withEmailThree(String emailThree) {
    this.emailThree = emailThree;
    return this;
  }

  public ContactData withEmailsAll(String emailsAll) {
    this.emailsAll = emailsAll;
    return this;
  }


  public ContactData withBday(String bday) {
    this.bday = bday;
    return this;
  }

  public ContactData withBmonth(String bmonth) {
    this.bmonth = bmonth;
    return this;
  }

  public ContactData withByear(String byear) {
    this.byear = byear;
    return this;
  }

  public ContactData withAddress2(String address2) {
    this.address2 = address2;
    return this;
  }

  public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (middlename != null ? !middlename.equals(that.middlename) : that.middlename != null) return false;
    return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (middlename != null ? middlename.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }
}
