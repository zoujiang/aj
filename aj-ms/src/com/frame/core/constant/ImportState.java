/**
 * 
 */
package com.frame.core.constant;

/**
 * @author roy
 * 导入状态
 */

public enum ImportState {
	notImport(1),importing(2),success(3),failure(4);
	private final int value;
    public int getValue() {
        return value;
    }
    ImportState(int value) {
        this.value = value;
    }

}
