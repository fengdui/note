/**
 * Pro_65
 * 矩阵中的路径
 * @author FD
 * @date 2016/5/19
 */
public class Pro_65 {
    int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int[][] vis;
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (str.length == 0) {
            return false;
        }
        vis = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i*cols+j] == str[0]) {
                    vis[i][j] = 1;
                    if (dfs(i, j, matrix, str, 1, rows, cols)) {
                        return true;
                    }
                    vis[i][j] = 0;
                }
            }
        }
        return false;
    }
    public boolean dfs(int x, int y, char[] matrix, char[] str, int len, int rows, int cols) {
        if (len == str.length) {
            return true;
        }
        for (int i = 0; i < 4; i++) {
            int xx = x + dir[i][0];
            int yy = y + dir[i][1];
            if (xx >= 0 && xx < rows && yy >= 0 && yy < cols && vis[xx][yy] == 0 && matrix[xx*cols+yy] == str[len]) {
                vis[xx][yy] = 1;
                if (dfs(xx, yy, matrix, str, len+1, rows, cols)) {
                    return true;
                }
                vis[xx][yy] = 0;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new Pro_65().hasPath("ABCESFCSADEE".toCharArray(), 3, 4, "ABCCED".toCharArray()));
    }
}
