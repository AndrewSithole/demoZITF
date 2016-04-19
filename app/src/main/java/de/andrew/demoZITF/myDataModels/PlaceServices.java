package de.andrew.demoZITF.myDataModels;

/**
 * Created by Andrew on 4/11/16.
 */
public class PlaceServices {
    private String serviceName;
    private double servicePrice;

    public PlaceServices(){

    }
    public PlaceServices(String name, double price){
        serviceName =name;
        servicePrice =price;
    }
    public double getServicePrice() {
        return servicePrice;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }
}