package cn.guoyukun.pdm2pdf.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.com.sinosoft.ie.data.reverse.vo.ColInfo;

public class TableInfo extends Model {
	private static final long serialVersionUID = 6037601412419170041L;
	private Map<String, ColInfo>  columns = new LinkedHashMap<String, ColInfo>();
	private Map<String, ColInfo>  headerColumns = new LinkedHashMap<String, ColInfo>();
	private Map<String, ColInfo>  footerColumns = new LinkedHashMap<String, ColInfo>();
	
	public Map<String, ColInfo> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, ColInfo> columns) {
		this.columns = columns;
	}
	public Map<String, ColInfo> getHeaderColumns() {
		return headerColumns;
	}
	public void setHeaderColumns(Map<String, ColInfo> headerColumns) {
		this.headerColumns = headerColumns;
	}
	public Map<String, ColInfo> getFooterColumns() {
		return footerColumns;
	}
	public void setFooterColumns(Map<String, ColInfo> footerColumns) {
		this.footerColumns = footerColumns;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result
				+ ((footerColumns == null) ? 0 : footerColumns.hashCode());
		result = prime * result
				+ ((headerColumns == null) ? 0 : headerColumns.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableInfo other = (TableInfo) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (footerColumns == null) {
			if (other.footerColumns != null)
				return false;
		} else if (!footerColumns.equals(other.footerColumns))
			return false;
		if (headerColumns == null) {
			if (other.headerColumns != null)
				return false;
		} else if (!headerColumns.equals(other.headerColumns))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final int maxLen = 3;
		return "TableInfo [columns="
				+ (columns != null ? toString(columns.entrySet(), maxLen)
						: null)
				+ ", headerColumns="
				+ (headerColumns != null ? toString(headerColumns.entrySet(),
						maxLen) : null)
				+ ", footerColumns="
				+ (footerColumns != null ? toString(footerColumns.entrySet(),
						maxLen) : null) + "]";
	}
	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
