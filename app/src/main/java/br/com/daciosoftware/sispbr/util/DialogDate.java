package br.com.daciosoftware.sispbr.util;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dácio Braga on 21/07/2016.
 * Classe pra chamar um datepicker e setar o valor da data na view informada no construtor;
 */
public class DialogDate {

    private TextView textView;
    private boolean onlyMonthAndYear = false;
    private Calendar date;
    private OnAfterSelectDate onAfterSelectDate;

    /**
     * @param textView View que receberá a data vinda do DatePicker
     *                 Parametros aceitaveis do tipo TextView, EditText, Button
     */
    public DialogDate(TextView textView) {
        this.textView = textView;
        this.date = Calendar.getInstance();
    }

    /**
     * @param textView         View que receberá a data vinda do DatePicker
     *                         Parametros aceitaveis do tipo TextView, EditText, Button
     * @param onlyMonthAndYear True Exibe somente mes e ano no date picker
     */
    public DialogDate(TextView textView, boolean onlyMonthAndYear) {
        this(textView);
        this.onlyMonthAndYear = onlyMonthAndYear;
    }

    public DialogDate(TextView textView, OnAfterSelectDate onAfterSelectDate) {
        this(textView);
        this.onAfterSelectDate = onAfterSelectDate;
    }

    public DialogDate(TextView textView, boolean onlyMonthAndYear, OnAfterSelectDate onAfterSelectDate) {
        this(textView, onlyMonthAndYear);
        this.onAfterSelectDate = onAfterSelectDate;
    }

    public void show() {
        if (textView != null) {
            int dia;
            int mes;
            int ano;

            try {
                Calendar dateOfTextView;
                if (onlyMonthAndYear) {
                    dateOfTextView = dateShortBrToCalendar(textView.getText().toString());
                } else {
                    dateOfTextView = dateBrToCalendar(textView.getText().toString());
                }
                dia = dateOfTextView.get(Calendar.DAY_OF_MONTH);
                mes = dateOfTextView.get(Calendar.MONTH);
                ano = dateOfTextView.get(Calendar.YEAR);

            } catch (ParseException e) {
                Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                ano = calendar.get(Calendar.YEAR);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(textView.getContext(), new OnDateSetInTextViewListener(), ano, mes, dia);

            if (onlyMonthAndYear) {

                try {

                    Field[] datePickerDialogFields = datePickerDialog.getClass().getDeclaredFields();
                    for (Field datePickerDialogField : datePickerDialogFields) {
                        if (datePickerDialogField.getName().equals("mDatePicker")) {
                            datePickerDialogField.setAccessible(true);
                            DatePicker datePicker = (DatePicker) datePickerDialogField.get(datePickerDialog);
                            Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();
                            for (Field datePickerField : datePickerFields) {
                                if ("mDayPicker".equals(datePickerField.getName())
                                        || "mDaySpinner".equals(datePickerField
                                        .getName())) {
                                    datePickerField.setAccessible(true);
                                    Object dayPicker = datePickerField.get(datePicker);
                                    ((View) dayPicker).setVisibility(View.GONE);
                                }
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            datePickerDialog.setCancelable(true);
            datePickerDialog.show();
        }
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    private Calendar dateBrToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private String calendarToDateBr(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(data.getTime());
    }

    private String calendarToShortDateBr(Calendar data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.getDefault());
        return sdf.format(data.getTime());
    }

    private Calendar dateShortBrToCalendar(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.getDefault());
        Date date = sdf.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    public interface OnAfterSelectDate {

        void execute(Calendar dateSelected);
    }

    private class OnDateSetInTextViewListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);

            setDate(calendar);

            if (onlyMonthAndYear) {
                textView.setText(calendarToShortDateBr(calendar));
            } else {
                textView.setText(calendarToDateBr(calendar));
            }

            if (onAfterSelectDate != null) {
                onAfterSelectDate.execute(calendar);
            }
        }
    }
}