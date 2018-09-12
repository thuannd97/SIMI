package com.linkin.simi.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.context.i18n.LocaleContextHolder;

public class FormatUtils {

	public static String formatVNDCurrency(Long number) {
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator(',');
		custom.setGroupingSeparator('.');
		custom.setCurrencySymbol("");
		DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance(LocaleContextHolder.getLocale());
		format.setMaximumFractionDigits(0);
		format.setDecimalFormatSymbols(custom);
		return format.format(number);
	}

}
