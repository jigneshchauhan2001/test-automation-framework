package framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public enum SFTPproperties {

	SFTP_HOST("sftp.host", "",Type.String),
	SFTP_PORT("sftp.port", null, Type.Integer),
	SFTP_USERNAME("sftp.username", null,Type.String),
	SFTP_PASSWORD("sftp.password", null,Type.String),
	SFTP_LOCAL_DIRECTORY("sftp.localdirectory", "test-data\\SFTP",Type.String),
	SFTP_REMOTE_DIRECTORY("sftp.remotedirectory", "/five.admin/EXPORT",Type.String),
	SFTP_RTLOG_REMOTE_DIRECTORY("sftp.remotedirectory", "/five.admin/COMMAND/",Type.String),
	SFTP_FILE_PREFIX("sftp.file.prefix", "REGPC",Type.String),
	SFTP_ZIP_FILE_EXTENSION("sftp.zipfile.extension", ".zip",Type.String),
	SFTP_FILE_DATEFORMAT("sftp.file.dateformat", "M/d/yyyy",Type.String);
	
	private String key;
	private Object value;
	private Type type;

	private enum Type {
		Integer, String, Boolean, Long
	};

	private SFTPproperties(String key, Object value, Type type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}

	public void setProperty(Object value) {
		this.value = value;
	}

	public Object getProperty() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	public Integer toInteger() {
		return Integer.parseInt(this.value.toString());
	}

	public Long toLong() {
		return Long.parseLong(this.value.toString());
	}

	public Boolean toBoolean() {
		return Boolean.parseBoolean(this.value.toString());
	}

	public static void loadProperties(String testPropertyFile) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(testPropertyFile));

		for (SFTPproperties testProperty : SFTPproperties.values()) {
			String valFromFile = p.getProperty(testProperty.key);

			if (valFromFile != null) {
				if (testProperty.type == Type.Integer) {
					testProperty.setProperty(Integer.parseInt(valFromFile));
				} else if (testProperty.type == Type.Long) {
					testProperty.setProperty(Long.parseLong(valFromFile));
				} else if (testProperty.type == Type.Boolean) {
					testProperty.setProperty(Boolean.parseBoolean(valFromFile));
				} else {
					testProperty.setProperty(valFromFile);
				}

			}

		}
	}
}
