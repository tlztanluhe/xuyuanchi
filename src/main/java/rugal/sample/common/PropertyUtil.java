package rugal.sample.common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



/****
 * 初始化读取property属性 json格式
 * @author 伟
 *
 */
public class PropertyUtil {

	public static ConcurrentMap<String, Object> propertiesMap = new ConcurrentHashMap<String, Object>();

	static {
		initProperties();

	}

	public static void initProperties() {
		JSONParser parser = new JSONParser();

		try {

			InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream("systemConfig.json");
			InputStreamReader inReader = new InputStreamReader(in);
			Object obj = parser.parse(inReader);

			JSONObject jsonObject = (JSONObject) obj;

			Set<String> keyStringSet = jsonObject.keySet();
			for (String keyString : keyStringSet) {
				Object valueObj = jsonObject.get(keyString);
				propertiesMap.put(keyString, valueObj);
			}

			System.out.println(propertiesMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
