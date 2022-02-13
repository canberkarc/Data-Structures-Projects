import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{
    public static void users() throws IOException {
        /* Putting customers in file */
        try{
        BufferedReader read = new BufferedReader(new FileReader("e-commerce-samples.csv"));
        ArrayList<String> traders = new ArrayList<>();
        int count = 0;
        String line;
        StringBuilder str = new StringBuilder();
        int num = 9999999;
        FileWriter traderCustomer = new FileWriter("users.txt");
        while ((line = read.readLine()) != null) {
            String[] cols = line.split(";");
            if (!traders.contains(cols[6]) && count > 0){
                traders.add(cols[6]);
                str.append("T").append(num).append(" / ").append(num).append(" / ").append(cols[6]).append("\n");
                traderCustomer.write(str.toString());
                num--;
                str = new StringBuilder();
            }
            count++;
        }
        traders.clear();
        traderCustomer.close();
        read.close();

        /* Adding customers */
        FileWriter fw = new FileWriter("users.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        num = 9999999;
        str = new StringBuilder();
        for(int i=0; i<5; i++) {
            str.append("C").append(num).append(" / ").append(num).append("\n");
            bw.write(str.toString());
            num--;
            str = new StringBuilder();
        }
        bw.close();
        fw.close();
        }
    	catch(Exception e){
    		System.out.println("Please put e-commerce-samples.csv in the directory\n");
        	return;
    	}
    }

    public static void putProducts() throws IOException {
        /* Putting products into file */
        try{
        FileWriter products = new FileWriter("products.txt");
        BufferedReader read = new BufferedReader(new FileReader("e-commerce-samples.csv"));
        int count = 0;
        String str, line;
        while ((line = read.readLine()) != null) {
            String[] cols = line.split(";");
            if (count > 0){
                str = cols[0] + " /// " + cols[1] + " /// " + cols[2] + " /// " + cols[3] + " /// " + cols[4] + " /// " + cols[5] + " /// "
                        + cols[6] + "\n";
                products.write(str);
                products.write("&&&\n");
            }
            count++;
        }
        read.close();
        products.close();
        }
    	catch(Exception e){
    		System.out.println("Please put e-commerce-samples.csv in the directory\n");
        	return;
    	}
    }

    public static void menu() throws IOException {
    	File file = new File("e-commerce-samples.csv");
        boolean exists = file.exists();
        if(!exists){
        	System.out.println("Please put e-commerce-samples.csv in the directory\n");
        	return;
        }

        String id,password,line, traderName="";
        int check = 0;
        Scanner inputString= new Scanner(System.in);
        System.out.println("Please enter your id and password");
        System.out.println("ID: ");
        id = inputString.nextLine();
        System.out.println("Password: ");
        password = inputString.nextLine();

        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
        if(id.contains("T")) check = 1;
        else if(id.contains("C")) check = 2;

        boolean log = false;
        while ((line = reader.readLine()) != null) {
            String[] cols = line.split(" / ");
            if (cols[0].compareTo(id) == 0){
                if(check == 1){
                    traderName = cols[2];
                }
                log = true;
                break;
            }
        }

        if(check == 1 && traderName.compareTo("") != 0 && log == true){
            Trader t = new Trader(id,password,traderName);
            int exit = -1;
            while(exit != 0) {
                System.out.println("\nWELCOME\n");
                System.out.println("1-Add Product\n");
                System.out.println("2-Remove Product\n");
                System.out.println("3-Edit Product\n");
                System.out.println("4-View Orders\n");
                System.out.println("5-Meet Orders\n");
                System.out.println("6-Cancel Orders\n");
                System.out.println("0-Exit\n");
                System.out.println("Please enter your choice: ");
                Scanner input= new Scanner(System.in);
                try {
                    exit = input.nextInt();
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("Please try with a number!!");
                }
                while(exit > 7 || exit < 0){
                    System.out.println("Invalid choice!");
                    System.out.println("Please enter valid choice:");
                    try{
                        exit = input.nextInt();
                    }
                    catch(InputMismatchException e){
                        input.next();
                        System.out.println("Please try with a number!!");
                        break;
                    }
                }
                switch (exit){
                    case 0:
                        return;
                    case 1:
                        t.addProduct(null);
                        break;
                    case 2:
                        t.removeProduct(null);
                        break;
                    case 3:
                        t.editProduct(null);
                        break;
                    case 4:
                        t.viewOrders();
                        break;
                    case 5:
                        t.meetOrder("");
                        break;
                    case 6:
                        t.cancelOrder("");
                        break;
                    default:
                        System.out.println("Please enter valid choice");
                        break;
                }
            }
        }
        else if(check == 2 && log == true){
            Customer c = new Customer(id,password);
            inputString= new Scanner(System.in);
            int exit = -1;
            while(exit != 0) {
                System.out.println("WELCOME\n");
                System.out.println("1-View A Trader's Products\n");
                System.out.println("2-Search & Query\n");
                System.out.println("0-Exit\n");
                System.out.println("Please enter your choice: ");
                Scanner input= new Scanner(System.in);
                try {
                    exit = input.nextInt();
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("Please try with a number!!");
                }
                while(exit > 7 || exit < 0){
                    System.out.println("Invalid choice!");
                    System.out.println("Please enter valid choice:");
                    try{
                        exit = input.nextInt();
                    }
                    catch(InputMismatchException e){
                        input.next();
                        System.out.println("Please try with a number!!");
                        break;
                    }
                }
                switch (exit){
                    case 0:
                        return;
                    case 1:
                        System.out.println("Please enter trader name to view trader's products: ");
                        String traderN = inputString.nextLine();
                        c.seeProductsOfTrader(traderN);
                        break;
                    case 2:
                        c.searchQuery();
                        break;

                    default:
                        System.out.println("Please enter valid choice");
                        break;
                }
            }
        }
        else{
            System.out.println("Unsuccessful login\n");
            return;
        }
    }

    public static void driver() throws IOException {
    	File file = new File("e-commerce-samples.csv");
        boolean exists = file.exists();
        if(!exists){
        	System.out.println("Please put e-commerce-samples.csv in the directory\n");
        	return;
        }
        Trader t = new Trader("T9999999","999999","Alisha");

        System.out.println("\nView orders:");
        t.viewOrders();

        System.out.println("\nCancel order:");
        t.cancelOrder("SRTEH2FF9KEDEFGF");

        System.out.println("\nMeet nonexistent order:");
        t.meetOrder("SRTEH2FF9KEDEFGF");

        System.out.println("\nAdd product:");
        t.addProduct(new Product("1afafqw123","Masa","Mavi masa",100, 50, "Alisha"));

        System.out.println("\nRemove product:");
        t.removeProduct(new Product("1afafqw123","Masa","Mavi masa",100, 50, "Alisha"));

        System.out.println("\nAdd product:");
        t.addProduct(new Product("1afafqw123","Masa","Mavi masa",100, 50, "Alisha"));

        System.out.println("\nEdit product:");
        t.editProduct(new Product("1afafqw123","Masa","Mavi masa",100, 50, "Alisha"));

        Customer ca = new Customer("a","b");

        System.out.println("Sort products that contains given text in their name or description\n");
        ca.sortByName("Alisha",1);

        System.out.println("Filter sorted products that contains given text in their name or description by price\n");
        ca.filterByPrice(ca.sortByName("Alisha",0),10,1000);

        System.out.println("Give order");
        ca.giveOrder("AW Bellies");
        System.out.println("\n");

        System.out.println("View products of a trader");
        ca.seeProductsOfTrader("Ladela");
        System.out.println("\n");

    }
    public static void main(String [] args) throws IOException {
        users();
        putProducts();
        driver();
        menu();

    }
}