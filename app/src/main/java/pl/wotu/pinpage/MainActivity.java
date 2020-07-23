package pl.wotu.pinpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private boolean hasAccessToLockedFunctions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setAccessibilityToLockedFunctions(boolean hasAccessToLockedFunctions) {
        this.hasAccessToLockedFunctions = hasAccessToLockedFunctions;
    }

    public boolean setAccessibilityToLockedFunctions(){
        return this.hasAccessToLockedFunctions;
    }
}
