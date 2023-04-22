//Daniel Yu 501120369
import java.util.ArrayList;
public class Cart {
    private ArrayList<CartItem> carts;

    public Cart(ArrayList<CartItem> cartc){
        this.carts = cartc;
    }

    public void addToCart(Product prod, String options){ // add item to cart arraylist
        if (prod.getCategory().equals(Product.Category.BOOKS)){
            Book book = (Book) prod;
            book.validOptions(options);
        } else if (prod.getCategory().equals(Product.Category.SHOES)){
            Shoes shoe = (Shoes) prod;
            shoe.validOptions(options);
        }
        this.carts.add(new CartItem(prod, options));
    }

    public void printCart(){ // print cart
        for(CartItem c: carts){
            c.print(); // call cartitem print method
        }
    }

    public ArrayList<CartItem> getCarts(){ // return cart
        return this.carts;
    }

    public void clearItems(){ // clear the cart arraylist
        this.carts.clear();
    }
}
