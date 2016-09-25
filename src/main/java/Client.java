import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Client {
  private String name;
  private String preferences;
  private int age;
  private int id;
  private int stylistId;

  public Client(String name, String preferences, int age, int stylistId) {
    this.name = name;
    this.preferences = preferences;
    this.age = age;
    this.stylistId = stylistId;
  }

  public String getName() {
    return name;
  }

  public String getPreferences() {
    return preferences;
  }

  public int getAge() {
    return age;
  }

  public int getId() {
    return id;
  }

  public int getStylistId() {
    return stylistId;
  }

  public static List<Client> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, preferences, age, stylistId FROM clients";
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, age, preferences, stylistid) VALUES (:name, :age, :preferences, :stylistId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("age", this.age)
        .addParameter("preferences", this.preferences)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client client = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Client.class);
    return client;
    }
  }

  public static List<Client> search(String search) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE name LIKE :search";
      return con.createQuery(sql)
      .addParameter("search", (search + "%"))
      .executeAndFetch(Client.class);
    }
  }

  public void updateName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updatePreferences(String preferences) {
    this.preferences = preferences;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET preferences = :preferences WHERE id = :id";
      con.createQuery(sql)
        .addParameter("preferences", preferences)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateAge(int age) {
    this.age = age;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET age = :age WHERE id = :id";
      con.createQuery(sql)
        .addParameter("age", age)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
             this.getPreferences().equals(newClient.getPreferences()) && this.getAge() == newClient.getAge() &&
             this.getId() == newClient.getId() &&
             this.getStylistId() == newClient.getStylistId();
    }
  }
}
