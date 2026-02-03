import java.util.*;
public class Stringbuilders {
    public static void main(String args[]){
        StringBuilder sb = new StringBuilder("Buny");
        System.out.println(sb);
        //char at index 0
        System.out.println(sb.charAt(0));
        //set char at index 0
        sb.setCharAt(0,'F');
        System.out.println(sb);
        //insert
        sb.insert(2,'n');
        System.out.println(sb);
        //delete 
        sb.delete(3,5);
        System.out.println(sb);
        //append
        sb.append(" d");
        sb.append("a");
        sb.append("y");
        System.out.println(sb);
        System.out.println(sb.length());


    }
    
}
