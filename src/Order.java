public class Order {
    private String stockSymbol;
    private String typeOfOrder;
    private double price;
    private int quantity;
    private int timeStamp;
    private String ID;
    private boolean allOrNone;

    public Order(String stockSymbol, String typeOfOrder, double price, int quantity, int timeStamp, String ID, boolean allOrNone) {
        this.stockSymbol = stockSymbol;
        this.typeOfOrder = typeOfOrder;
        this.price = price;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
        this.ID = ID;
        this.allOrNone = allOrNone;
    }

    public Order(String stockSymbol, String typeOfOrder, double price, int quantity, int timeStamp, String ID) {
        this.stockSymbol = stockSymbol;
        this.typeOfOrder = typeOfOrder;
        this.price = price;
        this.quantity = quantity;
        this.timeStamp = timeStamp;
        this.ID = ID;
        this.allOrNone = false;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isAllOrNone() {
        return allOrNone;
    }

    public void setAllOrNone(boolean allOrNone) {
        this.allOrNone = allOrNone;
    }
}
