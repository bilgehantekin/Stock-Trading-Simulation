import java.util.ArrayList;
import java.util.Comparator;

// STOCK TRADING SIMULATION PROGRAM

public class Trader {
    Queue<Order> allOrders = new Queue<>(); // to add incoming orders into the Queue
    Queue<Order> helperAllOrders = new Queue<>(); // to add non-executed orders into the helper Queue
    HashMultiMap<String, Order> buyOrders = new HashMultiMap<>(); // to add incoming buy orders into the HashMultiMap
    HashMultiMap<String, Order> sellOrders = new HashMultiMap<>(); // to add incoming sell orders into the HashMultiMap
    ArrayList<Order []> executedOrders = new ArrayList<>(); // to add executed orders into the ArrayList

    public void pushOrder(Order order) {

        // pushing incoming orders into the Queue

        allOrders.enqueue(order);

    }

    public void execute() {

        // executing any possible order pairs

        while(!allOrders.isEmpty()) {

            // if the incoming order is a buy order

            if(allOrders.peek().getTypeOfOrder().equalsIgnoreCase("buy")) {

                ArrayList<Order> sell = (ArrayList<Order>) sellOrders.get(allOrders.peek().getStockSymbol());

                Order bestOrder = mostProperOrder(allOrders.peek(), sell);

                if(bestOrder.getID() == null) { // if there is no possible order

                    buyOrders.put(allOrders.peek().getStockSymbol(), allOrders.peek());
                    helperAllOrders.enqueue(allOrders.dequeue());

                }

                else {

                    for(int i = 0; i < sell.size(); i++) {

                        if(sell.get(i).equals(bestOrder)) {

                            Order o1 = new Order(bestOrder.getStockSymbol(), bestOrder.getTypeOfOrder(),
                                    bestOrder.getPrice(), bestOrder.getQuantity(), bestOrder.getTimeStamp(),
                                    bestOrder.getID(), bestOrder.isAllOrNone());
                            Order o2 = new Order(allOrders.peek().getStockSymbol(), allOrders.peek().getTypeOfOrder(),
                                    allOrders.peek().getPrice(), allOrders.peek().getQuantity(), allOrders.peek().getTimeStamp(),
                                    allOrders.peek().getID(), allOrders.peek().isAllOrNone());

                            Order [] orderArray = new Order[] {o1, o2};
                            executedOrders.add(orderArray);

                            if(bestOrder.getQuantity() == allOrders.peek().getQuantity()) {
                                sell.remove(bestOrder);
                                removeHelperOrders(bestOrder);
                                allOrders.dequeue();
                            }

                            else if(bestOrder.getQuantity() > allOrders.peek().getQuantity()) {
                                bestOrder.setQuantity(bestOrder.getQuantity() - allOrders.peek().getQuantity()); // decreasing quantity
                                allOrders.dequeue();
                            }

                            else if(bestOrder.getQuantity() < allOrders.peek().getQuantity()) {
                                allOrders.peek().setQuantity(allOrders.peek().getQuantity() - bestOrder.getQuantity()); // decreasing quantity
                                buyOrders.put(allOrders.peek().getStockSymbol(), allOrders.peek());
                                sell.remove(bestOrder);
                                removeHelperOrders(bestOrder);
                            }

                            break;

                        }
                    }

                }

            }

            // if the incoming order is a sell order

            else if(allOrders.peek().getTypeOfOrder().equalsIgnoreCase("sell")) {

                ArrayList<Order> buy = (ArrayList<Order>) buyOrders.get(allOrders.peek().getStockSymbol());

                boolean executed = false;

                for(int i = 0; i < buy.size(); i++) {

                    if(areTheseExecutable(allOrders.peek(), buy.get(i))) {

                        executed = true;

                        Order o1 = new Order(allOrders.peek().getStockSymbol(), allOrders.peek().getTypeOfOrder(),
                                allOrders.peek().getPrice(), allOrders.peek().getQuantity(), allOrders.peek().getTimeStamp(),
                                allOrders.peek().getID(), allOrders.peek().isAllOrNone());
                        Order o2 = new Order(buy.get(i).getStockSymbol(), buy.get(i).getTypeOfOrder(),
                                buy.get(i).getPrice(), buy.get(i).getQuantity(), buy.get(i).getTimeStamp(),
                                buy.get(i).getID(), buy.get(i).isAllOrNone());

                        Order [] orderArray = new Order[] {o1, o2};
                        executedOrders.add(orderArray);

                        if(buy.get(i).getQuantity() == allOrders.peek().getQuantity()) {
                            removeHelperOrders(buy.get(i));
                            buy.remove(buy.get(i));
                            allOrders.dequeue();
                        }

                        else if(buy.get(i).getQuantity() > allOrders.peek().getQuantity()) {
                            buy.get(i).setQuantity(buy.get(i).getQuantity() - allOrders.peek().getQuantity()); // decreasing quantity
                            allOrders.dequeue();
                        }

                        else if(buy.get(i).getQuantity() < allOrders.peek().getQuantity()) {
                            allOrders.peek().setQuantity(allOrders.peek().getQuantity() - buy.get(i).getQuantity()); // decreasing quantity
                            sellOrders.put(allOrders.peek().getStockSymbol(), allOrders.peek());
                            removeHelperOrders(buy.get(i));
                            buy.remove(buy.get(i));
                        }

                        break;

                    }

                }

                if(!executed) { // if the order has not been executed

                    sellOrders.put(allOrders.peek().getStockSymbol(), allOrders.peek());
                    helperAllOrders.enqueue(allOrders.dequeue());

                }

            }

            // if the incoming order is a cancel order

            else if(allOrders.peek().getTypeOfOrder().equalsIgnoreCase("cancel")) {

                ArrayList<Order> sell = (ArrayList<Order>) sellOrders.get(allOrders.peek().getStockSymbol());
                ArrayList<Order> buy = (ArrayList<Order>) buyOrders.get(allOrders.peek().getStockSymbol());

                // looking for a matching order in the sell queue

                for(Order sellOrder : sell) {

                    if(     sellOrder.getStockSymbol().equals(allOrders.peek().getStockSymbol()) && sellOrder.getTimeStamp() == allOrders.peek().getTimeStamp() &&
                            sellOrder.getID().equals(allOrders.peek().getID()) && sellOrder.isAllOrNone() == allOrders.peek().isAllOrNone()) {

                        sell.remove(sellOrder);
                        removeHelperOrders(sellOrder);
                        break;

                    }

                }

                // looking for a matching order in the buy queue

                for(Order buyOrder : buy) {

                    if(     buyOrder.getStockSymbol().equals(allOrders.peek().getStockSymbol()) && buyOrder.getTimeStamp() == allOrders.peek().getTimeStamp() &&
                            buyOrder.getID().equals(allOrders.peek().getID()) && buyOrder.isAllOrNone() == allOrders.peek().isAllOrNone()) {

                        buy.remove(buyOrder);
                        removeHelperOrders(buyOrder);
                        break;

                    }

                }

                allOrders.dequeue();

            }

        }

        while(!helperAllOrders.isEmpty()) {
            allOrders.enqueue(helperAllOrders.dequeue());
        }

    }
    public void printExecutedOrders() {

        // printing executed orders

        executedOrders.sort(Comparator.comparingInt(array -> array[0].getTimeStamp())); // sorting executed orders by their timestamp

        for (Order[] executedOrder : executedOrders) {
            Order o1 = executedOrder[0];
            Order o2 = executedOrder[1];
            System.out.println(o1.getID() + ", " + o2.getID() + ", " + Math.min(o1.getPrice(), o2.getPrice()) + ", " + Math.min(o1.getQuantity(), o2.getQuantity()));
        }

        executedOrders.clear();

    }

