package 小题目;

/*
 	有一串字符串String s = "ababab", 这个字符串可以看做由3个"ab"构成,即n=3, L = "ab", s = nL. 
 	现在要求编写一段程序,使用单例模式,输入任意字符串s,输出nL. 如输入: aaaaa 输出 5a ,输入: ababa 输出: 1ababa
 */

public class StringNStr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "abcdac";
		String result;

        int length = s.length();
        System.out.println("length: "+length);
        int count  = 0;

        for (int i= 1;i<=length;i++){
            String[] strings1 = s.split(s.substring(0,i));
            if (strings1.length==0){
                System.out.println("切分到"+i);
	            count = i;
	            break;
            }
        }
        result = length/count + s.substring(0,count);
        System.out.println(result);;
	}

}
