//Daniel Yu 501120369
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args) 
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			
			try{		
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
				{
					amazon.printAllProducts(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
				{
					amazon.printAllBooks(); 
				}
				else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
				{
					amazon.printCustomers();	
				}
				else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();	
				}
				else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
				{
					amazon.printAllShippedOrders();	
				}
				else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
				{
					String name = "";
					String address = "";
					
					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();
					
					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();
					
					amazon.createCustomer(name, address);
				}
				else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
				{
					String orderNumber = "";
		
					System.out.print("Order Number: ");
					// Get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();
					// Ship order to customer (see ECommerceSystem for the correct method to use
					ProductOrder order = amazon.shipOrder(orderNumber);
					order.print();

				}
				else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine())
							customerId = scanner.nextLine();
					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);
				}
				else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
				// Get product Id from scanner
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
				// Get customer Id from scanner
					
					// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
					String success = amazon.orderProduct(productId, customerId, "");
					System.out.println("Order #" + success);
					// Print Order Number string returned from method in ECommerceSystem
					
				}
				else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get book forma and store in options string
					if (scanner.hasNextLine())
						options = scanner.nextLine();
					
					// Order product. Check for error mesage set in ECommerceSystem
					String success = amazon.orderProduct(productId, customerId, options);
					// Print order number string if order number is not null
					System.out.println("Order #" + success);
				}
				else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
				{
					String productId = "";
					String customerId = "";
					String options = "";
					
					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options	
					if (scanner.hasNextLine())
						options = scanner.nextLine();

					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine())
						options = options + " " + scanner.nextLine();
						
					//order shoes
					String success = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + success);
				}
				
				
				else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();
					// cancel order. Check for error
					amazon.cancelOrder(orderNumber);
				}
				else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					amazon.PrintByPrice();
				}
				else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.PrintByName();
				}
				else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();
				} 
				else if (action.equalsIgnoreCase("BooksByAuthor")) // sort books by author and year
				{
					String author = "";
					System.out.print("Author: "); 
					if (scanner.hasNextLine()) // get author from user
						author = scanner.nextLine();

					amazon.printByYear(author); // call sorting function
				}
				else if (action.equalsIgnoreCase("ADDTOCART"))
				{
					String productId = ""; // make variables
					String customerID = "";
					String productOptions = "";
					String validOptions = "";

					System.out.print("Product Id: "); // get product id
					if (scanner.hasNextLine())
						productId = scanner.next();
					
					validOptions = amazon.ValidOptions(productId);

					System.out.print("Customer Id: "); // get customer id
					if (scanner.hasNextLine())
						customerID = scanner.next();

					if (!validOptions.equals("")){
						System.out.print(validOptions); // get product option
						productOptions = scanner.nextLine(); // read previous newline since scanner.next does not read newline
						productOptions = scanner.nextLine(); // get product options
					}
					amazon.MainCart(productId, customerID, productOptions); // call function
				}
				else if (action.equalsIgnoreCase("REMCARTITEM"))
				{
					String customerId = ""; // make variables
					String productId = "";

					System.out.print("Customer Id: "); // get customer id
					if (scanner.hasNextLine())
						customerId = scanner.next();
					
					System.out.print("Product Id: "); // get product id
					if (scanner.hasNextLine())
						productId = scanner.next();
					
					amazon.mainRemoveCartItem(customerId, productId); // call remove cart item funciton
				}
				else if (action.equalsIgnoreCase("PRINTCART"))
				{
					String customerId = ""; // make variables

					System.out.print("Customer Id: "); // get customer id;
					if (scanner.hasNextLine())
						customerId = scanner.next();

					amazon.printCart(customerId); // call printcart method
				}
				else if (action.equalsIgnoreCase("ORDERITEMS"))
				{
					String customerId = ""; // make variable

					System.out.print("Customer Id: "); // get customer id from user
					if (scanner.hasNextLine())
						customerId = scanner.next();
					
					amazon.orderItems(customerId); // call orderitems method
				}
				else if (action.equalsIgnoreCase("STATS")){ // print stats
					amazon.printStats();
				}
				else if(action.equalsIgnoreCase("RATE")){ // rate product
					String rate = ""; 
					String prodId = "";
					System.out.print("Product Id: "); // get the product id to rate
					if(scanner.hasNextLine())
						prodId = scanner.next();
					
					System.out.println("Enter rating from 1-5: "); // get the rating
					if(scanner.hasNextLine())
						rate = scanner.next();
					
					amazon.rateProduct(prodId, rate); // rate the product
				}
				else if (action.equalsIgnoreCase("RATINGS")){ // print the ratings of a product
					String prodId = "";
					System.out.print("Product Id: "); // get the product id
					if (scanner.hasNextLine())
						prodId = scanner.next();
					amazon.printRatings(prodId); // print the ratings
				}
				else if (action.equalsIgnoreCase("CATEGORYRATE")) 
				{
					String category = "";
					String rating = "";
					System.out.print("Enter product category: ");
					if (scanner.hasNextLine())
						category = scanner.next();
					
					System.out.print("Enter rating: ");
					if (scanner.hasNextLine())
						rating = scanner.next();
					
					amazon.printCategoryRatings(category, rating);
				}
			}
			catch(NumberFormatException n){ // catch exceptions
				System.out.println("Invalid rating");
			}
			catch(UnknownCustomerException c){ 
				System.out.println(c.getMessage());
			}
			catch(UnknownProductException p){
				System.out.println(p.getMessage());
			}
			catch(InvalidProdOpException po){
				System.out.println(po.getMessage());
			}
			catch(InvalidAuthorException a){
				System.out.println(a.getMessage());
			}
			catch(InvalidOrderNumException on){
				System.out.println(on.getMessage());
			}
			catch(InvalidCustNameException cn){
				System.out.println(cn.getMessage());
			}
			catch(InvalidCustAddressException ca){
				System.out.println(ca.getMessage());
			}
			catch(InvalidCategoryException c){
				System.out.println(c.getMessage());
			}
			catch(OutOfStockException s){
				System.out.println(s.getMessage());
			}
			System.out.print("\n>");
		}
	}
}
