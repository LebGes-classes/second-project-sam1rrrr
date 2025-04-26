package models.storage;

import java.util.ArrayList;

public class Storage {
    public int id;
    public String location;
    ArrayList<Cell> cells = new ArrayList<>();
    public int adminId;

    public Storage() {}

    public Storage(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public void setId(int id) { this.id = id; }
    public void setLocation(String location) { this.location = location; }
    public void setAdminId(int adminId) { this.adminId = adminId; }
    public void setCells(ArrayList<Cell> cells) { this.cells = cells; }

    @Override
    public String toString() {
        //return "Storage [id=" + id + ", location=" + location + ", cells_count=" + cells.size() + ", admin_id=" + adminId +"]";
        return "Склад №" + id + "\t|\tАдрес: " + location + "\t|\tОтветственный: " + adminId;
    }
}