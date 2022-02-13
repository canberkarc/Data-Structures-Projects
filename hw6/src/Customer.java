import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Customer implements Person{
    /**
     * Hash table for id password
     */
    private Hashtable<String,String> idPass = new Hashtable<String,String>();
    /**
     * id
     */
    private String id;

    /**
     * Constructor
     * @param id
     * @param password
     */
    public Customer(String id, String password) {
        this.id = id;
        this.idPass.put(id,password);
    }

    /**
     * Set id and password
     * @param id
     * @param password
     */
    @Override
    public void setIdPass(String id, String password) {
        this.idPass.put(id,password);
    }

    /**
     * getter  of id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Heapify method accorrding to names
     * @param list product list
     * @param n
     * @param i
     */
    private static void heapifyName(ArrayList<Product> list, int n, int i)
    {
        int small = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && list.get(l).getName().compareTo(list.get(small).getName()) < 0)
            small = l;

        if (r < n && list.get(r).getName().compareTo(list.get(small).getName()) < 0)
            small = r;

        if (small != i) {
            Product temp = list.get(i);
            list.set(i,list.get(small));
            list.set(small,temp);

            heapifyName(list, n, small);
        }
    }

    /**
     * Heapsort method
     * @param list product list
     * @param nameOrPrice heapsort according to price or name
     */
    private static void heapSort(ArrayList<Product> products, int nameOrPrice)
    {
        int n = products.size();
        for (int i = n / 2 - 1; i >= 0; i--){
            heapifyName(products, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {

            Product temp = products.get(0);
            products.set(0,products.get(i));
            products.set(i,temp);

            heapifyName(products, i, 0);
        }
    }

    /**
     * Method to sort by name
     * @param productName product name
     * @throws IOException
     */
    public ArrayList<Product> sortByName(String productName, int val) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
        String line;
        StringBuilder str = new StringBuilder();
        ArrayList<Product> products = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if(line.compareTo("&&&") == 0) continue;
            String[] cols = line.split(" /// ");
            if (cols[1].contains(productName) || cols[5].contains(productName)){
                products.add(new Product(cols[0], cols[1], cols[5], Integer.parseInt(cols[3]), Integer.parseInt(cols[4]), cols[6]));
            }
        }
        reader.close();

        if(products.size() == 0){
            System.out.println("There is no such product\n");
            return null;
        }

        heapSort(products,0);

        if(val == 1) {
            ListIterator<Product> iter = products.listIterator();
            while (iter.hasNext()) {
                Product p = iter.next();
                str.append("Product Id: ").append(p.getId()).append("\nProduct Name: ").append(p.getName()).append("\nProduct Price: ").append(p.getPrice()).append("\nDiscounted Price: ").append(p.getDiscounted_price()).append("\nDescription: ").append(p.getDescription()).append("\n\n");
                System.out.println(str);
                str = new StringBuilder();
            }
        }
        return products;
    }

    /**
     * Filters products by prices
     * @param lowerLimit
     * @param upperLimit
     * @throws IOException
     */
    public void filterByPrice (ArrayList<Product> product, int lowerLimit, int upperLimit)throws IOException {
        LinkedList<Product> filtered = new LinkedList<>();
        String str = "";
        if(lowerLimit >= 0 && upperLimit > lowerLimit && product.size() > 0){
            for (int i=0; i<product.size(); i++) {
                if (product.get(i).getPrice() > lowerLimit && product.get(i).getPrice() < upperLimit){
                    filtered.add(new Product(product.get(i).getId(), product.get(i).getName(), product.get(i).getDescription(), product.get(i).getPrice(), product.get(i).getDiscounted_price(), product.get(i).getTrader()));
                }
            }

            if(filtered.size() == 0){
                System.out.println("There is no such product\n");
                return;
            }

            /* Bubble Sort */
            Product pr;
            for (int i=0; i<filtered.size()-1; i++)
            {
                for (int j=0; j < filtered.size()-i-1; j++) {
                    if (filtered.get(j).getPrice() < filtered.get(j+1).getPrice() )
                    {
                        pr = filtered.get(j);
                        filtered.set(j,filtered.get(j+1) );
                        filtered.set(j+1, pr);
                    }
                }
            }


            ListIterator<Product> iter = filtered.listIterator();
            while(iter.hasNext()) {
                Product p = iter.next();
                str = str + "Product Id: " + p.getId() + "\nProduct Name: " + p.getName() + "\nProduct Price: " + p.getPrice() +
                        "\nDiscounted Price: " + p.getDiscounted_price() + "\nDescription: " + p.getDescription() + "\n\n";
                System.out.println(str);
                str = "";
            }
        }

        else if(lowerLimit == -1  && upperLimit > 0){
            for (int i=0; i<product.size(); i++) {
                if (product.get(i).getPrice() < upperLimit){
                    filtered.add(new Product(product.get(i).getId(), product.get(i).getName(), product.get(i).getDescription(), product.get(i).getPrice(), product.get(i).getDiscounted_price(), product.get(i).getTrader()));
                }
            }

            if(filtered.size() == 0){
                System.out.println("There is no such product\n");
                return;
            }

            /* Bubble Sort */
            Product pr;
            for (int i=0; i<filtered.size()-1; i++)
            {
                for (int j=0; j < filtered.size()-i-1; j++) {
                    if (filtered.get(j).getPrice() < filtered.get(j+1).getPrice() )
                    {
                        pr = filtered.get(j);
                        filtered.set(j,filtered.get(j+1) );
                        filtered.set(j+1, pr);
                    }
                }
            }


            ListIterator<Product> iter = filtered.listIterator();
            while(iter.hasNext()) {
                Product p = iter.next();
                str = str + "Product Id: " + p.getId() + "\nProduct Name: " + p.getName() + "\nProduct Price: " + p.getPrice() +
                        "\nDiscounted Price: " + p.getDiscounted_price() + "\nDescription: " + p.getDescription() + "\n\n";
                System.out.println(str);
                str = "";
            }
        }
        else if(lowerLimit > 0 && upperLimit == -1){
            for (int i=0; i<product.size(); i++) {
                if (product.get(i).getPrice() > lowerLimit){
                    filtered.add(new Product(product.get(i).getId(), product.get(i).getName(), product.get(i).getDescription(), product.get(i).getPrice(), product.get(i).getDiscounted_price(), product.get(i).getTrader()));
                }
            }

            if(filtered.size() == 0){
                System.out.println("There is no such product\n");
                return;
            }

            /* Bubble Sort */
            Product pr;
            for (int i=0; i<filtered.size()-1; i++)
            {
                for (int j=0; j < filtered.size()-i-1; j++) {
                    if (filtered.get(j).getPrice() < filtered.get(j+1).getPrice() )
                    {
                        pr = filtered.get(j);
                        filtered.set(j,filtered.get(j+1) );
                        filtered.set(j+1, pr);
                    }
                }
            }


            ListIterator<Product> iter = filtered.listIterator();
            while(iter.hasNext()) {
                Product p = iter.next();
                str = str + "Product Id: " + p.getId() + "\nProduct Name: " + p.getName() + "\nProduct Price: " + p.getPrice() +
                        "\nDiscounted Price: " + p.getDiscounted_price() + "\nDescription: " + p.getDescription() + "\n\n";
                System.out.println(str);
                str = "";
            }
        }

    }

    /**
     * Search & query
     * @throws IOException
     */
    public void searchQuery() throws IOException {
        Scanner inputString= new Scanner(System.in);
        Scanner input= new Scanner(System.in);
        System.out.println("Please enter product name: ");
        String pName = inputString.nextLine();

        System.out.println("How do you want to filter search results?\n");
        System.out.println("1-By name");
        System.out.println("Please enter your choice:");
        Scanner scan = new Scanner(System.in);
        ArrayList<Product> products;
        int num = scan.nextInt();
        if(num == 1) {
            products = sortByName(pName,1);
        }
        else{
            return;
        }
        System.out.println("Do you want to filter search results? Please write yes or no\n");
        String answer = inputString.nextLine();
        if(answer.compareTo("yes") == 0){
            System.out.println("Please make choice");
            System.out.println("1-Filter by price");
            int filter = input.nextInt();
            if(filter == 1){
                System.out.println("Please make choice");
                System.out.println("1-Filter with upper limit and lower limit");
                System.out.println("2-Filter with upper limit");
                System.out.println("3-Filter with lower limit");
                int filter2 = input.nextInt();
                switch (filter2){
                    case 1:
                        System.out.println("Please enter upper limit: ");
                        int upper = input.nextInt();
                        System.out.println("Please enter lower limit: ");
                        int lower = input.nextInt();
                        filterByPrice(products,lower,upper);
                        break;
                    case 2:
                        System.out.println("Please enter upper limit: ");
                        upper = input.nextInt();
                        filterByPrice(products,-1,upper);
                        break;
                    case 3:
                        System.out.println("Please enter lower limit: ");
                        lower = input.nextInt();
                        filterByPrice(products,lower,-1);
                        break;
                    default:
                        System.out.println("Please enter a valid choice");
                        break;
                }
            }
        }

        System.out.println("Do you want to give order? Please write yes or no\n");
        answer = inputString.nextLine();
        if(answer.compareTo("yes") == 0){
            System.out.println("Please enter product name: ");
            String prName = inputString.nextLine();
            this.giveOrder(prName);
        }
    }

    /**
     * Method to give order
     * @param productName product to buy
     * @throws IOException
     */
    public void giveOrder(String productName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
        int count = 0;
        String line, str, traderName = "null", productId = "null";
        while ((line = reader.readLine()) != null) {
            if(line.compareTo("&&&") == 0) continue;
            String[] cols = line.split(" /// ");
            if (cols[1].compareTo(productName) == 0 && count > 0){
                traderName = cols[6];
                productId = cols[0];
                break;
            }
            count++;
        }
        reader.close();

        if(traderName.compareTo("null") == 0){
            System.out.format("Product couldn't ordered\n");
            return;
        }

        FileWriter orders = new FileWriter("orders.txt");
        str = "";
        str = str + productId + " / " + this.getId() + " / " + traderName + "\n";
        System.out.println("Below order is given:");
        System.out.format("Product Id: %s\nTrader: %s\n",productId,traderName);
        orders.write(str);
        orders.close();
    }

    /**
     * Views products of trader
     * @param traderName trader name
     * @throws IOException
     */
    public void seeProductsOfTrader(String traderName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
        int count = 0;
        String line;
        StringBuilder str = new StringBuilder();
        ArrayList<Product> products = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if(line.compareTo("&&&") == 0) continue;
            String[] cols = line.split(" /// ");
            if (cols[6].compareTo(traderName) == 0 && count > 0){
                products.add(new Product(cols[0], cols[1], cols[5], Integer.parseInt(cols[3]), Integer.parseInt(cols[4]), traderName));
            }
            count++;
        }
        reader.close();

        if(products.size() == 0){
            System.out.println("This trader has no product\n");
            return;
        }

        /* Insertion Sort */
        for (int j = 1; j < products.size(); j++) {
            Product p = products.get(j);
            int i = j;
            while ((i > 0) && ((products.get(i-1).getName().compareTo(p.getName())) < 0)) {
                products.set(i, products.get(i-1));
                i--;
            }
            products.set(i, p);
        }

        for(int i=0; i<products.size(); i++) {
            str.append("Product Id: ").append(products.get(i).getId()).append("\nProduct Name: ").append(products.get(i).getName()).append("\nProduct Price: ").append(products.get(i).getPrice()).append("\nDiscounted Price: ").append(products.get(i).getDiscounted_price()).append("\nDescription: ").append(products.get(i).getDescription()).append("\n\n");
            System.out.println(str);
            str = new StringBuilder();
        }

    }


}
