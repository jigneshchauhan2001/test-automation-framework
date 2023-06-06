package framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;



public class YamlUtils {

	/**
	 * Description : loads the all test-data from given yaml file path into object. Then based on the testMethodName(key) it will return data object (Object) specific to that testMethodName only. Later you can cast it to your mapping class like (LoginPageTestData).
	 * @param key
	 * @param yamlFilePath
	 * @return
	 */
	public static Object loadYaml(String key, String yamlFilePath) {
		try {
			Yaml yaml=new Yaml();
			@SuppressWarnings({ "rawtypes"})
			Map map = (Map) yaml.load(new FileInputStream(new File(yamlFilePath)));
			return map.get(key);	
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	
	/**
	 * Description : loads the all test-data from given yaml file path into object. As well as returns the entire map. from that map you can get particular testMethod data by entering that testMethodName as key and it will return Object. Later you can cast it to your mapping class like (LoginPageTestData).
	 * @param key
	 * @param yamlFilePath
	 * @return
	 */
	public static Object loadYamlMap(String yamlFilePath) {
		try {
			Yaml yaml=new Yaml();
			@SuppressWarnings("rawtypes")
			Map map=(Map) yaml.load(new FileInputStream(new File(yamlFilePath)));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
