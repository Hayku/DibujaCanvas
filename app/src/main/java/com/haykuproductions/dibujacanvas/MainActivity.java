package com.haykuproductions.dibujacanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressBar bar;
    public TextView tv;
    public float cox, coy;
    Switch sw;
    int i = 0;
    int opc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sw = (Switch) findViewById(R.id.switch1);
        tv = (TextView) findViewById(R.id.lbProgress);
        bar = (ProgressBar) findViewById(R.id.progressbar);
        bar.setMax(50);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_circunferencias) {
            opc = 1;
        } else if (id == R.id.nav_rectangulos) {
            opc = 2;
        } else if (id == R.id.nav_limpia) {
            opc = 3;
        } else if (id == R.id.nav_ocultar) {
            oculta();
        } else if (id == R.id.nav_pulsar) {
            pulsar();
        } else if (id == R.id.nav_pulsar2) {
            pulsarm();
        } else if (id == R.id.nav_hilo) {
            hilo();
        } else if (id == R.id.nav_salir) {
            System.exit(0);}


        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.layout);
        layout1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                cox = arg1.getX();
                coy = arg1.getY();
                Toast.makeText(getApplication(), "X=" + cox + " Y=" + coy, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        Lienzo fondo = new Lienzo(this, tv, sw);
        layout1.addView(fondo);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    public void pulsar() {
        bar.setVisibility(View.VISIBLE);
        if (i > 50)
            i = 0;
        bar.setProgress(i++);
        tv.setText("" + i);
    }
    public void pulsarm() {
        bar.setVisibility(View.VISIBLE);
        if (i < 0)
            i = 50;
        bar.setProgress(i--);
        tv.setText("" + i);
    }

    public void oculta() {
        if (bar.getVisibility() == ProgressBar.VISIBLE)
            bar.setVisibility(View.GONE);
        else
            bar.setVisibility(View.VISIBLE);
    }


    public void addFive(View v) {
        for (int i = 0; i < 5; i++) {
            pulsar();
        }
    }
    public void lessFive(View v) {
        for (int i = 0; i < 5; i++) {
            pulsarm();
        }
    }


    public void hilo() {
        AsyncTaskCargaDatos as = new AsyncTaskCargaDatos();
        as.execute();

    }

    public class AsyncTaskCargaDatos extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            for (int i = 1; i <= 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                publishProgress(i * 1);

                if (isCancelled())
                    break;
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            tv.setText("" + progreso);
            bar.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            bar.setMax(50);
            bar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                Toast.makeText(MainActivity.this, "Tarea finalizada!",
                        Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    class Lienzo extends View {

        public Lienzo(Context context, TextView tv, Switch sw) {
            super(context);
        }
        protected void onDraw(Canvas canvas) {
            int ancho = canvas.getWidth();
            int alto = canvas.getHeight();


            Paint pincel1 = new Paint();

            limpiar(canvas, pincel1, ancho, alto);
            //59-131-189
            pincel1.setARGB(255, 0,255, 0);
            if (sw.isChecked())
                pincel1.setStyle(Paint.Style.FILL);
            else
                pincel1.setStyle(Paint.Style.STROKE);

            switch (opc) {
                case 1:
                    dibujaCirculo(canvas, pincel1, ancho, alto);
                    break;
                case 2:
                    dibujaRectangulo(canvas, pincel1, ancho, alto);
                    break;
                case 3:
                    limpiar(canvas, pincel1, ancho, alto);
                    break;
            }
        }

        public void limpiar(Canvas canvas, Paint pincel1, int ancho, int alto) {
            pincel1.setColor(Color.WHITE);
            pincel1.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, ancho, alto, pincel1);
        }

        public void dibujaCirculo(Canvas canvas, Paint pincel1, int ancho, int alto) {
            for (int f = 0; f < Integer.parseInt(tv.getText().toString()); f++) {
                canvas.drawCircle(cox, coy, f * 20, pincel1);
            }
        }

        public void dibujaRectangulo(Canvas canvas, Paint pincel1, int ancho, int alto) {
            for (int f = 0; f < Integer.parseInt(tv.getText().toString()); f++) {
                canvas.drawRect(cox, coy, f * 15 + 300, f * 15 + 300, pincel1);
            }
        }

    }
}