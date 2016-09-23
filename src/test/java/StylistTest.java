import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class StylistTest {
  private Stylist firstStylist;
  private Stylist secondStylist;

  @Before
  public void initialize() {
    firstStylist = new Stylist("Chase", "15 years experience", 37);
    secondStylist = new Stylist("Tina", "10 years experience", 35);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Stylist_instantiatesCorrectly_true() {
    assertEquals(true, firstStylist instanceof Stylist);
  }

  @Test
  public void getName_returnsName_String() {
    assertEquals("Chase", firstStylist.getName());
  }

  @Test
  public void getBackground_returnsBackground_String() {
    assertEquals("15 years experience", firstStylist.getBackground());
  }

  @Test
  public void getAge_returnsAge_int() {
    assertEquals(37, firstStylist.getAge());
  }

  @Test
  public void getId_returnsId_true() {
    firstStylist.save();
    assertTrue(firstStylist.getId() > 0);
  }

  @Test
  public void getClients_returnListOfClients_True() {
    firstStylist.save();
    Client firstClient = new Client("Sandro", "men's cut", 23, firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Melissa", "women's cut", 33, firstStylist.getId());
    secondClient.save();
    assertTrue(firstStylist.getClients().contains(firstClient));
    assertTrue(firstStylist.getClients().contains(secondClient));
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    firstStylist.save();
    secondStylist.save();
    assertTrue(Stylist.all().get(0).equals(firstStylist));
    assertTrue(Stylist.all().get(1).equals(secondStylist));
  }

  @Test
  public void find_returnsStylistWithSameId_secondStylist() {
    firstStylist.save();
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Stylist myStylist = new Stylist("Chase", "15 years experience", 37);
    assertTrue(firstStylist.equals(myStylist));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstStylist.save();
    assertTrue(Stylist.all().get(0).equals(firstStylist));
  }

  @Test
  public void save_assignsIdToObject() {
    firstStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(firstStylist.getId(), savedStylist.getId());
  }

  @Test
  public void updateName_updatesStylistName_true() {
    firstStylist.save();
    firstStylist.updateName("Brock");
    assertEquals("Brock", Stylist.find(firstStylist.getId()).getName());
  }

  @Test
  public void updateBackground_updatesStylistBackground_true() {
    firstStylist.save();
    firstStylist.updateBackground("12 years experience");
    assertEquals("12 years experience", Stylist.find(firstStylist.getId()).getBackground());
  }

  @Test
  public void updateAge_updatesStylistAge_true() {
    firstStylist.save();
    firstStylist.updateAge(30);
    assertEquals(30, Stylist.find(firstStylist.getId()).getAge());
  }

  @Test
  public void delete_deletesStylist_true() {
    firstStylist.save();
    int firstStylistId = firstStylist.getId();
    firstStylist.delete();
    assertEquals(null, Stylist.find(firstStylistId));
  }
}
