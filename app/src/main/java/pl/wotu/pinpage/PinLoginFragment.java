package pl.wotu.pinpage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class PinLoginFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    //TO CHANGE NUMBER OF DOTS
    private static final int INCREMENTING = 1;
    private static final int DECREMENTING = 2;

    private NavController navController;
    private Button pinButton1,pinButton2,pinButton3,pinButton4,pinButton5,pinButton6,pinButton7,pinButton8,pinButton9,pinButton0;
//    private Button pinButtonLogin;
    private ImageButton pinButtonReorder,pinButtonBackspace;
    private ArrayList<Integer> pinNumbers = new ArrayList<>();;
    private ArrayList<Integer> pin = new ArrayList<>();;
    private LinearLayout pinDot[] = new LinearLayout[12];
    private String userPin;
    private TextView triesLeft;
    private int numberOfTries = 3;

    public PinLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO odczytaj z sharedpreferences
        userPin ="123456789";

        navController = Navigation.findNavController(view);

        triesLeft = view.findViewById(R.id.pinfragment_tries_left_tv);

        pinDot[0] = view.findViewById(R.id.pin_dot_1);
        pinDot[1] = view.findViewById(R.id.pin_dot_2);
        pinDot[2] = view.findViewById(R.id.pin_dot_3);
        pinDot[3] = view.findViewById(R.id.pin_dot_4);
        pinDot[4] = view.findViewById(R.id.pin_dot_5);
        pinDot[5] = view.findViewById(R.id.pin_dot_6);
        pinDot[6] = view.findViewById(R.id.pin_dot_7);
        pinDot[7] = view.findViewById(R.id.pin_dot_8);
        pinDot[8] = view.findViewById(R.id.pin_dot_9);
        pinDot[9] = view.findViewById(R.id.pin_dot_10);
        pinDot[10] = view.findViewById(R.id.pin_dot_11);
        pinDot[11] = view.findViewById(R.id.pin_dot_12);

        pinButton1 = view.findViewById(R.id.pin_button_1);
        pinButton2 = view.findViewById(R.id.pin_button_2);
        pinButton3 = view.findViewById(R.id.pin_button_3);
        pinButton4 = view.findViewById(R.id.pin_button_4);
        pinButton5 = view.findViewById(R.id.pin_button_5);
        pinButton6 = view.findViewById(R.id.pin_button_6);
        pinButton7 = view.findViewById(R.id.pin_button_7);
        pinButton8 = view.findViewById(R.id.pin_button_8);
        pinButton9 = view.findViewById(R.id.pin_button_9);
        pinButton0 = view.findViewById(R.id.pin_button_0);
        pinButtonBackspace = view.findViewById(R.id.pin_button_backspace);
        pinButtonReorder = view.findViewById(R.id.pin_button_reorder);

        pinButton0.setOnClickListener(this);
        pinButton1.setOnClickListener(this);
        pinButton2.setOnClickListener(this);
        pinButton3.setOnClickListener(this);
        pinButton4.setOnClickListener(this);
        pinButton5.setOnClickListener(this);
        pinButton6.setOnClickListener(this);
        pinButton7.setOnClickListener(this);
        pinButton8.setOnClickListener(this);
        pinButton9.setOnClickListener(this);
        pinButtonBackspace.setOnClickListener(this);
        pinButtonReorder.setOnClickListener(this);
        pinButtonReorder.setOnLongClickListener(this);

        for(int i=0;i<12;i++){
            if (i< userPin.length()){
                pinDot[i].setVisibility(View.VISIBLE);
            }else {
                pinDot[i].setVisibility(View.GONE);
            }
        }

        for(int i=0;i<10;i++){
            pinNumbers.add(i);
        }
        changeButtonsOrder();
    }

    @SuppressLint("SetTextI18n")
    private void orderList(){
        pinNumbers = new ArrayList<>();
        for(int i=0;i<10;i++){
            pinNumbers.add(i);
        }
        updateKeyboard();
    }

    @SuppressLint("SetTextI18n")
    private void changeButtonsOrder() {
        Collections.shuffle(pinNumbers);
        updateKeyboard();
        checkPinAndDots();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pin_button_0:
                actionAfterPressNumber(0);
                break;

            case R.id.pin_button_1:
                actionAfterPressNumber(1);
                break;

            case R.id.pin_button_2:
                actionAfterPressNumber(2);
                break;

            case R.id.pin_button_3:
                actionAfterPressNumber(3);
                break;

            case R.id.pin_button_4:
                actionAfterPressNumber(4);
                break;

            case R.id.pin_button_5:
                actionAfterPressNumber(5);
                break;

            case R.id.pin_button_6:
                actionAfterPressNumber(6);
                break;

            case R.id.pin_button_7:
                actionAfterPressNumber(7);
                break;

            case R.id.pin_button_8:
                actionAfterPressNumber(8);
                break;

            case R.id.pin_button_9:
                actionAfterPressNumber(9);
                break;

            case R.id.pin_button_backspace:
                if(pin.size()>0){
                    pin.remove(pin.size()-1);
                    checkPinAndDots();
                }
                break;

            case R.id.pin_button_reorder:
                changeButtonsOrder();
                break;
        }
    }

    private void actionAfterPressNumber(int i) {
        if (numberOfTries>0) {
            if (pin.size() < userPin.length()) {
                pin.add(pinNumbers.get(i));
                checkPinAndDots();
            }
        }
    }

    private boolean checkPinAndDots() {
        for(int i = 0; i< userPin.length(); i++){
            ViewGroup.LayoutParams changeParams = pinDot[i].getLayoutParams();
            final int smallDot = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
            final int largeDot = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

            if (i<pin.size()){
                changeParams.height = largeDot;
                changeParams.width = largeDot;
                pinDot[i].setLayoutParams(changeParams);
            }else {
                changeParams.height = smallDot;
                changeParams.width = smallDot;
                pinDot[i].setLayoutParams(changeParams);
            }
        }

        if (pin.size()== userPin.length()){
            //Checking PIN
            String pinString = "";
            for (Integer pinNumber:pin) {
                pinString+=pinNumber;
            }
            if (userPin.equals(pinString)){
                Toast.makeText(getContext(),"PIN POPRAWNY!\nPIN: "+pinString,Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).setAccessibilityToLockedFunctions(true);
                return true;
            }else {
                Toast.makeText(getContext(),"PODANO NIEPOPRAWNY PIN\nPin użytkownika"+ userPin +"\nPodany PIN: "+pinString+"\nSize: "+pin.size(),Toast.LENGTH_LONG).show();
                numberOfTries-=1;

                //people if they wanted to know


                while (pin.size() >0){
                    pin.remove(pin.size()-1);
                }

                checkPinAndDots();
                pinString = "";

                if (numberOfTries<=0) {
                    numberOfTries = 0;
                    triesLeft.setText("Nie udało się odblokować ekranu. Spróbuj ponownie później");

                }
                else{
                    triesLeft.setText("Pozostało prób: "+numberOfTries);
                }
            }
        }
        return false;
    }

    private void updateKeyboard() {
        pinButton0.setText(pinNumbers.get(0) + "");
        pinButton1.setText(pinNumbers.get(1) + "");
        pinButton2.setText(pinNumbers.get(2) + "");
        pinButton3.setText(pinNumbers.get(3) + "");
        pinButton4.setText(pinNumbers.get(4) + "");
        pinButton5.setText(pinNumbers.get(5) + "");
        pinButton6.setText(pinNumbers.get(6) + "");
        pinButton7.setText(pinNumbers.get(7) + "");
        pinButton8.setText(pinNumbers.get(8) + "");
        pinButton9.setText(pinNumbers.get(9) + "");
    }
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.pin_button_reorder:
                orderList();
                return true;
            default:
                return false;
        }
    }
}
