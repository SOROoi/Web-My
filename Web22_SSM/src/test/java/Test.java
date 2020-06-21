import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 测试类
 * @author asus pc
 *
 */
public class Test {

	public static void main(String[] args) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		String str = b.encode("123456");
		System.out.println(str);
		System.out.println("长度"+str.length());
	}

}