    public void printOrderQueue() {

        // printing orders that have not been executed yet

        while (!allOrders.isEmpty()) {
            System.out.println(allOrders.peek().getStockSymbol() + ", " + allOrders.peek().getTypeOfOrder() + ", " + allOrders.peek().getPrice() + ", " +
                    allOrders.peek().getQuantity() + ", " + allOrders.peek().getTimeStamp() + ", " + allOrders.peek().getID() + ", " + allOrders.peek().isAllOrNone());
            helperAllOrders.enqueue(allOrders.dequeue());
        }
        while (!helperAllOrders.isEmpty()) {
            allOrders.enqueue(helperAllOrders.dequeue());
        }

    }

    protected boolean areTheseExecutable(Order o1, Order o2) {

        // checking whether these orders are executable or not

        if(     (o1.getPrice() == o2.getPrice()) ||
                (o1.getTypeOfOrder().equalsIgnoreCase("buy") && o1.getPrice() > o2.getPrice()) ||
                (o1.getTypeOfOrder().equalsIgnoreCase("sell") && o1.getPrice() < o2.getPrice())) {

            if (!o1.isAllOrNone() && !o2.isAllOrNone()) {
                return true;
            }
            else if (o1.isAllOrNone() && !o2.isAllOrNone()) {
                return o1.getQuantity() <= o2.getQuantity();
            }
            else if (!o1.isAllOrNone() && o2.isAllOrNone()) {
                return o2.getQuantity() <= o1.getQuantity();
            }
            else if (o1.isAllOrNone() && o2.isAllOrNone()) {
                return o1.getQuantity() == o2.getQuantity();
            }
            else {
                return false;
            }
        }

        else {
            return false;
        }

    }

    protected Order mostProperOrder(Order buyOrder, ArrayList<Order> orders) {

        // choosing most proper sell order for buy order

        Order bestOrder = new Order(null, null, Double.MAX_VALUE, 0, 0, null);

        for (Order order : orders) {

            if (areTheseExecutable(buyOrder, order)) {
                if (order.getPrice() < bestOrder.getPrice()) {
                    bestOrder = order;
                }
            }

        }

        return bestOrder;
    }

    protected void removeHelperOrders(Order order) {

        // removing order from non-executed orders queue

        Queue<Order> temp = new Queue<>();

        while(!helperAllOrders.isEmpty()) {
            if(helperAllOrders.peek().equals(order)) {
                helperAllOrders.dequeue();
            }
            else {
                temp.enqueue(helperAllOrders.dequeue());
            }
        }

        while(!temp.isEmpty()) {
            helperAllOrders.enqueue(temp.dequeue());
        }

    }

}
