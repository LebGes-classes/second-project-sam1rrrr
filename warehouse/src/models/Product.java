package models;

public class Product {
    public int id;
    public String name;
    public double price;
    public double sellPrice;
    public String status;

    public Product() {}

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setSellPrice(double sellPrice) { this.sellPrice = sellPrice; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        //return "Product[id=" + id + ", name=" + name + ", price=" + price + ", sellPrice=" + sellPrice + ", status=" + status + "]";
        return "Товар №" + id + "\t|\tНазвание: " + name + "\t|\tЦена закупки: " + price + "\t|\tЦена продажи: " + sellPrice;
    }
}
