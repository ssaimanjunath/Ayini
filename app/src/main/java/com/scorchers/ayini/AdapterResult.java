package com.scorchers.ayini;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssaim on 07-07-2018.
 */

public class AdapterResult extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<DataResult> data= Collections.emptyList();
    DataResult current;
    public AdapterResult(Context context, List<DataResult> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.content_result, parent,false);
        AdapterResult.MyHolder holder=new AdapterResult.MyHolder(view);
        return holder;
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AdapterResult.MyHolder myHolder= (AdapterResult.MyHolder) holder;
        final DataResult current=data.get(position);
        myHolder.crop_name.setText(current.crop_name);
        float mark_c = Float.parseFloat(current.mark);
        mark_c = mark_c/60;
        mark_c = mark_c * 100;
        int m_c = Math.round(mark_c);
        if(mark_c>0)
        {
            myHolder.mark_p.setProgress(m_c);
        }
        else
        {
            myHolder.mark_p.setProgress(0);
        }
        int crop_id = Integer.parseInt(current.crop_id);
        switch (crop_id)
        {
            case 1 : myHolder.crop_img.setImageResource(R.drawable.peanut);
                     myHolder.short_desc.setText("Peanut, Arachis hypogaea, is an herbaceous annual plant in the family Fabaceae grown for its oil and edible nuts. Peanut plants are small, usually erect, thin stemmed plants with feather-like leaves. The leaves are arranged in alternate pairs and have leaf-like attachments near the stalk. The peanut plant produces yellow, orange, cream or white flowers which produce 'pegs', characteristic floral structures which sink into the ground to grow the pod. ");
                break;
            case 2 : myHolder.crop_img.setImageResource(R.drawable.potato);
                     myHolder.short_desc.setText("The potato is a starchy, tuberous crop from the perennial nightshade Solanum tuberosum. Potato may be applied to both the plant and the edible tuber. Potatoes have become a staple food in many parts of the world and an integral part of much of the world's food supply. Potatoes are the world's fourth-largest food crop, following maize (corn), wheat, and rice. Tubers produce glycoalkaloids in small amounts. ");
                break;
            case 3 : myHolder.crop_img.setImageResource(R.drawable.garlic);
                     myHolder.short_desc.setText("Garlic, (Allium sativum), perennial plant of the amaryllis family (Amaryllidaceae), grown for its flavourful bulbs. The plant is native to central Asia but grows wild in Italy and southern France and is a classic ingredient in many national cuisines.");
                break;
            case 4 : myHolder.crop_img.setImageResource(R.drawable.onion);
                     myHolder.short_desc.setText("Onion, Allium cepa, is an herbaceous biennial in the family Liliaceae grown for its edible bulb. The stem of the plant is a flattened disc at the base and the tubular leaves form a pseudostem where their sheaths overlap. The leaves are either erect or oblique and there are 3–8 per plant.");
                break;
            case 5 : myHolder.crop_img.setImageResource(R.drawable.orange);
                     myHolder.short_desc.setText("An orange is a type of citrus fruit which people often eat. Oranges are a very good source of vitamins, especially vitamin C. Orange juice is an important part of many people's breakfast. The sweet orange, which is the kind that are most often eaten today, grew first in South and East Asia but now grows in many parts of the world. ");
                break;
            case 6 : myHolder.crop_img.setImageResource(R.drawable.peas);
                     myHolder.short_desc.setText("It belongs to Leguminaceae family. It is a cool season crop grown throughout the world. Green pods are used for vegetable purpose and dried peas are used as pulse. In India it is cultivated in Himachal Pradesh, Madhya Pradesh, Rajasthan, Maharashtra, Punjab, Haryana, Karnataka and Bihar. ");
                break;
            case 7 : myHolder.crop_img.setImageResource(R.drawable.rice);
                     myHolder.short_desc.setText("Rice, edible starchy cereal grain and the plant by which it is produced. Roughly one-half of the world population, including virtually all of East and Southeast Asia, is wholly dependent upon rice as a staple food. ");
                break;
            case 8 : myHolder.crop_img.setImageResource(R.drawable.tomato);
                     myHolder.short_desc.setText("Tomato, Lycopersicum esculentum (syn. Solanum lycopersicum and Lycopersicon lycopersicum) is an herbaceous annual in the family Solanaceae grown for its edible fruit. The plant can be erect with short stems or vine-like with long, spreading stems.");
                break;
            case 9 : myHolder.crop_img.setImageResource(R.drawable.wheat);
                     myHolder.short_desc.setText("Wheat is a grass widely cultivated for its seed, a cereal grain which is a worldwide staple food. The many species of wheat together make up the genus Triticum; the most widely grown is common wheat (T. aestivum). Wheat is an important source of carbohydrates. Globally, it is the leading source of vegetal protein in human food, having a protein content of about 13%, which is relatively high compared to other major cereals but relatively low in protein quality for supplying essential amino acids. ");
                break;
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView crop_name;
        TextView short_desc;
        CircleImageView crop_img;
        ArcProgress mark_p;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            crop_name= itemView.findViewById(R.id.crop_name);
            short_desc= itemView.findViewById(R.id.short_desc);
            crop_img = itemView.findViewById(R.id.crop_img);
            mark_p = itemView.findViewById(R.id.crop_mark);
        }

    }
}
