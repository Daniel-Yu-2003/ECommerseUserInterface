// Daniel Yu 501120369
public class CartItem {
    private Product product;
    private String productOptions;
    public CartItem(Product productp, String producto){
        this.product = productp;
        this.productOptions = producto;
    }
    
    public void print(){
        this.product.print(); // call product print method
        System.out.print(" Product Options: " + this.productOptions); // print product options
    }

    public Product getProduct(){
        return this.product;
    }

    public String getProductOptions(){
        return this.productOptions;
    }
}
