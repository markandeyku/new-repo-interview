package test;

public class Test implements MyAbstractClass {
    public static void main(String[] args) {
        System.out.println("ye kya ho rha hai");
         new Test().myAbstractMethod();

    }

    @Override
    public void myAbstractMethod() {
        System.out.println("Abstract method implementation");
    }
}


 interface MyAbstractClass {
    // Abstract method (cannot be static)
     void myAbstractMethod();

    // Static method (cannot be abstract)
     static void myStaticMethod() {
        System.out.println("Static method in abstract class");
    }
}
