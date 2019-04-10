package ajou.com.skechip.Fragment.bean;

//import android.os.Parcel;
//import android.os.Parcelable;

/**
 * Created by zhouchaoyuan on 2017/1/14.
 */

public class Cell {

    private int status;// 과목
    private String channelName;//
    private String bookingName;//

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

//    public Cell(Parcel in) {
//        super();
//        readFromParcel(in);
//    }
//
//    public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>() {
//        public Cell createFromParcel(Parcel in) {
//            return new Cell(in);
//        }
//
//        public Cell[] newArray(int size) {
//            return new Cell[size];
//        }
//
//    };
//
//    public void readFromParcel(Parcel in) {
//        status = in.readInt();
//        channelName = in.readString();
//        bookingName = in.readString();
//    }
//    public int describeContents() {
//        return 0;
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(status);
//        dest.writeString(channelName);
//        dest.writeString(bookingName);
//    }
}
