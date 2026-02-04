import java.util.*;
public class Insertionsort {
    public static void printArray(int arr[]){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String args[]){
        int arr[]={9,0,2,7,3,4,5};
        for(int i=1;i<arr.length;i++){
            int current = arr[i];
            int j =i-1;
            while(j >= 0 && current < arr[j]){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1]=current;
        }
        printArray(arr);
    }
    
}
