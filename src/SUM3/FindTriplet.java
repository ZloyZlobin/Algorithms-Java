package SUM3;

import java.util.Arrays;

public class FindTriplet
{
    public static boolean find3Numbers(int[] a, int sum)
    {
        int l, r;

        Arrays.sort(a);

        for(int i = 0; i < a.length - 2; i++)
        {
            l = i + 1;
            r = a.length - 1;

            while(l < r)
            {
                int checkSum = a[i] + a[l] + a[r];
                if(checkSum == sum)
                {
                    System.out.print(a[i] + " + " + a[l] + " + " + a[r] + " = " + sum);
                    return true;
                }
                else if(checkSum < sum)
                {
                    l++;
                }
                else
                {
                    r--;
                }
            }
        }
        return false;
    }

    public static void main(String[] args)
    {
        FindTriplet.find3Numbers(new int[] {1, 2, 3,  4, 5}, 9);
    }
}
