package com.gdg.studyjam.smartchef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;


public class NavigationDrawerFragment extends Fragment {
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawerlLayout;
    private RecyclerView recyclerView;
    ListView listView;

    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    public NavigationDrawerFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        listView= (ListView) view.findViewById(R.id.listViewNavBar);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems);

        listItems.add(0,"Courses");
        listItems.add(1,"Cuisins");
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            Intent intent;
                                            @Override
                                            public void onItemClick (AdapterView < ? > arg0, View arg1,int pos, long id){
                                                // TODO Auto-generated method stub
                                                if(pos==0) {

                                                    intent = new Intent(getActivity(), CoursesSearchActivity.class);
                                                    startActivity(intent);
                                                }
                                                else{

                                                    intent=new Intent(getActivity(), CuisinesSearchActivity.class);
                                                    startActivity(intent);
                                                }



                                            }
                                        }

        );
        //recyclerView = (RecyclerView) view.findViewById(R.id.drawer_list);

        return view;
    }


    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerlLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < 0.5) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        mDrawerlLayout.setDrawerListener(drawerToggle);
        mDrawerlLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });


    }




}
