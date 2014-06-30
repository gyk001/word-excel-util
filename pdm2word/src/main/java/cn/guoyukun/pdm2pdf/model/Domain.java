package cn.guoyukun.pdm2pdf.model;

import java.util.List;

public class Domain extends Model {

	private static final long serialVersionUID = 4742465839951203749L;
	
	private List<Biz> bizs;

	public List<Biz> getBizs() {
		return bizs;
	}

	public void setBizs(List<Biz> bizs) {
		this.bizs = bizs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bizs == null) ? 0 : bizs.hashCode());
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
		Domain other = (Domain) obj;
		if (bizs == null) {
			if (other.bizs != null)
				return false;
		} else if (!bizs.equals(other.bizs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final int maxLen = 3;
		return "Domain [bizs="
				+ (bizs != null ? bizs
						.subList(0, Math.min(bizs.size(), maxLen)) : null)
				+ "]";
	}

}
