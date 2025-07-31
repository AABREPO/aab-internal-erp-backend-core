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
    public static String formatIndianCurrency(BigDecimal amount) {
        String num = amount.toPlainString();
        StringBuilder result = new StringBuilder();

        String[] parts = num.split("\\.");
        String intPart = parts[0];
        String decimalPart = parts.length > 1 ? "." + parts[1] : "";

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

        return result.toString() + decimalPart;
    }

    public static byte[] generateMonthlyRentReportPdf(List<MonthlyRentReports> entries, int reportNumber) throws Exception{
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
        String monthYear = YearMonth.now().minusMonths(1)
                .format(DateTimeFormatter.ofPattern("MMMM yyyy")); // e.g., May 2025
        addHeaderCell(header, monthYear, normalFont);

        String previousMonthKey = YearMonth.now().minusMonths(1).toString(); // e.g., "2025-05"
        BigDecimal totalAmount = entries.stream()
                .filter(e -> previousMonthKey.equals(e.getForTheMonthOf()))
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
                        if ("Shop Closure".equalsIgnoreCase(e.getFormType())) {
                            return amount.negate(); // subtract if Shop Closure
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
                        if ("Shop Closure".equalsIgnoreCase(e.getFormType())) {
                            return amount.negate(); // subtract if Shop Closure
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
                        if ("Shop Closure".equalsIgnoreCase(e.getFormType())) {
                            return amount.negate(); // subtract if Shop Closure
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
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        DateTimeFormatter paidOnDateInputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // adjust if your input format differs
        DateTimeFormatter paidOnDateOutputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter forTheMonthOutputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        int serialNumber = 1;
        for (MonthlyRentReports e : entries) {
            // Format amount as currency
            // Format amount as currency (with - if Shop Closure)
            String formattedAmount;
            try {
                double parsedAmount = Double.parseDouble(e.getAmount());
                if ("Shop Closure".equalsIgnoreCase(e.getFormType())) {
                    parsedAmount = -parsedAmount;
                }
                formattedAmount = currencyFormat.format(parsedAmount);
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
            // Format forTheMonthOf from yyyy-MM to "MMMM yyyy"
            String formattedForTheMonth;
            try {
                formattedForTheMonth = YearMonth.parse(e.getForTheMonthOf()).format(forTheMonthOutputFormatter);
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
