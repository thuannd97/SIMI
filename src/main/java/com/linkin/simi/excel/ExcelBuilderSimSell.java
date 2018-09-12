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

import com.linkin.simi.model.SimSellDTO;
import com.linkin.simi.utils.FormatUtils;

public class ExcelBuilderSimSell extends AbstractXlsView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// change the file name
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"simSells.xls\"");

		// create excel xls sheet
		Sheet sheet = workbook.createSheet("Sim Sell");
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
		header.createCell(1).setCellValue("Created date");
		header.getCell(1).setCellStyle(style);
		header.createCell(2).setCellValue("Created by");
		header.getCell(2).setCellStyle(style);
		header.createCell(3).setCellValue("Customer info");
		header.getCell(3).setCellStyle(style);
		header.createCell(4).setCellValue("Customer name");
		header.getCell(4).setCellStyle(style);
		header.createCell(5).setCellValue("Deposit");
		header.getCell(5).setCellStyle(style);
		header.createCell(6).setCellValue("Sell date");
		header.getCell(6).setCellStyle(style);
		header.createCell(7).setCellValue("Sell price");
		header.getCell(7).setCellStyle(style);
		header.createCell(8).setCellValue("Seller ID");
		header.getCell(8).setCellStyle(style);
		header.createCell(9).setCellValue("Seller name");
		header.getCell(9).setCellStyle(style);
		header.createCell(10).setCellValue("Updated date");
		header.getCell(10).setCellStyle(style);
		header.createCell(11).setCellValue("Updated by");
		header.getCell(11).setCellStyle(style);
		header.createCell(12).setCellValue("Sim no");
		header.getCell(12).setCellStyle(style);

		List<SimSellDTO> simSellDTOs = (List<SimSellDTO>) model.get("simSellDTOs");
		if (simSellDTOs.size() > 0) {
			int rowNum = 1;
			for (SimSellDTO simSellDTO : simSellDTOs) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(simSellDTO.getId());
				row.createCell(1).setCellValue(simSellDTO.getCreatedDate());
				row.createCell(2).setCellValue(simSellDTO.getCreatedBy());
				row.createCell(3).setCellValue(simSellDTO.getCustomerInfo());
				row.createCell(4).setCellValue(simSellDTO.getCustomerName());
				row.createCell(5).setCellValue(FormatUtils.formatVNDCurrency(simSellDTO.getDeposit()));
				row.createCell(6).setCellValue(simSellDTO.getSellDate());
				row.createCell(7).setCellValue(simSellDTO.getSellPrice());
				row.createCell(8).setCellValue(simSellDTO.getSellerId());
				row.createCell(9).setCellValue(simSellDTO.getSellerName());
				row.createCell(10).setCellValue(simSellDTO.getUpdatedDate());
				row.createCell(11).setCellValue(simSellDTO.getUpdatedBy());
				row.createCell(12).setCellValue(simSellDTO.getSimNo());
			}
		}
	}
	

}
