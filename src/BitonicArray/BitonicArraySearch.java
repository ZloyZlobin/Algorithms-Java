package BitonicArray;

public class BitonicArraySearch
{
    private static int findMiddleIndex(int[] arr, int l, int r)
    {
        int mid = (r + l)/2;
        if(arr[mid] > arr[mid -1] && arr[mid] > arr[mid + 1])
            return mid;
        else if(arr[mid] > arr[mid - 1] && arr[mid] < arr[mid + 1])
            return findMiddleIndex(arr, mid, r);
        else if(arr[mid] < arr[mid - 1] && arr[mid] >arr[mid + 1])
            return findMiddleIndex(arr, l, mid);
        return mid;
    }

    private static int descendingBinarySearch(int[] arr, int l, int r, int key)
    {
        if(l > r)
            return -1;

        int mid = (l + r)/2;
        if(arr[mid] == key)
            return mid;
        else if(arr[mid] > key)
            return descendingBinarySearch(arr, mid + 1, r, key);
        else
            return descendingBinarySearch(arr, l, mid - 1, key);
    }

    private static int ascendingBinarySearch(int[] arr, int l, int r, int key)
    {
        if(l > r)
            return -1;
        int mid = (l + r)/2;
        if(arr[mid] == key)
            return mid;
        else if(arr[mid] > key)
            return ascendingBinarySearch(arr, l, mid - 1, key);
        else
            return ascendingBinarySearch(arr, mid + 1, r, key);
    }

    public static int searchBitonic(int[] arr, int key)
    {
        int mid = findMiddleIndex(arr, 0, arr.length - 1);
        int result = ascendingBinarySearch(arr, 0, mid, key);
        if(result == -1)
            result = descendingBinarySearch(arr, mid, arr.length - 1, key);
        return result;
    }

    public static void main(String[] args)
    {
        int arr[] = {-8, 1, 2, 3, 4, 5, -2, -3};
        int key = 1;
        int index = BitonicArraySearch.searchBitonic(arr, key);
        System.out.print(index);
    }
}
