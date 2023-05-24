package net.dkcraft.uniqueitemidentifiers;

import java.util.List;

public class Item {
	
	private String material;
	
	private String name;

	private List<String> lore;
	
	private List<String> enchantments;
	
	private int count;
	
	public Item(String material, String name, List<String> lore, List<String> enchantments, int count) {
		this.setMaterial(material);
		this.setName(name);
		this.setLore(lore);
		this.setEnchantments(enchantments);
		this.setCount(count);
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public List<String> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<String> enchantments) {
		this.enchantments = enchantments;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
