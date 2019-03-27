package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTestsCheckBlocker extends TestBase {


  @Test()
  public void testsSoapCheckBlocker() throws RemoteException, ServiceException, MalformedURLException {
    int issueId = 2;
    if (isIssueOpen(issueId) == false) {
      Set<Project> projects = app.soap().getProjects();
      System.out.println(projects.size());
    } else {
      skipIfNotFixed(issueId);
    }
  }
}
