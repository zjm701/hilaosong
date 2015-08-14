package com.hi.pay.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "T_CATER_THIRDMERCHANTINFO")
public class TCaterThirdmerchantinfo implements java.io.Serializable {

	private Long id;
	private String channelNo;
	private String channelPayType;
	private String storeId;
	private String merchantNo;
	private String merchantKey;

	/**
	 * @param id
	 * @param channelNo
	 * @param channelPayType
	 * @param storeId
	 * @param merchantNO
	 * @param merchantKey
	 */
	public TCaterThirdmerchantinfo(Long id, String channelNo,
			String channelPayType, String storeId, String merchantNO,
			String merchantKey) {
		
		this.id = id;
		this.channelNo = channelNo;
		this.channelPayType = channelPayType;
		this.storeId = storeId;
		this.merchantNo = merchantNo;
		this.merchantKey = merchantKey;
	}

	public TCaterThirdmerchantinfo() {
	}

	public TCaterThirdmerchantinfo(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_THIRDMERCHANTINFO")
	@SequenceGenerator(name = "SEQ_THIRDMERCHANTINFO", sequenceName = "seq_cater_thirdmerchantinfo_id", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "CHANNEL_NO", length = 32)
	public String getChannelNo() {
		return this.channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	@Column(name = "CHANNEL_PAYTYPE", length = 2)
	public String getChannelPayType() {
		return channelPayType;
	}

	public void setChannelPayType(String channelPayType) {
		this.channelPayType = channelPayType;
	}

	@Column(name = "STORE_ID", length = 32)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "MERCHANT_NO", length = 64)
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	@Column(name = "MERCHANT_KEY", length = 128)
	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}


}
