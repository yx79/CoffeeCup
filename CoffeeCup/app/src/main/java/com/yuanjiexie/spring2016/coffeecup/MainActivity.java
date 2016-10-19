package com.yuanjiexie.spring2016.coffeecup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.widget.Toast;


import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int numberCups = 2;
    private double unitPrice = 3.0;
    private double paperCup = 2.0;
    private boolean isCheckedWhippedCream = false;
    private boolean isCheckedChocolate = false;

    private String nameStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when + button clicked
     */
    public void increment(View view) {
        numberCups++;
        if (numberCups > 100) {
            numberCups = 100;
            Context context = getApplicationContext();
            String text = "The maximum Quantity is 100.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
        displayQuantity(numberCups);
    }

    /**
     * This method is called when - button clicked
     */
    public void decrement(View view) {
        numberCups--;
        if (numberCups < 1) {
            numberCups = 1;
            Toast.makeText(this, "The minimum Quantity is 1.", Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(numberCups);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //displayQuantity(numberCups);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkBox);
        isCheckedWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        isCheckedChocolate = chocolateCheckBox.isChecked();

        EditText text = (EditText) findViewById(R.id.name_textfield);
        nameStr = text.getText().toString();

        double finalUnitPrice = unitPrice;
        if (isCheckedWhippedCream) {
            finalUnitPrice = unitPrice + 0.5;
        }
        if (isCheckedChocolate) {
            finalUnitPrice = unitPrice + 0.75;
        }
        double price = numberCups * finalUnitPrice;


        String orderSummaryText = createOrderSummary(price);

        String subject = "CoffeCup Order for " + nameStr;
        composeEmail(subject, orderSummaryText);



    }


    public void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setData(Uri.parse("mailto:"));
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


//    public void onCheckedBoxClicked(View view) {
//        // Is the view now checked?
//        boolean isCheck = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.whipped_cream_checkBox:
//                if (isCheck) {
//                    isCheckedWhippedCream = true;
//                }
//                break;
//        }
//
//
//    }

    /**
     * This method is to create order summary
     */
    private String createOrderSummary(double price) {
        String string = "Name: " + nameStr + "\n\nAdd whipped cream? -" + isCheckedWhippedCream +
                "\nAdd Chocolate?     -" + isCheckedChocolate +
                "\nQuantity: " + numberCups + "\n\nTotal Due: "
                + NumberFormat.getCurrencyInstance().format(price) + "\n\nThank you.";
        return string;

    }






    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}
