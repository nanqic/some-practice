package com.example.niceweather;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niceweather.base.BaseFragment;
import com.example.niceweather.bean.WeatherBean;
import com.example.niceweather.db.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.List;


public class CityWeatherFragment extends BaseFragment {
    // 定义控件
    TextView tempTv, conditonTv, windTv, cityTv, rangeTempTv,dateTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    String urlPerfix = "http://api.map.baidu.com/telematics/v3/weather?location=";
    String urlSuffix = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";
    String city;

    public CityWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        // 根据Activity传值 获取城市天气信息
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        String url = urlPerfix + city +urlSuffix;
        loadData(url);
        return view;
    }

    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        // 解析并展示数据
        parseShowData(result);
        //         更新数据
        int i = DBManager.updateInfoByCity(city, result);
        if (i<=0) {
//            更新数据库失败，说明没有这条城市信息，增加这个城市记录
            DBManager.addCityInfo(city,result);
        }
    }

    private void parseShowData(String result) {
        // 使用Gson解析数据
        WeatherBean weatherBean = new Gson().fromJson(result,WeatherBean.class);
        WeatherBean.ResultsBean resultsBean = weatherBean.getResults().get(0); // 获取全部信息
        dateTv.setText(weatherBean.getDate());
        cityTv.setText(resultsBean.getCurrentCity());

        WeatherBean.ResultsBean.WeatherDataBean todayDataBean = resultsBean.getWeather_data().get(0);//获取今天天气信息
        windTv.setText(todayDataBean.getWind());
        rangeTempTv.setText(todayDataBean.getTemperature());
        conditonTv.setText(todayDataBean.getWeather());
        //     获取实时天气温度情况
        String[] split = todayDataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        tempTv.setText(todayTemp);
//        设置显示的天气情况图片
        Picasso.with(getActivity()).load(todayDataBean.getDayPictureUrl()).into(dayIv);
        // 获取未来三天的天气 加载到layout中
        List<WeatherBean.ResultsBean.WeatherDataBean> futureList = resultsBean.getWeather_data();
        futureList.remove(0);
        for (int i=0; i<futureList.size(); i++){
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv = itemView.findViewById(R.id.item_center_iv);
            // 获取对应位置天气情况
            WeatherBean.ResultsBean.WeatherDataBean dataBean = futureList.get(i);
            idateTv.setText(dataBean.getDate());
            itemprangeTv.setText(dataBean.getTemperature());
            iconTv.setText(dataBean.getWeather());
            Picasso.with(getActivity()).load(dataBean.getDayPictureUrl()).into(iIv);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        super.onError(ex, isOnCallback);
    }

    private void initView(View view){
        // 初始化控件
        tempTv = view.findViewById(R.id.frag_tv_currrenttemp);
        conditonTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        cityTv = view.findViewById(R.id.frag_tv_city);
        dateTv = view.findViewById(R.id.frag_tv_date);
        rangeTempTv = view.findViewById(R.id.frag_tv_temprange);
        dayIv = view.findViewById(R.id.today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
    }
}
