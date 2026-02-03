import java.util.*;
public class Stringreverse {
    public static void main(String args[]){
        StringBuilder sb = new StringBuilder("Sahana");
        for(int i=0;i<sb.length()/2;i++){
            int start = i;
            int end = sb.length()-1-i;
            char startChar = sb.charAt(start);
            char endChar = sb.charAt(end);
            sb.setCharAt(start,endChar );
            sb.setCharAt(end, startChar);


        }
        System.out.println(sb);
    }
    
}
