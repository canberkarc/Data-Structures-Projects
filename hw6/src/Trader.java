import java.io.*;
import java.util.*;

public class Trader implements Person{
    /**
     * Hash table for id and password
     */
    private Hashtable<String,String> idPass = new Hashtable<String,String>();
    /**
     * trader id
     */
    private String id;
    /**
     * trader name
     */
    private String name;

    /**
     * Constructor
     * @param id
     * @param password
     * @param name
     */
    public Trader(String id, String password, String name) {
        this.id = id;
        this.name = name;
        this.idPass.put(id,password);
    }

    /**
     * Name getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Id getter
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Id password setter
     * @param id
     * @param password
     */
    @Override
    public void setIdPass(String id, String password) {
        this.idPass.put(id,password);
    }

    /**
     * Adds product to products
     * @throws IOException
     */
    public void addProduct(Product a) throws IOException {
        String id = "", pName = "", tree = "", price = "" , dPrice = "", desc = "", str = "";
        if(a == null) {
            Scanner inputString = new Scanner(System.in);
            System.out.println("Please enter product informations respectively");
            System.out.println("Product Id: ");
            id = inputString.nextLine();
            System.out.println("Product Name: ");
            pName = inputString.nextLine();
            System.out.println("Product Category Tree: ");
            tree = inputString.nextLine();
            System.out.println("Price: ");
            price = inputString.nextLine();
            System.out.println("Discounted Price: ");
            dPrice = inputString.nextLine();
            System.out.println("Description: ");
            desc = inputString.nextLine();

            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            int count = 0;
            String line = "";
            str = "";
            ArrayList<Product> products = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) continue;
                String[] cols = line.split(" /// ");
                if (cols[0].compareTo(id) == 0 && count > 0) {
                    System.out.println("Same product cannot be added\n");
                    return;
                }
                count++;
            }
            reader.close();
            FileWriter productsFile = new FileWriter("products.txt", true);
            str = id + " /// " + pName + " /// " + tree + " /// " + price + " /// " + dPrice + " /// " + desc + " /// "
                    + this.name + "\n";
            productsFile.write(str);
            productsFile.write("&&&\n");
            productsFile.close();
            System.out.println("Product is added\n");
            System.out.format("Product Id: %s\nProduct Name: %s\nPrice: %d\nDiscounted Price: %d\nDescription: %s\n",id,pName,Integer.parseInt(price),Integer.parseInt(dPrice),desc);
        }
        else{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            int count = 0;
            String line = "";
            str = "";
            ArrayList<Product> products = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) continue;
                String[] cols = line.split(" /// ");
                if (cols[0].compareTo(a.getId()) == 0 && count > 0) {
                    System.out.println("Same product cannot be added\n");
                    return;
                }
                count++;
            }
            reader.close();
            FileWriter productsFile = new FileWriter("products.txt", true);
            str = a.getId() + " /// " + a.getName() + " /// " + a.getCategory_tree() + " /// " + a.getPrice() + " /// " + a.getDiscounted_price() + " /// " + a.getDescription() + " /// "
                    + this.name + "\n";
            productsFile.write(str);
            productsFile.write("&&&\n");
            productsFile.close();
            System.out.println("Product is added\n");
            System.out.format("Product Id: %s\nProduct Name: %s\nPrice: %d\nDiscounted Price: %d\nDescription: %s\n",a.getId(),a.getName(),a.getPrice(),a.getDiscounted_price(),a.getDescription());

        }
      }

    /**
     * Removes product from products
     * @throws IOException
     */
    public void removeProduct(Product a) throws IOException{
        int count = 0, check = 0;
        String id, str = "", trader = "", line = "";
        if(a == null) {
            Scanner inputString = new Scanner(System.in);
            System.out.println("Please enter product id");
            System.out.println("Product Id: ");
            id = inputString.nextLine();

            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));

            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) continue;
                String[] cols = line.split(" /// ");

                if (cols[0].compareTo(id) == 0 && count > 0) {
                    check = count;
                    trader = cols[6];
                    break;
                }
                count++;
            }
            reader.close();


            if (check == 0) {
                System.out.println("Product couldn't find and deleted.\n");
                return;
            }


            if (check != 0 && this.getName().compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }
        }
        else{
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));

            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) continue;
                String[] cols = line.split(" /// ");

                if (cols[0].compareTo(a.getId()) == 0 && count > 0) {
                    check = count;
                    trader = cols[6];
                    break;
                }
                count++;
            }
            reader.close();


            if (check == 0) {
                System.out.println("Product couldn't find and deleted.\n");
                return;
            }


            if (check != 0 && this.getName().compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
        count = 0;
        FileWriter fileW = new FileWriter("file1.txt");

        String removed = "";
        while((line = reader.readLine()) != null){
            if(line.compareTo("&&&") == 0){
                continue;
            }
            if(count == check){
                removed = line;
                line = reader.readLine();
                count++;
                continue;
            }
            String[] cols = line.split(" /// ");

            str = cols[0] + " /// " + cols[1] + " /// " + cols[2] + " /// " + cols[3] + " /// " + cols[4] + " /// " + cols[5] + " /// "
                    + cols[6] + "\n";
            fileW.write(str);
            fileW.write("&&&\n");
            str = "";
            count++;
        }
        fileW.close();

        File productsFile = new File("products.txt");
        File file = new File("file1.txt");
        productsFile.delete();
        file.renameTo(productsFile);

        reader.close();
        System.out.println("Below product is removed\n");
        String[] cols = removed.split(" /// ");
        System.out.format("Product Id: %s\nProduct Name: %s\nPrice: %d\nDiscounted Price: %d\nDescription: %s\n",cols[0],cols[1],Integer.parseInt(cols[3]),Integer.parseInt(cols[4]),cols[5]);
    }

    /**
     * Edits product in products
     * @throws IOException
     */
    public void editProduct(Product a) throws IOException {
        String id = "", pName, str = "", tree, price, dPrice, desc, trader = "";
        int count = 1, check = 0;
        boolean found = false;
        String line = "";
        Scanner inputString = new Scanner(System.in);
        if(a == null) {

            System.out.println("Please enter product id");
            System.out.println("Product Id: ");
            id = inputString.nextLine();


            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));

            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) continue;
                String[] cols = line.split(" /// ");

                if (cols[0].compareTo(id) == 0 && count > 0) {
                    check = count;
                    trader = cols[6];
                    found = true;
                    break;
                }
                count++;
            }
            reader.close();
            if (found == false){
                System.out.println("Product couldn't find and deleted.\n");
                return;
            }

            if(found != false && this.name.compareTo(trader) != 0){
                System.out.format("This product belongs to %s\n",trader);
                return;
            }


            System.out.println("Please enter new information of product respectively");
            System.out.println("Product Name: ");
            pName = inputString.nextLine();
            System.out.println("Product Category Tree: ");
            tree = inputString.nextLine();
            System.out.println("Price: ");
            price = inputString.nextLine();
            System.out.println("Discounted Price: ");
            dPrice = inputString.nextLine();
            System.out.println("Description: ");
            desc = inputString.nextLine();

            reader = new BufferedReader(new FileReader("products.txt"));
            boolean done = true;
            count = 1;
            FileWriter fileW = new FileWriter("file1.txt");

            while((line = reader.readLine()) != null){
                if(line.compareTo("&&&") == 0){
                    continue;
                }
                if(count == check){
                    line = reader.readLine();
                    str = id + " /// " + pName + " /// " + tree + " /// " + price + " /// " + dPrice + " /// " + desc + " /// "
                            + this.name + "\n";
                    fileW.write(str);
                    fileW.write("&&&\n");
                    count++;
                    str = "";
                    continue;
                }
                String[] cols = line.split(" /// ");


                str = cols[0] + " /// " + cols[1] + " /// " + cols[2] + " /// " + cols[3] + " /// " + cols[4] + " /// " + cols[5] + " /// "
                        + cols[6] + "\n";
                fileW.write(str);
                fileW.write("&&&\n");
                str = "";
                count++;
            }
            fileW.close();

            File productsFile = new File("products.txt");
            File file = new File("file1.txt");
            productsFile.delete();
            file.renameTo(productsFile);

            reader.close();
        }
        else {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));

            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) continue;
                String[] cols = line.split(" /// ");

                if (cols[0].compareTo(a.getId()) == 0 && count > 0) {
                    check = count;
                    trader = cols[6];
                    found = true;
                    break;
                }
                count++;
            }
            reader.close();


            if (found == false) {
                System.out.println("Product couldn't find and deleted.\n");
                return;
            }

            if (found != false && this.name.compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }


            pName = "Edited";
            tree = "Edited";
            price = "5";
            dPrice = "1";
            desc = "Edited";

            reader = new BufferedReader(new FileReader("products.txt"));
            boolean done = true;
            count = 1;
            FileWriter fileW = new FileWriter("file1.txt");

            while ((line = reader.readLine()) != null) {
                if (line.compareTo("&&&") == 0) {
                    continue;
                }
                if (count == check) {
                    line = reader.readLine();
                    str = id + " /// " + pName + " /// " + tree + " /// " + price + " /// " + dPrice + " /// " + desc + " /// "
                            + this.name + "\n";
                    fileW.write(str);
                    fileW.write("&&&\n");
                    count++;
                    str = "";
                    continue;
                }
                String[] cols = line.split(" /// ");


                str = cols[0] + " /// " + cols[1] + " /// " + cols[2] + " /// " + cols[3] + " /// " + cols[4] + " /// " + cols[5] + " /// "
                        + cols[6] + "\n";
                fileW.write(str);
                fileW.write("&&&\n");
                str = "";
                count++;
            }
            fileW.close();

            File productsFile = new File("products.txt");
            File file = new File("file1.txt");
            productsFile.delete();
            file.renameTo(productsFile);

            reader.close();
            System.out.println("Product edited.");
           }
    }

    /**
     * Shows orders of products of trader
     * @throws IOException
     */
    public void viewOrders() throws IOException {
        File file = new File("orders.txt");
        boolean exists = file.exists();
        if(!exists) file.createNewFile();
        BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
        String line = "", str = "";
        boolean check = false;
        Queue<String> orders = new LinkedList<>();
        while((line = reader.readLine()) != null){
            String[] cols = line.split(" / ");
            if(cols[2].compareTo(this.getName()) == 0) {
                if (cols.length != 4)
                    str = "Product Id: " + cols[0] + "\n" + "Customer Id: " + cols[1] + "\n" + "Trader: " + cols[2] + "\n" + "State: Not met\n\n";
                else
                    str = "Product Id: " + cols[0] + "\n" + "Customer Id: " + cols[1] + "\n" + "Trader: " + cols[2] + "\n" + cols[3] + "\n\n";
                orders.add(str);
                str = "";
                check = true;
            }
        }

        if(check == false){
            System.out.println("There is no order for your products\n");
            return;
        }

        for(String ord : orders){
            System.out.println(ord);
        }
    }

    /**
     * Cancels customer's order
     * @throws IOException
     */
    public void cancelOrder(String id) throws IOException {
        if(id.compareTo("") == 0) {
            System.out.println("Please enter id of product to cancel order: ");
            Scanner inputString = new Scanner(System.in);
            String orderId = inputString.nextLine(), trader = "", str = "", line = "";

            int count = 1, check = 0;
            boolean found = false;
            BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));

            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(" / ");
                if (cols[0].compareTo(orderId) == 0) {
                    check = count;
                    trader = cols[2];
                    found = true;
                    break;
                }
                count++;
            }
            reader.close();

            if (found == false) {
                System.out.println("Order couldn't find and cancelled.\n");
                return;
            }

            if (found != false && this.getName().compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }

            reader = new BufferedReader(new FileReader("orders.txt"));
            boolean done = true;
            count = 1;
            FileWriter fileW = new FileWriter("file1.txt");

            String cancelled = "";
            while ((line = reader.readLine()) != null) {
                if (count == check) {
                    if (count == 1) cancelled = line;
                    line = reader.readLine();
                    count++;
                    continue;
                }
                String[] cols = line.split(" / ");

                if (cols.length == 4)
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + " / " + cols[3] + "\n";
                else
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + "State: Not met" + "\n";
                fileW.write(str);
                str = "";
                count++;
            }
            fileW.close();
            reader.close();

            File ordersFile = new File("orders.txt");
            File file = new File("file1.txt");
            ordersFile.delete();
            file.renameTo(ordersFile);

            System.out.println("Below order is cancelled\n");
            String[] cols = cancelled.split(" / ");
            System.out.format("Product Id: %s\nCustomer Id: %s\nTrader: %s\n\n", cols[0], cols[1], cols[2]);
        }
        else{
            String orderId = id, trader = "", str = "", line = "";

            int count = 1, check = 0;
            boolean found = false;
            BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));

            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(" / ");
                if (cols[0].compareTo(orderId) == 0) {
                    check = count;
                    trader = cols[2];
                    found = true;
                    break;
                }
                count++;
            }
            reader.close();

            if (found == false) {
                System.out.println("Order couldn't find and cancelled.\n");
                return;
            }

            if (found != false && this.getName().compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }

            reader = new BufferedReader(new FileReader("orders.txt"));
            boolean done = true;
            count = 1;
            FileWriter fileW = new FileWriter("file1.txt");

            String cancelled = "";
            while ((line = reader.readLine()) != null) {
                if (count == check) {
                    if (count == 1) cancelled = line;
                    line = reader.readLine();
                    count++;
                    continue;
                }
                String[] cols = line.split(" / ");

                if (cols.length == 4)
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + " / " + cols[3] + "\n";
                else
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + "State: Not met" + "\n";
                fileW.write(str);
                str = "";
                count++;
            }
            fileW.close();
            reader.close();

            File ordersFile = new File("orders.txt");
            File file = new File("file1.txt");
            ordersFile.delete();
            file.renameTo(ordersFile);

            System.out.println("Below order is cancelled\n");
            String[] cols = cancelled.split(" / ");
            System.out.format("Product Id: %s\nCustomer Id: %s\nTrader: %s\n\n", cols[0], cols[1], cols[2]);

        }
    }

    /**
     * Meets customers order
     * @throws IOException
     */
    public void meetOrder(String id) throws IOException {
        if(id.compareTo("") == 0) {
            System.out.println("Please enter id of product to meet order: ");
            Scanner inputString = new Scanner(System.in);
            String orderId = inputString.nextLine(), trader = "", str = "";

            int count = 1, check = 0;
            boolean found = false;
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));

            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(" / ");
                if (cols[0].compareTo(orderId) == 0) {
                    check = count;
                    trader = cols[2];
                    found = true;
                    break;
                }
                count++;
            }
            reader.close();

            if (found == false) {
                System.out.println("Order couldn't find and met.\n");
                return;
            }

            if (found != false && this.getName().compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }

            reader = new BufferedReader(new FileReader("orders.txt"));
            boolean done = true;
            count = 1;
            FileWriter fileW = new FileWriter("file1.txt");

            String cancelled = "";
            while ((line = reader.readLine()) != null) {
                if (count == check) {
                    if (count == 1) cancelled = line;
                    line = reader.readLine();
                    count++;
                    continue;
                }
                String[] cols = line.split(" / ");

                if (cols.length > 4)
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + " / " + cols[3] + "\n";
                else
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + " / " + "State: Not met" + "\n";
                fileW.write(str);
                str = "";
                count++;
            }
            fileW.close();
            reader.close();

            File ordersFile = new File("orders.txt");
            File file = new File("file1.txt");
            ordersFile.delete();
            file.renameTo(ordersFile);

            String[] cols = cancelled.split(" / ");
            System.out.println("Below order is met\n");
            System.out.format("Product Id: %s\nCustomer Id: %s\nTrader: %s\n\n", cols[0], cols[1], cols[2]);
        }
        else{
            String orderId = id, trader = "", str = "";

            int count = 1, check = 0;
            boolean found = false;
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));

            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(" / ");
                if (cols[0].compareTo(orderId) == 0) {
                    check = count;
                    trader = cols[2];
                    found = true;
                    break;
                }
                count++;
            }
            reader.close();

            if (found == false) {
                System.out.println("Order couldn't find and met.\n");
                return;
            }

            if (found != false && this.getName().compareTo(trader) != 0) {
                System.out.format("This product belongs to %s\n", trader);
                return;
            }

            reader = new BufferedReader(new FileReader("orders.txt"));
            boolean done = true;
            count = 1;
            FileWriter fileW = new FileWriter("file1.txt");

            String cancelled = "";
            while ((line = reader.readLine()) != null) {
                if (count == check) {
                    if (count == 1) cancelled = line;
                    line = reader.readLine();
                    count++;
                    continue;
                }
                String[] cols = line.split(" / ");

                if (cols.length > 4)
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + " / " + cols[3] + "\n";
                else
                    str = cols[0] + " / " + cols[1] + " / " + cols[2] + " / " + "State: Not met" + "\n";
                fileW.write(str);
                str = "";
                count++;
            }
            fileW.close();
            reader.close();

            File ordersFile = new File("orders.txt");
            File file = new File("file1.txt");
            ordersFile.delete();
            file.renameTo(ordersFile);

            String[] cols = cancelled.split(" / ");
            System.out.println("Below order is met\n");
            System.out.format("Product Id: %s\nCustomer Id: %s\nTrader: %s\n\n", cols[0], cols[1], cols[2]);
        }
    }
}