package com.frame.system.vo;


public class TreeModel {
	public static String TreeRoot = "root";
	/**
	 * 节点ID
	 */
	private String id;

	/**
	 * 节点文本
	 */
	private String text;

	/**
	 * 节点图标
	 */
	private String iconCls;

	/**
	 * 指示节点是否处于选中状态
	 */
	private boolean checked = false;

	/**
	 * 节点的初始化状态(关闭或展开)
	 */
	private String state="closed";

	/**
	 * 自定义属性
	 */
	private AttributesModel attributes;

	/**
	 * 子节点
	 */
	private TreeModel[] children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TreeModel[] getChildren() {
		return children;
	}

	public void setChildren(TreeModel[] children) {
		this.children = children;
	}

	public AttributesModel getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributesModel attributes) {
		this.attributes = attributes;
	}
	
	
	public class AttributesModel {
		/**
		 * 资源路径
		 */
		private String href;
		
		/**
		 *资源父节点 
		 */
		private String parentId;
		
		/**
		 * 资源编码
		 */
		private String code;
		
		private String iconCls;

		public String getIconCls() {
			return iconCls;
		}

		public void setIconCls(String iconCls) {
			this.iconCls = iconCls;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}

}
