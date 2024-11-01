import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.*;


public class CDL_Solution{

    private static Map<String, Item> prices = new HashMap<>();
    private static Map<String, Integer> count_items = new HashMap<>();
    private static int totalCount = 0;

    //The item class enables the user to insert as many items as he wants to in the .txt file, with their name, price, and discounts 
    static class Item {
        String name;
        int price;
        int discount_at_count;
        int discountedPrice;

        Item(String name, int price, int discount_at_count, int discountedPrice) {
            this.name = name;
            this.price = price;
            this.discount_at_count = discount_at_count;
            this.discountedPrice = discountedPrice;
        }
    }

    /* This function determines the current sum of all items in the basket based on the set prices of items, thier special offers (if any) and their quantities
    in the shopping basket. If there is a discount for an item, meaning that there are a specific number of items (discount_at_count variable) and the discounted price
    of the quantity (discountedPrice variable), then add to the current sum the value determined by a mathematic formula that calculates if there are enough items in the
    basket to be given the discount, meaning that their number can be divided to the quantity at which the discount is given, then multiplied by the value of the disocunt and,
    if the number is not a multiple of quantity of discounted items, we add to the sum the product between the modulus of the number of items and the quantity at which discounted
    is given, and the price of the item. If there is no discounted price for more items, the functions simply adds the product between the quantity of items and then the price. */
    private static int currentSum() {
        int sum = 0;
        for (Map.Entry<String, Integer> entry : count_items.entrySet()) {
            String itemCode = entry.getKey();
            int count = entry.getValue();
            Item item = prices.get(itemCode);
            if (item.discount_at_count > 0 && item.discountedPrice>0) {
                sum += (count / item.discount_at_count) * item.discountedPrice + (count % item.discount_at_count) * item.price;
            } else {
                sum += count * item.price;
            }
        }
        return sum;
    }
    /*This function read the set of prices from a file. If it encounters a line that contains one of the letters of a product, it then determines what number or numbers
      follow after it, the first number representing the price of the item, the second being the quantity of items at which there is a discount, at the third one being the
      the discount price for the given quantity of items. If the quantity for the discounted price is specified, but the new price itself is not, then the discount cannot be
    applied, so the discountedPrice variable will become 0.*/
    private static void readPrices(String file) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split(" ");
                String code = parts[0];
                String name = parts[1];
                int price = Integer.parseInt(parts[2]);
                int discount_at_count = Integer.parseInt(parts[3]);
                int discountedPrice = Integer.parseInt(parts[4]);
                prices.put(code, new Item(name, price, discount_at_count, discountedPrice));
                count_items.put(code, 0);
            }
        }
    }
    /*The main function tries to read a file by handling exceptions, then initialies a Scanner varaible, the counters and displays messages for the user to inform him about
    the prices and any potential offers. It then enters a while loop, which works continously until the transaction has been cancelled by pressing Q or until the enter button
    has been pressed. The while loop asks for specific input from the user, specifically to enter one product at a time, as a capital letter, and handles exceptions quite well.
    After each correct input has been introduced, the program prints the current total sum of the scanned items. If the user inserts R followed by a whitespace and then the correct item     
    to be removed, the program shows which item has been removed, if the user does not insert a correct item to be removed, the program will tell him the quantity of each item.*/ 

    public static void main(String[] args){

        try {
            readPrices("prices.txt");
        } catch (IOException e) {
            System.out.println("Error reading pricing rules: " + e.getMessage());
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Welcome to the checkout system!");
        System.out.println("Please insert one item at a time, in capital letters:");
        for (Map.Entry<String, Item> entry : prices.entrySet()) {
            String code = entry.getKey();
            Item item = entry.getValue();
            if (item.discount_at_count > 0 && item.discountedPrice > 0) {
                System.out.println(code + " for " + item.name + " (" + item.price + " pence each, " + item.discount_at_count + " for " + item.discountedPrice + " pence)");
            } else {
                System.out.println(code + " for " + item.name + " (" + item.price + " pence each)");
            }
        }
        System.out.println("If you want to remove an item from the basket, type R followed by a whitespace and then the item to be removed as a capital letter.");
        System.out.println("Press Enter to finish the checkout process and pay. If you want to quit the transaction and remove all items, press Q.");        

        while (true) {
            System.out.print("Enter an item: ");
            input = scanner.nextLine().trim();
        
            if (input.equalsIgnoreCase("Q")){
                System.out.println("Transaction cancelled.");
                break;}
            else if (input.isEmpty()) {
                if (totalCount == 0) {
                    System.out.print("Please enter a valid item code. Available options are: ");
                    for (Map.Entry<String, Item> entry : prices.entrySet()) {
                        System.out.print(entry.getKey() + " for " + entry.getValue().name + ", ");
                    }
                }
                else {
                       System.out.println("Total sum paid: "+currentSum() + " pence");
                       break;}
            }
            else if (prices.containsKey(input)) {
                count_items.put(input, count_items.get(input) + 1);
                totalCount++;
            } 
            else if (input.startsWith("R") && input.length() == 3 && prices.containsKey(input.substring(2))){
                String delete_item = input.substring(2);
                if (Character.isUpperCase(delete_item.charAt(0)) == false){
                    System.out.println("Please insert the item to be removed as a capital letter.");
                }
                if (count_items.get(delete_item) > 0) {
                    count_items.put(delete_item, count_items.get(delete_item)-1);
                    totalCount--;
                    System.out.println("Removed item "+ prices.get(delete_item).name + " from the basket.");
                }
                else 
                {  System.out.println("Could not remove item "+ delete_item + " from the basket. The basket contains:");
                   int k =0;
                   int size = prices.size();
                   for (Map.Entry<String, Integer> entry: count_items.entrySet()){
                        k++;
                        if (k< size)
                          { System.out.print(entry.getValue()+" "+ entry.getKey()+ ", ");  }                    
                        else {System.out.print(entry.getValue()+" "+ entry.getKey()+ ". ");}
                        }
                 }
            }

            else
            {
                System.out.print("Please enter a valid item code. Available options are: ");
                int k = 0;
                int size = prices.size();
                for (Map.Entry<String, Item> entry : prices.entrySet()) {
                    k++;
                    if (k< size)
                      {System.out.print(entry.getKey() + " for " + entry.getValue().name + ", ");}
                    else 
                       {System.out.print(entry.getKey() + " for " + entry.getValue().name + ".");}  
                }
                System.out.println();
            } 
            
            if (totalCount > 0) {
                 System.out.println("Current total: " + currentSum() + " pence");
            }
        }
        scanner.close();    
    }
}