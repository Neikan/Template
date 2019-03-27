package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestTestsCheckBlocker {

  @Test
  public void testCreateIssueCheckBlocker() throws IOException {
    int issueId = 750;
    if (isIssueOpen(issueId) == false) {
      Set<Issue> oldIssues = getIssues();
      Issue newIssue = new Issue().withSubject("Test issue subject").withDescription("Test issue description");
      int isssueId = createIssue(newIssue);
      Set<Issue> newIssues = getIssues();
      oldIssues.add(newIssue.withId(isssueId));
      Assert.assertEquals(newIssues, oldIssues);
    } else {
      skipIfNotFixed(issueId);
    }
  }

  private Set<Issue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues.json")).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("http://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    String state_name = getIssueState(issueId);
    if (state_name.equals("Closed")) {
      return false;
    } else {
      return true;
    }
  }

  public String getIssueState(int issueId) throws IOException {
    String state_name = "";
    String json = getExecutor().execute(Request.Get(String.format("http://bugify.stqa.ru/api/issues/%s.json", issueId))).returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonArray issues = parsed.getAsJsonObject().get("issues").getAsJsonArray();
    Set<Issue> issuesSet = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    if (issuesSet.size() == 1) { //Проверяем, что в множестве действительно один элемент
      state_name = issuesSet.iterator().next().getState_name();
    } else {
      System.out.println("Мы все умрем, а элементов больше одного :(");
    }
    return state_name;
  }
}
