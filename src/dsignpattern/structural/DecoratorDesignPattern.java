package dsignpattern.structural;

public class DecoratorDesignPattern {
}
// I can take the example of coffee or pizza

//Here we are going to discuss about coffee with decorator design pattern
//Attach additional responsiblites to an object dynamically


//This is component interface
interface Coffee{
    double getCost();
    String getDescription();
}

//Concrete components
//Step 2: Concrete Coffee (Basic Coffee)
class PlainCoffee implements Coffee{
    @Override
    public double getCost() {
        return 10;
    }

    @Override
    public String getDescription() {
        return "Simple coffee";
    }
}


// Step 3: Abstract Decorator (Wraps a Coffee)
 abstract class DecoratorCoffee implements Coffee{
    protected Coffee coffee;

    DecoratorCoffee(Coffee coffee){
        this.coffee = coffee;
    }


     @Override
     public double getCost() {
         return coffee.getCost();
     }

     @Override
     public String getDescription() {
         return coffee.getDescription();
     }
 }

 //Concrete decorators

class MilkDecorator extends DecoratorCoffee{

    MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return coffee.getCost()+10;
    }

    public String getDescription() {
        return coffee.getDescription()+ ", Milk";
    }
}

class SugarDecorator extends DecoratorCoffee {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }

    public double getCost() {
        return coffee.getCost() + 5;
    }
}

class CreamDecorator extends DecoratorCoffee{

    CreamDecorator(Coffee coffee) {
        super(coffee);
    }


    public String getDescription() {
        return coffee.getDescription() + ", cream";
    }

    public double getCost() {
        return coffee.getCost() + 20;
    }
}

//
// Step 5: Main Class for Testing
 class CoffeeShop {
    public static void main(String[] args) {
        Coffee coffee = new PlainCoffee();
        System.out.println(coffee.getDescription() +" $::  "+ coffee.getCost());

        Coffee milkCoffee = new MilkDecorator(coffee);
        System.out.println(milkCoffee.getDescription() +" $::  "+ milkCoffee.getCost());

        Coffee milkCreamyCoffee = new CreamDecorator(milkCoffee);

        System.out.println(milkCreamyCoffee.getDescription() +" $::  "+ milkCreamyCoffee.getCost());

        Coffee sugarConffee = new SugarDecorator(milkCreamyCoffee);

        System.out.println(sugarConffee.getDescription() +" $::  "+ sugarConffee.getCost());

    }
 }