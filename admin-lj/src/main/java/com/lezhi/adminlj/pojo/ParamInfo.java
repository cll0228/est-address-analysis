package com.lezhi.adminlj.pojo;

/**
 * Created by chendl on 2017/2/23.
 */
public class ParamInfo {
	private String dataId;
	private String type;
	private String siteType;
	private String keyword;
	private String keyId;
	private String keyType;
	private String a;
	private String l;
	private String p;
    private String t;
    private String c;
    private String jdh;
	@Override
	public String toString() {
		return "ParamInfo [dataId=" + dataId + ", type=" + type + ",keyword=" + keyword + ", keyId="
				+ keyId + ", keyType=" + keyType + ", a="
				+ a + ", l=" + l + ", p="
				+ p + ", t=" + t + ", c=" + c + "]";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJdh() {
		return jdh;
	}

	public void setJdh(String jdh) {
		this.jdh = jdh;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
}
