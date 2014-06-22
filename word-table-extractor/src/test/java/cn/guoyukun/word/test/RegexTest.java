package cn.guoyukun.word.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static void main(String[] args) {
		replace("1住院诊疗摆药/发药明细记录表");
		replace("1.7住院诊疗摆药/发药明细记录表");
		replace("1.2.7住院诊疗摆药/发药明细记录表");
		replace("1.2.1.7住院诊疗摆药/发药明细记录表");
		
		match("精神康复措施代码表（CV0600209）");
		match("精神康复措施代码表（GBT0600209）");
//		>> 国籍代码（GBT2659） : null
//		>> RH血型代码表（CV9900006） : null
//		>> 联系人电话类别代码表（CV040001） : null
//		>> 标志是否代码表（CV9900014） : null
//		>> 避孕方式代码表（CV0600211） : null
//		>> 医疗费用来源类别代码表（CV0710003） : null
//		>> 有无未说明代码表（CV9900024） : null
//		>> 饮水类别代码表（CV0300115） : null
//		>> 燃料类型类别代码表（CV0300303） : null
//		>> 厨房排风设施类别代码（CV0300302） : null
//		>> 家庭禽畜栏类别代码表（CV9900023） : null
//		>> 厕所类别代码表（CV0300304） : null
		match("用药途径代码表（CV0600102）");
		match("民族代码 （GB3304）");
		match("饮水类别代码表CV0300115");
	}

	static void replace(String orig){
		System.out.println(orig.replaceFirst("\\d[\\\\.\\d]*", ""));
	}
	
	static void match(String s){
		Pattern p = Pattern.compile("(.*)（?((CV|GBT|GB)\\d+)）?");
		Matcher matcher = p.matcher(s);
		if(matcher.find()){
			System.out.println(matcher.group(2));
		}else{
			System.out.println(false);
		}
	
	}
}
