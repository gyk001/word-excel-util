package cn.guoyukun.pdm2pdf.model;

/**
 * 
 * @author GuoYukun (<a href="mailto:gyk001@gmail.com">Gyk001@gmail.com</a>)
 * @version 2013年11月14日
 *
 */
public class ColInfo {
	private String code;
	// 字段名
	private String name;
	// 字段类型
	private String type;
	// 字段描述
	private String desc;
	// 可空？
	private boolean nullable;
	// 字段对应的java类
	private String clazz;
	
	//指示指定列的最大标准宽度，以字符为单位。getColumnDisplaySize(int column) 
	//作废
	private int displaySize;
	//获取指定列的指定列宽。getPrecision
	private int precision;
	//获取指定列的小数点右边的位数。	getScale
	private int scale;
	
	public int getDisplaySize() {
		return displaySize;
	}
	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + displaySize;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (nullable ? 1231 : 1237);
		result = prime * result + precision;
		result = prime * result + scale;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColInfo other = (ColInfo) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (displaySize != other.displaySize)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nullable != other.nullable)
			return false;
		if (precision != other.precision)
			return false;
		if (scale != other.scale)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ColInfo [code=" + code + ", name=" + name + ", type=" + type
				+ ", desc=" + desc + ", nullable=" + nullable + ", clazz="
				+ clazz + ", displaySize=" + displaySize + ", precision="
				+ precision + ", scale=" + scale + "]";
	}
	
	
	
		
}
