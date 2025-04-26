package models;

public class Order {
    public int id;
    public int clientId;
    public int productId;
    public int amount;
    public double totalPrice;
    public long date;
    public int employeeId;
    public int salePointId;
    public String status;

    public Order() {}

    public void setId(int id) { this.id = id; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setDate(long date) { this.date = date; }

    public void setDate() {
        this.date = System.currentTimeMillis() / 1000L;
    }

    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public void setSalePointId(int salePointId) { this.salePointId = salePointId; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        //return "Order [id=" + id + ", clientId=" + clientId + ", productId=" + productId + ", amount=" + amount + ", totalPrice=" + totalPrice + ", date=" + date + ", employeeId=" + employeeId + ", salePointId=" + salePointId + ", status=" + status + "]";
        return "Заказ №" + id + "\t|\tID покупателя: " + clientId + "\t|\tID товара: " + productId + "\t|\tКоличество: " + amount + "\t|\tИтоговая сумма: " + totalPrice;
    }
}
