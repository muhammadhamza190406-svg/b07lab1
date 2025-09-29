import java.io.File;


public class Driver {
    public static void main(String [] args) {
        
        double [] c1 = {6,4,3,2};
        int [] e1 = {0,1,2,3};
        Polynomial p1 = new Polynomial(c1,e1);
        double [] c2 = {4,6,7,8};
        int[] e2 = {3,2,5,6};
        Polynomial p2 = new Polynomial(c2,e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(2.5) = " + s.evaluate(2.5));


        double [] c3 = {1,-6,11,-6} ;
        int [] e3 = {3,2,1,0};
        Polynomial p3 = new Polynomial(c3,e3);
        if(p3.hasRoot(3)){
            System.out.println("3 is a root of p3");
        }
        else{
            System.out.println("3 is not a root of p3");
        }
        
        double [] c4 = {1,2} ;
        int [] e4 = {1,0};
        Polynomial p4 = new Polynomial(c4,e4);
        double[] c5 = {1, 3, 1};
        int[] e5 = {2, 1, 0};
        Polynomial p5 = new Polynomial(c5, e5);
        Polynomial p6 = p4.multiply(p5);
        System.out.println("p6(2) = " + p6.evaluate(2));



        Polynomial p7 = new Polynomial(new File("D:/UOFT/CSCB07/b07lab1/test.txt"));
        System.out.println("p7(2) = " + p7.evaluate(2));

        double [] c8 = {3, -4,-5,6,7};
        int [] e8 = {4,3,2,1,0};
        Polynomial p8 = new Polynomial(c8,e8);
        p8.saveToFile("D:/UOFT/CSCB07/b07lab1/test2.txt");
    }
}