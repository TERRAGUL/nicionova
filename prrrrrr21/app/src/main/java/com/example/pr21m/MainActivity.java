package com.example.pr21m;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextSubject, editTextMessage;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(v -> {
            String recipient = editTextEmail.getText().toString().trim();
            String subject = editTextSubject.getText().toString().trim();
            String message = editTextMessage.getText().toString().trim();

            if (recipient.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                new SendMailTask().execute(recipient, subject, message);
            }
        });
    }
    private final String senderPassword = "lzid gajh vmxs mrsi";
    private final String senderEmail = "isip_d.s.lukyanov@mpt.ru";
    private class SendMailTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String recipient = params[0];
            String subject = params[1];
            String messageBody = params[2];

            try {
                Properties props = new Properties();

                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setText(messageBody);

                Transport.send(message);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                editTextEmail.setText("");
                editTextSubject.setText("");
                editTextMessage.setText("");
                Toast.makeText(MainActivity.this, "Письмо отправлено", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Ошибка при отправке", Toast.LENGTH_LONG).show();
            }
        }
    }
}
