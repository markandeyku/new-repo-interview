package dsignpattern.creational;

public class BuilderDesignPattern {

    // I can take the examples of phone that have lots of properties to be added during creation


}

class Phone{
    private String ram;
    private String ssd;
    private String operatingsystem;
    private String cpu;
    private String battery;

    public Phone(String ram, String ssd, String operatingsystem, String cpu, String battery) {
        this.ram = ram;
        this.ssd = ssd;
        this.operatingsystem = operatingsystem;
        this.cpu = cpu;
        this.battery = battery;
    }

    public Phone(PhoneBuilder phoneBuilder){
        this.ram = phoneBuilder.getRam();
        this.ssd = phoneBuilder.getSsd();
        this.operatingsystem = phoneBuilder.getOperatingsystem();
        this.cpu = phoneBuilder.getCpu();
        this.battery = phoneBuilder.getBattery();
    }

    public String getRam() {
        return ram;
    }

    public String getSsd() {
        return ssd;
    }

    public String getOperatingsystem() {
        return operatingsystem;
    }

    public String getCpu() {
        return cpu;
    }

    public String getBattery() {
        return battery;
    }
}

class PhoneBuilder{
    private String ram;
    private String ssd;
    private String operatingsystem;

    private String cpu;

    private String battery;

    public PhoneBuilder setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public PhoneBuilder setSsd(String ssd) {
        this.ssd = ssd;
        return this;

    }

    public PhoneBuilder setOperatingsystem(String operatingsystem) {
        this.operatingsystem = operatingsystem;
        return this;

    }

    public PhoneBuilder setCpu(String cpu) {
        this.cpu = cpu;
        return this;

    }

    public PhoneBuilder setBattery(String battery) {
        this.battery = battery;
        return this;

    }

    public Phone build(){

        // I can directly pass PhoneBuilder object in Phone  like new Phone(this);
        return new Phone(this.ram, this.ssd, this.operatingsystem, this.cpu, this.battery);
    }

    public String getRam() {
        return ram;
    }

    public String getSsd() {
        return ssd;
    }

    public String getOperatingsystem() {
        return operatingsystem;
    }

    public String getCpu() {
        return cpu;
    }

    public String getBattery() {
        return battery;
    }
}


// client creating phone, not worry about passing parameter in a particular sequence
class PhoneCreationService{

    public static void main(String[] args) {

        Phone phone = new PhoneBuilder().setBattery("battery").setCpu("cpu").setRam("").build();

    }
}