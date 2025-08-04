package test.designpattern.practice.builder;

public class PhoneBuilder {

    private String os;
    private String cpu;
    private String ram;
    private String storage;
    private String camera;
    private String battery;
    private String display;
    private String frontCamera;
    private String backCamera;
    private String batteryType;

    public PhoneBuilder setOS(String os) {
        this.os = os;
        return this;
    }

    public PhoneBuilder setCPU(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public PhoneBuilder setRAM(String ram) {
        this.ram = ram;
        return this;
    }

    public PhoneBuilder setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public PhoneBuilder setCamera(String camera) {
        this.camera = camera;
        return this;
    }

    public PhoneBuilder setBattery(String battery) {
        this.battery = battery;
        return this;
    }

    public PhoneBuilder setDisplay(String display) {
        this.display = display;
        return this;
    }

    public PhoneBuilder setFrontCamera(String frontCamera) {
        this.frontCamera = frontCamera;
        return this;
    }

    public PhoneBuilder setBackCamera(String backCamera) {
        this.backCamera = backCamera;
        return this;
    }

    public PhoneBuilder setBatteryType(String batteryType) {
        this.batteryType = batteryType;
        return this;
    }

    public Phone build() {
        return new Phone(this);
    }


    public String getOs() {
        return os;
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getStorage() {
        return storage;
    }

    public String getCamera() {
        return camera;
    }

    public String getBattery() {
        return battery;
    }

    public String getDisplay() {
        return display;
    }

    public String getFrontCamera() {
        return frontCamera;
    }

    public String getBackCamera() {
        return backCamera;
    }

    public String getBatteryType() {
        return batteryType;
    }
}
