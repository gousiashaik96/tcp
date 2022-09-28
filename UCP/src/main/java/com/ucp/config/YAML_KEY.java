package com.ucp.config;

public enum YAML_KEY {
	 IILEVEL_PM("Pm"), DCTEMAIL("dctEmail");
	private String keyStr = "";

	YAML_KEY(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public static YAML_KEY[] getThirdPartys() {
		return new YAML_KEY[] { IILEVEL_PM,DCTEMAIL};
	}
}
