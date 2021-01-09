package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class ShowProducts extends AppCompatActivity {

        String[] productNames = {"Ring 1", "Ring 2", "Ring 3", "Ring 4", "Ring 5", "Ring 6","Ring 7","Ring 8","Ring 9","Ring 10","Ring 11-"};
        Integer[] productImages = {R.drawable.ring1,R.drawable.ring2,R.drawable.ring3,R.drawable.ring4,R.drawable.ring5,R.drawable.ring6,R.drawable.ring7,R.drawable.ring8,R.drawable.ring9,R.drawable.ring10,R.drawable.ring11};
        double[] price={23456.78,5666.90,3333.3,7777,9999.44,7777.666,8888.90,666.90,7777.45,333.33};
        private TextView categoryName;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_products);
            categoryName=findViewById(R.id.categoryName);
            categoryName.setText("Rings");

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.showproductRecycler);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager);

            Intent i=getIntent();
            String category_type=i.getStringExtra("category_type");
            if(category_type.equals("earrings"))
            {
                productNames = new String[]{"Earring 1", "Earring 2", "Earring 3", "Earring 4", "Earring 5", "Earring 6", "Earring 7","Earring 8","Earring 9","Earring 10","Earring 11","Earring 12"};
                productImages = new Integer[]{R.drawable.earring1, R.drawable.earring2, R.drawable.earring3, R.drawable.earring4, R.drawable.earring5, R.drawable.earring6, R.drawable.earring7,R.drawable.earring8,R.drawable.earring9,R.drawable.earring10,R.drawable.earring11,R.drawable.earring12};
                categoryName.setText("Earrings");
            }
            if(category_type.equals("necklaces"))
            {
                productNames = new String[]{"Necklace 1", "Necklace 2", "Necklace 3", "Necklace 4", "Necklace 5", "Necklace 6", "Necklace 7","Necklace 8","Necklace 9","Necklace 10","Necklace 11"};
                productImages = new Integer[]{R.drawable.necklace1, R.drawable.necklace2, R.drawable.necklace3, R.drawable.necklace4, R.drawable.necklace5, R.drawable.necklace6, R.drawable.necklace7,R.drawable.necklace8,R.drawable.necklace9,R.drawable.necklace10,R.drawable.necklace11};
                categoryName.setText("Necklaces");
            }
            ShowProductAdapter customAdapter = new ShowProductAdapter(ShowProducts.this, productNames,productImages);
            recyclerView.setAdapter(customAdapter);
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // do whatever
                            // Toast.makeText(getApplicationContext(),"Position"+productNames[position],Toast.LENGTH_LONG).show();
                            String product=productNames[position];
                            Integer perimg=productImages[position];
                            Intent i=new Intent(getApplicationContext(),SingleProductView.class);
                            i.putExtra("personImage", perimg);
                            i.putExtra("personName",product);
                            String dprice=Double.toString(price[position]);
                            i.putExtra("price",dprice);
                            startActivity(i);


                        }

                        @Override public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }
class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}