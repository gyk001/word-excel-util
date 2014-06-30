package cn.guoyukun.pdm2pdf.model;

import java.util.List;

public class TableTree extends Model {
	private static final long serialVersionUID = 1900385039596998649L;

	// 和父表关系，如 1..1, 1..n
	private String rel;

	private List<TableTree> tableTrees;
	
	public TableTree() {
		super();
	}

	public TableTree(String code) {
		super(code);
	}

	public TableTree(String code, String name) {
		super(code, name);
	}

	public List<TableTree> getSubTables() {
		return tableTrees;
	}

	public void setSubTables(List<TableTree> subTables) {
		this.tableTrees = subTables;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((rel == null) ? 0 : rel.hashCode());
		result = prime * result
				+ ((tableTrees == null) ? 0 : tableTrees.hashCode());
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
		TableTree other = (TableTree) obj;
		if (rel == null) {
			if (other.rel != null)
				return false;
		} else if (!rel.equals(other.rel))
			return false;
		if (tableTrees == null) {
			if (other.tableTrees != null)
				return false;
		} else if (!tableTrees.equals(other.tableTrees))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 3;
		return "TableTree [rel="
				+ rel
				+ ", subTables="
				+ (tableTrees != null ? tableTrees.subList(0,
						Math.min(tableTrees.size(), maxLen)) : null) + "]";
	}

}
