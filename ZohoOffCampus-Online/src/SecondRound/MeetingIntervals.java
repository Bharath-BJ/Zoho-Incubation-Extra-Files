package SecondRound;

public class MeetingIntervals {
    public static void main(String[] args) {
        // int[][]  intervals = {{0,30},{5,10},{15,20}};
        // int [][] intervals={{7,10},{2,4}};
        int[][]  intervals = {{15,30},{5,10},{0,5},{29,41}};
        System.out.println(solve(intervals,intervals[0][0],intervals[0][1], 1));
    }
    private static boolean solve(int[][] intervals,int start, int end, int i)
    {
        System.out.println(start+" "+end);
        int n=intervals.length;
        if(i== n)
            return true;
        boolean curr;
        if(intervals[i][1]<=start || intervals[i][0]>=end) 
        {
            curr=true;
            start=intervals[i][0]<start ? intervals[i][0] : start;
            end=intervals[i][1]>end ? intervals[i][1] : end;
        } 
        else
            return false;
    return curr && solve(intervals,start,end,i+1);
    }
}

