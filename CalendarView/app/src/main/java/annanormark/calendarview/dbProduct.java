package annanormark.calendarview;


import java.security.PublicKey;
import java.util.Date;

public class dbProduct {
        private int _id;
        private String _productname;
        private Long _date;

        //Constructors
        public dbProduct(){}

        public dbProduct(String productname, Long date1){
            this._productname = productname;
            this._date = date1;
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

}
