import java.util.*;
public class ArrayPractice2 {
    public static void main(String args[]){
        Scanner sc=new Scanner (System.in);
        int size=sc.nextInt();
        int num[]=new int[size];
        for(int i=0;i<size;i++){
            num[i]=sc.nextInt();
        }
        boolean isAscending = true;
        for(int i=0;i<size-1;i++){
            if(num[i]>num[i+1]){
                isAscending = false;
            }
        }
            if(isAscending){
                System.out.println("Array is sorted in ascending order");
            }else{
                System.out.println("Araay is not sorted");
            }
        }
    }
    

