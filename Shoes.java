//Daniel Yu 501120369
public class Shoes extends Product{
    private int[] brownStock; // array for stock of each size of brown shoes
    private int[] blackStock; // array for stock of each size of black shoes

    public Shoes(String name, String id, double price, int[] brownStock, int[] blackStock){ // initialize the varibales
        super(name, id, price, 100, Product.Category.SHOES); // get variables from product class
        this.brownStock = brownStock;
        this.blackStock = blackStock;
    }
    public int getStockCount(String productOptions){ // get stock count of a shoe colour and size
        int indexShoe = productOptions.indexOf(" "); // get index of space in productOptions to disguish between size and colour
        String shoeOption = productOptions.substring(indexShoe+1, productOptions.length()); // get shoe colour
        String shoeSize = productOptions.substring(0, indexShoe); // get shoe size

        if(shoeOption.equals("Brown")){ // colour is brown
            if (shoeSize.equals("6")) // size is 6
                return brownStock[0]; // return stock of size 6 brown shoe
            else if (shoeSize.equals("7"))
                return brownStock[1]; // return size 7 
            else if (shoeSize.equals("8"))
                return brownStock[2];
            else if (shoeSize.equals("9"))
                return brownStock[3];
            else 
                return brownStock[4];
        } else { // colour is black
            if (shoeSize.equals("6"))
                return blackStock[0];
            else if (shoeSize.equals("7"))
                return blackStock[1];
            else if (shoeSize.equals("8"))
                return blackStock[2];
            else if (shoeSize.equals("9")) // size is 9
                return blackStock[3]; // return stock of size 9 brown shoe
            else
                return blackStock[4];
        }
    }
    public void reduceStockCount(String productOptions) // reduce stock count of the shoe
	{
    int indexShoe = productOptions.indexOf(" ");
    String shoeSize = productOptions.substring(0, indexShoe);
    String shoeOption = productOptions.substring(indexShoe+1, productOptions.length());
    
     if (shoeOption.equals("Brown")){ // shoe colour is brown
        if (shoeSize.equals("6")) // size is 6
            brownStock[0]--; // reduce stock of size 6 brown shoe
        else if (shoeSize.equals("7"))
                brownStock[1]--;
        else if (shoeSize.equals("8"))
                brownStock[2]--;
        else if (shoeSize.equals("9"))
                brownStock[3]--;
        else 
            brownStock[4]--;
    } else { // shoe is black
        if (shoeSize.equals("6")) 
            blackStock[0]--;
        else if (shoeSize.equals("7"))
            blackStock[1]--;
        else if (shoeSize.equals("8"))
            blackStock[2]--;
        else if (shoeSize.equals("9"))
            blackStock[3]--;
        else // size is 10
            blackStock[4]--; // reduce stock of size 10 black shoe
     }
	}

    public void validOptions(String productOptions){ // check if option is valid
        int indexShoe = productOptions.indexOf(" "); // get index of space
        if(indexShoe < 0)
            throw new InvalidProdOpException("Invalid product option"); // option is not size plus colour, only 1 word

        String shoeSize = productOptions.substring(0, indexShoe); // get shoesize
        String shoeOption = productOptions.substring(indexShoe+1, productOptions.length()); // get shoe colour
        if (shoeSize.equals("6") || shoeSize.equals("7") || shoeSize.equals("8") || shoeSize.equals("9") || shoeSize.equals("10")){ // check if size is valid
        } else { // size is not valid
            throw new InvalidProdOpException("Invalid product option");
        }
        if (shoeOption.equals("Black") || shoeOption.equals("Brown")){ // check if colour is valid
        } else { // colour is not valid
            throw new InvalidProdOpException("Invalid product option");
        }
    }

    public void print()
	{
        super.print();

	}
}
