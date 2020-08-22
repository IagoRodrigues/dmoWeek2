package br.edu.dmos5.loginlocal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Objetos utilizados para armazenar e recuperar os dados
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText usuarioEditText;
    private EditText senhaEditText;
    private Button logarButton;
    private CheckBox lembrarCheckBox;
    private TextView novoUsuarioTextView;
    private String usuario, senha;

    /*
    Esse método executa só, quando a activity é criada, instanciando as coisas que serão usadas.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuarioEditText = findViewById(R.id.edittext_usuario);
        senhaEditText = findViewById(R.id.edittext_senha);
        logarButton = findViewById(R.id.button_logar);
        lembrarCheckBox = findViewById(R.id.checkbox_lembrar);
        novoUsuarioTextView = findViewById(R.id.textview_novo);
        logarButton.setOnClickListener(this);
        novoUsuarioTextView.setOnClickListener(this);

        //Vamos instanciar as preferencias em modo privado, ou seja, somente acessíveis
        //ao próprio app.
        mSharedPreferences = this.getPreferences(MODE_PRIVATE);
        //mSharedPreferences = this.getSharedPreferences(getString(R.string.file_preferences),MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /*
    Esse método executa toda vez depois da activity ser criada ou restartada
     */
    protected void onStart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onStart()");
        super.onStart();
    }

    /*
    Esse método executa toda vez que a activity é restartada
     */
    @Override
    protected void onRestart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onRestart()");
        super.onRestart();
    }

    /*
    Esse método é executado toda vez que a activity fica ativa em primeiro plano, por isso:

    No onResume() é um bom momento para verificar se o usuário possui dados armazenados ou não,
    lembre-se que o onCreate() só é executado uma vez.
     */
    @Override
    protected void onResume() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onResume()");

                //Vamos verificar se o usuário possui preferências
                verificarPreferencias();

        super.onResume();
    }

    /*
    Se quisermos salvar os dados inseridos no formulário para que o usuário possa voltar a digitar de onde parou quando essa
    activity sair de foco, aqui seria o lugar pra chamar o método responsável por guardar isso (salvaPreferencias() só salva
    quando a caixa está habilitada) e é chamado por onClick no botão login
     */
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

    /*
    Executado toda vez que rotacionamos a tela, quando fechamos a activity e quando a activity está
    na pilha e é necessário liberar memória
     */
    @Override
    protected void onDestroy() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onDestroy()");
        super.onDestroy();
    }

    /*
    Três return no mesmo método?? O.o hahaha
    Podemos criar uma exception para campos vazios como eu fiz na semana 1
     */
    @Override
    public void onClick(View view) {
        if(view == logarButton){
            usuario = usuarioEditText.getText().toString();
            senha = senhaEditText.getText().toString();
            if(usuario.isEmpty() || senha.isEmpty()){
                Toast.makeText(this, R.string.erro_entrada_msg, Toast.LENGTH_SHORT).show();
                return;
            }

            //Antes de abrir a outra tela se verifica se o usuário deseja armazenar
            //os dados de login para outros acessos.
            salvaPreferencias();
            abrirBoasVindas();
            return;
        }
        if(view == novoUsuarioTextView){
            Intent in = new Intent(this, NovoUsuarioActivity.class);
            startActivity(in);
            return;
        }
    }

    /*
    Na semana 1 acho que não extraí essa "funcionalidade" para outro método,
    fica pra uma próxima!
     */
    private void abrirBoasVindas(){
        Intent in = new Intent(this, BemVindoActivity.class);
        Bundle args = new Bundle();
        args.putString(getString(R.string.key_usuario), usuario);
        args.putString(getString(R.string.key_senha), senha);
        in.putExtras(args);
        startActivity(in);
    }

    /*
    Se o usuário marcou para não lembrar, salvo os campos vazios... interessante!
     */
    private void salvaPreferencias(){
        //Caso o checkbox esteja marcado, armazenamos os dados no objeto,
        //caso contrário vamos apenas armazenar um vazio.
        if(lembrarCheckBox.isChecked()){
            mEditor.putString(getString(R.string.key_usuario), usuario);
            mEditor.commit();
            mEditor.putString(getString(R.string.key_senha), senha);
            mEditor.commit();
            mEditor.putBoolean(getString(R.string.key_lembrar), true);
            mEditor.commit();
        }else{
            mEditor.putString(getString(R.string.key_usuario), "");
            mEditor.commit();
            mEditor.putString(getString(R.string.key_senha), "");
            mEditor.commit();
            mEditor.putBoolean(getString(R.string.key_lembrar), false);
            mEditor.commit();
        }
    }

    /*
    Aqui recuperamos as preferências do usuário, e caso existam (boolean lembrar) atualizamos
    os dados na tela da activity.

    Passo a chave do valor que quero trazer e o valor padrão caso essa preference não exista.
    Busco usuario, senha e se o check está marcado pra lembrar, se estiver, trago o que encontrei
    e preencho os campos.
     */
    private void verificarPreferencias() {
        //getString(String key, String defValue)
        usuario = mSharedPreferences.getString(getString(R.string.key_usuario), "");
        senha = mSharedPreferences.getString(getString(R.string.key_senha), "");

        //getString(String key, String defValue)
        boolean lembrar = mSharedPreferences.getBoolean(getString(R.string.key_lembrar), false);
        if(lembrar){
            usuarioEditText.setText(usuario);
            senhaEditText.setText(senha);
            lembrarCheckBox.setChecked(true);
        }
    }
}