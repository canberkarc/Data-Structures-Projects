public class Product<E extends Comparable<E>> {
    /**
     * Product name
     */
    private String name;
    /**
     * Product description
     */
    private String description;
    /**
     * Product price
     */
    private int price;
    /**
     * Product discounted price
     */
    private int discounted_price;
    /**
     * Product id
     */
    private String id;
    /**
     * Trader of product
     */
    private String trader;
    /**
     * Category tree of product
     */
    private String category_tree;

    /**
     * Setter of id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter of name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter of description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter of price
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Setter of discounter price
     * @param discounted_price
     */
    public void setDiscounted_price(int discounted_price) {
        this.discounted_price = discounted_price;
    }

    /**
     * Id getter
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Description getter
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Price getter
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * Discounted price getter
     * @return
     */
    public int getDiscounted_price() {
        return discounted_price;
    }

    /**
     * Name getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * No parameter constructor
     */
    public Product(){
        this.trader = "";
        this.id = "No id";
        this.name = "No name";
        this.description = "No description";
        this.price = 0;
        this.discounted_price = 0;
        this.category_tree = "No category";
    }

    /**
     * Parameter constructor
     * @param name
     */
    public Product(String name){
        this.trader = "";
        this.id = "No id";
        this.name = name;
        this.description = "No description";
        this.price = 0;
        this.discounted_price = 0;
        this.category_tree = "No category";
    }

    /**
     * Parameter constructor
     * @param id
     * @param name
     * @param description
     * @param price
     * @param discounted_price
     * @param trader
     */
    public Product(String id, String name, String description, int price, int discounted_price, String trader) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discounted_price = discounted_price;
        this.trader = trader;
        this.category_tree = "No category";
    }

    /**
     * Parameter constructor
     * @param id
     * @param name
     * @param description
     * @param price
     * @param discounted_price
     * @param trader
     * @param category_tree
     */
    public Product(String id, String name, String description, int price, int discounted_price, String trader, String category_tree) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discounted_price = discounted_price;
        this.trader = trader;
        this.category_tree = category_tree;
    }

    /**
     * Category tree getter
     * @return
     */
    public String getCategory_tree() {
        return category_tree;
    }

    /**
     * Category tree setter
     * @param category_tree
     */
    public void setCategory_tree(String category_tree) {
        this.category_tree = category_tree;
    }

    /**
     * Trader getter
     * @return
     */
    public String getTrader() {
        return trader;
    }

    /**
     * Trader setter
     * @param trader
     */
    public void setTrader(String trader) {
        this.trader = trader;
    }

}
