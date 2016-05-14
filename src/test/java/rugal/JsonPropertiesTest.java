package rugal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = config.ApplicationContext.class)
public class JsonPropertiesTest {

	
	@Test
	public void test_properties() throws IOException{
		InputStream in = JsonPropertiesTest.class.getClassLoader()
                .getResourceAsStream("systemConfig.json");
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		byte[] bArray = new byte[1024];
		while(-1 != in.read(bArray)){
			b.write(bArray);
		}
		Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
		Map<String,Object> jsonMap = new Gson().fromJson(new String(b.toByteArray()), type);
		System.out.println(jsonMap);
	}
	
	@Test
	public void test_properties_2(){
		
		ConcurrentMap<String,Object> propertiesMap = new ConcurrentHashMap<String,Object>();
		
		 JSONParser parser = new JSONParser();
		 	
		 
	        try {
	 
	        	InputStream in = JsonPropertiesTest.class.getClassLoader().getResourceAsStream("systemConfig.json");
	        	InputStreamReader inReader = new InputStreamReader(in);
	        	Object obj = parser.parse(inReader);
	 
	            
	            JSONObject jsonObject = (JSONObject) obj;
	 
	            
	            Set<String> keyStringSet = jsonObject.keySet();
	            for(String keyString : keyStringSet){
	            	Object valueObj = jsonObject.get(keyString);
	            	propertiesMap.put(keyString, valueObj);
	            }
	            
	            System.out.println(propertiesMap);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
