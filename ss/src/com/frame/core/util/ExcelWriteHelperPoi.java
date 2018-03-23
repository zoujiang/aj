package com.frame.core.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public abstract class ExcelWriteHelperPoi {

	public static final String EXPORT_DATA = "Export_Data";
	public static final String EXPORT_HEADER = "Export_Header";
	public static final String EXPORT_TITLE = "Export_Title";
	public static final String EXPORT_FILENAME = "Export_FileName";
	
	public ExcelWriteHelperPoi(List<?> datalist) {
		this.head = getHead();
		this.data = new ArrayList<String[]>();
		int columnCount = getColumnCount();
		for (Object element : datalist) {
			String[] str = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				str[i] = this.getText(element, i);
			}
			data.add(str);
		}
	}

	/**
	 * 表头
	 */
	private String[] head;

	protected abstract String[] getHead();

	protected abstract String getText(Object element, int columnIndex);
	
	protected int getColumnCount() {
		if (null != head) return head.length;
		return 0;
	}

	/**
	 * 数据
	 */
	private List<String[]> data;

	public boolean writeNormal(OutputStream outputStream, String title) {
		exportExcel(title, outputStream);
		return true;
	}

	@SuppressWarnings("deprecation")
	private void exportExcel(String title, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		int index = 0;
		HSSFRow row = null;
		// 产生表格标题行
		if (null != head) {
			row = sheet.createRow(index);
			index++;
			for (short i = 0; i < head.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(head[i]);
				cell.setCellValue(text);
			}
		}

		// 遍历集合数据，产生数据行
		Iterator<String[]> it = data.iterator();
		
		while (it.hasNext()) {
			//
			row = sheet.createRow(index);
			index++;
			String[] t = it.next();
			for (short i = 0; i < t.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				try {
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (t[i] != null) {
						HSSFRichTextString richString = new HSSFRichTextString(t[i]);
//						HSSFFont font3 = workbook.createFont();
//						font3.setColor(HSSFColor.BLUE.index);
//						richString.applyFont(font3);
						cell.setCellValue(richString);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} finally {
				}
			}
		}
		try {
			workbook.write(out); 
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
