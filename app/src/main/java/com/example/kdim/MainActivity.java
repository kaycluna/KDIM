package com.example.kdim;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kdim.database.ScriptDDL;
import com.example.kdim.database.bdtipoaço;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText edtnsd;
    private  EditText edtmdx;
    private  EditText edtmdy;
    private  TextView testezx;
    private ProgressBar load;
    private TextView statusbarra ;
    private int progresso = 0;
    private Handler mHandler = new Handler();
    private Button continuar01;
    private  Toolbar toolbar;
    private double mcalc = 0;
    private MenuItem botsobre;
    public double zx=0;
    private SQLiteDatabase conexao;
    private  SQLiteDatabase gravar;
    private bdtipoaço bdtipoaço;

    //conexao bd
    public void criarConexao(){
        try {

            bdtipoaço = new bdtipoaço(this);
            conexao = bdtipoaço.getWritableDatabase();
            gravar = bdtipoaço.getWritableDatabase();

            //mss de conexao ok
            Toast toast = Toast.makeText(this, "Conexão Realizada com Sucesso.",Toast.LENGTH_LONG);
            toast.show();
            //fim da msg

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok",null);
            dlg.show();

        }
    }


    // fim banco de daods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtnsd = (EditText) findViewById(R.id.edtnsd);
        edtmdx = (EditText) findViewById(R.id.edtmdx);
        edtmdy = (EditText) findViewById(R.id.edtmdy);
        testezx = (TextView) findViewById(R.id.testezx);
        load = (ProgressBar) findViewById(R.id.load);
        statusbarra = (TextView) findViewById(R.id.statusbarra);
        continuar01 = (Button) findViewById(R.id.continuar01);
        botsobre = (MenuItem) findViewById(R.id.botsobre);


        //Tirando o nome da barra
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //fim

       criarConexao();
    }

    //inicio do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent itconfug = new Intent(MainActivity.this, telaconfig.class);
            startActivity(itconfug);
        }else if (id==R.id.botsobre){
            Intent itsobre = new Intent(MainActivity.this, telasobre.class);
            startActivity(itsobre);
        }else if (id== R.id.botajuda){
            Intent itajuda = new Intent(MainActivity.this, telaajuda.class);
            startActivity(itajuda);

        }

        return super.onOptionsItemSelected(item);
    }
    //fim do menu

    // BOTÃO ANALISAR
    public void analisar(View view){

        Global global = (Global) getApplicationContext();

        double mdx = Double.valueOf(edtmdx.getText().toString());
        double mdy = Double.valueOf(edtmdy.getText().toString());
        double fy = 345;

        if (mdx>=mdy) {
            zx = ((mdx * Math.pow(10, 6)) *global.ya1) / (fy * 1000);
            testezx.setText(String.valueOf(zx));
            global.setZxcalc(zx);

        }else if (mdy>mdx){

             zx = ((mdy * Math.pow(10, 6)) * global.ya1) / (fy * 1000);
            testezx.setText(String.valueOf(zx));
            global.setZxcalc(zx);
        }

        load.setVisibility(1); //deixando a barra de progresso visivel
        statusbarra.setVisibility(1); //deixandoo satatus da barra visivel

        // BARRA DE PROGRESSO
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progresso< 100){
                    progresso++;
                    android.os.SystemClock.sleep(15);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            load.setProgress(progresso);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                       statusbarra.setText("Concluido.");
                       continuar01.setVisibility(1);
                    }
                });
            }
        }).start();
        //FIM  BARRA DE PROGRESSO
    }
      //FIM BOTÃO ANALISAR

    //botão chamar tela zx
    public void chamarzx (View view){
        Intent it = new Intent(MainActivity.this, telazx.class);
        startActivity(it);

    }
    // fim chamada telazx






    }



