import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientTest {
  private Client firstClient;
  private Client secondClient;

  @Before
  public void initialize() {
    firstClient = new Client("Sandro", "men's cut", 23, 1);
    secondClient = new Client("Melissa", "women's cut", 33, 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Client_instantiatesCorrectly_true() {
    assertEquals(true, firstClient instanceof Client);
  }

  @Test
  public void getName_returnsName_String() {
    assertEquals("Sandro", firstClient.getName());
  }

  @Test
  public void getPreference_returnsPreference_String() {
    assertEquals("men's cut", firstClient.getPreferences());
  }

  @Test
  public void getAge_returnsAge_int() {
    assertEquals(23, firstClient.getAge());
  }

  @Test
  public void getId_returnsId_true() {
    firstClient.save();
    assertTrue(firstClient.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    firstClient.save();
    secondClient.save();
    assertTrue(Client.all().get(0).equals(firstClient));
    assertTrue(Client.all().get(1).equals(secondClient));
  }

  @Test
  public void find_returnsClientWithSameId_secondClient() {
    firstClient.save();
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  @Test
  public void search_returnClientListWithSearchedString_true() {
    firstClient.save();
    Client myClient = new Client("Satchel", "full shave", 24, 1);
    myClient.save();
    assertTrue(Client.search("Sa").contains(firstClient));
    assertTrue(Client.search("Sa").contains(myClient));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Client myClient = new Client("Sandro", "men's cut", 23, 1);
    assertTrue(firstClient.equals(myClient));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstClient.save();
    assertTrue(Client.all().get(0).equals(firstClient));
  }

  @Test
  public void save_assignsIdToObject() {
    firstClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(firstClient.getId(), savedClient.getId());
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist myStylist = new Stylist("Chase", "15 years experience", 37);
    myStylist.save();
    Client myClient = new Client("Satchel", "men's cut with face shave", 24, myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }

  @Test
  public void updateName_updatesClientName_true() {
    firstClient.save();
    firstClient.updateName("Mateo");
    assertEquals("Mateo", Client.find(firstClient.getId()).getName());
  }

  @Test
  public void updatePreferences_updatesClientPreferences_true() {
    firstClient.save();
    firstClient.updatePreferences("buzz cut");
    assertEquals("buzz cut", Client.find(firstClient.getId()).getPreferences());
  }

  @Test
  public void updateAge_updatesClientAge_true() {
    firstClient.save();
    firstClient.updateAge(26);
    assertEquals(26, Client.find(firstClient.getId()).getAge());
  }

  @Test
  public void delete_deletesClient_true() {
    firstClient.save();
    int firstClientId = firstClient.getId();
    firstClient.delete();
    assertEquals(null, Client.find(firstClientId));
  }
}
