package 小题目;

/*
	打印杨辉三角形(行数可以键盘录入)
	1
	1	1
	1	2	1
	1	3	3	1
	1	4	6	4	1
	1	5	10	10	5	1
*/
public class YangHui {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int row = 11;
		int[][] arr = new int[row][]; // 二维数组
		for (int i = 0; i < row; i++) {
			arr[i] = new int[i + 1]; // 为每个二维数组定长度
			for (int j = 0; j < arr[i].length; j++) {
				if (j == 0 || j == i) { // 首尾等于1
					arr[i][j] = 1;	
				} else {
					arr[i][j] = arr[i - 1][j - 1] + arr[i - 1][j];
				}
			}
		}
		for(int[] ar:arr) {
			for(int i:ar) {
				System.out.print(i+"\t");
			}
			System.out.println();
		}
	}

}
