package Hashmap;
import java.util.*;

public class Iterationinmap {
    public static void main(String args[]){
        HashMap <String,Integer> map = new HashMap<>(); 
        map.put("Sahana",120);
        map.put("Sanjana",90);
        map.put("Srija",100);
        map.put("Shreya",96);
        //iteration 1
        for(Map.Entry<String,Integer> S : map.entrySet()){
            System.out.println(S.getKey());
            System.out.println(S.getValue());
        }
        //iteration 2
        Set<String> keys = map.keySet();
        for(String key : keys){
            System.out.println(key + " " + map.get(key));

        }
        //remove
        map.remove("Shreya");
        System.out.println(map);
    }
    
}
