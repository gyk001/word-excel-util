package cn.guoyukun.word.test;

public class RegexTest {
	public static void main(String[] args) {
		replace("1住院诊疗摆药/发药明细记录表");
		replace("1.7住院诊疗摆药/发药明细记录表");
		replace("1.2.7住院诊疗摆药/发药明细记录表");
		replace("1.2.1.7住院诊疗摆药/发药明细记录表");
	}

	static void replace(String orig){
		System.out.println(orig.replaceFirst("\\d[\\\\.\\d]*", ""));
	}
}
