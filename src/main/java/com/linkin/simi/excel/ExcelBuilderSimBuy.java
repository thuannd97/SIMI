package com.linkin.simi.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.linkin.simi.model.SimBuyDTO;
import com.linkin.simi.utils.FormatUtils;

public class ExcelBuilderSimBuy extends AbstractXlsView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// change the file name
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"simBuys.xls\"");

		// create excel xls sheet
		Sheet sheet = workbook.createSheet("Sim Buy");
		sheet.setDefaultColumnWidth(30);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		style.setFont(font);

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.getCell(0).setCellStyle(style);
		header.createCell(1).setCellValue("Buy date");
		header.getCell(1).setCellStyle(style);
		header.createCell(2).setCellValue("Cost");
		header.getCell(2).setCellStyle(style);
		header.createCell(3).setCellValue("Sim no");
		header.getCell(3).setCellStyle(style);
		header.createCell(4).setCellValue("Buyer name");
		header.getCell(4).setCellStyle(style);
		header.createCell(5).setCellValue("Buyer ID");
		header.getCell(5).setCellStyle(style);
		header.createCell(6).setCellValue("Created by name");
		header.getCell(6).setCellStyle(style);
		header.createCell(7).setCellValue("Created date");
		header.getCell(7).setCellStyle(style);

		List<SimBuyDTO> simBuyDTOs = (List<SimBuyDTO>) model.get("simBuyDTOs");
		if (simBuyDTOs.size() > 0) {
			int rowNum = 1;
			for (SimBuyDTO simBuyDTO : simBuyDTOs) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(simBuyDTO.getId());
				row.createCell(1).setCellValue(simBuyDTO.getBuyDate());
				row.createCell(2).setCellValue(FormatUtils.formatVNDCurrency(simBuyDTO.getCost()));
				row.createCell(3).setCellValue(simBuyDTO.getSimNo());
				row.createCell(4).setCellValue(simBuyDTO.getBuyerName());
				row.createCell(5).setCellValue(simBuyDTO.getBuyerId());
				row.createCell(6).setCellValue(simBuyDTO.getCreatedByName());
				row.createCell(7).setCellValue(simBuyDTO.getCreatedDate());
			}
		}
	}
	

}
