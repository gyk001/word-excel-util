package cn.guoyukun.word.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author GuoYukun 
 * @version 2014-06-22
 */
public class PropertiesUtil {

	/**
	 * 把properties里的内容加载到map里
	 * @return
	 */
	public static Map<String, String> loadToMap(String properties) {
		Map<String, String> map = new HashMap<String, String>();
		Properties props = new Properties();
		try {
			props.load(PropertiesUtil.class
					.getResourceAsStream(properties));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 返回Properties中包含的key-value的Set视图
		Set<Entry<Object, Object>> set = props.entrySet();
		// 返回在此Set中的元素上进行迭代的迭代器
		Iterator<Map.Entry<Object, Object>> it = set.iterator();
		String key = null, value = null;
		// 循环取出key-value
		while (it.hasNext()) {

			Entry<Object, Object> entry = it.next();

			key = String.valueOf(entry.getKey());
			value = String.valueOf(entry.getValue());

			key = key == null ? key : key.trim().toUpperCase();
			value = value == null ? value : value.trim().toUpperCase();
			// 将key-value放入map中
			map.put(key, value);
		}
		return map;

	}
}
