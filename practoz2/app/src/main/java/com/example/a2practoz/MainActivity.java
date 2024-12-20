package com.example.a2practoz;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView1 = findViewById(R.id.recycler_view1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        List<Item> itemList1 = generateItemList("FLKA ");
        ItemAdapter adapter1 = new ItemAdapter(this, itemList1);
        recyclerView1.setAdapter(adapter1);
    }

    // генерация списка
    private List<Item> generateItemList(String prefix) {
        List<Item> itemList = new ArrayList<>();

        itemList.add(new Item(prefix + "1", "FabFilter " + prefix + "1", R.drawable.img1));
        itemList.add(new Item(prefix + "2", "Valhalaa " + prefix + "2", R.drawable.img2));
        itemList.add(new Item(prefix + "3", "Cramit " + prefix + "3", R.drawable.img3));
        itemList.add(new Item(prefix + "4", "Soothe2 " + prefix + "4", R.drawable.img4));

        return itemList;
    }

}