package cn.guoyukun.pdm2pdf.model;

import java.util.List;

public class Biz extends Domain {
	private static final long serialVersionUID = -6102485717868718896L;

	// 表层次关系
	private List<TableTree> tableTrees;

	public List<TableTree> getTableTrees() {
		return tableTrees;
	}

	public void setTableTrees(List<TableTree> tableTrees) {
		this.tableTrees = tableTrees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Biz other = (Biz) obj;
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
		return "Biz [tableTrees="
				+ (tableTrees != null ? tableTrees.subList(0,
						Math.min(tableTrees.size(), maxLen)) : null) + "]";
	}

}
