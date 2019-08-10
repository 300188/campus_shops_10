package cn.edu.mju.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 *微信用户实体类
 * @author zengcheng.xie
 */
public class WeiXinUser{

	@JsonProperty("openid")
	private String openId;

	@JsonProperty("nickname")
	private String nickName;


	@JsonProperty("sex")
	private int sex;

	@JsonProperty("language")
	private String language;

	@JsonProperty("city")
	private String city;

	@JsonProperty("province")
	private String province;

	@JsonProperty("country")
	private String country;

	@JsonProperty("headimgurl")
	private String headimgurl;

	@JsonProperty("privilege")
	private String[] privilege;


	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "WeiXinUser{" +
				"openId='" + openId + '\'' +
				", nickName='" + nickName + '\'' +
				", sex=" + sex +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", headimgurl='" + headimgurl + '\'' +
				", language='" + language + '\'' +
				'}';
	}
}
