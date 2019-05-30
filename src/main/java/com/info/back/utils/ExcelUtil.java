package com.info.back.utils;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel操作类
 * 
 * @author LIUTQ
 * 
 */
@Slf4j
public class ExcelUtil {

	public static void setFileDownloadHeader(HttpServletRequest request,
			HttpServletResponse response, String fileName) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		String finalFileName = fileName;
		try {
			if (StringUtils.contains(userAgent, "firefox")) {// 火狐浏览器
				finalFileName = new String(fileName.getBytes(), "ISO8859-1");
			} else {
				finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
			}
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ finalFileName + "\"");// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
		} catch (Exception e) {
			log.error("setFileDownloadHeader error:{}", e);
		}
	}

	/**
	 * 多sheet导出
	 * 
	 */
	public final static void buildExcel(SXSSFWorkbook wb, String mainTitle,
			String[] titles, List<Object[]> contents, int sheetNum,
			int totalSheet, OutputStream os) {
		try {
			/** **********创建工作表************ */
			Sheet sheet = wb.createSheet("Sheet" + sheetNum);

			/** ************设置单元格字体************** */
			Font font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示

			/** ************以下设置三种单元格样式************ */
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setFont(font);
			Row row = sheet.createRow(0);
			for (short i = 0; i < titles.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(titles[i]);
			}
			/** ***************以下是EXCEL正文数据********************* */
			for (int i = 0; i < contents.size(); i++) {// row
				Object[] rowContent = contents.get(i);
				Row row2 = sheet.createRow(i + 1);
				for (int j = 0; j < titles.length; j++) { // cell
					Cell cell = row2.createCell(j);
					cell.setCellValue(String.valueOf(rowContent[j]));
				}
			}
			if (totalSheet == sheetNum) {
				wb.write(os);
				os.close();
			}

		} catch (Exception e) {
			log.error("buildExcel error;{}", e);
		}
	}
	// 读取单元格的值
	private String getValue(Cell cell) {
		String result = "";

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				// 可能是普通数字，也可能是日期
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					result = DateUtil.getJavaDate(cell.getNumericCellValue())
							.toString();
				} else {
					result = cell.getNumericCellValue() + "";
				}
				break;
		}
		return result;
	}

	/***
	 * 这种方法支持03，和07版本的excel读取
	 * 但是对于合并的单元格，除了第一行第一列之外，其他部分读取的值为空
	 * @param is
	 */
	public void importXlsx(InputStream is) {
		try {
			Workbook wb = WorkbookFactory.create(is);
			// OPCPackage pkg = OPCPackage.open(is);
			// XSSFWorkbook wb = new XSSFWorkbook(pkg);
			for (int i = 0, len = wb.getNumberOfSheets(); i < len; i++) {
				Sheet sheet = wb.getSheetAt(i);
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					if (sheet == null) {
						return;
					}
					Row row = sheet.getRow(j);
					if(row==null){
						return;
					}
					// 读取每一个单元格
					for (int k = 0; k < row.getLastCellNum(); k++) {
						Cell cell = row.getCell(k);
						if (cell == null) {
							return;
						}
						System.out.print(getValue(cell));

					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是合并的单元格，如果是的话，返回合并区域，否则返回空（仅适用于）
	 *
	 * @param sheet
	 * @return
	 */
	private CellRangeAddress isMerged(Sheet sheet, Cell cell) {

		CellRangeAddress result = null;
		CellRangeAddress cra = null;
		int cellRow = cell.getRowIndex();
		int cellColumn = cell.getColumnIndex();
		int mergedNum = sheet.getNumMergedRegions();
		for (int i = 0; i < mergedNum; i++) {
			// 如果是xlsx的格式，怎么办？
			cra = ((HSSFSheet) sheet).getMergedRegion(i);
			if (cellRow >= cra.getFirstRow() && cellRow <= cra.getLastRow()
					&& cellColumn >= cra.getFirstColumn()
					&& cellColumn <= cra.getLastColumn()) {
				result = cra;
			}
		}
		return result;
	}

	private String getCellValue(Sheet sheet, Cell cell) {
		String result = "";
		// 判断是否是合并的单元格
		CellRangeAddress cra = null;
		if ((cra = isMerged(sheet, cell)) != null) {
			Cell fcell = sheet.getRow(cra.getFirstRow()).getCell(
					cra.getFirstColumn());
			result = getValue(fcell);
		} else {
			result = getValue(cell);
		}
		return result;
	}

}
