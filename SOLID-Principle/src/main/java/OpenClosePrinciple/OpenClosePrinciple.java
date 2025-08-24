package OpenClosePrinciple;

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

interface saveInvoice{
    public void save();
}

// Tip bro - always add a refernce in Concreteclass and never in the interface

class saveToDb implements saveInvoice{
    ShoppingCart cart;
    public saveToDb(ShoppingCart cart){
        this.cart=cart;
    }
    @Override
    public void save() {
        System.out.println("Saving the Invoice Details to database");
    }
}

class saveToFile implements saveInvoice{
    ShoppingCart cart;
    public saveToFile(ShoppingCart cart){
        this.cart=cart;
    }
    @Override
    public void save() {
        System.out.println("Saving the Invoice Details to File");
    }
}


public class OpenClosePrinciple {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Apple", 1.5));
        products.add(new Product("Banana", 0.8));
        products.add(new Product("Orange", 1.2));

        ShoppingCart cart = new ShoppingCart(products);

        printInvoice printer = new printInvoice(cart);
        printer.print();

        saveInvoice saver = new saveToFile(cart);
        saver.save();

        System.out.println("Total: " + cart.calculateTotal());
    }
}
