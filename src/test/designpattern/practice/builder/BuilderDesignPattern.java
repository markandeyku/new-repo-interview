package test.designpattern.practice.builder;

public class BuilderDesignPattern {

    public static void main(String[] args) {
        Phone phone = new PhoneBuilder()
                .setCamera("12 MP")
                .setBattery("5000 mAh").setOS("Android").build();

        System.out.println(phone);
    }

}
