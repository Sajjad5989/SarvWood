package ir.sarvwood.workshop.model;


public class WoodOrderModel {

   private int woodType;
    private  String woodDescription;

    private String woodColor;
    private String pvcColor;

    private int paperCount;
    private int persianCutter;
    private int groove;
    private int direction;
    private int thickness;

    private double width;
    private double height;
    private String size;

    private String date;
    private String time;


    public WoodOrderModel() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public WoodOrderModel(int woodType, String woodDescription, String woodColor, String pvcColor, int paperCount, int persianCutter, int groove, int direction, int thickness, double width, double height, String date, String time, String size) {
        this.woodType = woodType;
        this.woodDescription = woodDescription;
        this.woodColor = woodColor;
        this.pvcColor = pvcColor;
        this.paperCount = paperCount;
        this.persianCutter = persianCutter;
        this.groove = groove;
        this.direction = direction;
        this.thickness = thickness;
        this.width = width;
        this.height = height;
        this.date = date;
        this.time = time;
        this.size=size;
    }

    public int getWoodType() {
        return woodType;
    }

    public void setWoodType(int woodType) {
        this.woodType = woodType;
    }

    public String getWoodDescription() {
        return woodDescription;
    }

    public void setWoodDescription(String woodDescription) {
        this.woodDescription = woodDescription;
    }

    public String getWoodColor() {
        return woodColor;
    }

    public void setWoodColor(String woodColor) {
        this.woodColor = woodColor;
    }

    public String getPvcColor() {
        return pvcColor;
    }

    public void setPvcColor(String pvcColor) {
        this.pvcColor = pvcColor;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public int getPersianCutter() {
        return persianCutter;
    }

    public void setPersianCutter(int persianCutter) {
        this.persianCutter = persianCutter;
    }

    public int getGroove() {
        return groove;
    }

    public void setGroove(int groove) {
        this.groove = groove;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
