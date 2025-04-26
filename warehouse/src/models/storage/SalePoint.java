package models.storage;

public class SalePoint extends Storage {
    public int adminId;
    public double profit;
    public String location;

    public SalePoint() {}

    public void setAdminId(int adminId) { this.adminId = adminId; }
    public void setProfit(double profit) { this.profit = profit; }
    public void setLocation(String location) { this.location = location; }

    public void increaseProfit(double amount) {
        this.profit = profit + amount;
    }

    public void reduceProfit(double amount) {
        this.profit = profit - amount;
    }

    @Override
    public String toString() {
        //return "SalePoint[id=" + id + ", location=" + location + ", admin_id=" + adminId + "]";
        return "Пункт продаж №" + id + "\t|\tАдрес: " + location + "\t|\tВыручка: " + profit + "\t|\tОтветственный: " + adminId;
    }

}