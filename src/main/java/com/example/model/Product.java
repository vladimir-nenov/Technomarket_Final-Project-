package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.model.exceptions.InvalidProductDataException;
import com.example.model.util.RegexValidator;

;

public class Product {
	private long productId;
	private String name;
	private String tradeMark;
	private BigDecimal price;
	private String productNumber;
	private Credit credit;
	private Category category;
	private LocalDate dateAdded;
	private ArrayList<Characteristics> characteristics = new ArrayList<>();
	private int worranty;
	private int percentPromo;
	private boolean isNewProduct;
	private String imageUrl;
	private String description;
	public enum campareEnum{ defaultt,price,markName };
	
	public Product(String name, String tradeMark, BigDecimal price, Credit credit, Category category, int worranty,
			int percentPromo, LocalDate dateAdded, String imageUrl) throws InvalidProductDataException {

		if (correctName(name)) {
			this.name = name;
		} else {
			throw new InvalidProductDataException();
		}
		if (correctName(tradeMark)) {
			this.tradeMark = tradeMark;
		} else {
			throw new InvalidProductDataException();
		}
		
	//	BigDecimal big = new BigDecimal(price);
		
		if (price.compareTo(new BigDecimal("0")) < 0 && price.compareTo(new BigDecimal("99999")) > 1) {
			throw new InvalidProductDataException();
		} else {
			this.price = price;
		}
		if(worranty >= 0 && worranty <= 99){
			this.worranty = worranty;
		}else{
			throw new InvalidProductDataException();
		}
		if(percentPromo >= 0 && percentPromo <= 99){
			this.percentPromo = percentPromo;
		}else{
			throw new InvalidProductDataException();
		}
		if(RegexValidator.validateImageFile(imageUrl)){
			this.imageUrl = imageUrl;
		}else{
			throw new InvalidProductDataException();
		}
		if(category != null){
			this.category = category;
		}else{
			throw new InvalidProductDataException();
		}
		this.dateAdded = dateAdded;
		this.isNewProduct = findIfProductIsNew();
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	private boolean findIfProductIsNew() {
		if(this.dateAdded.plusDays(15).isAfter(LocalDate.now())){
			return true;
		}else{
			return false;
		}
	}

	private boolean correctName(String name) {
		if (name != null && !name.isEmpty() && name.length() >= 2 && name.length() <= 35) {
			return true;
		}
		return false;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public String getName() {
		return name;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public int getWorranty() {
		return worranty;
	}

	public int getPercentPromo() {
		return percentPromo;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProductId() {
		return productId;
	}

	public Category getCategory() {
		return category;
	}

	public List<Characteristics> getCharacteristics() {
		return Collections.unmodifiableList(characteristics);
	}

		public void setName(String name) {
			this.name = name;
		}
		public void setTradeMark(String tradeMark) {
			this.tradeMark = tradeMark;
		}
		public void setPrice(String price) {
			this.price = new BigDecimal(price);
		}
		public void setProductNumber(String productNumber) {
			this.productNumber = productNumber;
		}
		public void setCategory(Category category) {
			this.category = category;
		}
		public void setCharacteristics(ArrayList<Characteristics> characteristics) {
			this.characteristics = characteristics;
		}
		public void setWorranty(int worranty) {
			this.worranty = worranty;
		}
		public void setPercentPromo(int percentPromo) {
			this.percentPromo = percentPromo;
		}
		
		public void setDateAdded(LocalDate dateAdded) {
			this.dateAdded = dateAdded;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public boolean getIsNewProduct() {
			return isNewProduct;
		}
		
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		//hashCode and equals overrided for collections:

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((category == null) ? 0 : category.hashCode());
			result = prime * result + ((characteristics == null) ? 0 : characteristics.hashCode());
			result = prime * result + ((credit == null) ? 0 : credit.hashCode());
			result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
			result = prime * result + (isNewProduct ? 1231 : 1237);
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + percentPromo;
			result = prime * result + ((price == null) ? 0 : price.hashCode());
			result = prime * result + (int) (productId ^ (productId >>> 32));
			result = prime * result + ((productNumber == null) ? 0 : productNumber.hashCode());
			result = prime * result + ((tradeMark == null) ? 0 : tradeMark.hashCode());
			result = prime * result + worranty;
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
			Product other = (Product) obj;
			if (category == null) {
				if (other.category != null)
					return false;
			} else if (!category.equals(other.category))
				return false;
			if (characteristics == null) {
				if (other.characteristics != null)
					return false;
			} else if (!characteristics.equals(other.characteristics))
				return false;
			if (credit == null) {
				if (other.credit != null)
					return false;
			} else if (!credit.equals(other.credit))
				return false;
			if (dateAdded == null) {
				if (other.dateAdded != null)
					return false;
			} else if (!dateAdded.equals(other.dateAdded))
				return false;
			if (isNewProduct != other.isNewProduct)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (percentPromo != other.percentPromo)
				return false;
			if (price == null) {
				if (other.price != null)
					return false;
			} else if (!price.equals(other.price))
				return false;
			if (productId != other.productId)
				return false;
			if (productNumber == null) {
				if (other.productNumber != null)
					return false;
			} else if (!productNumber.equals(other.productNumber))
				return false;
			if (tradeMark == null) {
				if (other.tradeMark != null)
					return false;
			} else if (!tradeMark.equals(other.tradeMark))
				return false;
			if (worranty != other.worranty)
				return false;
			return true;
		}

		
	
	
}
