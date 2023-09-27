package SecondRound;

public class PostionOfBit {
    public static void main(String[] args) {
        int M=17;
        int N=9;
        int M1=numToBinary(M);
        int N1=numToBinary(N);
        System.out.println(M1+" "+N1);
        int i=1;
        while(M1>0 && N1>0)
        {
            if(M1%10!=N1%10)
                break;
            M1=M1/10;
            N1=N1/10;
            i++;
        }
        System.out.println(i);
    }
    private static int numToBinary(int n)
    {
        int dig=0;
        int number=0;
        while(n>0)
        {
            number+=n%2*Math.pow(10, dig++);
            n=n/2;
        }
    return number;
    }
}

