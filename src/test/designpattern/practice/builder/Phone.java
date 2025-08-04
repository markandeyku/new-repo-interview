package test.designpattern.practice.builder;

public class Phone {
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

    Phone(PhoneBuilder builder) {
        this.os = builder.getOs();
        this.cpu = builder.getCpu();
        this.ram = builder.getRam();
        this.storage = builder.getStorage();
        this.camera = builder.getCamera();
        this.battery = builder.getBattery();
        this.display = builder.getDisplay();
        this.frontCamera = builder.getFrontCamera();
        this.backCamera = builder.getBackCamera();
        this.batteryType = builder.getBatteryType();
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

    @Override
    public String toString() {
        return "Phone{" +
                "os='" + os + '\'' +
                ", cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", storage='" + storage + '\'' +
                ", camera='" + camera + '\'' +
                ", battery='" + battery + '\'' +
                ", display='" + display + '\'' +
                ", frontCamera='" + frontCamera + '\'' +
                ", backCamera='" + backCamera + '\'' +
                ", batteryType='" + batteryType + '\'' +
                '}';
    }
}
