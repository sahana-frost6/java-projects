import java.util.*;
public class SubstringsandcharAt {
    public static void main(String args[]){
        String sent = "My name is Sahana";
        String name = sent.substring(11,sent.length());
        System.out.println(name);
        for( int i=0;i<sent.length();i++){
        System.out.println(sent.charAt(i));
        }
    } 
    
}
