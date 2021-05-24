package com.example.androidcalculator;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Calculator extends AppCompatActivity{

    private Button num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, num_0;
    private Button add, subtract, multiply, divide, point, All_clear, bt_equal;
    private TextView showText;

    private String first_num = "";
    private String second_num = "";
    private String operator = "";
    private boolean StateChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find bt
        showText = findViewById(R.id.c_num);
        num_0 = findViewById(R.id.num_0);
        num_1 = findViewById(R.id.num_1);
        num_2 = findViewById(R.id.num_2);
        num_3 = findViewById(R.id.num_3);
        num_4 = findViewById(R.id.num_4);
        num_5 = findViewById(R.id.num_5);
        num_6 = findViewById(R.id.num_6);
        num_7 = findViewById(R.id.num_7);
        num_8 = findViewById(R.id.num_8);
        num_9 = findViewById(R.id.num_9);
        add = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        point = findViewById(R.id.point);
        All_clear = findViewById(R.id.All_clear);
        bt_equal = findViewById(R.id.bt_equal);

        OnClick on = new OnClick();
        num_0.setOnClickListener(on);
        num_1.setOnClickListener(on);
        num_2.setOnClickListener(on);
        num_3.setOnClickListener(on);
        num_4.setOnClickListener(on);
        num_5.setOnClickListener(on);
        num_6.setOnClickListener(on);
        num_7.setOnClickListener(on);
        num_8.setOnClickListener(on);
        num_9.setOnClickListener(on);
        add.setOnClickListener(on);
        subtract.setOnClickListener(on);
        multiply.setOnClickListener(on);
        divide.setOnClickListener(on);
        point.setOnClickListener(on);
        All_clear.setOnClickListener(on);
        bt_equal.setOnClickListener(on);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String input = showText.getText().toString();
            switch (view.getId()) {
                case R.id.num_0:
                case R.id.num_1:
                case R.id.num_2:
                case R.id.num_3:
                case R.id.num_4:
                case R.id.num_5:
                case R.id.num_6:
                case R.id.num_7:
                case R.id.num_8:
                case R.id.num_9:
                case R.id.point:
                    if(input.equals("0")){
                        if(view.getId() == R.id.point){
                        } else {
                            input = "";
                        }
                    }
                    if(StateChange) {
                        input = "";
                        StateChange = false;
                    }
                    showText.setText(input + ((Button) view).getText());
                    break;
                case R.id.add:
                case R.id.subtract:
                case R.id.multiply:
                case R.id.divide:
                    second_num = showText.getText().toString();
                    while(!StateChange){
                        if(first_num.equals("")){
                            first_num = second_num;
                        } else {
                            String result = calculate(first_num,second_num,operator);
                            first_num = result;
                            showText.setText("" + result);
                        }
                        StateChange = true;
                    }
                    operator = ((Button) view).getText().toString();
                    break;
                case R.id.bt_equal:

                    if(StateChange){
                        String result = calculate(first_num,second_num,operator);
                        first_num = result;
                        showText.setText("" + result);
                    } else {
                        second_num = showText.getText().toString();
                        if(!operator.equals("")){
                            String result = calculate(first_num,second_num,operator);
                            first_num = result;
                            showText.setText("" + result);
                        }
                        StateChange = true;
                    }


                    break;
                case R.id.All_clear:
                    showText.setText("0");
                    first_num = "";
                    second_num = "";
                    operator = "";
                    StateChange = false;
                    break;
            }
        }
    }


/*    public static void main(String[] args) {
        calculate("1","2","+");
    }*/

    //calculate
    public static String calculate(String first_num, String second_num, String operator)  {
        String url = "http://localhost:8080/test/Calculator?";
        url += "num1=" + first_num;
        url += "&num2=" + second_num;
        url += "&operator=" + operator;
        OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();
        try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            } catch (Exception e){
                e.printStackTrace();
            }
        return "";


/*

        double num1 = Double.parseDouble(first_num);
        double num2 = Double.parseDouble(second_num);
        double sum = 0;
        if (operator.equals("+")) {
            sum = num1 + num2;
        }
        if (operator.equals("-")) {
            sum = num1 - num2;
        }
        if (operator.equals("×")) {
            sum = num1 * num2;
        }
        if (operator.equals("÷")) {
            sum = num1 / num2;
        }
        return doubleTrans(sum);*/
    }

    //when the num behind point is 0, just show the num. 3.0 shows 3
    public static String doubleTrans(double num)
    {
/*        int m;
        int n;
        String str = String.valueOf(num);
        String[] strlist = str.split("\\.");

        if(strlist.length < 2){
            if(str.length() <= 9){
                return String.valueOf((long)num);
            } else {
                return String.format("%.3E",num);
            }
        } else {
            m = strlist[0].length();
            n = strlist[1].length();
            if(m < 9){
                String result = strlist[0] + "." + strlist[1].substring(0,9-m);
                return result;
            } else {
                return String.format("%.3E",num);
            }
        }*/
        if(num % 1.0 == 0){
            return String.valueOf((long)num);
        }
        return String.valueOf(num);
    }

}