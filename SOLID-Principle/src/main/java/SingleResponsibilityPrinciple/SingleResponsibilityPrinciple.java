package SingleResponsibilityPrinciple;

import java.util.ArrayList;
import java.util.List;

class Product{
    public String name;
    public double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class ShoppingCart{
    public List<Product>products=new ArrayList<>();

    public ShoppingCart(List<Product> products) {
        this.products = products;
    }

    public double calculateTotal(){
        double ans=0;
        for(Product product:products){
            ans+=product.price;
        }
        return ans;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

class saveInvoice{

    ShoppingCart cart;
    public saveInvoice(ShoppingCart cart){
        this.cart=cart;
    }

    public void saveToDB(){
        System.out.println("Saving the Invoice Details to database");
    }
}

class printInvoice{

    ShoppingCart cart;
    public printInvoice(ShoppingCart cart){
        this.cart=cart;
    }

    public void print(){
        List<Product>products=cart.getProducts();
        for(Product product:products){
            System.out.println(product.name+" : "+product.price);
        }
    }
}

public class SingleResponsibilityPrinciple {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Apple", 1.5));
        products.add(new Product("Banana", 0.8));
        products.add(new Product("Orange", 1.2));

        ShoppingCart cart = new ShoppingCart(products);

        printInvoice printer = new printInvoice(cart);
        printer.print();

        saveInvoice saver = new saveInvoice(cart);
        saver.saveToDB();

        System.out.println("Total: " + cart.calculateTotal());
    }
}
