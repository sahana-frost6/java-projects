import java.util.*;
public class sumofArrays {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.println("rows:");
        int n=sc.nextInt();
        System.out.println("cols:");
        int m=sc.nextInt();
        int matrix[][]=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matrix[i][j]=sc.nextInt();
            }
        }
        int sum = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                sum += matrix[i][j];
            }
        }
        System.out.println("Sum = " +sum);
    }
        }
    

