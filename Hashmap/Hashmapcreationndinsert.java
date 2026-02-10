package Hashmap;
import java.util.*;

public class Hashmapcreationndinsert {
    public static void main(String args[]){
        //creation
        HashMap <String,Integer> students = new HashMap<>();
        //insert
        students.put("Sahana",100);
        students.put("Sona",101);
        students.put("Neha",102);
        students.put("Likitha",103);
        System.out.println(students);
        //search
        if(students.containsKey("Sahana")){
            System.out.println("Key is present in students");
        }else{
            System.out.println("Key is absent in students");
        }
        System.out.println(students.get("Sona"));//key present 
        System.out.println(students.get("Jyothi"));//key not present

    }
    
}
