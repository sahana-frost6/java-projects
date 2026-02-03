import java.util.*;
public class replaceletter {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter string:");
        String S = sc.nextLine();
        String result = "";
        for(int i=0;i<S.length();i++){
            if(S.charAt(i) == 'e'){
                result += 'i';

            }else{
                result += S.charAt(i);
            }
        }
        System.out.println(result);
    }
    
}
