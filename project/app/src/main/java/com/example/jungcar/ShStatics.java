package com.example.jungcar;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ShStatics extends AppCompatActivity {

    static StringRequest request;
    static RequestQueue requestQueue;
    String json;
    ImageButton btnBack;
    RecyclerView yearList;
    TextView tvCharge, tvMaintain,tvTotal, tvDist,tvAmount,tvEffi;
    BarChart chOutcome;
    LineChart chEffi;
    yearAdpt yearadpt;
    ArrayList<yearItem> yearItems;
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###.####");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shstatics);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        yearList = (RecyclerView) findViewById(R.id.yearList);
        tvCharge = (TextView) findViewById(R.id.tvCharge);
        tvMaintain = (TextView) findViewById(R.id.tvMaintain);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvDist = (TextView) findViewById(R.id.tvDist);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvEffi = (TextView) findViewById(R.id.tvEffi);
        chOutcome = (BarChart) findViewById(R.id.chOutcome);
        chEffi = (LineChart) findViewById(R.id.chEffi);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        yearList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));



        setYear();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
    public void setBarChart(String year) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setBarChart.php";

        ArrayList<BarEntry> oils = new ArrayList<>();
        ArrayList<BarEntry> maintains = new ArrayList<>();
        ArrayList<String> months = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴


                try {

                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();

                    int index = 0;

                    try {

                        while (true) {

                            parsing.barChartParsing(response, index);
                            if (parsing.month == "") {

                                break;
                            }

                            String month = parsing.month;
                            String oil =  parsing.oil;
                            String maintain = parsing.maintain;

                            months.add(month+"월");
                            months.add("");
                            oils.add(new BarEntry((index*2),Float.parseFloat(oil)));
                            maintains.add(new BarEntry((index*2+1),Float.parseFloat(maintain)));
                            index++;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BarDataSet barDataSet1 = new BarDataSet(oils, "주유");
                barDataSet1.setColors(Color.rgb(99,00,00));
                BarDataSet barDataSet2 = new BarDataSet(maintains, "정비");
                barDataSet2.setColors(Color.rgb(00,00,99));
                barDataSet1.setValueTextColor(Color.WHITE);
                barDataSet2.setValueTextColor(Color.WHITE);
                barDataSet1.setValueTextSize(13F);
                barDataSet2.setValueTextSize(13F);
                BarData data = new BarData(barDataSet1,barDataSet2);
                data.setBarWidth(0.5f);

                XAxis xAxis = chOutcome.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(12f);
                xAxis.setDrawGridLines(false);
                xAxis.setLabelCount(months.size()*2);
                xAxis.setGranularity(1f);
                xAxis.setTextColor(Color.WHITE);

                Legend legend = chOutcome.getLegend();

                legend.setTextColor(Color.WHITE);

                YAxis yAxis = chOutcome.getAxisLeft();
                yAxis.setTextColor(Color.WHITE);



                xAxis.setGranularityEnabled(true);

                chOutcome.animateY(1000);
                chOutcome.setData(data);
                chOutcome.setFitBars(true);
                chOutcome.getAxisRight().setEnabled(false);
                chOutcome.getDescription().setEnabled(false);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음

                param.put("car_idx",shared_preferences.get_user_car(ShStatics.this));
                param.put("year",year);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setLineChart(String year) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setLineChart.php";

        ArrayList<Entry> effis = new ArrayList<>();

        ArrayList<String> months = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴


                try {

                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();

                    int index = 0;

                    try {

                        while (true) {

                            parsing.lineChartParsing(response, index);
                            if (parsing.month == "") {

                                break;
                            }

                            String month = parsing.month;
                            String effi = parsing.effi;

                            months.add(month+"월");
                            effis.add(new BarEntry(index,Float.parseFloat(effi)));
                            index++;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LineDataSet lineDateSet =new LineDataSet(effis,null);
                lineDateSet.setValueTextColor(Color.WHITE);
                lineDateSet.setValueTextSize(12f);
                LineData lineData =new LineData(lineDateSet);

                XAxis xAxis = chEffi.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(12f);
                xAxis.setDrawGridLines(false);
                xAxis.setLabelCount(months.size());
                xAxis.setGranularity(1f);
                xAxis.setTextColor(Color.WHITE);
                YAxis yAxis = chEffi.getAxisLeft();
                yAxis.setTextColor(Color.WHITE);
                yAxis.setTextSize(10f);
                chEffi.setData(lineData);
                chEffi.animateX(1000);
                chEffi.getLegend().setEnabled(false);
                chEffi.getAxisRight().setEnabled(false);
                chEffi.getDescription().setEnabled(false);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음

                param.put("car_idx",shared_preferences.get_user_car(ShStatics.this));
                param.put("year",year);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }

    public void setStat(String year) {

        //php url 입력
        String URL = "http://172.26.126.172:443/setStat.php";

        ArrayList<Entry> effis = new ArrayList<>();

        ArrayList<String> months = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴


                try {

                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();
                    try {
                        parsing.setStatParsing(json);
                         } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    tvMaintain.setText("₩"+decimalFormat.format(Integer.parseInt(parsing.maintain)));
                    tvTotal.setText("₩"+decimalFormat.format(Integer.parseInt(parsing.number)));
                    tvCharge.setText("₩"+decimalFormat.format(Float.parseFloat(parsing.oil)));
                    tvDist.setText(decimalFormat.format(Float.parseFloat(parsing.distance))+"km");
                    tvEffi.setText(decimalFormat2.format(Float.parseFloat(parsing.effi))+"km/L");
                    tvAmount.setText(decimalFormat2.format(Float.parseFloat(parsing.amount))+"L");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음

                param.put("car_idx",shared_preferences.get_user_car(ShStatics.this));
                param.put("year",year);
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }





    public void setYear() {

        //php url 입력
        String URL = "http://172.26.126.172:443/setYear.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            JSONObject jsonObject;

            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴


                try {
                    yearItems = new ArrayList<yearItem>();
                    jsonObject = new JSONObject(response);
                    json = response;
                    Parsing parsing = new Parsing();

                    int index = 0;

                    try {
                        yearItem mi;
                        while (true) {

                            parsing.yearParsing(response, index);
                            if (parsing.year == "") {

                                break;
                            }

                            String year = parsing.year;
                            index++;
                            mi = new yearItem(year);
                            yearItems.add(mi);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                yearadpt = new yearAdpt(ShStatics.this, yearItems);

                yearadpt.setOnItemClickListener(new yearAdpt.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        if(yearadpt.getIdx()!=pos) {
                            setBarChart(yearItems.get(pos).year);
                            setLineChart(yearItems.get(pos).year);
                            setStat(yearItems.get(pos).year);
                        }
                    }
                });
                yearList.setAdapter(yearadpt);
                if(yearList.getAdapter().getItemCount()!=0) {
                    yearadpt.setIdx(yearList.getAdapter().getItemCount() - 1);
                    setBarChart(yearItems.get(yearadpt.getIdx()).year);
                    setLineChart(yearItems.get(yearadpt.getIdx()).year);
                    setStat(yearItems.get(yearadpt.getIdx()).year);
                }
            }


            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴


            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음

                param.put("car_idx",shared_preferences.get_user_car(ShStatics.this));

                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);


    }
}

class yearItem {
    String year;
    int chk;
    yearItem(String year){
        this.year = year;
        this.chk = 0;
    }
}
class yearAdpt extends RecyclerView.Adapter<yearAdpt.ViewHolder> {

    ArrayList<yearItem> itemList = new ArrayList<>();
    Context context;
    int idx;

    yearAdpt(Context context,ArrayList<yearItem> itemList) {
        this.itemList = itemList;
        this.context=context;
    }


    @Override
    public yearAdpt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yearlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(yearAdpt.ViewHolder holder, int position) {
        yearItem item = itemList.get(position);
        final int pos = position;
        holder.year.setText(item.year);
        if (itemList.get(pos).chk==1){
            holder.line.setVisibility(View.VISIBLE);
        }else{
            holder.line.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public void setItems(ArrayList<yearItem> itemList) {
        this.itemList = itemList;
    }

    public void setIdx(int idx){
        this.idx=idx;
        itemList.get(idx).chk=1;
        notifyDataSetChanged();

    }

    public int getIdx(){
        return idx;
    }


    public String getItem() {
        return itemList.get(idx).year;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView year;
        View line;

        public ViewHolder(View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.year);
            line = itemView.findViewById(R.id.line);

            year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                            int tmp = getIdx();
                            itemList.get(tmp).chk=0;
                            setIdx(position);

                        }
                    }
                }
            });

        }
    }
}
