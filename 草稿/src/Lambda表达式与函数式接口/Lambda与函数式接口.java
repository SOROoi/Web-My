package Lambda表达式与函数式接口;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/*
	1. 函数式接口是什么？
		
		函数式接口是Java编程语言中的一个概念，它指的是只包含一个抽象方法的接口。在Java 8之后引入的Lambda表达式和函数式编程功能中，函数式接口变得非常重要，因为它们可以用作Lambda表达式的类型。
		
		函数式接口具有以下特征：
		
		只包含一个抽象方法：函数式接口只能定义一个未实现的抽象方法。这是它们的核心特征，因为Lambda表达式需要与这个抽象方法的签名匹配。
		
		可以包含默认方法和静态方法：函数式接口可以包含默认方法（使用default关键字定义）和静态方法（使用static关键字定义），这些方法可以有方法体，不会破坏接口的函数式特性。
		
		可以使用@FunctionalInterface注解：虽然不是必需的，但可以使用@FunctionalInterface注解来显式声明一个接口为函数式接口。这有助于编译器检查该接口是否符合函数式接口的规则。
		
		Java标准库中有一些内置的函数式接口，例如java.util.function包中的Function、Predicate和Consumer等。您还可以创建自己的函数式接口，只需确保接口只包含一个抽象方法即可。
		
		使用函数式接口和Lambda表达式，Java编程变得更加函数式，更容易编写简洁的代码，特别是在涉及集合操作和并行处理等方面。
		
		
		
	2.函数式接口与java7前定义的接口不一样对吗？
		
		是的，函数式接口与Java 7之前定义的接口有一些重要的不同之处。主要区别包括以下几点：
		
		抽象方法数量：函数式接口只包含一个抽象方法，而Java 7之前的接口可以包含多个抽象方法。
		
		Lambda表达式：函数式接口的引入是为了支持Lambda表达式。Lambda表达式是一种简洁地表示匿名函数的方式，因此函数式接口的设计目标是确保接口只有一个抽象方法，从而使Lambda表达式的类型推断变得明确和可靠。
		
		默认方法和静态方法：函数式接口可以包含默认方法和静态方法，而Java 7之前的接口不能包含默认实现。这允许在函数式接口中添加额外的方法，而不会破坏现有代码。
		
		@FunctionalInterface注解：虽然不是必需的，但可以使用@FunctionalInterface注解显式声明一个接口为函数式接口。这个注解有助于编译器检查接口是否满足函数式接口的条件。
		
		总之，函数式接口是Java 8引入的概念，它与Java 7之前的接口有一些关键区别，主要是为了支持Lambda表达式和函数式编程的特性。这些接口的设计目标是促进更简洁、更易读的代码编写，以便利用Java中的函数式编程功能。
		
		
		
	3.java8的lambda表达式
		
		Java 8引入了Lambda表达式，这是一项重要的语言特性，它使Java编程更加简洁和函数式。Lambda表达式允许您以更紧凑的方式定义匿名函数，通常用于函数式编程、集合操作和多线程编程等场景。以下是Lambda表达式的一般语法和用法：
		
		Lambda表达式的一般语法如下：
		
		(parameter) -> expression
		其中：
		
		parameter：表示Lambda表达式的参数列表。
		->：被称为箭头操作符，分隔参数列表和Lambda表达式的主体。
		expression：表示Lambda表达式的主体，通常是一个表达式，可以包含对参数的操作。
		Lambda表达式的主体可以是单个表达式或一组语句。如果是单个表达式，不需要使用大括号 {} 包围，而如果是一组语句，则需要使用大括号 {} 包围，并且可能需要使用 return 语句来返回值。
		
		以下是一些Lambda表达式的示例：
		
		Lambda表达式作为一个简单函数的例子：
		(x, y) -> x + y
		Lambda表达式作为一个具有多个语句的函数的例子：
		(x, y) -> {
		    int sum = x + y;
		    return sum;
		}
		Lambda表达式通常与函数式接口一起使用，函数式接口是只包含一个抽象方法的接口。Lambda表达式可以与函数式接口的方法签名匹配，从而将Lambda表达式赋值给接口类型的变量，或者传递给接受函数式接口的方法。Java标准库中的java.util.function包包含了一些常用的函数式接口，如Function、Predicate和Consumer等，可以与Lambda表达式一起使用。
		
		Lambda表达式的引入使Java编程更加现代化和函数式，减少了冗余的代码，并提供了更强大的工具来处理集合和并行编程等任务。
		
		Lambda表达式通常与函数式接口一起使用，举个例子
		
		Lambda表达式通常与函数式接口一起使用，因为Lambda表达式的目的是提供一个简洁的方式来实现函数式接口的抽象方法。下面是一个示例，演示了Lambda表达式如何与java.util.function.Predicate函数式接口一起使用。
		
		假设您有一个整数列表，并且您想过滤出所有偶数。这可以通过Lambda表达式和Predicate接口来实现：
		
		import java.util.ArrayList;
		import java.util.List;
		import java.util.function.Predicate;
		
		public class LambdaExample {
		    public static void main(String[] args) {
		        List<Integer> numbers = new ArrayList<>();
		        numbers.add(1);
		        numbers.add(2);
		        numbers.add(3);
		        numbers.add(4);
		        numbers.add(5);
		
		        // 使用Lambda表达式和Predicate来过滤偶数
		        List<Integer> evenNumbers = filter(numbers, (Integer n) -> n % 2 == 0);
		
		        System.out.println("偶数列表: " + evenNumbers);
		    }
		
		    // 使用Predicate接口和Lambda表达式来过滤列表
		    public static List<Integer> filter(List<Integer> list, Predicate<Integer> predicate) {
		        List<Integer> result = new ArrayList<>();
		        for (Integer number : list) {
		            if (predicate.test(number)) {
		                result.add(number);
		            }
		        }
		        return result;
		    }
		}
		在这个示例中，我们定义了一个filter方法，该方法接受一个整数列表和一个Predicate接口的Lambda表达式作为参数。Lambda表达式 (Integer n) -> n % 2 == 0 表示一个函数，它接受一个整数参数并检查它是否为偶数。然后，我们使用这个Lambda表达式来过滤整数列表中的偶数，并将结果存储在evenNumbers列表中。
		
		Lambda表达式的使用使代码更加紧凑和可读，同时允许我们将功能作为参数传递给方法。这是Java 8引入的函数式编程特性的一个强大示例。
		
		
		
	4.意思是lambda表达式(Integer n) -> n % 2 == 0 成为了predicate.test(Integer)方法了吗？
		
		是的，Lambda表达式(Integer n) -> n % 2 == 0 实际上成为了Predicate接口的test(Integer)方法的实现。在这个Lambda表达式中，(Integer n)表示接受一个整数参数 n，并在Lambda表达式的主体中进行了条件判断 n % 2 == 0，该条件判断检查整数是否为偶数。
		
		当我们使用这个Lambda表达式来调用predicate.test(number)时，它实际上执行了Lambda表达式中的条件判断，检查传递给 test 方法的整数是否满足偶数条件。这就是Lambda表达式与函数式接口的关联：Lambda表达式提供了函数体，它实现了函数式接口中的抽象方法。
		
		因此，Lambda表达式可以看作是实现函数式接口抽象方法的一种方式，使我们能够以更紧凑、可读的方式传递功能。在上面的示例中，Lambda表达式 (Integer n) -> n % 2 == 0 就是 Predicate<Integer> 接口的实现，它用于测试整数是否为偶数。
		
		未实现Predicate接口的test(Integer)方法是怎样的
		
		在Java中，Predicate接口是一个函数式接口，它定义了一个名为test的抽象方法，该方法接受一个参数并返回一个布尔值。该方法用于执行某种条件检查，通常用于过滤或测试元素。
		
		以下是Predicate接口的简化定义：
		
		@FunctionalInterface
		public interface Predicate<T> {
		    boolean test(T t);
		}
		这个接口定义了一个泛型类型 T，并规定了一个抽象方法 test(T t)，该方法接受一个参数 t，并返回一个布尔值，表示对 t 执行某种条件测试的结果。
		
		如果您想要实现一个不使用Predicate接口的自定义条件测试方法，可以按照以下模板创建一个类似的函数式接口和方法：
	
		@FunctionalInterface
		public interface MyCondition<T> {
		    boolean test(T t);
		}
		
		public class MyFilter {
		    public static <T> List<T> filter(List<T> list, MyCondition<T> condition) {
		        List<T> result = new ArrayList<>();
		        for (T item : list) {
		            if (condition.test(item)) {
		                result.add(item);
		            }
		        }
		        return result;
		    }
		}
		在上面的示例中，我们首先定义了一个自定义的函数式接口MyCondition，它与Predicate接口非常相似，都包含了一个test方法。然后，我们创建了一个MyFilter类，其中包含一个filter方法，它接受一个列表和一个自定义条件测试的实现（通过MyCondition接口）。这个方法使用传递的条件测试来过滤列表中的元素，并返回满足条件的元素列表。
		
		这种方法允许您创建自己的条件测试方法，而不依赖于Predicate接口，同时仍然能够使用Lambda表达式和函数式编程的概念来实现条件测试。
		
		
		
	5.可以说lambda表达式在java8中就是常用于实现函数式接口的方法吗？
		
		是的，Lambda表达式在Java 8中通常用于实现函数式接口的方法。Lambda表达式是一种用于创建匿名函数的简洁语法，而函数式接口是只包含一个抽象方法的接口。Lambda表达式的主要目的之一是提供一种方便的方式来实现函数式接口的抽象方法。
		
		在Java 8之前，要实现接口中的抽象方法，通常需要创建一个匿名类的实例，这样的代码相对冗长。但是，Java 8引入了Lambda表达式，使得以更紧凑和易读的方式来实现函数式接口成为可能。这使得函数式编程概念更容易在Java中应用，特别是在集合操作、多线程编程和事件处理等方面。
		
		因此，Lambda表达式在Java 8中通常用于以下情况：
		
		实现函数式接口的抽象方法。
		作为参数传递给接受函数式接口的方法。
		用于创建简洁的、内联的函数，以便在需要函数的地方使用。
		Lambda表达式是Java 8引入的一个强大特性，它极大地提高了Java编程的表达能力和灵活性。
 */

public class Lambda与函数式接口 {
	 public static void main(String[] args) {
	        List<Integer> numbers = new ArrayList<>();
	        numbers.add(1);
	        numbers.add(2);
	        numbers.add(3);
	        numbers.add(4);
	        numbers.add(5);
	
	        // 使用Lambda表达式和Predicate来过滤偶数
	        List<Integer> evenNumbers = filter(numbers, (Integer n) -> n % 2 == 0);
	
	        System.out.println("偶数列表: " + evenNumbers);
	    }
	
	    // 使用Predicate接口和Lambda表达式来过滤列表
	    public static List<Integer> filter(List<Integer> list, Predicate<Integer> predicate) {
	        List<Integer> result = new ArrayList<>();
	        for (Integer number : list) {
	            if (predicate.test(number)) {
	                result.add(number);
	            }
	        }
	        return result;
	    }
}
