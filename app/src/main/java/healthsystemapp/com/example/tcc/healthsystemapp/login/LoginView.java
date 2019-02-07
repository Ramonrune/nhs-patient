package healthsystemapp.com.example.tcc.healthsystemapp.login;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class LoginView {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton, registerButton;

    protected LoginView(AppCompatActivity view) {
        view.setContentView(R.layout.activity_login);
        view.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
      //  emailEditText.setText("ramonrune@gmail.com");
        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
//        passwordEditText.setText("1111");

        loginButton = (Button) view.findViewById(R.id.loginButton);
        registerButton = (Button) view.findViewById(R.id.regiterButton);

    }

    public EditText getEmailEditText() {
        return emailEditText;
    }

    public EditText getPasswordEditText() {
        return passwordEditText;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public  Button getRegisterButton(){ return registerButton; }

}
