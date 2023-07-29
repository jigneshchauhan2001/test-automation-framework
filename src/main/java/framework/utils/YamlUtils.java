package framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class YamlUtils {

	/**
	 * Description : loads the all test-data from given yaml file path into object.
	 * Then based on the testMethodName(key) it will return data object (Object)
	 * specific to that testMethodName only. Later you can cast it to your mapping
	 * class like (LoginPageTestData).
	 * 
	 * @param key
	 * @param yamlFilePath
	 * @return
	 */
	public static Object loadYaml(String key, String yamlFilePath) {
		try {
			Yaml yaml = new Yaml();
			@SuppressWarnings({ "rawtypes" })
			Map map = (Map) yaml.load(new FileInputStream(new File(yamlFilePath)));
			return map.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	

	
	/**
	 * Creates a YAML instance with a custom representer that excludes properties with null values during serialization.
	 * The YAML instance is configured with default flow style set to BLOCK.
	 *
	 * @return The YAML instance with the custom representer.
	 */
	private static Yaml createYamlWithCustomRepresenter() {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(new CustomRepresenter(), options);
		return yaml;
	}

	/**
	 * A custom representer class that extends SnakeYAML's Representer to exclude properties with null values or zero values
	 * during serialization. This method overrides the default behavior to check for null values or zero values and return null
	 * to skip the representation of such properties.
	 *
	 * @param javaBean      The Java bean being represented.
	 * @param property      The property of the Java bean.
	 * @param propertyValue The value of the property being represented.
	 * @param customTag     The custom tag for the property.
	 * @return A NodeTuple representing the Java bean property or null if the propertyValue is null.
	 */
	private static class CustomRepresenter extends Representer {
		@Override
		protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,
				Tag customTag) {
			if (propertyValue == null || isZeroValue(propertyValue)) {
				return null; // Exclude properties with null values or zero values
			}
			return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
		}
		
		 // Method to check if the propertyValue is a numeric zero
	    private boolean isZeroValue(Object propertyValue) {
	        if (propertyValue instanceof Number) {
	            double doubleValue = ((Number) propertyValue).doubleValue();
	            return doubleValue == 0.0;
	        }
	        return false;
	    }
	}

	 

	/**
	 * Description: Update the YAML file with data object based on the provided
	 * testcase name.
	 *
	 * @param key          The testcase name (key in the YAML file).
	 * @param yamlFilePath The path of the YAML file to update.
	 * @param data         The data object (e.g., LoginPageTestData) to be updated
	 *                     in the YAML file.
	 * @return True if the update was successful, false otherwise.
	 */
	public static void updateYaml(String key, String yamlFilePath, Object data) {
		try {
			// Read the existing YAML content from the file
			String yamlContent;
			try (FileInputStream inputStream = new FileInputStream(yamlFilePath)) {
				byte[] buffer = new byte[inputStream.available()];
				inputStream.read(buffer);
				yamlContent = new String(buffer, StandardCharsets.UTF_8);
			}

			// Parse the YAML content into a map
			Yaml yaml = createYamlWithCustomRepresenter();
			Map<String, Object> yamlMap = yaml.load(yamlContent);

			// Update the map with the new data
			yamlMap.put(key, data);

			// Serialize the updated map back to YAML
			String updatedYamlContent = yaml.dumpAsMap(yamlMap);

			// Write the updated YAML content back to the file
			try (FileWriter writer = new FileWriter(yamlFilePath)) {
				writer.write(updatedYamlContent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
