package com.mikaela.hhtimer.Database;

import java.security.PublicKey;
import java.util.Date;

public class dbProduct {
    private int _id;
    private String _productname;
    private Long _date;
    private int _work;
    private int _workperday;

    //Constructors
    public dbProduct(){}

    public dbProduct(String productname, Long date, int work, int workperday){
        this._productname = productname;
        this._date = date;
        this._work = work;
        this._workperday = workperday;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }

    public int get_id() {
        return _id;
    }

    public String get_productname() {
        return _productname;
    }

    public Long get_date(){
        return _date;
    }

    public int get_work(){
        return _work;
    }

    public int get_workperday() {
        return _workperday;
    }
}

