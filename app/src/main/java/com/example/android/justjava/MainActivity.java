/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import java.lang.String;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int unit_price = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice(quantity, 5);
        //String priceMessage = "Total = $" + price;
        //priceMessage = priceMessage + "\nThank you!";
        //Log.v("MainActivity: ", "The price is " + price);
        String summaryMessage = createOrderSummary(price);
        //sendMessage(summaryMessage);
        displayMessage(summaryMessage);
    }

    /**
     * createOrderSummary takes in price of order (an integer), returns a message.
     * @param  price of hte order
     * @return  a text of summary
     */
    private String createOrderSummary(int orderPrice) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String customerName = (String) nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);
        if (hasWhippedCream) {
            orderPrice += quantity * 1;
        }

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "Has chocolate: " + hasChocolate);
        if (hasChocolate) {
            orderPrice += quantity * 2;
        }

        String summaryMessage = getString(R.string.order_summary_name, customerName) + "\n" +
                                "Add whipped cream? " + hasWhippedCream + "\n" +
                                "Add chocolate? " + hasChocolate + "\n" +
                                "Quantity: " + quantity + "\n" +
                                "Total price: " + orderPrice + "\n" +
                                getString(R.string.thank_you);
        return summaryMessage;
    }
    /**
     *  Calculate the price of an order
     *  @ return total price.
     */
    private int calculatePrice(int quantity, int priceOfOneCup) {
        int total = quantity * priceOfOneCup;
        return total;
    }


    /**
     * This method is called when the '+' button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this,
                           "Cannot have more than 100 cup of coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the '-" button is clicked.
     */
    public void decrement(View view) {
        if (quantity ==1) {
            Toast.makeText(this,
                      "Cannot have less than one cup of coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /* *
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int nubmer) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the order summary for coffees.
     */
    private void sendMessage(String message) {
        // This is bad design, but for now that's ok.
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String customerName = (String) nameField.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, "llee@arris.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffer Order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * This method displays the order summary for coffees.
     */
    private void displayMessage(String message) {
        TextView orderSummaryView = (TextView) findViewById(R.id.price_text_view);
        orderSummaryView.setText(message);
    }
}
