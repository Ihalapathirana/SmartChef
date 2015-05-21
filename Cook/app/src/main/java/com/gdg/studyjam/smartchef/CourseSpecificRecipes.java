package com.gdg.studyjam.smartchef;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CourseSpecificRecipes extends ActionBarActivity implements AsyncResponse {

    ListView listView;
    String response;
    String url;
    String position;
    String imageGet;
    JSONObject joRecipy;
    String recipe;
    String cookingMethod;
    String url1;
    MyAdapter myAdapter;
    ArrayList<String> urlList;
    TextView txtView;
    Toolbar toolbar;

    ArrayList<Course> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_specific_recipes);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        WebService2 webService=new WebService2(this,"get","Please wait");
        webService.asyncResponse= this;
        urlList = new ArrayList<String>();
       // webService.execute("http://api.pearson.com:80/kitchen-manager/v1/courses?limit=30");

        txtView = (TextView) findViewById(R.id.textView8);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        position=my_bundle_received.get("item1").toString();
        url=my_bundle_received.get("item2").toString();
        Log.d("Value", "--" + my_bundle_received.get("item2").toString());

        webService.execute(url+"?limit=40");

}



    public void processFinish(String output) {

        try {


            JSONObject jo = new JSONObject(output);
            String courseName = jo.getString("recipes");
            JSONArray ja = new JSONArray(courseName);


            for (int i = 0; i < ja.length(); i++) {
                joRecipy = (JSONObject) ja.get(i);
                recipe = joRecipy.getString("name");
                cookingMethod=joRecipy.getString("cooking_method");
                imageGet=((JSONObject) ja.get(i)).getString("image");
                arrayList.add(new Course(recipe,"Cooking Method : "+cookingMethod,imageGet));
                url=joRecipy.getString("url");
                urlList.add(url);
            }

            myAdapter=new MyAdapter(this,arrayList);
            listView= (ListView) findViewById(R.id.listView3);
            listView.setAdapter(myAdapter);



            if(arrayList.size() == 0){

                txtView.setText("Recipes not found");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                        {
                                            Intent i;
                                            @Override
                                            public void onItemClick (AdapterView < ? > arg0, View arg1,int position, long id){
                                                // TODO Auto-generated method stub

                                                url1=urlList.get(position);

                                                Bundle simple_bundle = new Bundle();
                                                simple_bundle.putString("item1", String.valueOf(position));
                                                simple_bundle.putString("item2",url1);
                                                Intent intent = new Intent(CourseSpecificRecipes.this, CourseRecipeDirections.class);
                                                intent.putExtras(simple_bundle);
                                                startActivity(intent);

                                            }
                                        }

        );
    }


}
