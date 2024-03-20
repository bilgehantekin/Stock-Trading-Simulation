

public class Main {
    public static void main(String[] args) {

        // CHECKING RULE OF "BENEFIT ON BUYER"

        System.out.println("CHECKING RULE OF \"BENEFIT ON BUYER\"");
        System.out.println();

        Trader trader1 = new Trader();

        Order order1 = new Order("GARAN", "sell", 150.0, 100, 1, "user1", false);
        Order order2 = new Order("GARAN", "sell", 100.0, 100, 2, "user2", false);
        Order order3 = new Order("GARAN", "buy", 200.0, 100, 3, "user3", false);

        trader1.pushOrder(order1);
        trader1.pushOrder(order2);
        trader1.pushOrder(order3);

        trader1.execute();

        // order2 and order3 must be matched because order2 is cheaper than order1

        System.out.println("Executed Orders:");
        trader1.printExecutedOrders();
        System.out.println();
        System.out.println("Order Queue:");
        trader1.printOrderQueue();

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println();

        // CHECKING RULE OF "CANCEL ORDER"

        System.out.println("CHECKING RULE OF \"CANCEL ORDER\"");
        System.out.println();

        Trader trader2 = new Trader();

        Order order4 = new Order("VAKBN", "buy", 200.0, 100, 1, "user4", false);
        Order order5 = new Order("VAKBN", "sell", 200.0, 100, 2, "user5", false);
        Order order6 = new Order("VAKBN", "buy", 100.0, 100, 3, "user6", false);
        Order order7 = new Order("VAKBN", "sell", 150.0, 100, 4, "user7", false);
        Order cancelOrder6 = new Order("VAKBN", "cancel", 0.0, 0, 3, "user6", false);

        trader2.pushOrder(order4);
        trader2.pushOrder(order5);
        trader2.pushOrder(order6);
        trader2.pushOrder(order7);
        trader2.pushOrder(cancelOrder6);

        trader2.execute();

        // order4 and order5 will match
        // order6 will cancel
        // order7 will remain

        System.out.println("Executed Orders:");
        trader2.printExecutedOrders();
        System.out.println();
        System.out.println("Order Queue:");
        trader2.printOrderQueue();

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println();

        // CHECKING RULE OF "TIMESTAMP"

        System.out.println("CHECKING RULE OF \"TIMESTAMP\"");
        System.out.println();

        Trader trader3 = new Trader();

        Order order8 = new Order("ASELS", "buy", 200.0, 100, 1, "user8", false);
        Order order9 = new Order("ASELS", "sell", 150.0, 100, 2, "user9", false);
        Order order10 = new Order("ASELS", "sell", 100.0, 100, 3, "user10", false);

        trader3.pushOrder(order8);
        trader3.pushOrder(order9);
        trader3.pushOrder(order10);

        trader3.execute();

        // order8 and order9 must be matched even though order10 is cheaper than order9
        // because order9 will come first
        // order10 will remain

        System.out.println("Executed Orders:");
        trader3.printExecutedOrders();
        System.out.println();
        System.out.println("Order Queue:");
        trader3.printOrderQueue();

        System.out.println("----------------------------------------------------------------------------------------");

        // CHECKING RULE OF "ALL OR NONE"

        System.out.println("CHECKING RULE OF \"ALL OR NONE\"");
        System.out.println();

        Trader trader4 = new Trader();

        Order order11 = new Order("ISCTR", "buy", 100.0, 100, 1, "user11", false);
        Order order12 = new Order("ISCTR", "sell", 100.0, 150, 2, "user12", true);
        Order order13 = new Order("ISCTR", "sell", 100.0, 150, 3, "user13", false);

        trader4.pushOrder(order11);
        trader4.pushOrder(order12);
        trader4.pushOrder(order13);

        trader4.execute();

        // order11 and order 13 will match
        // because order12 wants one transaction

        System.out.println("Executed Orders:");
        trader4.printExecutedOrders();
        System.out.println();
        System.out.println("Order Queue:");
        trader4.printOrderQueue();

        System.out.println("----------------------------------------------------------------------------------------");

        // MIXED ORDERS

        System.out.println("MIXED ORDERS");
        System.out.println();

        Trader trader5 = new Trader();

        Order order100 = new Order("ZARA", "buy", 100.0, 150, 1, "user100", false);
        Order order101 = new Order("ZARA", "sell", 80.0, 100, 2, "user101", true);
        Order order102 = new Order("ZARA", "cancel", 0.0, 0, 1, "user100", false);
        Order order103 = new Order("ZARA", "sell", 100.0, 50, 3, "user103", true);

        Order order104 = new Order("LINE", "cancel", 0.0, 0, 4, "user104", true);
        Order order105 = new Order("LINE", "sell", 200.0, 100, 4, "user104", true);
        Order order106 = new Order("LINE", "sell", 150.0, 100, 5, "user106", true);
        Order order107 = new Order("LINE", "buy", 200.0, 150, 6, "user107", false);

        Order order108 = new Order("MCSFT", "sell", 500.0, 150, 7, "user108", false);
        Order order109 = new Order("MCSFT", "buy", 500.0, 100, 8, "user109", true);
        Order order110 = new Order("MCSFT", "buy", 500.0, 50, 9, "user110", true);
        Order order111 = new Order("MCSFT", "cancel", 0.0, 0, 9, "user110", true);

        Order order112 = new Order("NFLIX", "sell", 300.0, 100, 10, "user112", false);
        Order order113 = new Order("NFLIX", "sell", 250.0, 100, 11, "user113", true);
        Order order114 = new Order("NFLIX", "sell", 200.0, 50, 12, "user114", true);
        Order order115 = new Order("NFLIX", "buy", 500.0, 100, 13, "user115", false);

        trader5.pushOrder(order100);
        trader5.pushOrder(order101);
        trader5.pushOrder(order102);
        trader5.pushOrder(order103);

        trader5.pushOrder(order104);
        trader5.pushOrder(order105);
        trader5.pushOrder(order106);
        trader5.pushOrder(order107);

        trader5.pushOrder(order108);
        trader5.pushOrder(order109);
        trader5.pushOrder(order110);
        trader5.pushOrder(order111);

        trader5.pushOrder(order112);
        trader5.pushOrder(order113);
        trader5.pushOrder(order114);
        trader5.pushOrder(order115);

        trader5.execute();

        System.out.println("Executed Orders:");
        trader5.printExecutedOrders();
        System.out.println();
        System.out.println("Order Queue:");
        trader5.printOrderQueue();

        System.out.println("----------------------------------------------------------------------------------------");

        // TWO TYPE ORDERS

        System.out.println("TWO TYPE ORDERS");
        System.out.println();

        Trader trader6 = new Trader();

        Order o1 = new Order("EUPWR", "sell", 100.0, 100, 1, "user1", false);
        Order o2 = new Order("CWENE", "buy", 1000.0, 100, 2, "user2", false);
        Order o3 = new Order("EUPWR", "buy", 80.0, 150, 3, "user3", true);
        Order o4 = new Order("EUPWR", "sell", 80.0, 200, 4, "user4", false);
        Order o5 = new Order("CWENE", "buy", 1500.0, 200, 5, "user5", true);
        Order o6 = new Order("EUPWR", "buy", 100.0, 100, 6, "user6", true);
        Order o7 = new Order("CWENE", "sell", 2000.0, 150, 7, "user7", false);
        Order o8 = new Order("CWENE", "sell", 1500.0, 200, 8, "user8", false);
        Order o9 = new Order("EUPWR", "buy", 80.0, 50, 9, "user9", false);
        Order o10 = new Order("CWENE", "sell", 500.0, 100, 10, "user10", true);
        Order o11 = new Order("CWENE", "cancel", 0.0, 0, 7, "user7", false);

        trader6.pushOrder(o1);
        trader6.pushOrder(o2);
        trader6.pushOrder(o3);
        trader6.pushOrder(o4);
        trader6.pushOrder(o5);
        trader6.pushOrder(o6);
        trader6.pushOrder(o7);
        trader6.pushOrder(o8);
        trader6.pushOrder(o9);
        trader6.pushOrder(o10);
        trader6.pushOrder(o11);

        trader6.execute();

        System.out.println("Executed Orders:");
        trader6.printExecutedOrders();
        System.out.println();
        System.out.println("Order Queue:");
        trader6.printOrderQueue();

        System.out.println("----------------------------------------------------------------------------------------");


    }
}
