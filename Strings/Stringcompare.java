import java.util.*;
public class Stringcompare {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter String1:");
        String s1 = sc.next();
        System.out.println("Enter String2:");
        String s2 = sc.next();
        if(s1.compareTo(s2) == 0){
            System.out.println("Strings are equal");
        }else{
            System.out.println("Strings are not equal");
        }
    }
    
}
