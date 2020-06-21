package 小题目;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num = (int) (Math.random() * 100 + 1);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("请输入你的答案（1-100之间）");
		while(true) {
			int cai = sc.nextInt();
			if(cai==num) {
				System.out.println("恭喜你答对啦！");
				break;
			}
			if(cai < num) {
				System.out.println("正确数字比这个大哦");
				continue;
			}
			if(cai > num) {
				System.out.println("正确数字比这个小哦");
				continue;
			}
		}
	}

}
