import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CDL_Solution{

    private static int priceA, discountedPriceA, discount_at_count_A;
    private static int priceB, discountedPriceB, discount_at_count_B;
    private static int priceC, discountedPriceC, discount_at_count_C;
    private static int priceD, discountedPriceD, discount_at_count_D;

    // This function determines the current sum of all items in the basket based on the set prices of items, thier special offers (if any) and their quantities
    //in the shopping basket. If there is a discount for an item, meaning that there are a specific number of items (discount_at_count variables) and the discounted price
    //of the quantity (discountedPrice variables), then add to the current sum the value determined by a mathematic formula that calculates if there are enough items in the
    //basket to be given the discount, meaning that their number can be divided to the quantity at which the discount is given, then multiplied by the value of the disocunt and,
    // if the number is not a multiple of quantity of discounted items, we add to the sum the product between the modulus of the number of items and the quantity at which discounted
    //is given, and the price of the item. If there is no discounted price for more items, the functions simply adds the product between the quantity of items and then the price. 
    private static int currentSum(int countA, int countB, int countC, int countD){
        int sum = 0;
        if (discount_at_count_A > 0 && discountedPriceA>0) {
           sum += (countA / discount_at_count_A) * discountedPriceA + (countA % discount_at_count_A)* priceA;}
        else {
            sum += countA * priceA;
           }
        
           if (discount_at_count_B > 0 && discountedPriceB>0){
           sum += (countB / discount_at_count_B) * discountedPriceB + (countB % discount_at_count_B)* priceB;}
        else { sum += countB * priceB;}   

        if (discount_at_count_C > 0 && discountedPriceC>0){
            sum += (countC / discount_at_count_C) * discountedPriceC + (countC % discount_at_count_C)* priceC;}
         else { sum += countC * priceC;} 

         if (discount_at_count_D > 0 && discountedPriceD>0){
            sum += (countD / discount_at_count_D) * discountedPriceD + (countD % discount_at_count_D)* priceD;}
         else { sum += countD * priceD;}  
        return sum;
    }
    //This function read the set of prices from a file. If it encounters a line that contains one of the letters of a product, it then determines what number or numbers
    //follow after it, the first number representing the price of the item, the second being the quantity of items at which there is a discount, at the third one being the
    //the discount price for the given quantity of items. If the quantity for the discounted price is specified, but the new price itself is not, then the discount cannot be
    //applied, so the discountedPrice variable will become 0.
    private static void readPrices(String file) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split(" ");
                if(parts[0].equals("A")){
                    priceA = Integer.parseInt(parts[1]);
                    if(parts.length > 2){
                        discount_at_count_A = Integer.parseInt(parts[2]);
                        if(parts.length > 3){
                            discountedPriceA = Integer.parseInt(parts[3]);
                        } else {discountedPriceA = 0;}
                    } else { discount_at_count_A=0; discountedPriceA=0;}
                }
                if(parts[0].equals("B")){
                    priceB = Integer.parseInt(parts[1]);
                    if(parts.length > 2){
                        discount_at_count_B = Integer.parseInt(parts[2]);
                        if(parts.length > 3){
                            discountedPriceB = Integer.parseInt(parts[3]);
                        } else {discountedPriceB = 0;}
                    } else { discount_at_count_B=0; discountedPriceB=0;}
                }
                if(parts[0].equals("C")){
                    priceC = Integer.parseInt(parts[1]);
                    if(parts.length > 2){
                        discount_at_count_C = Integer.parseInt(parts[2]);
                        if(parts. length > 3){
                            discountedPriceC = Integer.parseInt(parts[3]);
                        } else {discountedPriceC = 0;}
                    } else { discount_at_count_C=0; discountedPriceC=0;}
                }
                if(parts[0].equals("D")){
                    priceD = Integer.parseInt(parts[1]);
                    if(parts.length > 2){
                        discount_at_count_D = Integer.parseInt(parts[2]);
                        if(parts.length > 3){
                            discountedPriceD = Integer.parseInt(parts[3]);
                        } else {discountedPriceD = 0;}
                    } else { discount_at_count_D=0; discountedPriceD=0;}
                }
            }
        }
    }
    //The main function tries to read a file by handling exceptions, then initialies a Scanner varaible, the counters and displays messages for the user to inform him about
    //the prices and any potential offers. It then enters a while loop, which works continously until the transaction has been cancelled by pressing Q or until the enter button
    //has been pressed. The while loop asks for specific input from the user, specifically to enter one product at a time, as a capital letter, and handles exceptions quite well.
    //After each correct input has been introduced, the program prints the current total sum of the scanned items. 
    public static void main(String[] args){

        try {
            readPrices("prices.txt");
        } catch (IOException e) {
            System.out.println("Error reading pricing rules: " + e.getMessage());
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int countA=0, countB=0, countC=0, countD=0;
        String input;

        System.out.println("Welcome to the checkout system!");
        System.out.println("Please insert one item at a time, in capital letters: A for apple, B for banana, C for cherries, D for dates.");
        if (discountedPriceA > 0 && discount_at_count_A > 0)
           {System.out.println("Each apple costs "+priceA+" pence. This week, we have a special offer: buy "+discount_at_count_A+" apples for "+ discountedPriceA +" pence.");}
        else
            {System.out.println("Each apple costs "+ priceA+ " pence.");
        }
        if (discount_at_count_B > 0 && discountedPriceB > 0)
           System.out.println("Each banana costs "+priceB +" pence. This week, we have a special offer: buy "+ discount_at_count_B +" bananas at "+ discountedPriceB +" pence.");
        else
            System.out.println("Each banana costs "+priceB+" pence");
        if (discount_at_count_C > 0 && discount_at_count_C > 0){
            System.out.println("Each box of cherries costs "+ priceC+" pence. This week, we have a special offer: buy "+ discount_at_count_C+" boxes of cherries for "+discountedPriceC+" pence.");
        } else    
           System.out.println("Each box of cherries costs "+ priceC +" pence.");
        if (discount_at_count_D > 0 && discountedPriceD > 0)
           System.out.println("Each pack of dates costs "+priceD +" pence. This week, we have a special offer: buy "+ discount_at_count_D +" packs at "+ discountedPriceD +" pence.");
        else
            System.out.println("Each pack of dates costs "+priceD+" pence.");
        System.out.println("When you want to finish the transaction process and pay, press Enter. If you want to quit the transaction process and remove all items, press Q.");        
    
        while (true) {
            System.out.print("Enter an item: ");
            input = scanner.nextLine().trim();
        
            if (input.equalsIgnoreCase("Q")){
                System.out.println("Transaction cancelled.");
                break;}
            else if (input.isEmpty()) {
                if (countA == 0 && countB == 0 && countC == 0 && countD == 0) {
                System.out.println("Please enter one of the following options: A for Apple, B for Banana, C for Cherries, D for Dates");}
            else {
                System.out.println("Total sum paid: "+currentSum(countA, countB, countC, countD) + " pence");
                break;
                }
            } 
             else if (input.equals("A")) {
                  countA++;
            } else if (input.equals("B")) {
                  countB++;
            } else if (input.equals("C")) {
                 countC++;
            } else if (input.equals("D")) {
                countD++;
            } else if (input.equalsIgnoreCase("a") || input.equalsIgnoreCase("b") || input.equalsIgnoreCase("c") || input.equalsIgnoreCase("d")) {
                System.out.println("Enter the item as a capital  letter.");
            } else {
                System.out.println("Please enter one of the following options: A for Apple, B for Banana, C for Cherries, D for Dates");
            }
            
            if (countA > 0 || countB > 0 || countC > 0 || countD > 0) {
                 System.out.println("Current total: " + currentSum(countA, countB, countC, countD) + " pence");
            }
        }
        scanner.close();    
    }
}