//Daniel Yu 501120369
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    private Map<String, Product> products = new TreeMap<String, Product>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();

    private Map<String, Integer> stats = new TreeMap<String, Integer>(); // map for stats
    private Map<String, Rating> ratings = new TreeMap<String, Rating>(); // map for rating of the product id, and another map of ratings

    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
    	// NOTE: do not modify or add to these objects!! - the TAs will use for testing
    	// If you do the class Shoes bonus, you may add shoe products

      products = readProducts(); // call method that read products from file
      getStatsRatings(); // call method that sets the stats and rating
      
    	// Create some products. Notice how generateProductId() method is used
    	/*products.add(new Product("Acer Laptop", generateProductId(), 989.0, 99, Product.Category.COMPUTERS));
    	products.add(new Product("Apex Desk", generateProductId(), 1378.0, 12, Product.Category.FURNITURE));
    	products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna Make You Learn", "T. McInerney", 2000));
    	products.add(new Product("DadBod Jeans", generateProductId(), 24.0, 50, Product.Category.CLOTHING));
    	products.add(new Product("Polo High Socks", generateProductId(), 5.0, 199, Product.Category.CLOTHING));
    	products.add(new Product("Tightie Whities", generateProductId(), 15.0, 99, Product.Category.CLOTHING));
    	products.add(new Book("Book", generateProductId(), 35.0, 4, 2, "How to Fool Your Prof", "D. Umbast", 2001));
    	products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Escape from Prison", "A. Fugitive", 2002));
    	products.add(new Book("Book", generateProductId(), 44.0, 14, 12, "Ahm Gonna Make You Learn More", "T. McInerney", 2003));
    	products.add(new Product("Rock Hammer", generateProductId(), 10.0, 22, Product.Category.GENERAL));
    	products.add(new Shoes("Nike", generateProductId(), 100, new int[]{6,7,8,9,10}, new int[]{1,2,3,4,5})); // shoe */

    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
    }
    
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public void printAllProducts()
    {
    	for (String s : products.keySet())
    		products.get(s).print();
    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
    	for (int i = 0; i < products.size(); i++){ // go through all products
        Product p = products.get(i); // check each product
        if (p.getCategory() == Product.Category.BOOKS){ // check if product is book
          p.print(); // print if it is book
        }
      }
    }
    
    // Print all current orders
    public void printAllOrders()
    {
    	for(ProductOrder p : orders)
        p.print();
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
    	for(ProductOrder p : shippedOrders)
        p.print();
    }
    
    // Print all customers
    public void printCustomers()
    {
      for(Customer cust: customers){
        cust.print();
      }
    }
    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */
    public void printOrderHistory(String customerId)
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set errMsg String and return false
    	// see video for an appropriate error message string
    	// ... code here
      boolean cuscheck = false;
    	for(Customer c: customers){ // go through all customers
        if (c.getId().equals(customerId)){ // check if id matches
          cuscheck = true; // add count to show there is matching id
          break; // break out of loop
        }
      }
      if(!cuscheck){ // if no customer matching id
        throw new UnknownCustomerException("Customer not found");
      }
    	// Print current orders of this customer 
    	System.out.println("Current Orders of Customer " + customerId);
    	// enter code here
      for(ProductOrder p : orders){
        if(customerId.equals(p.getCustomer().getId())){
          p.print();
        }
      }
    	
    	// Print shipped orders of this customer 
    	System.out.println("\nShipped Orders of Customer " + customerId);
    	//enter code here
      for(ProductOrder s : shippedOrders){
        if(customerId.equals(s.getCustomer().getId())){
          s.print();
        }
      }
    }
    
    public String orderProduct(String productId, String customerId, String productOptions)
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Customer object
      int procount = 0, cuscount = 0;
      Customer cust = null;
    	for(Customer c: customers){ // go through all customers
        if (c.getId().equals(customerId)){ // check if id matches
          cuscount++; // add count to show there is matching id
          cust = c; // get the customer of that id
          break; // break out of loop
        }
      }
      if(cuscount < 1){ // if no customer matching id
        throw new UnknownCustomerException("Customer not found");
      }

    	// Check to see if product object with productId exists in array list of products
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
      Product prod = null;
    	for(String s: products.keySet()){ // go through all products
        if (products.get(s).getId().equals(productId)){ // get if there is matching id
          procount++; // add count to show there is matching id
          prod = products.get(s); // get product of that id
          break;
        }
      }
      if(procount < 1){ // if no matching product id
        throw new UnknownProductException("Product not found");
      }
    	
    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method vaidOptions()
    	// If options are not valid, set errMsg string and return null;
      Shoes shoe = null;
      Book books = null;
      if (!productOptions.equals("")){ // check if it is called from order method
        if(prod.getCategory() == Product.Category.BOOKS){     
          books = (Book) prod; // cast product to book
          books.validOptions(productOptions); // check if the option is valid

        } else if (prod.getCategory() == Product.Category.SHOES){ // check if its a shoe
          shoe = (Shoes) prod; // cast product to shoe
          shoe.validOptions(productOptions); // check if the option is valid

        } else {
          throw new InvalidCategoryException("Product is not a book or shoe"); // used orderbook or ordershoe method but not book or shoe
        }
      } else {
        if(prod.getCategory() == Product.Category.BOOKS || prod.getCategory() == Product.Category.SHOES){ // check if user used order method but chose book or shoe
          throw new InvalidCategoryException("Not a general product");
        }
      }
      
    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null
      boolean stock = true;
      if (prod.getCategory() == Product.Category.BOOKS) { // check stock for book of its option
        if (books.getStockCount(productOptions) < 1)
          stock = false;
      } else if (prod.getCategory() == Product.Category.SHOES){ // check if its a shoe
        if (shoe.getStockCount(productOptions) < 1){ // check if product of the option has stock
          stock = false; // no stock
        }
      } 
      if (!stock){ // no stock return false and set error message
        throw new OutOfStockException("Out of stock");
      }
        
      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string

      int orderCount = stats.get(prod.getId()) + 1; // add ordercount
      stats.replace(prod.getId(), orderCount);

      if (prod.getCategory() == Product.Category.BOOKS){
        books.reduceStockCount(productOptions); // reduce stock for book
      } else if (prod.getCategory() == Product.Category.SHOES){
        shoe.reduceStockCount(productOptions); // reduce stock for shoe
      } else {
        prod.reduceStockCount(productOptions); // reduce stock for product
      }
      String ordNum = Integer.toString(orderNumber); // change order number to string
      orders.add(new ProductOrder(ordNum, prod, cust, productOptions)); // add new order
      generateOrderNumber(); // generate an order number
    	return ordNum; //return the order number
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public void createCustomer(String name, String address)
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter
    	if (name.equals(null) || name.equals("")){ // check if name is null or ""
        throw new InvalidCustNameException("Invalid name");
      }
      if (address.equals(null) || address.equals("")){ // check if address is null or ""
        throw new InvalidCustAddressException("Invalid address");
      }
    	// Create a Customer object and add to array list
      customers.add(new Customer(generateCustomerId(),name, address)); // add new customer to the arraylist
    }
    
    public ProductOrder shipOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
    	// return a reference to the order
      ProductOrder order = null;
    	for(ProductOrder p : orders){ // go through orders
        if(p.getOrderNumber().equals(orderNumber)){ // check if ordernumbers are equal
          shippedOrders.add(p); //add shipped order
          order = p;
          break; //exit loop
        }
      }
      if (order == null){ // no order numbers are equal
        throw new InvalidOrderNumException("Order number not found");
      }

      orders.remove(order); //remove order
      return order;
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public void cancelOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
      ProductOrder order = null;
    	for(ProductOrder p: orders){ //go through orders
        if(p.getOrderNumber().equals(orderNumber)){ //check if order numbers are equal
          order = p;
        }
      }

      if (order == null) // order not found in list
        throw new InvalidOrderNumException("Order number not found");

        orders.remove(order); // remove order
    }
    
    // Sort products by increasing price
    public void PrintByPrice()
    {
      ArrayList<Product> prods = new ArrayList<Product>(); // make arraylist for products
      Product temp = null; // variable for temperary value
      prods.addAll(products.values()); // add all values from products
      for(int i = 0; i < prods.size(); i++){ // loop though arraylist
        for(int j = 0; j < prods.size()-1; j++){
          if(prods.get(j).getPrice() > prods.get(j+1).getPrice()){ // compare price
            temp = prods.get(j); // swap price if greater
            prods.set(j, prods.get(j+1));
            prods.set(j+1, temp);
          }
        }
      }
      for(Product p: prods){ // print sorted 
        p.print();
      }
    }
    
    
    // Sort products alphabetically by product name
    public void PrintByName()
    {
      ArrayList<Product> prods = new ArrayList<Product>(); // same as printbyprice but with names
      Product temp = null;
      prods.addAll(products.values());
      for(int i = 0; i < prods.size(); i++){
        for(int j = 0; j < prods.size()-1; j++){
          if(prods.get(j).getName().compareTo(prods.get(j+1).getName()) > 0){
            temp = prods.get(j);
            prods.set(j, prods.get(j+1));
            prods.set(j+1, temp);
          }
        }
      }
      for(Product p: prods){
        p.print();
      }
    }
    
        
    // Sort products alphabetically by product name
    public void sortCustomersByName()
    {
  	  ArrayList<Customer>  custSort = new ArrayList<Customer>(); // make new arraylist for sorted custs
  	  for(int i = 0; i < customers.size(); i++){ // go through all cust
        Customer large = customers.get(0); // get customer at index 0
        for(int j = 0; j < customers.size(); j++){ // go through all custs again
          Customer temp = customers.get(j); // get each customer 
          int bigger = temp.compare(large); // compare highest alphabetical name customer with other ones
          if(bigger < 0){ // check if other customer name is larger
            large = temp; // make largest customer variable equal other customer
          }
          
        }
        custSort.add(large); // add largest customer name to new array
        customers.remove(large); // remove largest customer name from old array
        i--; // subtract count because size of old array is smaller from the removal of an element
      }
      customers = custSort; // make old arraylist equal new sorted arraylist
    }

    public void printByYear(String author){
      ArrayList <Book> sortByYear = new ArrayList<Book>(); // make arraylist for sorted books of an author
      int size; // variable for arraylist size
      for (String s : products.keySet()){ // go through products
        if (products.get(s).getCategory() == Product.Category.BOOKS){ // check which products are books
          Book b = (Book) products.get(s); // cast product to book
          if(b.getAuthor().equals(author)){ // check if book is the author
            sortByYear.add(b); // add book of the author to arraylist
          }
        }
      }

      size = sortByYear.size(); // get arraylist size
      if (size == 0){ // size is 0 so author has no books
        throw new InvalidAuthorException("Author not found");

      } else if (size == 1){ // only 1 book so don't need sort
        sortByYear.get(0).print();
      } else {
        for (int i = 0; i < size-1; i++){ // loop through author books twice
          for (int j = 0; j < size-1; j++){
            Book temp = sortByYear.get(j); // get book at j
            Book temp2 = sortByYear.get(j+1); // get book at j+1
            if (temp2.getYear() < temp.getYear()){ // check if year of book is greater than other
              sortByYear.set(j, temp2); // swap books
              sortByYear.set(j+1, temp);
            }
          }
        }
        for(Book b: sortByYear){ // print books
          b.print();
        }
      }
    }

    public String ValidOptions(String id){
      Product prod = null;
      String options = "";
      for(String s: products.keySet()){ // find product using product id
        if (products.get(s).getId().equals(id)){
          prod = products.get(s);
          break;
        }
      }

      if(prod == null)
        throw new UnknownProductException("Invalid product Id");

      if(prod.getCategory() == Product.Category.BOOKS)
        options = "Book Options: Paperback Hardcover EBook ";
      else if(prod.getCategory() == Product.Category.SHOES)
        options = "Shoe Size Options: 6, 7, 8, 9, 10\tShoe Colour Options: Black Brown";
      return options;
    }

    public void MainCart(String productId, String customerId, String options){ // add item to cart
      Customer cust = null; // make varibales for the customer and product
      Product prod = null;
      for(Customer c: customers){ // find customer using customer id
        if (c.getId().equals(customerId)){
          cust = c;
          break;
        }
      }

      for(String s: products.keySet()){ // find product using product id
        if (products.get(s).getId().equals(productId)){
          prod = products.get(s);
          break;
        }
      }
      if(cust == null){ // if customer or product not in list
        throw new UnknownCustomerException("Customer not found"); // throw exceptions
      }

      cust.CustCart(prod, options); // call custcart method
    }

    public void printCart(String customerId){
      Customer cust = null; // make varible for the customer

      for(Customer c: customers){ // find customer
        if (c.getId().equals(customerId)){
          cust = c;
        }
      }
      
      if (cust == null) // customer id matches none in list
        throw new UnknownCustomerException("Customer not found");

      cust.PrintCustCart(); // call printcustcart method
    }
  
    public void mainRemoveCartItem(String customerId, String productId){
      Customer cust = null;
      ArrayList<CartItem> carts = null;
      CartItem item = null;
      for(Customer c: customers){ // find customer
        if (c.getId().equals(customerId))
          cust = c;
      }

      if (cust == null) // customer id matches none in list
        throw new UnknownCustomerException("Customer not found");

      carts = cust.getCart().getCarts();
      
        for(CartItem c: carts){ // go through cartitems
            if(c.getProduct().getId().equals(productId)) // check if product id matches
                item = c;
                break;
        }

        if (item == null) // product id matches none in list
            throw new UnknownProductException("Product not found");

        carts.remove(item); // remove cartitem
    }

    public void orderItems(String customerId){ // order items in cart
      Customer cust = null; // make varibles for customer, productoption and product
      Product prod = null;
      String options = "";
      int orderCount = 0;
      ArrayList<CartItem> cart = new ArrayList<CartItem>();

      for(Customer c: customers){ // find customer
        if (c.getId().equals(customerId))
          cust = c;
      }
    
      if (cust == null) // customer id matches none in list
        throw new UnknownCustomerException("Customer not found");

      cart = cust.getCart().getCarts(); // get customer cart
      
      for(CartItem c: cart){ // go thorugh items in cart
        prod = c.getProduct(); // get the product
        options = c.getProductOptions(); // get the productoption

        orderCount = stats.get(prod.getId()) + 1; // add ordercount
        stats.replace(prod.getId(), orderCount);

        if (prod.getCategory() == Product.Category.BOOKS){ 
          ((Book)prod).reduceStockCount(options); // reduce stock for book
        } else if (prod.getCategory() == Product.Category.SHOES){
          ((Shoes)prod).reduceStockCount(options); // reduce stock for shoe
        } else {
          prod.reduceStockCount(options); // reduce stock for product
        }
        String ordNum = Integer.toString(orderNumber); // get ordernumber
        orders.add(new ProductOrder(ordNum, prod, cust, options)); // add order
        generateOrderNumber();// make new order number
      }
      
      cust.getCart().clearItems(); // clear cart
    }
    public Map<String, Product> readProducts(){ // read products from the file
      Map<String, Product> fileProducts = new TreeMap<String, Product>(); // make variables
      int count = 1; // count for each line of product
      String category = "";
      String name = "";
      double price = 0;
      int stock = 0;
      int paperstock = 0;
      int hardstock = 0;
      String title = "";
      String author = "";
      int year = 0;
      String prodId = "";
      int [] brownStock = new int[5];
      int [] blackStock = new int[5];

      try{ // try catch for io exception
        File file = new File("products.txt"); // make file
        Scanner scan = new Scanner(file); // scan file

        scan.useDelimiter(":"); // stop reading word at colon
        while(scan.hasNextLine()){
          if(count == 1) // product first line
            category = scan.nextLine(); // get category
          else if(count == 2) 
            name = scan.nextLine(); // get name
          else if(count == 3)
            price = Double.parseDouble(scan.nextLine().trim()); // get price
          else if(count == 4){
            if(Product.Category.valueOf(category) == Product.Category.BOOKS){ // check if category is book
              String bookstock = scan.nextLine(); // get line
              int space = bookstock.indexOf(" "); // find the space
              paperstock = Integer.parseInt(bookstock.substring(0,space)); // get paperback stock
              hardstock = Integer.parseInt(bookstock.substring(space+1, bookstock.length())); // get hardcover stock

            } else if (Product.Category.valueOf(category) == Product.Category.SHOES){
              String stocks = scan.nextLine();
              Scanner stockScan = new Scanner(stocks);
              for(int i = 0; i < 5; i ++)
                brownStock[i] = Integer.parseInt(stockScan.next());
              for(int i = 0; i < 5; i++)
                blackStock[i] = Integer.parseInt(stockScan.next());
              stockScan.close();
            }
            else
              stock = Integer.parseInt(scan.nextLine().trim()); // get stock
          }
          else if(count == 5){
            if(Product.Category.valueOf(category) == (Product.Category.BOOKS)){ // check if book
              title = scan.next().replace(":",""); // get properties and remove the colon
              author = scan.next().replace(":","");
              year = Integer.parseInt(scan.nextLine().replace(":","").trim());
            }
            else
              scan.nextLine(); // go next line

            prodId = generateProductId();
            if(Product.Category.valueOf(category) == Product.Category.BOOKS){ // check if book
              fileProducts.put(prodId, new Book(name, prodId, price, paperstock, hardstock, title, author, year)); // add book
            } else if(Product.Category.valueOf(category) == Product.Category.SHOES){
              fileProducts.put(prodId, new Shoes(name, prodId, price, brownStock, blackStock)); 
            } else {
              fileProducts.put(prodId, new Product(name, prodId, price, stock, Product.Category.valueOf(category))); // add product
            }
            count = 0; // reset count for new product
          }
          count++; // add count for each loop
        }
        scan.close(); // close scanner
      }
      catch(IOException e){ // catch io exception
        System.out.println(e.getMessage());
        System.exit(1); // exit
      } 
      return fileProducts; // return the Map
    }

    public void printStats(){ // print the stats of products
      for(String s: stats.keySet()){ // get product id
        Product p = products.get(s); // get product of that id
        System.out.printf("\nProduct Name: %-20s Id: %-10s Number of orders: %s ", p.getName(), p.getId(), stats.get(s)); // print stats
      }
    }

    public void getStatsRatings(){ 
      Rating rating = new Rating();
      for(String s: products.keySet()){ // loop through products
        stats.put(products.get(s).getId(), 0); // add the product id and 0 for order count
        ratings.put(products.get(s).getId(), rating);
      }
    }

    public void rateProduct(String id, String rate){ // rate product
      Rating rates = null; // create rating object
      int rating = 0;
      for(String s: ratings.keySet()){ // find product of the id
        if(id.equals(s))
          rates = ratings.get(s);
      }
      if (rates == null) // id does not exist
        throw new UnknownProductException("Product not found");

      rating = Integer.parseInt(rate); // convert to string and throws exception if not integer
      if (rating < 1 || rating > 5) // check if number is correct rating
        throw new NumberFormatException();

      rates = rates.addRate(rates, rating); // get new rating
      ratings.replace(id, rates); // add the rating to the product
    }

    public void printRatings(String id){ // print ratings
      Rating rate = null;
      for(String s: ratings.keySet()){ // check if id is valid
        if(id.equals(s))
          rate = ratings.get(s);
      }
      if (rate == null)
        throw new UnknownProductException("Product not found");
      
      rate.print(); // print ratings

    }

    public void printCategoryRatings(String category, String rating){ // print ratings of a category with average above user's choice
      int rate = 0;
      Product prod = null;
      category = category.toUpperCase(); // change the name to upper case
      if(!Product.validCategory(category)) // check if a valid category
        throw new InvalidCategoryException("Invalid Category");
      
      rate = Integer.parseInt(rating); // change to string and throws exception if not int
      if (rate < 1 || rate > 5) // check if the rating is valid
        throw new NumberFormatException();

      for(String s: ratings.keySet()){ // go through ratings map
        prod = products.get(s); // get the product
        if (prod.getCategory() == Product.Category.valueOf(category)){ // check if same category
          if(ratings.get(s).averageRating() > rate) // check if average rating is acceptable
            products.get(s).print(); // print the rating
        }

      }
        
    }
}
// making exceptions
class UnknownProductException extends RuntimeException{  // unknown product id
  public UnknownProductException(String message){
      super(message);
  }
}
class UnknownCustomerException extends RuntimeException { // unknown customer id
  public UnknownCustomerException(String message){
      super(message);
  }
}
class InvalidProdOpException extends RuntimeException { // invalid product option
  public InvalidProdOpException(String message){
    super(message);
  }
}
class OutOfStockException extends RuntimeException{ // product out of stock
  public OutOfStockException(String message){
    super(message);
  }
}
class InvalidCustNameException extends RuntimeException{ // invalid customer name
  public InvalidCustNameException(String message){
    super(message);
  }
}
class InvalidCustAddressException extends RuntimeException{ // invalid customer adress
  public InvalidCustAddressException(String message){
    super(message);
  }
}
class InvalidOrderNumException extends RuntimeException{ // unknown order number
  public InvalidOrderNumException(String message){
    super(message);
  }
}
class InvalidAuthorException extends RuntimeException{ // unknown author
  public InvalidAuthorException(String message){
  super(message);
  }
}
class InvalidCategoryException extends RuntimeException{ // invalid category
  public InvalidCategoryException(String message){
    super(message);
  }
}