package com.eqdd.common.utils;



import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.bean.MultiChooseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.DataProvider;
import chihane.jdaddressselector.ISelectAble;
import chihane.jdaddressselector.Selector;


/**
 * Created by lvzhihao on 17-4-25.
 */

public class MultiChooseUtil {

    private List<MultiChooseBean> areaBeanSecond;
    private List<MultiChooseBean> areaBeanThird;
    private List<MultiChooseBean> areaBeanFour;
    private List<MultiChooseBean> areaBeanFive;
    private Map<Integer, Integer> selectIds = new HashMap<>();
    OnMuiltiChooseSelectListener onMuiltiChooseSelectListener;

    public interface OnMuiltiChooseSelectListener {
        void onMuiltiChooseSelectListener(Map<Integer, Integer> selectIds, ArrayList<ISelectAble> selectAbles);
    }

    public BottomDialog getAreaDialog(BaseActivity activity, int deep, List<MultiChooseBean> areaBeanList, OnMuiltiChooseSelectListener onMuiltiChooseSelectListener) {
        this.onMuiltiChooseSelectListener = onMuiltiChooseSelectListener;
        this.areaBean = areaBeanList;
        Selector selector = new Selector(activity, deep);
        selector.setDataProvider((currentDeep, preId, receiver) -> {
                    //根据tab的深度和前一项选择的id，获取下一级菜单项
                    selectIds.put(currentDeep, preId);
                    System.out.println(currentDeep + "  " + preId);
                    getDatas(currentDeep, preId, receiver);
                }
        );
        selector.setSelectedListener((ArrayList<ISelectAble> selectAbles) -> {
                    onMuiltiChooseSelectListener.onMuiltiChooseSelectListener(selectIds, selectAbles);
                }
        );

        BottomDialog dialog = new BottomDialog(activity);
        dialog.init(activity, selector);
        return dialog;
    }

    List<MultiChooseBean> areaBean = new ArrayList<>();

    private void getDatas(int currentDeep, int preId, DataProvider.DataReceiver receiver) {

        ArrayList<ISelectAble> selectAbles = null;
        switch (currentDeep) {
            case 0:
                selectAbles = getAreaData(areaBean);
                receiver.send(selectAbles);
                break;
            case 1:
                areaBeanSecond = areaBean.get(preId).getNextDeep();
                selectAbles = getAreaData(areaBeanSecond);
                receiver.send(selectAbles);
                break;
            case 2:
                areaBeanThird = areaBeanSecond.get(preId).getNextDeep();
                selectAbles = getAreaData(areaBeanThird);
                receiver.send(selectAbles);
                break;
            case 3:
                areaBeanFour = areaBeanThird.get(preId).getNextDeep();
                selectAbles = getAreaData(areaBeanFour);
                receiver.send(selectAbles);
                break;
            case 4:
                areaBeanFive = areaBeanFour.get(preId).getNextDeep();
                selectAbles = getAreaData(areaBeanFive);
                receiver.send(selectAbles);
                break;
        }
    }

    private static ArrayList<ISelectAble> getAreaData(List<MultiChooseBean> areaBeans) {
        ArrayList<ISelectAble> data = new ArrayList<>();
        for (int j = 0; j < areaBeans.size(); j++) {
            final int finalJ = j;
            data.add(new ISelectAble() {
                @Override
                public String getName() {
                    return areaBeans.get(finalJ).getContent();
                }

                @Override
                public int getId() {
                    return finalJ;
                }

                @Override
                public Object getArg() {
                    return areaBeans.get(finalJ);
                }
            });
        }
        return data;
    }
}
