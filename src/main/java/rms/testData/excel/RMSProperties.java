package rms.testData.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum RMSProperties {
	RMS_APPLICATION_URL("rms.application.url", null, Type.String),
	EXCEL_PATH("excel.path", null, Type.String),
	EXCEL_PO_SHEET_NAME("excel.poSheetName", null,Type.String),
	EXCEL_ITEM_DATA_SHEET_NAME("excel.itemDataSheetName", null,Type.String),
	
	EXCEL_PO_SHEET_COLUMN_SCENARIO_ID("excel.po.sheet.column.scenarioID", null, Type.String),
	EXCEL_PO_SHEET_COLUMN_SCENARIO_DESCRIPTION("excel.po.sheet.column.scenarioDescription", null, Type.String),
	EXCEL_PO_SHEET_COLUMN_SUPPLIER_ID("excel.po.sheet.column.supplierID", null, Type.String),
	EXCEL_PO_SHEET_COLUMN_WAREHOUSE_NUMBER("excel.po.sheet.column.wareHouseNumber", null, Type.String),
	EXCEL_PO_SHEET_COLUMN_PO_NUMBER("excel.po.sheet.column.poNumber", null, Type.String),
	EXCEL_ITEM_SHEET_COLUMN_ITEM_ID("excel.item.sheet.column.itemID", null, Type.String),
	EXCEL_ITEM_SHEET_COLUMN_ITEM_QUANTITY("excel.item.sheet.column.itemQuantity", null, Type.String);
	
	private String key;
	private Object value;
	private Type type;

	private enum Type {
		Integer, String, Boolean, Long
	};

	private RMSProperties(String key, Object value, Type type) {
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

		for (RMSProperties property : RMSProperties.values()) {
			String valFromFile = p.getProperty(property.key);

			if (valFromFile != null) {
				if (property.type == Type.Integer) {
					property.setProperty(Integer.parseInt(valFromFile));
				} else if (property.type == Type.Long) {
					property.setProperty(Long.parseLong(valFromFile));
				} else if (property.type == Type.Boolean) {
					property.setProperty(Boolean.parseBoolean(valFromFile));
				} else {
					property.setProperty(valFromFile);
				}

			}

		}
	}
	
}
