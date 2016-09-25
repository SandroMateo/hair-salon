import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist {
  private String name;
  private String background;
  private int age;
  private int id;

  public Stylist(String name, String background, int age) {
    this.name = name;
    this.background = background;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public String getBackground() {
    return background;
  }

  public int getAge() {
    return age;
  }

  public int getId() {
    return id;
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE stylistId = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Client.class);
    }
  }

  public static List<Stylist> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, background, age FROM stylists";
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name, age, background) VALUES (:name, :age, :background)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("age", this.age)
        .addParameter("background", this.background)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Stylist.class);
    return stylist;
    }
  }

  public static List<Stylist> search(String search) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE name LIKE :search";
      return con.createQuery(sql)
      .addParameter("search", (search + "%"))
      .executeAndFetch(Stylist.class);
    }
  }

  public void updateName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateBackground(String background) {
    this.background = background;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET background = :background WHERE id = :id";
      con.createQuery(sql)
        .addParameter("background", background)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateAge(int age) {
    this.age = age;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET age = :age WHERE id = :id";
      con.createQuery(sql)
        .addParameter("age", age)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherStylist) {
    if(!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
             this.getBackground().equals(newStylist.getBackground()) && this.getAge() == newStylist.getAge() &&
             this.getId() == newStylist.getId();
    }
  }
}
