package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.RentalForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentalFormRepository extends JpaRepository<RentalForm, Long> {

    @Query(value = """
        SELECT * FROM rental_form
        WHERE STR_TO_DATE(paid_on_date, '%Y-%m-%d') >= DATE_FORMAT(CURDATE(), '%Y-%m-01')
          AND STR_TO_DATE(paid_on_date, '%Y-%m-%d') < DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 1 MONTH)
        """, nativeQuery = true)
    List<RentalForm> findCurrentMonthEntries();

    @Query(value = """
        SELECT * FROM rental_form r
        WHERE YEAR(STR_TO_DATE(r.paid_on_date, '%Y-%m-%d')) = :year
        AND MONTH(STR_TO_DATE(r.paid_on_date, '%Y-%m-%d')) = :month
        """, nativeQuery = true)
    List<RentalForm> findEntriesByMonth(int year, int month);
    @Query(value = """
    SELECT * FROM rental_form
    WHERE YEAR(STR_TO_DATE(paid_on_date, '%Y-%m-%d')) = YEAR(DATE_SUB(CURDATE(), INTERVAL 2 MONTH))
      AND MONTH(STR_TO_DATE(paid_on_date, '%Y-%m-%d')) = MONTH(DATE_SUB(CURDATE(), INTERVAL 2 MONTH))
    """, nativeQuery = true)
    List<RentalForm> findTwoMonthsBeforeEntries();

    @Query(value = """
        SELECT * FROM rental_form
        WHERE for_the_month_of = :forTheMonthOf
        AND (monthly_report_number IS NULL 
             OR TRIM(COALESCE(monthly_report_number, '')) = ''
             OR monthly_report_number NOT REGEXP '^[0-9]+$')
        """, nativeQuery = true)
    List<RentalForm> findEntriesByForTheMonthOf(String forTheMonthOf);

    @Query(value = """
        SELECT * FROM rental_form
        WHERE for_the_month_of = :forTheMonthOf
        """, nativeQuery = true)
    List<RentalForm> findAllEntriesByForTheMonthOf(String forTheMonthOf);

    @Query(value = """
        SELECT * FROM rental_form
        WHERE YEAR(timestamp) = :year
        AND MONTH(timestamp) = :month
        AND (monthly_report_number IS NULL 
             OR TRIM(COALESCE(monthly_report_number, '')) = ''
             OR monthly_report_number NOT REGEXP '^[0-9]+$')
        """, nativeQuery = true)
    List<RentalForm> findEntriesByTimestampMonth(int year, int month);

    @Query(value = """
        SELECT * FROM rental_form
        WHERE YEAR(timestamp) = :year
        AND MONTH(timestamp) = :month
        """, nativeQuery = true)
    List<RentalForm> findAllEntriesByTimestampMonth(int year, int month);

}
