package br.edu.dmos5.loginlocal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/*
Essa classe adiciona e salva um novo usuário
 */
public class NovoUsuarioActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText novoUsuarioEditText;
    private EditText novaSenhaEditText;
    private EditText confirmaSenhaEditText;
    private Button salvarButton;
    private User mUser;
    private List<User> userList = null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);
        novoUsuarioEditText = findViewById(R.id.edittext_novo_usuario);
        novaSenhaEditText = findViewById(R.id.edittext_nova_senha);
        confirmaSenhaEditText = findViewById(R.id.edittext_confirme_senha);
        salvarButton = findViewById(R.id.button_salvar);
        salvarButton.setOnClickListener(this);

        mSharedPreferences = this.getSharedPreferences(getString(R.string.file_usuario), MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        recuperaUsuarios();
    }

    @Override
    public void onClick(View view) {
        if(view == salvarButton){
            String usuario;
            String senha;
            String confirma;

            usuario = novoUsuarioEditText.getText().toString();
            senha = novaSenhaEditText.getText().toString();
            confirma = confirmaSenhaEditText.getText().toString();

            if(!senha.equals(confirma)){ //Se as duas senhas não baterem, msg de erro
                Toast.makeText(this, getString(R.string.erro_confirma_senha), Toast.LENGTH_SHORT).show();
                novaSenhaEditText.setText("");
                confirmaSenhaEditText.setText("");
                return;
            }

            mUser = new User(usuario, senha); //Se as senhas baterem, crio um user dentro de mUser

            adicionarBD(); //Adiciono nas SharedPreferences
            finish(); //fecha a view??
        }
    }

    /*
    Esse método recupera um usuário do banco
     */
    private void recuperaUsuarios(){
        String users = mSharedPreferences.getString(getString(R.string.key_usuarios_db), ""); //Pego a string com todos os users

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        userList = new ArrayList<>();

        try{
            jsonArray = new JSONArray(users); //Passo a string pro arrayJson

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i); //separo em objs
                mUser = new User(jsonObject.getString("username"), jsonObject.getString("password"));
                userList.add(mUser);//crio user com esses dados para dentro do list
            }
        } catch (JSONException ex){
            userList = null;
        }

        if (userList != null) {
            for (User u : userList) {
                Log.i(getString(R.string.tag), u.toString());
            }
        }
    }

    /*
    Esse método adiciona um usuário nas sharedPreferences
     */
    private void adicionarBD(){
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();

        //Verifico se userList existe, se não existe eu crio
        if(userList == null){
            userList = new ArrayList<>(1);
        }

        //Adiciono o mUser na userlist
        userList.add(mUser);

        //Para cada usuário presente no list:
        for(User u : userList){
            jsonObject = new JSONObject(); //Crio um objeto json
            try {
                jsonObject.put("username", u.getUsername()); //ponho nome e senha no obj
                jsonObject.put("password", u.getPassword());
                jsonArray.put(jsonObject); //Coloco o obj no array
            }catch (JSONException e){
                Log.e(getString(R.string.tag), getString(R.string.erro_recupera_lista));
            }
        }

        String users = jsonArray.toString(); //Crio uma string com todos os users

        mEditor.putString(getString(R.string.key_usuarios_db), users); //jogo a string para as preferences
        mEditor.commit();
    }
}