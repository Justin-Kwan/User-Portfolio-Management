import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.RequestHandler;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class RequestHandlerTest {

  RequestHandler requestHandler = new RequestHandler();

  private DatabaseAccessor beforeTest() {
    DatabaseAccessor DBA = new DatabaseAccessor();
    try {
      DBA.createConnection();
      DBA.clearDatabase();
    }
    catch(Exception error) {
      System.out.println(error);
    }
    return DBA;
  }

  private void afterTest(DatabaseAccessor DBA) {
    DBA.clearDatabase();
    DBA.closeConnection();
  }

  @Test
  public void test_handleCheckUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    boolean doesUserExist;
    User user;

    // test when db is empty
    doesUserExist = requestHandler.handleCheckUserExists("random_id");
    assertEquals(false, doesUserExist);

    user = new MockUser("username1", "user__id1*", "authtoken1", 543.32);
    DBA.insertNewUser(user);
    user = new MockUser("username2", "user__id2*", "authtoken1", 544.32);
    DBA.insertNewUser(user);
    user = new MockUser("username3", "user__id3*", "authtoken1", 545.32);
    DBA.insertNewUser(user);
    user = new MockUser("username4", "user__id4*", "authtoken1", 546.32);
    DBA.insertNewUser(user);

    doesUserExist = requestHandler.handleCheckUserExists("user__id0*");
    assertEquals(false, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id1*");
    assertEquals(true, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id2*");
    assertEquals(true, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id3*");
    assertEquals(true, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id4*");
    assertEquals(true, doesUserExist);

    this.afterTest(DBA);
  }






}