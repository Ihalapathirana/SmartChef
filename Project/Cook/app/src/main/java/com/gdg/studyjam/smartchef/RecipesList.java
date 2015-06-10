package com.gdg.studyjam.smartchef;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceActivity;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;




public class RecipesList extends ActionBarActivity implements AsyncResponse{

    ListView listView;
    String title;
    String recipe_id_pass;
    JSONObject joRecipy;
    String webAddress;
    String getMethod;
    String response;
    TextView txtView;
    TextView countTxt;
    Boolean isRandomRecipy;
    MyAdapter myAdapter;
    Toolbar toolbar;
    String img;
    String cookMeth;

    ArrayList<Course> arrayList=new ArrayList<>();
    ArrayList<String> recipylistItems=new ArrayList<String>();
    ArrayList<String> recipe_idListItem=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> recipyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        WebService2 webService=new WebService2(this,"get","Please wait");
        webService.asyncResponse= this;

        txtView= (TextView) findViewById(R.id.textView5);
        countTxt= (TextView) findViewById(R.id.textView6);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        getMethod=my_bundle_received.get("item1").toString();
        isRandomRecipy= (Boolean) my_bundle_received.get("item2");
        Log.d("Value","--"+my_bundle_received.get("item1").toString());

    //    Parse.enableLocalDatastore(this);

     //   Parse.initialize(this, "pUmiEuyvhENbtiT1CZq1SvxGdaUjVLGUj9nFZOho", "XBbeGCQncATbn9CxGl5tlQoL3uybz3KKpy6yZ5Fe");


        if(isRandomRecipy==true){
               webAddress="http://api.pearson.com/kitchen-manager/v1/recipes?&limit=500";
        }

        else{
            webAddress="http://api.pearson.com/kitchen-manager/v1/recipes?ingredients-any="+ getMethod+"&limit=150";
        }


        webService.execute(webAddress);




    }

    @Override
    public void processFinish(String output) {
        try {

          //  response = webService.execute(webAddress ).get();

            JSONObject jo = new JSONObject(output);
            // txView.setText(jo.getString("recipes"));
            String recipy = jo.getString("results");

            String count=jo.getString("total");
            countTxt.setText(count+" ");
            JSONArray ja = new JSONArray(recipy);
            //txView.setText(ja.length()+"");


            for (int i = 0; i < ja.length(); i++) {
                joRecipy = (JSONObject) ja.get(i);
                title = joRecipy.getString("name");
                recipylistItems.add(title);
                img=joRecipy.getString("image");
                cookMeth=joRecipy.getString("cooking_method");
                arrayList.add(new Course(title,"cooking_method : "+cookMeth,img));





                String recipeName;
                recipeName = title.toString();
                RecipeDB helper=new RecipeDB(getApplicationContext());
                SQLiteDatabase writeDatabase=helper.getWritableDatabase();
                ContentValues values=new ContentValues();

                values.put(RecipeDB.TABLE_RECIPE_NAME, recipeName);
                writeDatabase.insert(RecipeDB.TABLE_RECIPE, null, values);
                writeDatabase.close();

           //     writeDatabase.insert("recipes",null,values);
           //     writeDatabase.close();
                Toast.makeText(getApplication(), "Name saved succesfuly", Toast.LENGTH_LONG).show();


//read database
                SQLiteDatabase db =helper.getReadableDatabase();
                ArrayList<String> friends = new ArrayList<String>();
                Cursor cursor = db.query(RecipeDB.TABLE_RECIPE, new String[]
                        {RecipeDB.TABLE_RECIPE_NAME},null,null,null, null, null);
                while (cursor.moveToNext()){
                    friends.add(cursor.getString(0));
                }
                Log.d("RECIPE NAMES ANUSHA IHALAPATHIRANA - ",cursor.getString(0));
                cursor.close();
                db.close();



            }





            if (arrayList.size() == 0) {
                txtView.setText("Recipes are not available");

            }

            //txView.setText(title);

            myAdapter=new MyAdapter(this,arrayList);
            listView= (ListView) findViewById(R.id.listViewRecipes);
            listView.setAdapter(myAdapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent i;
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
                // TODO Auto-generated method stub

                recipe_id_pass=recipylistItems.get(position);

                Bundle simple_bundle=new Bundle();
                simple_bundle.putString("item1", String.valueOf(position));
                simple_bundle.putString("item2",getMethod);
                simple_bundle.putBoolean("item3",isRandomRecipy);
                Intent intent=new Intent(RecipesList.this,RecipyDescription.class);
                intent.putExtras(simple_bundle);
                startActivity(intent);

            }
        });
    }
}
