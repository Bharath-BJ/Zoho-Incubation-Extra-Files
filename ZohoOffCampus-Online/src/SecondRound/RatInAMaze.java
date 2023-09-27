package SecondRound;

public class RatInAMaze {
    public static void main(String[] args) {
        int[][] maze={{1, 0, 0, 0},
				      {1, 1, 0, 1}, 
				      {1, 1, 0, 0},
				      {0, 1, 1, 1}};
        // int[][] maze={  {1,1,0},
        //                 {0,1,0},
        //                 {0,1,1}};
        int n=maze.length;
        int visited[][]= new int[n][n];
        solve(maze,visited,0,0,"");
    }
    private static void solve(int[][] maze,int[][] visited,int i,int j,String path)
    {
        // System.out.println(i+" "+j);
        // System.out.println(path);
        int n=maze.length;
        // System.out.println(n-1);
        visited[i][j]=1;
        if(i==n-1 && j==n-1)
        {
            System.out.println(path);
            visited[i][j]=0;
            System.out.println("Backtracked");
            return;
        }
        if(i-1>=0 && i-1<n && j>=0 && j<n && maze[i-1][j]==1 && visited[i-1][j]!=1)
        {
            solve(maze,visited,i-1,j,path+"T");
            // System.out.println("Top");
        }
        if(i>=0 && i<n && j+1>=0 && j+1<n && maze[i][j+1]==1 && visited[i][j+1]!=1)
        {
            solve(maze,visited,i,j+1,path+"R");
            // System.out.println("Right");
        }
        if(i+1>=0 && i+1<n && j>=0 && j<n && maze[i+1][j]==1 && visited[i+1][j]!=1)
        {
            solve(maze,visited,i+1,j,path+"D");
            // System.out.println("Down");
        }
        if(i>=0 && i<n && j-1>=0 && j-1<n && maze[i][j-1]==1 && visited[i][j-1]!=1)
        {
            solve(maze,visited,i,j-1,path+"L");
            // System.out.println("Left");
        }
        visited[i][j]=0;
        
    }

}

