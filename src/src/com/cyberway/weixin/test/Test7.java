package com.cyberway.weixin.test;

public class Test7 {
	private final double PI = 3.14159265358979323; //圆周率
    private final double R = 6371229;              //地球的半径
   
    //longt是经度
    //lat是纬度
   // a=Lat1 – Lat2 为两点纬度之差  b=Lung1 -Lung2 为两点经度之差；
   // 6378.137（KM） 为地球半径 - -@

    //距离
    public double getDistance(double longt1, double lat1, double longt2, double lat2){
        double x,y,distance;
        x=(longt2-longt1)*PI*R*Math.cos( ((lat1+lat2)/2) *PI/180)/180;
        y=(lat2-lat1)*PI*R/180;
        distance=Math.hypot(x,y);
        return distance;
    }
   
  //经度
    public double getLongt(double longt1, double lat1, double distance){
        double a = (180*distance)/(PI*R*Math.cos(lat1*PI/180));
        return a;
    }
   
    //纬度
    public double getLat(double longt1, double lat1, double distance){
        double a = (180*distance)/(PI*R*Math.cos(lat1*PI/180));
        return a;
    }
  
    public static void main(String[] args){
    	 Test7 m = new Test7();
       
//        double longt = m.getLongt(113.430603, 23.167, 500);
//        System.out.println(longt);
//       
//        double lat = m.getLat(113.320107, 23.104105, 11131.9859);
//        System.out.println(lat);
       
        double s = D_jw(23.167765,113.43123,23.167,113.430603);
        System.out.println(s);
        
    }
    
    public static double D_jw(double wd1,double jd1,double wd2,double jd2)
    {
        double x,y,out;
        double PI=3.14159265;
        double R=6.371229*1e6;
        x=(jd2-jd1)*PI*R*Math.cos( ((wd1+wd2)/2) *PI/180)/180;
        y=(wd2-wd1)*PI*R/180;
        out=Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
        System.out.println(x);
        System.out.println(y);
        System.out.println((int)out);
        return out;
        
    }

}
