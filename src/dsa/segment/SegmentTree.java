package dsa.segment;

public class SegmentTree {

    int mark  = 0;
    String name = "markandey";

    void waveForm(int[] A){

        int n = A.length;

        for(int i = 0; i<n-1; i++){

            if(i%2 == 0){
                if(A[i] < A[i+1]){
                    swap(A, i, i+1);
                }
            }
            else{
                if(A[i] > A[i+1]){
                    swap(A, i, i+1);
                }
            }
        }
    }

    void swap(int[] A, int  i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

}
