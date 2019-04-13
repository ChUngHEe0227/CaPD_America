package ajou.com.skechip.Fragment.bean;

public class MyData {
//    private String meetingtitle;
//    private String meetingmember;
//    private ArrayList<String> arrayList;
//    public MyData(String title, String member){
//        this.meetingtitle = title;
//        this.meetingmember = member;
//    }
//    public String get_meetingtitle(){
//        return this.meetingtitle;
//    }
//    public String get_meetingmember(){
//        return this.meetingmember;
//    }
    private String Title;
    private int Profile_img;
    public MyData(String text, int img){
        this.Title = text;
        this.Profile_img = img;
    }

    public String getTitle() {
        return this.Title;
    }
    public int getProfile_img(){
        return this.Profile_img;
    }
}
