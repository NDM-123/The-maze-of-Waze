package dataStructure;

public class edgeData implements edge_data {
    int sour;
    int targ ;
    Double weight;
    String inf;
    int color;


    public edgeData(){
        this.sour = 0;
        this.targ = 0;
        this.weight = null;
        this.inf=null;
        this.color=0;
    }
    public edgeData(int so ,int tar,Double w,String in,int co){
        this.sour=so;
        this.targ=tar;
        this.weight=w;
        this.inf=in;
        this.color=co;
    }
    public edgeData(edgeData a){
        edgeData b = new edgeData(a.sour,a.targ,a.weight,a.inf,a.color);
    }

    @Override
    public int getSrc() {
        return this.sour;
    }

    @Override
    public int getDest() {
        return this.targ;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
