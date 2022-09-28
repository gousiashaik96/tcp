package com.ucp.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ArrayUtil {
	public static Object[] appendtoArray(Object[] arr1,Object...arr2){
		Object[] result = ArrayUtils.addAll(arr2, arr1);
		return result;
	}
	
	public static List<String> convertUsingAsList(String commaSeparatedStr)
	{
		List<String> result = new ArrayList<String>();
		if(StringUtils.isNotEmpty(commaSeparatedStr)) {
		    String[] commaSeparatedArr = commaSeparatedStr.split("\\s*,\\s*");
		    result = Arrays.stream(commaSeparatedArr).collect(Collectors.toList());
		}
	    return result;
	}
	
	public static List<String> convertUsingAsList(String commaSeparatedStr, String delimiter)
	{
		List<String> result = new ArrayList<String>();
		if(StringUtils.isNotEmpty(commaSeparatedStr)) {
		    String[] commaSeparatedArr = commaSeparatedStr.split(delimiter);
		    result = Arrays.stream(commaSeparatedArr).collect(Collectors.toList());
		}
	    return result;
	}
}
