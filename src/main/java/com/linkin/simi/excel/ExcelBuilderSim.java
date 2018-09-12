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

import com.linkin.simi.model.SimDTO;
import com.linkin.simi.utils.FormatUtils;

public class ExcelBuilderSim extends AbstractXlsView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// change the file name
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"Sims.xls\"");

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
		header.createCell(1).setCellValue("Price");
		header.getCell(1).setCellStyle(style);
		header.createCell(2).setCellValue("Sim no");
		header.getCell(2).setCellStyle(style);
		header.createCell(3).setCellValue("status");
		header.getCell(3).setCellStyle(style);
		header.createCell(4).setCellValue("Created date");
		header.getCell(4).setCellStyle(style);
		header.createCell(5).setCellValue("Updated date");
		header.getCell(5).setCellStyle(style);

		List<SimDTO> simDTOs = (List<SimDTO>) model.get("simDTOs");
		if (simDTOs.size() > 0) {
			int rowNum = 1;
			for (SimDTO simDTO : simDTOs) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(simDTO.getId());
				row.createCell(1).setCellValue(FormatUtils.formatVNDCurrency(simDTO.getPrice()));
				row.createCell(2).setCellValue(simDTO.getSimNo());
				row.createCell(3).setCellValue(simDTO.getStatus());
				row.createCell(4).setCellValue(simDTO.getCreatedDate());
				row.createCell(5).setCellValue(simDTO.getUpdatedDate());
			}
		}
	}

}