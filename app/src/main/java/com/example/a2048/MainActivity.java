package com.example.a2048;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button buttons[][];
    private Button resetButton;

    View panelPrincipal;

    public enum Direccion {
        IZQUIERDA,
        DERECHA,
        ARRIBA,
        ABAJO
    }



    private static final int TableroX = 4;
    private static final int TableroY = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        panelPrincipal = findViewById(R.id.panelPrincipal);
        buttons = new Button[TableroX][TableroY];
        resetButton = findViewById(R.id.ResetButton);


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego();
            }
        });


        iniciarJuego();

        panelPrincipal.setOnTouchListener(new View.OnTouchListener() {
            //private static final int MIN_DISTANCE = 100;
            private float startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ArrayList<Integer[]> posiciones = new ArrayList<>();



                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        startX = event.getX();
                        startY = event.getY();


                        break;

                    case MotionEvent.ACTION_UP:

                        float endX = event.getX();
                        float endY = event.getY();

                        direccionMovimiento(startX, startY, endX, endY);
                        generarDos();
                }

                return true;
            }
        });


    }



    public void iniciarJuego(){
        int id = 1;
        String boton;
        for (int i = 0; i < TableroX; i++) {
            for (int j = 0; j < TableroY; j++) {

                boton = "Boton" + id;
                int resID = getResources().getIdentifier(boton, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setText("0");
                buttons[i][j].setEnabled(false);

                id++;
            }
        }

        int inicioI = (int)(Math.random() * TableroX);
        int inicioJ = (int)(Math.random() * TableroY);
        buttons[inicioI][inicioJ].setText("2");
    }

    public void generarDos(){
        int inicioI = (int)(Math.random() * TableroX);
        int inicioJ = (int)(Math.random() * TableroY);

        while(!buttons[inicioI][inicioJ].getText().equals("0")){
            inicioI = (int)(Math.random() * TableroX);
            inicioJ = (int)(Math.random() * TableroY);
        }
        buttons[inicioI][inicioJ].setText("2");
    }

    public void reiniciarJuego(){

    }

    public void direccionMovimiento(float startX, float startY, float endX, float endY){
        float X = endX - startX;
        float Y = endY - startY;

        if (Math.abs(X) > Math.abs(Y)) {
            if (X > 0) {

                moverFichasEnFila(0,Direccion.DERECHA);
                moverFichasEnFila(1,Direccion.DERECHA);
                moverFichasEnFila(2,Direccion.DERECHA);
                moverFichasEnFila(3,Direccion.DERECHA);
            } else {

                moverFichasEnFila(0,Direccion.IZQUIERDA);
                moverFichasEnFila(1,Direccion.IZQUIERDA);
                moverFichasEnFila(2,Direccion.IZQUIERDA);
                moverFichasEnFila(3,Direccion.IZQUIERDA);
            }
        } else {
            if (Y > 0) {
                moverFichasEnColumna(0,Direccion.ABAJO);
                moverFichasEnColumna(1,Direccion.ABAJO);
                moverFichasEnColumna(2,Direccion.ABAJO);
                moverFichasEnColumna(3,Direccion.ABAJO);
            } else {

                moverFichasEnColumna(0,Direccion.ARRIBA);
                moverFichasEnColumna(1,Direccion.ARRIBA);
                moverFichasEnColumna(2,Direccion.ARRIBA);
                moverFichasEnColumna(3,Direccion.ARRIBA);
            }
        }
    }

    private void moverFichasEnColumna(int columna, Direccion direccion) {
        int[] columnaActual = new int[TableroX];
        boolean movimientos = true;

        for (int i = 0; i < TableroX; i++) {
            columnaActual[i] = Integer.parseInt(buttons[i][columna].getText().toString());
        }

        switch (direccion) {
            case ABAJO:
                while(movimientos){
                    movimientos = false;
                    for (int i = TableroX-1; i >= 0; i--) {
                        if (columnaActual[i] == 0) {
                            if(i != 0){
                                if(columnaActual[i-1] != 0){
                                    columnaActual[i] = columnaActual[i - 1];
                                    columnaActual[i - 1] = 0;
                                    buttons[i][columna].setText(buttons[i - 1][columna].getText());
                                    buttons[i - 1][columna].setText("0");
                                    movimientos = true;
                                }
                            }
                        } else {
                            if (!(i == 0)) {
                                if (columnaActual[i] == columnaActual[i-1]) {
                                    int suma = columnaActual[i] + columnaActual[i];
                                    columnaActual[i] = columnaActual[i] + columnaActual[i];
                                    columnaActual[i - 1] = 0;
                                    buttons[i][columna].setText(String.valueOf(suma));
                                    buttons[i - 1][columna].setText("0");
                                    movimientos = true;
                                }
                            }
                        }
                    }
                }
                break;


            case ARRIBA:
                while(movimientos){
                    boolean anteriorVacio = false;
                    movimientos = false;
                    for (int i = 0; i < TableroX; i++) {
                        if (columnaActual[i] == 0) {
                            if(!(i == TableroX-1)){
                                if(columnaActual[i+1] != 0){
                                    columnaActual[i] = columnaActual[i + 1];
                                    columnaActual[i + 1] = 0;
                                    buttons[i][columna].setText(buttons[i + 1][columna].getText());
                                    buttons[i + 1][columna].setText("0");
                                    movimientos = true;
                                }
                            }
                        } else {
                            if (!(i == TableroX-1)) {
                                if (columnaActual[i] == columnaActual[i+1]) {
                                    int suma = columnaActual[i] + columnaActual[i];
                                    columnaActual[i] = columnaActual[i] + columnaActual[i];
                                    columnaActual[i + 1] = 0;
                                    buttons[i][columna].setText(String.valueOf(suma));
                                    buttons[i + 1][columna].setText("0");
                                    movimientos = true;
                                }
                            }
                        }
                    }
                }
            break;
        }
    }
    private void moverFichasEnFila(int fila, Direccion direccion) {
        int[] filaActual = new int[TableroX];
        boolean movimientos = true;

        for (int i = 0; i < TableroX; i++) {
            filaActual[i] = Integer.parseInt(buttons[fila][i].getText().toString());
        }

        switch (direccion) {
            case DERECHA:
                while(movimientos){
                    movimientos = false;
                    for (int i = TableroY-1; i >= 0; i--) {
                        if (filaActual[i] == 0) {
                            if(i != 0){
                                if(filaActual[i-1] != 0){
                                    filaActual[i] = filaActual[i - 1];
                                    filaActual[i - 1] = 0;
                                    buttons[fila][i].setText(buttons[fila][i-1].getText());
                                    buttons[fila][i-1].setText("0");
                                    movimientos = true;
                                }
                            }
                        } else {
                            if (!(i == 0)) {
                                if (filaActual[i] == filaActual[i-1]) {
                                    int suma = filaActual[i] + filaActual[i];
                                    filaActual[i] = filaActual[i] + filaActual[i];
                                    filaActual[i - 1] = 0;
                                    buttons[fila][i].setText(String.valueOf(suma));
                                    buttons[fila][i-1].setText("0");
                                    movimientos = true;
                                }
                            }
                        }
                    }
                }
                break;

            case IZQUIERDA:
                while(movimientos){
                    boolean anteriorVacio = false;
                    movimientos = false;
                    for (int i = 0; i < TableroX; i++) {
                        if (filaActual[i] == 0) {
                            if(!(i == TableroX-1)){
                                if(filaActual[i+1] != 0){
                                    filaActual[i] = filaActual[i + 1];
                                    filaActual[i + 1] = 0;
                                    buttons[fila][i].setText(buttons[fila][i+1].getText());
                                    buttons[fila][i+1].setText("0");
                                    movimientos = true;
                                }
                            }
                        } else {
                            if (!(i == TableroX-1)) {
                                if (filaActual[i] == filaActual[i+1]) {
                                    int suma = filaActual[i] + filaActual[i];
                                    filaActual[i] = filaActual[i] + filaActual[i];
                                    filaActual[i + 1] = 0;
                                    buttons[fila][i].setText(String.valueOf(suma));
                                    buttons[fila][i+1].setText("0");
                                    movimientos = true;
                                }
                            }
                        }
                    }
                }
                break;
        }
    }


}