import java.util.*;
public class LinearSearch {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int size=sc.nextInt();
        int arr[]=new int[size];
        for(int i=0;i<size;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println("enter key:");
        int key=sc.nextInt();
        boolean found=false;
        for(int i=0;i<size;i++){
        if(arr[i]==key){
            System.out.println("Key found at index:"+ i );
            found = true;
            break;
        }
    }
        if(!found){
            System.out.println("Key element not found");
        
        }
        sc.close();
    }
    
}

