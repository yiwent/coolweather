package com.yiwen.coolweather;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwen.coolweather.db.City;
import com.yiwen.coolweather.db.County;
import com.yiwen.coolweather.db.Province;
import com.yiwen.coolweather.util.HttpUtil;
import com.yiwen.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/4/3
 * Time: 23:16
 */
public class ChooseAreaFragment extends Fragment {
    public static final int LEAVE_PROVICE = 0;
    public static final int LEAVE_CITY    = 1;
    public static final int LEAVE_COUNTY  = 2;
    private ProgressDialog       progressDialog;
    private TextView             titleText;
    private Button               backButton;
    private ListView             listView;
    private ArrayAdapter<String> adapt;
    private List<String> dataList = new ArrayList<>();
    /**
     * 省列表
     */
    private List<Province> proviceList;
    /**
     * 省列表
     */
    private List<County>   countyList;
    /**
     * 省列表
     */
    private List<City>     cityList;
    /**
     * 选中的
     */
    private Province       selectProvice;
    private County         selectCounty;
    private City           selectCity;
    private int            currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapt = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapt);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEAVE_PROVICE) {
                    selectProvice = proviceList.get(position);
                    queryCities();
                } else if (currentLevel == LEAVE_CITY) {
                    selectCity = cityList.get(position);
                    queryCounties();
                }else if (currentLevel == LEAVE_COUNTY){
                    String weatherId=countyList.get(position).getWeatherId();
                    if (getActivity()instanceof MainActivity){
                    Intent intent=new Intent(getActivity(),WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();
                    }else if (getActivity()instanceof WeatherActivity){
                        WeatherActivity activity= (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefesh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }

                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEAVE_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEAVE_CITY) {
                    queryProvices();
                }
            }
        });
        queryProvices();
    }

    private void queryProvices() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        proviceList = DataSupport.findAll(Province.class);
        if (proviceList.size() > 0) {
            dataList.clear();
            for (Province provice : proviceList) {
                dataList.add(provice.getProviceNane());
            }
            adapt.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEAVE_PROVICE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "provice");
        }
    }

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkhttpReques(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("provice".equals(type)) {
                    result = Utility.handProviceRespose(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handCityRespose(responseText, selectProvice.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handCountyRespose(responseText, selectCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeDialog();
                            if ("provice".equals(type)) {
                                queryProvices();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }

            }
        });
    }

    private void closeDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void queryCities() {
        titleText.setText(selectProvice.getProviceNane());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("proviceid = ?",
                String.valueOf(selectProvice.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCiytName());
            }
            adapt.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEAVE_CITY;
        } else {
            int proviceCode = selectProvice.getProviceCode();
            String address = "http://guolin.tech/api/china/" + proviceCode;
            queryFromServer(address, "city");
        }

    }

    private void queryCounties() {
        titleText.setText(selectCity.getCiytName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?",
                String.valueOf(selectCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapt.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEAVE_COUNTY;
        } else {
            int proviceCode = selectProvice.getProviceCode();
            int cityCode = selectCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + proviceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }
}