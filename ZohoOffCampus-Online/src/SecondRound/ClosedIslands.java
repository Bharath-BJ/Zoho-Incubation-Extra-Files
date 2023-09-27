package SecondRound;

public class ClosedIslands {
    public static void main(String[] args) {
        int mat[][] = {{1, 0, 0},
				       {0, 1, 0},
				       {0, 0, 1}};

        int n=mat.length;
        int[][] visited=new int[n][n];
        
        int islands=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                // System.out.println(i+" "+j);
                if(mat[i][j]==1 && visited[i][j]==0)
                {
                    solve(mat,visited,i,j);
                    islands++;
                }

            }
        }
        System.out.println(islands);
    }
    private static void solve(int[][] mat,int[][] visited,int i,int j)
    {
        int n=mat.length;
        visited[i][j]=1;

        
        for(int a=i-1;a<=i+1;a++)
        {
            for(int b=j-1;b<=j+1;b++)
            {
                if(a>=0 && a<n && b>=0 && b<n && mat[a][b]==1 && visited[a][b]==0)
                    solve(mat,visited,a,b);
            }
        }
    }
}
