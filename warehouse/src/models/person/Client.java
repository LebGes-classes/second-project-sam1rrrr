package models.person;

public class Client extends Person {
    public Client() {};

    public Client(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        //return "Client [id=" + id + ", name=" + name + "]";
        return "Покупатель №" + id + "\t|\tИмя: " + name;
    }
}

