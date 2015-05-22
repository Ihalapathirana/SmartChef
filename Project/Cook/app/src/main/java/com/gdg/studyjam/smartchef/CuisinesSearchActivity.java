package com.gdg.studyjam.smartchef;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CuisinesSearchActivity extends ActionBarActivity implements AsyncResponse {

    ListView listView;
    String webArddress;
    JSONObject joRecipy;
    String title;
    String response;
    String url;
    String numberOfCourses;
    String recipe_id_pass;
    String url1;
    Toolbar toolbar;
    ArrayList<String> coursesItem = new ArrayList<String>();
    ArrayList<String> urlList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    JSONObject jo;
    String courseName;
    JSONArray ja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisines_search);

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        WebService2 webService=new WebService2(this,"get","Please wait");
        webService.asyncResponse=this;
        urlList = new ArrayList<String>();
        webService.execute("http://api.pearson.com:80/kitchen-manager/v1/cuisines?limit=40");

    }


    @Override
    public void processFinish(String output) {
        //all UI updating
        ArrayList<Course> arrayList=new ArrayList<>();
        //  System.out.println(output);

        try {

          //  response = webService.execute(webArddress).get();

            jo = new JSONObject(output);
            courseName = jo.getString("results");

            String count = jo.getString("total");

            ja = new JSONArray(courseName);


            for (int i = 0; i < ja.length(); i++) {
                joRecipy = (JSONObject) ja.get(i);
                title = joRecipy.getString("name");
                url=joRecipy.getString("url");
                numberOfCourses = joRecipy.getString("recipe_count");
                urlList.add(url);
                arrayList.add(new Course(title,"Number of recipes : "+numberOfCourses));
            }

            MyAdapter myAdapter=new MyAdapter(this,arrayList);
            listView = (ListView) findViewById(R.id.listView4);
            listView.setAdapter(myAdapter);



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
                                                Intent intent = new Intent(CuisinesSearchActivity.this, CourseSpecificRecipes.class);
                                                intent.putExtras(simple_bundle);
                                                startActivity(intent);

                                            }
                                        }

        );
    }
}
