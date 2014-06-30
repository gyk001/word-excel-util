package cn.guoyukun.pdm2pdf.model.json;

import java.util.List;

import cn.guoyukun.pdm2pdf.model.Model;

public class JDomain extends Model{
	private static final long serialVersionUID = 1704533876641424935L;

	private List<String> bizs;

	public List<String> getBizs() {
		return bizs;
	}

	public void setBizs(List<String> bizs) {
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
		JDomain other = (JDomain) obj;
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
		return "JDomain [bizs="
				+ (bizs != null ? bizs
						.subList(0, Math.min(bizs.size(), maxLen)) : null)
				+ "]";
	}
	
}
