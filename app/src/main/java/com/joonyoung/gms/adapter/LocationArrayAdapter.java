package com.joonyoung.gms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joonyoung.gms.R;
import com.joonyoung.gms.model._Location;

import java.util.ArrayList;

/**
 *  Location을 적용하기 위해 Custom Array Adapter를 작성하였다.
 *  - 다음 출처의 글을 참고하여 작성하였다.
 * @from : http://blog.naver.com/PostView.nhn?blogId=newyorkinms&logNo=220760844250&parentCategoryNo=10&categoryNo=&viewDate=&isShowPopularPosts=true&from=search
 */
public class LocationArrayAdapter extends BaseAdapter {

    Context context;
    private ArrayList<_Location> items;

    public LocationArrayAdapter(Context context, ArrayList<_Location> locations){
        this.context = context;
        this.items = locations;
    }

    /** array계열 컨테이너의 사이즈(혹은 length)를 반환한다.
     * @return items.size()
     */
    @Override
    public int getCount() {
        /** 조건 연산자 구문이 사용되었다.
         *  - (조건)? 참이면 실행(혹은 반환)할 것 : 거짓이면 실행(혹은 반환)할 것
         *  - if - else 구문으로 작성하면 다음과 같다.
         *
         *    if(items==null){
         *      return 0;
         *    }else{
         *      return items.size();
         *    }
         */
        return (items==null)? 0 : items.size();
    }

    /** position(인덱스)에 해당하는 아이템을 반환한다.
     * @param position : 인덱스
     * @return items.get(index) :
     */
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_spinner_item, parent, false);
        }
        _Location location = items.get(position);
        TextView spinner_text = convertView.findViewById(R.id.spinner_text);
        spinner_text.setText(location.getCode());
        return convertView;
    }
}
