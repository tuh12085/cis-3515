package edu.temple.assignment6;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;


public class CanvasFragment extends Fragment {

    private String[] MYCOLORLIST;
    private String[] MYCOLORLIST_HEX;
    private int LENGTH_OF_COLOR;
    private Map MY_COLOR_MAP;



    public static CanvasFragment newInstance(String color, String color_hex){
        CanvasFragment canvasFragment  = new CanvasFragment();
        Bundle binder = new Bundle();
        binder.putString("color",color);
        binder.putString("color_hex",color_hex);
        canvasFragment.setArguments(binder);
        return canvasFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview = inflater.inflate(R.layout.fragment_canvas, container, false);
        map_setup();

        Bundle bundle = getArguments();

        if(bundle!=null){
            String color = bundle.getString("color");
            System.out.println("i received " + color);
            String hex = (String) MY_COLOR_MAP.get(color);
            myview.setBackgroundColor(Color.parseColor(hex));


        }else{
            System.out.println("there is nothing");
        }


        return myview;
    }


    public void map_setup(){
        //Get the list
        MYCOLORLIST = getResources().getStringArray(R.array.color_list);
        MYCOLORLIST_HEX = getResources().getStringArray(R.array.color_hex);

        Map<String, String> Color_map = new HashMap<String, String>();

        int len = MYCOLORLIST.length;
        LENGTH_OF_COLOR = len;
        for (int i = 0; i < len; i++) {
            Color_map.put(MYCOLORLIST[i], MYCOLORLIST_HEX[i]);
        }
        MY_COLOR_MAP = Color_map;
    }
}
