package ajou.com.skechip.Fragment.bean;

//import android.os.Parcel;
//import android.os.Parcelable;

/**
 * Created by zhouchaoyuan on 2017/1/14.
 */

public class Cell {

    private int status;// 과목
    private String PlaceName;//
    private String SubjectName;//

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String PlaceName) {
        this.PlaceName = PlaceName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName =SubjectName;
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
