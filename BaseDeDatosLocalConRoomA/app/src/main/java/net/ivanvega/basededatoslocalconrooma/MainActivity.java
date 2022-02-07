package net.ivanvega.basededatoslocalconrooma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import net.ivanvega.basededatoslocalconrooma.data.AppDatabase;
import net.ivanvega.basededatoslocalconrooma.data.User;
import net.ivanvega.basededatoslocalconrooma.data.UserDao;

public class MainActivity extends AppCompatActivity {

    ;
Button btnIn ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIn = findViewById(R.id.btnInsert);

        btnIn.setOnClickListener(view -> {

           AppDatabase db =
                   AppDatabase.getDatabaseInstance(getApplication());

           UserDao dao = db.userDao();



            AppDatabase.databaseWriteExecutor.execute(() -> {
                User u = new User();

                //u.uid = 0;
                u.firstName = "Juan";
                u.lastName = "Peres";

                dao.insertAll( u );
                /*
                Toast.makeText(this,
                        "Insertado",
                        Toast.LENGTH_LONG).show();

                 */
                Log.d("DBUsuario", "Elemento insertado");
            });
       });

       findViewById(R.id.btnQuery).setOnClickListener(view -> {
           AppDatabase db = AppDatabase.getDatabaseInstance(getApplication());
           UserDao dao = db.userDao();
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

               AppDatabase.databaseWriteExecutor.execute(() -> {
                   dao.getAll().stream().forEach(user -> {
                       Log.i("Consulta User",
                           user.uid + " " + user .firstName);

                   });
               });


           }else{
               AppDatabase.databaseWriteExecutor.execute(() -> {
               for ( User user : dao.getAll()){
                  Log.d("DBUsuario", user.firstName+  " "+  user.lastName);
               }
               });
           }
       });


    }
}