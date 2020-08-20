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
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        mensagemTextView = findViewById(R.id.textview_mensagem);
        //Apresenta navigation up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        usuario = intent.getStringExtra(getString(R.string.key_usuario));
        senha = intent.getStringExtra(getString(R.string.key_senha));

        validarUsuario();
    }

    @Override
    protected void onStart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onDestroy()");
        super.onDestroy();
    }

    private void validarUsuario(){
        //Observe que a validação se dá com os dados armazenados no arquivo de recursos.
        if(usuario.equals(getString(R.string.user_default)) &&
                senha.equals(getString(R.string.passwd_default))){
            mensagemTextView.setText(R.string.bem_vindo_msg);
        }else{
            mensagemTextView.setText(R.string.erro_login_msg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}