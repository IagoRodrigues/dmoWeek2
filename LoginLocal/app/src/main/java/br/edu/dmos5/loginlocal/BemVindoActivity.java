package br.edu.dmos5.loginlocal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class BemVindoActivity extends AppCompatActivity {

    private TextView mensagemTextView;
    private String usuario;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        mensagemTextView = findViewById(R.id.textview_mensagem);

        //Apresenta navigation up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recupera os dados enviados pela MainActivity
        Intent intent = getIntent();
        usuario = intent.getStringExtra(getString(R.string.key_usuario));
        senha = intent.getStringExtra(getString(R.string.key_senha));

        //Vamos analisar se o usuário pode logar ou não
        validarUsuario();
    }

    private void validarUsuario() {
        //Observe que a validação se dá com os dados armazenados no arquivo de recursos.
        if (usuario.equals(getString(R.string.user_default)) &&
                senha.equals(getString(R.string.passwd_default))) {
            mensagemTextView.setText(R.string.bem_vindo_msg);
        } else {
            mensagemTextView.setText(R.string.erro_login_msg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}