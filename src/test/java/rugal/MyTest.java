package rugal;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyTest {

	@Test
	public void test() {
		
		String url = "/www.baidu.com////ww/g";
		String newUrl = url.replaceAll("/+", "/");
		System.out.println(newUrl);
	}

}
