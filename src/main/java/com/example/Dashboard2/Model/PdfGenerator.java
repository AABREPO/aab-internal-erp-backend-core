package com.example.Dashboard2.Model;

import com.example.Dashboard2.Entity.MonthlyRentReports;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public class PdfGenerator {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    static class PdfFooter extends PdfPageEventHelper{
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, BaseColor.GRAY);

        @Override
        public void onEndPage(PdfWriter writer, Document document){
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase("Page" + writer.getPageNumber(), footerFont);

            ColumnText.showTextAligned(cb,
                    Element.ALIGN_CENTER,
                    footer,
                    (document.right() + document.left())/2,
                    document.bottom() - 10,
                    0);
        }
    }
    /**
     * Normalizes forTheMonthOf format to yyyy-MM
     * Handles both yyyy-MM and yyyy-M formats (e.g., "2025-05" or "2025-5")
     * 
     * @param forTheMonthOf The month string in any format
     * @return Normalized string in yyyy-MM format
     */
    private static String normalizeForTheMonthOf(String forTheMonthOf) {
        if (forTheMonthOf == null || forTheMonthOf.trim().isEmpty()) {
            return forTheMonthOf;
        }
        
        String trimmed = forTheMonthOf.trim();
        
        // If already in correct format (yyyy-MM), return as is
        if (trimmed.matches("\\d{4}-\\d{2}")) {
            return trimmed;
        }
        
        // Check if format is yyyy-M (without leading zero) and normalize
        if (trimmed.matches("\\d{4}-\\d{1,2}")) {
            String[] parts = trimmed.split("-");
            if (parts.length == 2) {
                try {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    
                    // Validate month range
                    if (month >= 1 && month <= 12) {
                        // Format as yyyy-MM with leading zero
                        return String.format("%04d-%02d", year, month);
                    }
                } catch (NumberFormatException e) {
                    // If parsing fails, return original
                    return trimmed;
                }
            }
        }
        
        // If doesn't match expected pattern, return as is
        return trimmed;
    }

    public static String formatIndianCurrency(BigDecimal amount) {
        boolean isNegative = amount.compareTo(BigDecimal.ZERO) < 0;
        BigDecimal absAmount = amount.abs();
        String num = absAmount.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
        StringBuilder result = new StringBuilder();

        String[] parts = num.split("\\.");
        String intPart = parts[0];
        String decimalDigits = parts.length > 1 ? parts[1] : "00";
        
        // Ensure decimal part is always 2 digits
        if (decimalDigits.length() == 0) {
            decimalDigits = "00";
        } else if (decimalDigits.length() == 1) {
            decimalDigits = decimalDigits + "0";
        } else if (decimalDigits.length() > 2) {
            decimalDigits = decimalDigits.substring(0, 2);
        }
        String decimalPart = "." + decimalDigits;

        int len = intPart.length();
        if (len > 3) {
            result.insert(0, "," + intPart.substring(len - 3));
            intPart = intPart.substring(0, len - 3);

            while (intPart.length() > 2) {
                result.insert(0, "," + intPart.substring(intPart.length() - 2));
                intPart = intPart.substring(0, intPart.length() - 2);
            }

            result.insert(0, intPart);
        } else {
            result.append(intPart);
        }

        String formatted = result.toString() + decimalPart;
        return isNegative ? "-" + formatted : formatted;
    }

    public static byte[] generateMonthlyRentReportPdf(List<MonthlyRentReports> entries, int reportNumber) throws Exception{
        // Default behavior: use previous month
        return generateMonthlyRentReportPdf(entries, reportNumber, YearMonth.now().minusMonths(1));
    }

    public static byte[] generateMonthlyRentReportPdf(List<MonthlyRentReports> entries, int reportNumber, YearMonth reportMonth) throws Exception{
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer =PdfWriter.getInstance(document, out);
        writer.setPageEvent(new PdfFooter());

        document.open();

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font normalFonts = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // ===== HEADER TABLE =====
        PdfPTable header = new PdfPTable(7);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{1.5f, 2f, 2f, 5.5f, 2f, 2f, 4f});

        // Indian format setup
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setGroupingSeparator(',');
        DecimalFormat indianFormat = new DecimalFormat("##,##,###", symbols);
        indianFormat.setGroupingUsed(true);

        addHeaderCell(header, "MONTH", normalFont);
        String monthYear = reportMonth
                .format(DateTimeFormatter.ofPattern("MMMM yyyy")); // e.g., May 2025
        addHeaderCell(header, monthYear, normalFont);

        String previousMonthKey = reportMonth.toString(); // e.g., "2025-05"
        BigDecimal totalAmount = entries.stream()
                .filter(e -> {
                    // Normalize forTheMonthOf to handle both 2025-05 and 2025-5 formats
                    String normalized = normalizeForTheMonthOf(e.getForTheMonthOf());
                    return previousMonthKey.equals(normalized);
                })
                .map(MonthlyRentReports::getAmount)
                .filter(Objects::nonNull)
                .map(amountStr -> {
                    try {
                        return new BigDecimal(amountStr.trim());
                    } catch (NumberFormatException ex) {
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        addHeaderCell(header,"Rs "+ formatIndianCurrency(totalAmount), normalFont);
        addHeaderCell(header, "PROPERTY RENT COLLECTION STATEMENT", normalFont);
        addHeaderCell(header, "CASH", normalFont);
        addHeaderCell(header, "NET BANKING", normalFont);
        addHeaderCell(header, "TOTAL RENT COLLECTED IN THIS MONTH", normalFont);

        // second row header
        addHeaderCell(header, "DATE", normalFont);
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        addHeaderCell(header, currentDate, normalFont);
        addHeaderCell(header, "VACANT SHOPS", normalFont);
        addHeaderCell(header, "", normalFont);

        // Total Cash
        BigDecimal totalCash = entries.stream()
                .filter(e -> "Cash".equalsIgnoreCase(e.getPaymentMode()))
                .map(e -> {
                    try {
                        BigDecimal amount = new BigDecimal(e.getAmount().trim());
                        if ("Shop Closure".equalsIgnoreCase(e.getFormType()) || "Refund".equalsIgnoreCase(e.getFormType())) {
                            return amount.negate(); // subtract if Shop Closure or Refund
                        }
                        return amount;
                    } catch (NumberFormatException ex) {
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        addHeaderCell(header, "Rs " + formatIndianCurrency(totalCash), labelFont);


        // Total Net Banking
        BigDecimal totalNetBanking = entries.stream()
                .filter(e -> "Net Banking".equalsIgnoreCase(e.getPaymentMode()))
                .map(e -> {
                    try {
                        BigDecimal amount = new BigDecimal(e.getAmount().trim());
                        if ("Shop Closure".equalsIgnoreCase(e.getFormType()) || "Refund".equalsIgnoreCase(e.getFormType())) {
                            return amount.negate(); // subtract if Shop Closure or Refund
                        }
                        return amount;
                    } catch (NumberFormatException ex) {
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        addHeaderCell(header, "Rs " + formatIndianCurrency(totalNetBanking), labelFont);


        // Total Amounts
        BigDecimal totalAmounts = entries.stream()
                .filter(Objects::nonNull)
                .map(e -> {
                    try {
                        BigDecimal amount = new BigDecimal(e.getAmount().trim());
                        if ("Shop Closure".equalsIgnoreCase(e.getFormType()) || "Refund".equalsIgnoreCase(e.getFormType())) {
                            return amount.negate(); // subtract if Shop Closure or Refund
                        }
                        return amount;
                    } catch (NumberFormatException ex) {
                        return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        addHeaderCell(header, "Rs " + formatIndianCurrency(totalAmounts), labelFont);

        document.add(header);
        // ===== DATA TABLE ====
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.2f, 3f, 1.8f, 4.3f, 2f, 2f, 2f, 2.4f, 1.7f,1.2f});

        BaseColor borderColor = new BaseColor(150, 150,150);

        Stream.of("S No", "Date", "Shop No", "Tenant Name", "Amount", "Type", "Paid On", "Rent Month", "Mode", "E No")
                .forEach(headerTitle ->{
                    PdfPCell hcell = new PdfPCell(new Phrase(headerTitle, headFont));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setBorderColor(borderColor);
                    hcell.setBorderWidth(0.5f);
                    hcell.setPadding(5);
                    table.addCell(hcell);
                });

        table.setHeaderRows(1);

        BaseColor lightGray = new BaseColor(200,200,200);

        entries.sort((e1, e2) ->{
            if (e1.getShopNo() == null) return -1;
            if (e2.getShopNo() == null) return 1;
            return e1.getShopNo().compareToIgnoreCase(e2.getShopNo());
        });
        DateTimeFormatter paidOnDateInputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // adjust if your input format differs
        DateTimeFormatter paidOnDateOutputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter forTheMonthOutputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        int serialNumber = 1;
        for (MonthlyRentReports e : entries) {
            // Format amount as currency in Indian number format
            // Format amount as currency (with - if Shop Closure or Refund)
            String formattedAmount;
            try {
                BigDecimal amountValue = new BigDecimal(e.getAmount());
                if ("Shop Closure".equalsIgnoreCase(e.getFormType()) || "Refund".equalsIgnoreCase(e.getFormType())) {
                    amountValue = amountValue.negate();
                }
                formattedAmount = formatIndianCurrency(amountValue);
            } catch (Exception ex) {
                formattedAmount = e.getAmount() != null ? e.getAmount() : "";
            }


            // Format paidOnDate from String to dd/MM/yyyy
            String formattedPaidOnDate;
            try {
                formattedPaidOnDate = LocalDate.parse(e.getPaidOnDate(), paidOnDateInputFormat)
                        .format(paidOnDateOutputFormat);
            } catch (Exception ex) {
                formattedPaidOnDate = e.getPaidOnDate() != null ? e.getPaidOnDate() : "";
            }
            // Format forTheMonthOf from yyyy-MM or yyyy-M to "MMMM yyyy"
            String formattedForTheMonth;
            try {
                // Normalize format first (handles both 2025-05 and 2025-5)
                String normalized = normalizeForTheMonthOf(e.getForTheMonthOf());
                formattedForTheMonth = YearMonth.parse(normalized).format(forTheMonthOutputFormatter);
            } catch (Exception ex) {
                formattedForTheMonth = e.getForTheMonthOf() != null ? e.getForTheMonthOf() : "";
            }

            table.addCell(createCell(String.valueOf(serialNumber++), normalFonts, Element.ALIGN_CENTER, lightGray)); // S No
            table.addCell(createCell(e.getTimestamp() != null ? e.getTimestamp().format(TIMESTAMP_FORMATTER) : "", normalFonts, Element.ALIGN_LEFT, lightGray));
            table.addCell(createCell(e.getShopNo(), normalFonts, Element.ALIGN_CENTER, lightGray));
            table.addCell(createCell(e.getTenantName(), normalFonts, Element.ALIGN_LEFT, lightGray));
            table.addCell(createCell(formattedAmount, normalFonts, Element.ALIGN_CENTER, lightGray));
            table.addCell(createCell(e.getFormType(), normalFonts, Element.ALIGN_CENTER, lightGray));
            table.addCell(createCell(formattedPaidOnDate, normalFonts, Element.ALIGN_CENTER, lightGray));
            table.addCell(createCell(formattedForTheMonth, normalFonts, Element.ALIGN_CENTER, lightGray));
            table.addCell(createCell(e.getPaymentMode(), normalFonts, Element.ALIGN_CENTER, lightGray));
            table.addCell(createCell(String.valueOf(e.getEno()), normalFonts, Element.ALIGN_CENTER, lightGray));
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }

    private static PdfPCell createCell(String text, Font font, int hAlign, BaseColor borderColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(hAlign);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(borderColor);
        cell.setBorderWidth(0.5f);
        cell.setMinimumHeight(24f);
        return cell;
    }

    private static void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        table.addCell(cell);
    }
}
