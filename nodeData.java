package dataStructure;

import utils.Point3D;

public class nodeData implements node_data{
    Point3D location;
    Integer id;
    Double weight;
    String inf;
    int color;


    public nodeData(){
       this.location = null;
       this.id = null;
       this.weight = null;
       this.inf=null;
       this.color=0;
    }
    public nodeData(Point3D a,int d,Double w,String in,int co){
        this.location=a;
        this.id=d;
        this.weight=w;
        this.inf=in;
        this.color=co;
    }
    public nodeData(nodeData a){
        nodeData b = new nodeData(a.location,a.id,a.weight,a.inf,a.color);
    }
    @Override
    public int getKey() {

        return this.id;
    }

    @Override
    public Point3D getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Point3D p) {
    this.location=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
    if(w<0){weight = null;}
    else{this.weight=w;}
    }

    @Override
    public String getInfo() {
        return this.inf;
    }

    @Override
    public void setInfo(String s) {
    this.inf=s;
    }

    @Override
    public int getTag() {
        return this.color;
    }

    @Override
    public void setTag(int t) {
    if(t<3 && t>-1){this.color=t;}
    else{this.color=0;}
    }
}
