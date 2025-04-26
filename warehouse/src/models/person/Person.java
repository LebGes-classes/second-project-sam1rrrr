package models.person;

public class Person {
    public int id;
    public String name;

    public Person() {}

    public Person(int id, String name) {
        this.name = name;
    }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "user[id=" + id + ", name=" + name + "]";
    }
}
