import java.util.Scanner;

public class Occurrancecount {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter size:");
        int size=sc.nextInt();
        int arr[]=new int[size];
        for(int i=0;i<size;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println("Enter target:");
        int target=sc.nextInt();
        int count=0;
        for(int i=0;i<size;i++){
            if(arr[i]==target){
                count ++;

            }
        }
        System.out.println(target + " occurs " + count + " times" );
        sc.close();
}
}