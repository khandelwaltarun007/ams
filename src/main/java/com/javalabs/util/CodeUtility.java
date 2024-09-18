package com.javalabs.util;

import java.beans.PropertyDescriptor;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.BeanUtils;

public class CodeUtility {
	
	public static String getStringCellValue(Cell cell) {
		return cell == null ? "" : cell.getStringCellValue();
	}

	public static boolean getBooleanCellValue(Cell cell) {
		return cell != null && cell.getBooleanCellValue();
	}
	
	public static LocalDate getDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
                break;
            case STRING:
                try {
                    return LocalDate.parse(cell.getStringCellValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return null;
    }
	
	public static String encodePassword(String password) {
		return new String(Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)));
	}
	
	public static String decodePassword(String password) {
		return new String(Base64.getDecoder().decode(password.getBytes(StandardCharsets.UTF_8)));
	}
	
	public static void copyProperties(Object source, Object target, String... properties) {
	    Arrays.stream(properties)
	          .forEach(property -> {
	              // Split property name into nested properties if applicable
	              String[] nestedProperties = property.split("\\.");

	              // Get last property name in case of nested properties
	              String finalProperty = nestedProperties[nestedProperties.length - 1];

	              // Recursively copy property
	              copyProperty(source, target, nestedProperties, finalProperty);
	          });
	}

	private static void copyProperty(Object source, Object target, String[] nestedProperties, String finalProperty) {
	    Object sourceValue = getValue(source, nestedProperties);
	    setValue(target, finalProperty, sourceValue);
	}

	private static Object getValue(Object source, String[] nestedProperties) {
	    Object value = source;
	    for (String propertyName : nestedProperties) {
	        value = getProperty(value, propertyName);
	        if (value == null) {
	            break;
	        }
	    }
	    return value;
	}

	private static Object getProperty(Object source, String propertyName) {
	    try {
	        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(source.getClass(), propertyName);
	        if (descriptor != null) {
	            return descriptor.getReadMethod().invoke(source);
	        }
	    } catch (Exception e) {
	        // Handle exceptions appropriately
	        throw new RuntimeException("Error getting property " + propertyName + " from source object", e);
	    }
	    return null;
	}

	private static void setValue(Object target, String propertyName, Object value) {
	    try {
	        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(target.getClass(), propertyName);
	        if (descriptor != null) {
	            descriptor.getWriteMethod().invoke(target, value);
	        }
	    } catch (Exception e) {
	        // Handle exceptions appropriately
	        throw new RuntimeException("Error setting property " + propertyName + " on target object", e);
	    }
	}


}
